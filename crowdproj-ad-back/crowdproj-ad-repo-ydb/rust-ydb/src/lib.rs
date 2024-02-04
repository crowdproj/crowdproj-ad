
use std::time::Duration;

use async_ffi::{FfiFuture, FutureExt};
use tokio::time::sleep;

#[no_mangle]
pub extern "C" fn work(arg: u32) -> FfiFuture<u32> {
    async move {
        let ret = do_some_io(arg).await;
        do_some_sleep(42).await;
        ret
    }
    .into_ffi()
}

async fn do_some_io(arg: u32) -> u32 {
    println!("res {arg}");
    arg + 2
}

async fn do_some_sleep(secs: u32) {
    let _ = sleep(Duration::from_secs(secs.into()));
}

#[cfg(test)]
mod tests {
    use tokio::task;
    // use super::*;

    #[test]
    fn it_works() {
        let local = task::LocalSet::new();
        local.enter();

        // let x: async_ffi::BorrowingFfiFuture<'_, u32> = work(10);
        // let poll = x.poll_unpin(cx);
    }
}
