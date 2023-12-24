use std::ffi::CStr;
use std::os::raw::c_char;
use gremlin_client::derive::{FromGMap, FromGValue};

#[repr(C)]
#[derive(Debug)]
pub struct CwpAdSt {
    pub id: *mut c_char,
    pub lock: *mut c_char,
    pub title: *mut c_char,
    pub description: *mut c_char,
    pub owner_id: *mut c_char,
    pub ad_type: *mut c_char,
    pub visibility: *mut c_char,
    pub product_id: *mut c_char,
}

#[repr(C)]
#[derive(Debug, PartialEq, FromGValue, FromGMap)]
pub struct CwpAd {
    pub id: Option<String>,
    pub lock: Option<String>,
    pub owner_id: Option<String>,
    pub title: Option<String>,
    pub description: Option<String>,
    pub ad_type: Option<String>,
    pub visibility: Option<String>,
    pub product_id: Option<String>,
}

impl CwpAd {
    pub(crate) fn from_c(c_ad: &CwpAdSt) -> CwpAd {
        return CwpAd {
            id: Some(unsafe { CStr::from_ptr(c_ad.id).to_string_lossy().into_owned() }),
            lock: Some(unsafe { CStr::from_ptr(c_ad.lock).to_string_lossy().into_owned() }),
            owner_id: Some(unsafe { CStr::from_ptr(c_ad.owner_id).to_string_lossy().into_owned() }),
            title: Some(unsafe { CStr::from_ptr(c_ad.title).to_string_lossy().into_owned() }),
            description: Some(unsafe { CStr::from_ptr(c_ad.description).to_string_lossy().into_owned() }),
            ad_type: Some(unsafe { CStr::from_ptr(c_ad.ad_type).to_string_lossy().into_owned() }),
            visibility: Some(unsafe { CStr::from_ptr(c_ad.visibility).to_string_lossy().into_owned() }),
            product_id: Some(unsafe { CStr::from_ptr(c_ad.product_id).to_string_lossy().into_owned() }),
        };
    }
}

// pub fn to_param<'a>(i: &Option<String>) -> &dyn ToGValue {
//     return i.
// }
