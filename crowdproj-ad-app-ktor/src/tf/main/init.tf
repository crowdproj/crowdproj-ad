provider "yandex" {
  token     = "<OAuth>"
  cloud_id  = "<cloud ID>"
  folder_id = "<folder ID>"
  zone      = "ru-central1-a"
}

resource "yandex_serverless_container" "test-container" {
   name               = "crowdproj-ad-ktor"
   memory             = 50
   service_account_id = "<service account ID>"
   image {
       url = "<Docker image URL>"
   }
}
