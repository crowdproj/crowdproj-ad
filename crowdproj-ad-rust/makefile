build: clean
	cargo build -r --target-dir build/target
	cp build/target/release/libcrowdproj_ad_rust.a build/dist
	touch build/cbindgen.toml
	cbindgen --config build/cbindgen.toml --crate crowdproj-ad-rust --output build/dist/crowdproj_ad_rust.h --lang c

clean:
	cargo clean
