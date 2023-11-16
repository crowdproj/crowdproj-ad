// #![no_std]
// #![no_main]
use std::ffi::{c_double, c_int, CStr};
use std::os::raw::c_char;

// A Rust struct mapping the C struct
#[repr(C)]
#[derive(Debug)]
pub struct CwpAdTestContStruct {
    pub c: char,
    pub ul: u64,
    pub c_string: *const c_char,
}

#[no_mangle]
pub extern "C" fn cwp_ad_testcont_mul(
    b: i32,
    x: i32
) -> i32 {
    b * x
}

// for C structs, need to convert each individual Rust member if necessary
#[no_mangle]
pub unsafe extern "C" fn cwp_ad_testcont_cstruct(c_struct: *mut CwpAdTestContStruct) {
    let rust_struct = &*c_struct;
    let s = CStr::from_ptr(rust_struct.c_string)
        .to_string_lossy()
        .into_owned();

    println!(
        "rust_cstruct() is called, values passed = <{} {} {}>",
        rust_struct.c, rust_struct.ul, s
    );
}

#[no_mangle]
pub static MY_VAR: c_double = 0.0;

pub const MY_CONST: c_int = 12;

#[cfg(test)]
mod tests {
    use crate::cwp_ad_testcont_mul;

    #[test]
    fn it_works() {
        unsafe { assert_eq!(cwp_ad_testcont_mul(2, 2), 4); }
    }
}
