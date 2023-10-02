use std::ffi::CStr;
use std::os::raw::{c_char, c_void};
use std::slice::from_raw_parts;
use std::str::from_utf8_unchecked;
use std::time::Duration;
use testcontainers::clients;
use testcontainers::core::WaitFor;
// use testcontainers::*;
// use testcontainers::core::WaitFor;
use testcontainers::images::generic::GenericImage;

#[no_mangle]
pub extern "C" fn some_test() -> u32 {
    345
}

#[no_mangle]
pub extern "C" fn start_container(img: *const u8, size: usize) -> u16 {
    let container = unsafe {
        // from_utf8_unchecked is sound because we checked in the constructor
        from_utf8_unchecked(from_raw_parts(img, size))
    };
    let x: u16 = if let Some((cnt, tag)) = container.split_once(":") {
        println!("cnt: {}", cnt);
        println!("tag: {}", tag);
        let docker = clients::Cli::default();

        let target_port = 80;

        // This server does not EXPOSE ports in its image.
        let generic_server = GenericImage::new(cnt, tag)
            .with_wait_for(WaitFor::Duration { length: std::time::Duration::new(20, 0) })
            // .with_wait_for(WaitFor::message_on_stdout("listening on 0.0.0.0:8080"))
            // Explicitly expose the port, which otherwise would not be available.
            .with_exposed_port(target_port)
            ;
        let node = docker.run(generic_server);
        let port = node.get_host_port_ipv4(target_port);
        println!("PORT: http://localhost:{port}/");
        port
    } else { 0u16 };
    x
    // let docker = clients::Cli::default();

    // let target_port = 8080;

    // This server does not EXPOSE ports in its image.
    // let generic_server = GenericImage::new(cnt, tag)
    //     .with_wait_for(WaitFor::message_on_stdout("listening on 0.0.0.0:8080"))
    // ;
    // Explicitly expose the port, which otherwise would not be available.
    // .with_exposed_port(target_port);
    //
    // let node = docker.run(generic_server);
    // #[allow(unused_variables)]
    // let port = node.get_host_port_ipv4(target_port);
    // println!("DDD done");
}

pub struct AveragedCollection {
    list: Vec<i32>,
    average: f64,
}

impl AveragedCollection {
    pub fn add(&mut self, value: i32) {
        self.list.push(value);
        self.update_average();
    }

    pub fn remove(&mut self) -> Option<i32> {
        let result = self.list.pop();
        match result {
            Some(value) => {
                self.update_average();
                Some(value)
            }
            None => None,
        }
    }

    pub fn average(&self) -> f64 {
        self.average
    }

    fn update_average(&mut self) {
        let total: i32 = self.list.iter().sum();
        self.average = total as f64 / self.list.len() as f64;
    }

    fn some_method(&self, arg: &str) {
        println!("ARG: {}, {}", arg, self.average);
    }
}

unsafe extern "C" fn trampoline(this: *mut c_void, string_argument: *const c_char) {
    let some_class = &*(this as *const AveragedCollection);

    let string_argument = CStr::from_ptr(string_argument).to_string_lossy();
    // AveragedCollection::some_method(&some_class, &string_argument)
    some_class.some_method(&string_argument);
}

#[cfg(test)]
mod tests {
    use libc::strdup;
    // Note this useful idiom: importing names from outer (for mod tests) scope.
    use super::*;

    #[test]
    fn test_start_container() {
        // let img = "nginx:latest";
        let img = unsafe { strdup(b"nginx:latest\0".as_ptr() as _) as *const u8 };
        let port = start_container(img, 12);
        let is_suc = reqwest::blocking::get(format!("http://127.0.0.1:{port}"))
            .unwrap()
            .status()
            .is_success();

        assert!(is_suc)
        // assert_eq!(add(1, 2), 3);
    }

    // #[test]
    // fn test_bad_add() {
    //     // This assert would fire and test will fail.
    //     // Please note, that private functions can be tested too!
    //     assert_eq!(bad_add(1, 2), 3);
    // }
}
