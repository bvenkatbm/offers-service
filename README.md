# Offers Service
Microservice to handle Offers data

## Getting Started
Please follow the guide to run the service

### Build
Execute the following command from the parent directory:
```
./gradlew build
```

### To run the service in IDE provide:
VM options
 ```
 -Dspring.profiles.active=dev 
 ```
The main entry point is 
```
com.kognitiv_.assignment.OffersServiceApplication 
```
class

### To run the service using Executable jar
```
java -jar -Dspring.profiles.active=dev offers-service-0.0.1-SNAPSHOT.jar
```

### To access swagger
[Swagger](http://localhost:8081/swagger-ui/index.html)

### To access H2 Database console
[H2 Console](http://localhost:8081/h2-console)
Username is `sa` and password is `password`