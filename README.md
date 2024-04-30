# Tails on Camp API 
The Tails on Camp Backend API serves as the foundation for managing a pet adoption platform 
inspired by the concept of connecting pets with loving homes, akin to a pet adoption service. 
Developed as part of the personal project "Tails on Camp", this REST API facilitates various functionalities crucial to the pet adoption process.

## Table of Contents

1. [Key Features](#key-features)
2. [API Documentation](#api-documentation)
3. [Usage](#usage)
4. [Next Steps](#next-steps)
5. [Technologies and Tools Used](#technologies-and-tools-used)
6. [Integration](#integration)

## Key Features
- **User Management**: Users can register, login, and manage their profiles, providing a personalized experience within the platform. 
- **Pet Registration**: Pet owners or shelters can register pets on the platform, providing essential information and images to attract potential adopters.
- **Shelter Registration**: Shelters can register on the platform to showcase the pets they have available for adoption and manage their profiles.
- **Adoption Management**: The API offers endpoints to manage the adoption process, enabling shelters to approve, put on-hold, cancel, reject, close, or complete adoption requests.
- **Pet Facts Integration**: Integration with a public third-party API provides users with access to random facts about dogs and cats.
- **Security**: Leveraging Spring Security and JWT (JSON Web Tokens), the API ensures secure authentication and authorization, safeguarding user data and interactions.                                            

## API Documentation
Explore the Tails on Camp API documentation using Swagger UI [here](http://3.27.202.209:8080/api/v1/swagger-ui/index.html).

## Usage
To start using the Tails on Camp API, you can explore the [API documentation](http://3.27.202.209:8080/api/v1/swagger-ui/index.html)
for detailed information about the available endpoints, request/response formats, and authentication requirements.

### User Registration
To register a new user, send a POST request to the `/register` endpoint with the user details. Here's an example request:
```json
{
  "email": "user@email.com",
  "username": "testusername",
  "password": "password123"
}
```
Upon successful registration, you'll receive a response with the user's information and authentication token.

### Login
To login with registered credentials, send a POST request to the `/login` endpoint. Here's an example request:
```json
{
  "username": "testusername",
  "password": "password123"
}
```

### Authentication
Before making requests to authenticated endpoints, you'll need to authenticate yourself using the provided JWT token. Follow these steps to authenticate in the Swagger UI:

1. Click on the "Authorize" button at the top-right corner of the Swagger UI interface.
2. In the "Value" field, paste the JWT token you received after successful login or registration.
3. Click on the "Authorize" button to apply the token.
4. You should now be authenticated and able to make requests to authenticated endpoints.

### Shelter Registration
To register a new shelter, use the `/shelters/register` endpoint with a POST request, providing the shelter's details. Here's an example request:
```json
{
  "shelterName": "Test Shelter",
  "lotBlockHouseBldgNo": "Address 1",
  "street": "Street Name",
  "subdivisionVillage": "",
  "barangay": "Barangay",
  "city": "City",
  "province": "Metro Manila",
  "country": "Philippines",
  "zipcode": "0000",
  "contactNumber": "0900000000",
  "email": "shelter@gmail.com",
  "website": ""
}
```

### Pet Registration
To register a new pet, use the `/pets/register` endpoint with a POST request, providing the pet's details. Here's an example request:
```json
{
  "name": "Test",
  "type": "Dog",
  "breed": "Pitbull",
  "age": 2,
  "gender": "Male",
  "size": "Medium",
  "description": "Black and White",
  "imageUrl": "",
  "availability": 1,
  "shelterId": 1
}
```

## Next Steps
- Implement logging to track API usage, diagnose issues, and monitor performance effectively
- Include unit tests, integration tests, and end-to-end tests
- Configure the project for cloning
- Set up a CI/CD pipeline to automate testing and deployment processes

## Technologies and Tools Used
- **Programming Language**: Java 17
- **Framework**: Spring Boot
- **Database**: MySQL
- **ORM**: JPA / Hibernate
- **Build Tool**: Maven
- **Authentication**: Spring Security + JWT
- **Containerization**: Docker
- **Cloud Services**: AWS EC2, AWS RDS
- **API Documentation**: OpenAPI Documentation + Swagger UI

## Integration
- **External API**: [cat-facts](https://github.com/alexwohlbruck/cat-facts) by alexwohlbruck [Alex Wohlbruck]
