# spring-security-oauth2-google-open-id

## Run app

### Requirements
* Java 8
* Gradle 3.5
* Docker
* Docker Compose

### How to do
* Build: `gradle buildDocker`
* Access [Google Credentials](https://console.developers.google.com/apis/credentials) and create your
* Configure `CLIENT_ID` with `CLIENT_ID=myid`
* Configure `CLIENT_SECRET` with `CLIENT_SECRET=mysecret`
* Run: `docker-compose up`
* Access: http://localhost:8081

## References
* [Spring Security and OpenID Connect](http://www.baeldung.com/spring-security-openid-connect)
