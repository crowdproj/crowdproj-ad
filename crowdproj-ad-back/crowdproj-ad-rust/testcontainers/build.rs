extern crate cbindgen;

use std::env;
use cbindgen::{Language, Style};

fn main() {
    let crate_dir = env::var("CARGO_MANIFEST_DIR").unwrap();
    let target_dir = format!("{crate_dir}/../target");
    // let target_dir = match env::var("CARGO_TARGET_DIR") {
    //     Ok(it) => it,
    //     Err(_) => return //format!("{crate_dir}/target")
    // };

    cbindgen::Builder::new()
        .with_crate(crate_dir)
        .with_language(Language::C)
        .with_style(Style::Type)
        // .with_style(Style::Tag)
        .with_include_guard("RS_TESTCONTAINERS_H")
        .generate()
        .expect("Unable to generate bindings")
        .write_to_file(format!("{target_dir}/includes/cwp-ad-testcontainers-rust.h"));
}
