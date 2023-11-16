use std::fs::File;
use std::io::Write;

pub fn prn(msg: String) {
    let mut file = File::options()
        .append(true)
        // .create_new(true)
        .open("hard-log.log")
        .expect("FAILED OPENING hard-log.log");
    file.write_all(msg.as_bytes()).expect("FILE PRINT FAILURE");
    file.try_clone().expect("FILE CLOSE FAILURE");
}

#[cfg(test)]
mod tests {
    use crate::debug::prn;

    #[test]
    fn print() {
        prn(String::from("DONE PRN\n"));
    }
}
