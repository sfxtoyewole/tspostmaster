PostMaster Service
---
The PostMaster Service is a Spring Boot application that provides blog capabilities via API endpoints. It allows for basic blog operations such as authentication, posting and commenting 

Prerequisites
Before running the PostMaster Service, ensure you have the following installed:

1. Java Development Kit (JDK) 11
2. Apache Maven

# Getting Started
Follow these steps to set up and run the PostMaster Service:
- Clone the repository:
  ``git clone https://github.com/sfxtoyewole/tspostmaster.git``
  Build the project:

    - ```cd postmaster-service```
- ### For Maven:
  ```mvn clean install```

- ### Run Application:
  ```mvn spring-boot:run```

Access the PostMaster Service:
Once the application is running, you can access the PostMaster Service on
```http://localhost:8080```.

# Testing
- To run the tests for the PostMaster Service, use the following Maven command:
- ```mvn test```

Configuration
---
The PostMaster Service can be configured through the `application.properties` file. Customize the configuration based on your requirements, such as database connection details and API endpoint mappings.

- `database-config`
    - Postgres DB is the database used. To configure Postgres DB for the app:
    - `spring.data.mongodb.uri` add this to application.properties and update the uri with a Postgres DB uri

> Usage
The PostMaster Service provides the following endpoints:

- `/post`: post related activities such as delete creation and getting posts.
- `/comment`: comment related activities such as getting comments for a particular post and commenting 
- `/access`: authentication and signing into the application 

Swagger Documentation
---
The PostMaster Service provides Swagger documentation for easy exploration of the available API endpoints and their parameters. Follow the steps below to access the Swagger UI:

1. Make sure the PostMaster Service is running locally.

2. Open your web browser and navigate to the following URL:

   ```http://localhost:8080/swagger-ui.html```

3. The Swagger UI page will open, allowing you to browse and interact with the API documentation.

### Contact
For any questions or support, please contact `taiwoOyewole329@gmail.com`.

