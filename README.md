# Bookingham Palace - book eshop

Set of services for the book eshop *Bookingham Palace*, which were developed as a project for the *PV217 Service Oriented Architecture* course.

## Project Description
This project implements a book eshop named *Bookingham Palace* (based in London, but shipping worldwide!) as a microservices application. The application consist of four microservices:

* **Basket service:** responsible for managing contents of baskets and for basket checkouts
* **Catalog service:** responsible for managing books sold in the eshop and authors of these books
* **Order service:** responsible for managing customer orders
* **Customer service:** responsible for managing customer accounts

## Scenario of Usage

1. A visitor of the eshop creates a customer account by using the endpoint `/customer/create`.
2. The customer gets all authors whose books are in the catalog by using the `/author` endpoint.
3. By using the `/book` endpoint, the customer either lists all books in the catalog or all books of an author if the author is specified.
4. The customer adds or removes items from his basket by using `/customer/{customerId}/basket/add` and `/customer/{customerId}/basket/remove/{itemId}`.
5. When the customer added all the desired books to his/her basket, (s)he does a basket checkout by using the `/customer/{customerId}/basket/checkout`. This creates order and clears the customer's basket.

## Why Microservice Architecture is Appropriate

We think that a microservice architecture is appropriate for this project because it brings several advantages (listed below) which can significantly improve the customer experience, which we believe to be the most important factor for an online bookstore. In the case of development, while the use of microservices can introduce some challenges, we think that it can bring some benefits as well.

## Benefits of Microservices

* **Fault isolation** - If a microservice fails, this failure is isolated, and most functionality is still available to our customers.
* **Readability** - The application source code is split into smaller parts, making it more readable and easier to maintain.
* **Extendability** - More microservices (such as a recommendation service) can be easily added and integrated with existing ones.
* **Development autonomy** - The application can easily be developed concurrently by multiple developers, so we can bring new features to customers faster.
* **Scalability** - We can scale overloaded microservices when needed, for example the order service before Christmas when people order more books.

## Drawbacks of Microservices

* **More complexity** - Microservice architecture is more complex, which slows down the development process.
* **Many APIs** - Each microservice exposes its own API, which can become difficult to track and document.
* **Difficult integration testing** - Testing of interaction between the services brings some challenges.

## Building and Running

Build the project with:
```
mvn clean install
```
After the project is built, use the following commands to run the application:
```
docker-compose build

docker-compose up
```
You can stop the application (kill the containers and remove them) with:
```
docker-compose down
```

## Running in Dev Mode

Start the supporting services:
```
docker-compose -f docker-compose-dev.yml up
```
Start all individual microservices:
```
./mvnw clean compile quarkus:dev
```
Stop the supporting services:
```
docker-compose -f docker-compose-dev.yml down
```

## OpenAPI

* [Basket Service OpenAPI](http://localhost:8082/q/openapi?format=json)
* [Catalog Service OpenAPI](http://localhost:8083/q/openapi?format=json)
* [Order Service OpenAPI](http://localhost:8084/q/openapi?format=json)
* [Customer Service OpenAPI](http://localhost:8085/q/openapi?format=json)

## Swagger UI

* [Basket Service Swagger UI](http://localhost:8082/q/swagger-ui/)
* [Catalog Service Swagger UI](http://localhost:8083/q/swagger-ui/)
* [Order Service Swagger UI](http://localhost:8084/q/swagger-ui/)
* [Customer Service Swagger UI](http://localhost:8085/q/swagger-ui/)

## Authors

* Kristián Tkáčik
* Radoslav Chudovský
* Jan Gavlik