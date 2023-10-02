use testcontainers::clients;
// use testcontainers::*
use testcontainers::core::WaitFor;
use testcontainers::images::generic::GenericImage;

#[no_mangle]
pub extern "C" fn some_test(
) -> u32 {
    345
}

#[no_mangle]
pub extern "C" fn start_container() {
    let docker = clients::Cli::default();

    let target_port = 8080;

    // This server does not EXPOSE ports in its image.
    let generic_server = GenericImage::new("no_expose_port", "latest")
        .with_wait_for(WaitFor::message_on_stdout("listening on 0.0.0.0:8080"))
        // Explicitly expose the port, which otherwise would not be available.
        .with_exposed_port(target_port);

    let node = docker.run(generic_server);
    #[allow(unused_variables)]
    let port = node.get_host_port_ipv4(target_port);
    // reqwest::blocking::get(format!("http://127.0.0.1:{port}"))
    //     .unwrap()
    //     .status()
    //     .is_success()
}
