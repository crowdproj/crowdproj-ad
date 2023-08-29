package main

import "C"
import (
	// общие импорты из стандартной библиотеки
	"context"
	//_ "fmt"
	//_ "log"
	//_ "math"
	//_ "path"
	//_ "sync"

	//"github.com/ydb-platform/ydb-go-yc" // для работы с YDB в Яндекс Облаке
	// импорты пакетов ydb-go-sdk
	//"github.com/ydb-platform/ydb-go-sdk-auth-environ" // для аутентификации с использованием перменных окружения
	"github.com/ydb-platform/ydb-go-sdk/v3"
	//"github.com/ydb-platform/ydb-go-sdk/v3/table"              // для работы с table-сервисом
	//"github.com/ydb-platform/ydb-go-sdk/v3/table/options"      // для работы с table-сервисом
	//"github.com/ydb-platform/ydb-go-sdk/v3/table/result"       // для работы с table-сервисом
	//"github.com/ydb-platform/ydb-go-sdk/v3/table/result/named" // для работы с table-сервисом
	//"github.com/ydb-platform/ydb-go-sdk/v3/table/types"        // для работы с типами YDB и значениями
)

var db *ydb.Driver
var err error
var ctx = context.Background()

//export Init
func Init(dsn *C.char, token *C.char) (*C.char, *C.char) {
	db, err = ydb.Open(
		ctx,
		C.GoString(dsn),
		//ydb.WithAccessTokenCredentials(token)
		ydb.WithAnonymousCredentials(),
	)
	x := err.Error()
	errMsg := C.CString(err.Error())
	errCmp := C.CString("test")
	return errMsg, errCmp
}

//export Close
func Close() {
	_ = db.Close(ctx)
}

func main() {}
