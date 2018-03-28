# myRetail

myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores
across the east coast. myRetail wants to make its internal data available to any number
of client devices, from myRetail.com to native mobile apps.

### myRetail RESTful service

A RESTful service that can retrieve product and price details by ID.

The goal is to create an end-to-end Proof-of-Concept for a products API,
which will aggregate product data from multiple sources and return it as JSON to the
caller.

### Built With

| Technology    | Version       |
| ------------- | ------------- |
| Java          | 8  |
| SpringBoot  | 2.0.0  |
|Mongodb |3.6|
|Mockito|2.15|
|Gradle|4.5.1  
|Swagger|1.5.14

### Getting Started

1. Install [MongoDB](https://docs.mongodb.com/manual/installation/) in your system.
2. Install [Gradle](https://gradle.org/gradle-download/)
3. Run MongoDB - Run 'mongod.exe' in order to start Mongodb 
4. Clone the code from git repository -> [myRetail](https://github.com/arunpsg/myRetail.git)
5. Run the following command to start the Spring boot application
`./gradlew bootRun`
6. Access the visually rendered documentation for the available APIs through
[Swagger-ui](http://localhost:8080/swagger-ui.html) 
7. There are 2 roles configured and available --> USER & ADMIN
8. Please make sure to use ADMIN credentials to access Swagger Documentation. 
9. Additional Role based access restrictions are as follows,
	* **USER** --> Can access GET method for (/products/v1/{id})
	* **ADMIN** --> Can access both GET & PUT methods for (/products/v1/{id})
	
### Testing

The testcases are present in the following folders  
 'src\test\java\com\arun\myRetail\controller'
 'src\test\java\com\arun\myRetail\service'

### Author

* **Arun Kumar Rajamanickam** - [arunpsg](https://github.com/arunpsg)
