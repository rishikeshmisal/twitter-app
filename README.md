# microservice-twitter-app

A microservice to provide APIs to simulate Twitter like application
With the ability to register users, tweet, like and follow fellow users


### Pe-requisites
 - Java 1.8
 - Maven 3+

You can build the application using 
```
mvn clean install
```

- There are two ways to run the project
     - To create an embedded postgres server within the application if a
     configured postgres database server is not readily available.
        ```
        java -jar -Dspring.profiles.active=embedded target/twitter-app-0.0.1-SNAPSHOT.jar 
        ```
     - The other way is to have a dedicated postgres database outside of the application.
     You will require the following environment variables to be configured with the appropriate values.
        ```
         export DB_USERNAME=postgres
         export DB_PASSWORD=postgres
         export DB_CONN_URL=jdbc:postgresql://localhost:5432/postgres
        ```
        Subsequently run the following command
        ```
        java -jar -Dspring.profiles.active=cloud target/twitter-app-0.0.1-SNAPSHOT.jar 
        ```
- Each API endpoint is protected by OAuth2 token. To access each API a mandatory
header with name `Authorization` and value as `Bearer {token}`

- Apart from `/api/v1/register` endpoint all the other application endpoints would require a user token
and `api/v1/register` would require a client_credentials grant token.

- The project also contains a Postman Collection with all the endpoints.

- To get client_credentials token
    ```
    client_id=uq4su8o61nbws5co
    client_secret=JBIv1TgqlSk2BfeFozvsXqBR
    
    ```
- The above client_id will help you fetch a valid user token with expiration time set by
default to 2 hours.

- Swagger-2.0 is also enabled and can be access from `/swagger-ui.html` request path.