install:
	rustup target add aarch64-unknown-linux-gnu
	rustup target add x86_64-unknown-linux-gnu

build: clean compile generate-header

compile:
	cargo build -r --target-dir ${PROJECT_BUILD_DIR} --target=${PLATFORM}
#	mkdir -p ${PROJECT_DIST_DIR}
#	cp ${PROJECT_BUILD_DIR}/${PLATFORM}/release/libktestcontainers.a ${PROJECT_DIST_DIR}/libktestcontainers_${PLATFORM}.a

generate-header:
	touch ${PROJECT_BUILD_DIR}/cbindgen.toml
	cbindgen --config ${PROJECT_BUILD_DIR}/cbindgen.toml --crate ktestcontainers --output lib/ktestcontainers.h --lang c
 #${PROJECT_BUILD_DIR}/${PLATFORM}/release/ktestcontainers.h --lang c

clean:
	cargo clean
