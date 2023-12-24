use std::alloc::{dealloc, Layout};
use std::ffi::CStr;
use std::os::raw::c_char;
use std::ptr;
use gremlin_client::{ConnectionOptions, GremlinClient};

#[repr(C)]
#[derive(Debug)]
pub struct RepoConnConf {
    pub host: *const c_char,
    pub port: u16,
    pub user: *const c_char,
    pub pass: *const c_char,
}

#[repr(C)]
#[derive(Debug)]
pub struct CwpAdGremConnection {
    pub conn: *mut GremlinClient,
}

#[no_mangle]
pub unsafe extern "C" fn cwp_ad_repo_conn(conf: *mut RepoConnConf) -> CwpAdGremConnection {
    let conf_struct = unsafe { &*conf };

    let hosts = CStr::from_ptr(conf_struct.host)
        .to_string_lossy()
        .into_owned();
    println!("HOST: {hosts}");

    let port = conf_struct.port;
    println!("PORT: {port}");
    let user = CStr::from_ptr(conf_struct.user)
        .to_string_lossy()
        .into_owned();
    println!("USER: {user}");
    let pass = CStr::from_ptr(conf_struct.pass)
        .to_string_lossy()
        .into_owned();
    println!("PASS: {pass}");

    let connection = GremlinClient::connect(
        ConnectionOptions::builder()
            .host(&hosts)
            .port(port)
            .credentials(&user, &pass)
            .build()
    ).unwrap();
    println!("CONNECTION ESTABLISHED");
    let con_new = Box::new(connection);
    return CwpAdGremConnection {
        conn: Box::into_raw(con_new)
    };
}

#[no_mangle]
pub unsafe extern "C" fn cwp_ad_repo_disconnect(conn: CwpAdGremConnection) {
    println!("Disconnect is called");
    let mut gc = unsafe { &mut *(conn.conn) };
    match gc.close_session() {
        Ok(_) => { println!("Successfully closed connection") }
        Err(err) => { println!("Error closing connection: {err}") }
    };
    unsafe {
        println!("dropping");
        ptr::drop_in_place(conn.conn);
        println!("deallocating");
        dealloc(conn.conn as *mut u8, Layout::new::<CwpAdGremConnection>());
        println!("all done");
    }
    println!("exiting")
}

// #[cfg(test)]
// mod tests {
//     use crate::connection::cwp_ad_repo_conn;
//
//     #[test]
//     fn it_works() {
//         cwp_ad_repo_conn()
//         assert_eq!(repo_grem_mul(2, 2), 4);
//     }
// }
