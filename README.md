# JustEat challenge

This is the implementation of the Just Eat challenge, described in the Employee API - Coding Challenge - Career.pdf

## Content

- [Tech stack](#tech-stack)
- [Future improvement](#future-improvements)
- [Building the app](#building-the-app)
- [Running the app](#running-the-app)
- [Testing the app](#testing-the-app)

## Tech stack

The tech stack used to implement this challenge is:

- Java 17
- Springboot 3.2.1
- Kafka (Embedded)
- Mongo DB
- OpenAPI 3(Swagger 3)
- Spring security oauth2 


## Future improvements

- Proper error handling
- Add more test cases for better test coverage
- Add a caching layer


## Building the app
Build in Intellij or use one of the following command:
```
mvn package

docker build -t employee-management-challenge:1.0.0 .
```
## Running the app

For running the application you need MongoDB. Please provide the address in application.properties. 
Or you can just run the docker-compose command, and it will take care of that. 
To use docker-compose, please first build the app using maven command above, because the docker-compose reads the jar in the target folder. 

Run with one of the following command:
```
java -jar EmployeeManagement-0.0.1-SNAPSHOT.jar

docker-compose up -d 

```

## Testing the app

After running the app, open http://localhost:8080/swagger-ui/index.html in the browser.

For accessing update and delete employee endpoints, authentication is added.
To access those endpoints you need to get an access token using the following command (or use Postman):

```
curl -X POST client1:secret1@localhost:8080/oauth2/token -d "grant_type=client_credentials" -d "scope=read"
```

Sample access token response is like this:
```
{
"access_token": "eyJraWQiOiJiMjZkYTIzYy03OWE0LTRhYjUtODZlOS1jNWVhM2Y1NmIwZTIiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJjbGllbnQxIiwiYXVkIjoiY2xpZW50MSIsIm5iZiI6MTcwMzg2MDQ4Miwic2NvcGUiOlsid3JpdGUiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwIiwiZXhwIjoxNzAzODYwNzgyLCJpYXQiOjE3MDM4NjA0ODIsImp0aSI6IjRmODBiM2I0LThlZTItNDkxMi04MTY3LWZlOWE1N2M3NTM0MSJ9.0GmcFD3rATkwEliR_NsgrOOMxVL4rDtvgjYAsd3FDmIR0YRrWJcKxvCff8XryhRSU6JdxkUBqzrpbcVu15UtglNHnub1RPg5QkE0eYBakakvG87hZ39oKPMwxp3oSe0LGdni_PKq4A9yvW3b9JjsA3lu-L4TKAUK62eCdw7VrnkiR-90HXeKJqgZsL-s_TwXolzyrj0xdtaoHjDPH2t3gw19lNisOaEmbSufQglSJhuvOaacDSaGSDlM_FjDR7g8lqLZsp-XMJrsnjrnvWw3asRNHEU1_LiWWtaISXsLQXsHXpneBY-F7XMND_v5142XshOnvIxDhqz_O6lqJ7C6jw",
"scope": "write",
"token_type": "Bearer",
"expires_in": 299
}

```
Please send the access token in the Authorization: Bearer token header for put and delete endpoints.