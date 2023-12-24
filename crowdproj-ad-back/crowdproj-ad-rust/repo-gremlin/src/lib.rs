use gremlin_client::ToGValue;
use crate::ad_struct::{CwpAd, CwpAdSt};
use crate::connection::CwpAdGremConnection;
use crate::constants::*;

mod connection;
mod ad_struct;
mod constants;
mod debug;

#[no_mangle]
pub extern "C" fn repo_grem_mul(
    b: i32,
    x: i32,
) -> i32 {
    b * x
}

impl CwpAdGremConnection {
    #[allow(unused_variables)]
    #[no_mangle]
    pub unsafe extern "C" fn cwp_ad_grem_save(self, data: *const CwpAdSt) {
        println!("RS SAVE: resolving conn");
        let mut gc = unsafe { &mut *(self.conn) };
        let c_ad: &CwpAdSt = unsafe { &*data };
        let ad: CwpAd = CwpAd::from_c(c_ad);

        println!("RS SAVE RECEIVED AD: {:?}", ad);
        let request = format! {
                "g.addV('{LABEL_AD}')
                    .property(single,'{FIELD_LOCK}', {FIELD_LOCK})
                    .property(single,'{FIELD_OWNER_ID}', {FIELD_OWNER_ID})
                    .property(single,'{FIELD_TITLE}', {FIELD_TITLE})
                    .property(single,'{FIELD_DESCRIPTION}', {FIELD_DESCRIPTION})
                    .property(single,'{FIELD_AD_TYPE}', {FIELD_AD_TYPE})
                    .property(single,'{FIELD_VISIBILITY}', {FIELD_VISIBILITY})
                    .property(single,'{FIELD_PRODUCT_ID}', {FIELD_PRODUCT_ID})
                    .project(
                        '{FIELD_ID}',
                        '{FIELD_LOCK}',
                        '{FIELD_OWNER_ID}',
                        '{FIELD_TITLE}',
                        '{FIELD_DESCRIPTION}',
                        '{FIELD_AD_TYPE}',
                        '{FIELD_VISIBILITY}',
                        '{FIELD_PRODUCT_ID}'
                    )
                    .by(id)
                    .by('{FIELD_LOCK}')
                    .by('{FIELD_OWNER_ID}')
                    .by('{FIELD_TITLE}')
                    .by('{FIELD_DESCRIPTION}')
                    .by('{FIELD_AD_TYPE}')
                    .by('{FIELD_VISIBILITY}')
                    .by('{FIELD_PRODUCT_ID}')
                "
            };
        println!("REQUEST: {:?}", request);
        let params: [(&str, &dyn ToGValue);7] = [
            (FIELD_LOCK, &ad.lock.unwrap_or_default()),
            (FIELD_OWNER_ID, &ad.owner_id.unwrap_or_default()),
            (FIELD_TITLE, &ad.title.unwrap_or_default()),
            (FIELD_DESCRIPTION, &ad.description.unwrap_or_default()),
            (FIELD_AD_TYPE, &ad.ad_type.unwrap_or_default()),
            (FIELD_VISIBILITY, &ad.visibility.unwrap_or_default()),
            (FIELD_PRODUCT_ID, &ad.product_id.unwrap_or_default()),
        ];
        // println!("PARAMS: {:?}", &params);

        let results = gc.execute(request, &params)
            .expect("HERE 1")
            // .map(|f| {
            //     println!("FF {:?}", f);
            //     f
            // })
            .filter_map(Result::ok)
            .map(|f| { println!("MAP: {:?}", f); CwpAd::try_from(f) })
            .next()
            // .collect::<Result<Vec<CwpAd>, _>>()
            .expect("HERE 2")
            ;
        match results {
            Ok(res) => { println!("OK RESULT: {:?}", res) }
            Err(err) => { println!("ER RESULT: {:?}", err) }
        }
        // println!("SAVED DATA: {:?}", results);
    }
    // #[no_mangle]
    // pub unsafe extern "C" fn cwp_ad_grem_create(self, data: *const CwpAdSt) {
    //
    // }
}

// #[no_mangle]
// pub static CWP_AD_REPO_GREM_MY_VAR: c_double = 0.0;
//
// pub const CWP_AD_REPO_GREM_MY_CONST: c_int = 12;
//
// #[cfg(test)]
// mod tests {
//     use crate::{repo_grem_mul};
//
//     #[test]
//     fn it_works() {
//         assert_eq!(repo_grem_mul(2, 2), 4);
//     }
// }
