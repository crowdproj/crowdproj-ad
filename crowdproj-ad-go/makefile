build: clean
	go mod init com.crowdptoj.ads
	go mod tidy
	env GOOS=${GOOS} GOARCH=${GOARCH} CGO_ENABLED=1 \
#		go build -buildmode=c-shared -o ${PROJECT_BUILD_DIR}/${GOOS}-${GOARCH}/${PROJECT_NAME}.so
		go build -buildmode=c-archive -o ${PROJECT_BUILD_DIR}/${GOOS}-${GOARCH}/${PROJECT_NAME}

clean:
	rm -f *.mod
	go clean -modcache
