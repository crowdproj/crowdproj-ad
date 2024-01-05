extern crate cbindgen;

use cbindgen::Config;
use std::env;

fn main() {
    let crate_dir = env::var("CARGO_MANIFEST_DIR").unwrap();
    let target_dir = format!("{crate_dir}/target");
    println!("cargo:warning=target dir: {}", target_dir);

    let config = Config::from_file("cbindgen.toml").unwrap();
    let output_file = format!("{target_dir}/includes/rs-ydb-conn-lib.h");

    cbindgen::generate_with_config(&crate_dir, config)
        .unwrap()
        .write_to_file(&output_file);

        // cbindgen::Builder::new()
    //     .with_crate(crate_dir)
    //     .with_config(config)
    //     .rename_item("FfiFuture", "FfiFutureType")
    //     .generate()
    //     .unwrap()
    //     .write_to_file(&output_file);

    // let x = cbindgen::Builder::new()
    //     .with_crate(crate_dir)
    //     .with_language(Language::C)
    //     // .with_style(Style::Type)
    //     .with_style(Style::Tag)
    //     .with_include_guard("RS_YDB_CONN_LIB_H")
    //     // .with_crate("async-ffi")
    //     // .with_include("xx.h")
    //     // .exclude_item("FfiFuture<u32>")
    //     // .with_no_includes()
    //     // .with_only_target_dependencies(true)
    //     // .rename_item("Client", "void")
    //     // .rename_item("Waker", "void")
    //     // .rename_item("Context", "void")
    //     .with_parse_deps(true)
    //     // .with_include("async-ffi")
    //     .with_parse_extra_bindings(&vec!["async-ffi"])
    //     // .rename_item("FfiFuture<uint32_t>", "void *")
    //     .generate()
    //     .expect("Unable to generate bindings")
    //     .write_to_file(format!("{target_dir}/includes/rs-ydb-conn-lib.h"));
    // println!("cargo:warning=DONE {}", x);
}
