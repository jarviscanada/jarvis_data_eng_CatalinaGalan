# Spring Boot Trading App

## Overview

The Trading App is a PoC (proof of concept) project for a trading platform. The MVP is a REST API 
with microservices architecture and Springboot framework. 
The project focuses on features rather than performance and security.

The application allows users to manage clients (traders) and their accounts, monitor portfolio 
performance and trade securities. It uses real world quote information from the Alpha Vantage API. 

## Technologies

* Java
* Spring Boot
* Maven
* PostgreSQL
* Docker
* Git / GitHub

## Quickstart

#### Prerequisites:  
* Docker 17 or higher.

### To run the app from docker image:

1. Verify docker version is 17 or higher:  
```bash
docker -v
```
2. Create a network to run both containers:  
```bash # create network
docker create network trading-net
# verify
docker network ls
```
3. Pull database image from docker: 
```bash
docker pull catagalan/trading-psql
```
4. Pull app image from docker:  
```bash
docker pull catagalan/trading-app
```
5. Verify the images:  
```bash
docker images -f reference=catagalan/trading-psql   
docker images -f reference=catagalan/trading-app
```
6. Get a free API key from Alpha Vantage:  

   https://www.alphavantage.co/support/#api-key 


7. Set env variables for: 
```bash
export POSTGRES_USER=<your_postgres_user>  
export POSTGRES_PASSWORD=<your_postgres_password>  
export ALPHA_VANTAGE_API_KEY=<paste_alpha_vantage_api_key>  
```
8. Create and start database container:  
```bash
docker run --rm -d --name trading-psql-dev --network trading-net -p 5432:5432 \
-e POSTGRES_USER -e POSTGRES_PASSWORD catagalan/trading-psql 
```
9. Create and start app container:  
```bash
docker run --rm -d --name trading-app-dev --network trading-net -p 8080:8080 \
   -e POSTGRES_USER -e POSTGRES_PASSWORD -e ALPHA_VANTAGE_API_KEY catagalan/trading-psql 
```   
10. Verify running containers:  
```bash
docker container ps
```

### Access the app at:  
http://localhost:8080/swagger-ui/index.html#/


![Trading_app_SwaggerUI_screenshot]()

#### Disclaimer:
Due to the nature of the free API key we are using for this application, we need to take into
consideration that there is a maximum of 25 API calls allowed per day, therefore some precautions
need to be taken for the correct functioning of the application, such as avoiding updating the
Daily List multiple times a day, for example.


_NOTE: There are some issues when manually editing and passing a json object in the Swagger UI 
to create a new trader, execute a market order or update a quote (for testing purposes 
only). For correct functioning it is necessary to add a wrapper for each of these objects. 
Detailed instructions are found in the description of each method._  

![create_trader_and_account_example]()

### To stop the app:
1. Stop docker containers:  
```bash
docker stop trading-psql-dev trading-app-dev
```
2. Verify:
```bash
docker container ls
```

# Implementation
## Architecture

![trading_app_architecture_diagram]()

The Trading App is built in Spring Boot, following the Spring Boot 3-tiered architecture. This modular 
architecture pattern organizes the app into 3 layers: a **Controller Layer** which handles HTTP 
Requests, a **Service Layer** which handles the business logic, and a **Repository/DAO Layer** which 
persists and retrieves data from a PostgreSQL database.

* #### Spring Boot:
   Spring Boot is a framework for building applications and microservices using Java. It is an 
extension of the Spring framework.
   The Spring Boot framework provides an embedded Tomcat web servlet, simplifying deployment by reducing
configuration and improving the app's portability. It also provides _IoC_ (Inversion of Control) and
supports _Dependency Injection_, handling the instantiation, configuration and lifecycle of objects
and allowing for rich interdependencies between these objects. Configuration metadata for Trading App
is provided via annotations and a simple application.properties file.
* #### Controller Layer: 
  Also called the Presentation Layer, this layer consists of all the app's controllers, which define 
the endpoints of the REST API and handle HTTP requests. It receives the requests and communicates 
with the Service Layer to retrieve or manipulate data.
Because this is a REST API application, The Controller Layer returns JSON to the client (instead of HTML.) 
* #### Service Layer:
   Also called the Business Layer, this layer handles all the business logic of the app, communicating 
with the Repository Layer to access data. It is the intermediary between Controllers and Repositories, 
thus ensuring separation of concerns.
* #### Repository Layer:
   Also named Data Access layer, this is the layer that interacts with the PostgreSQL database (in 
this case) to persist and retrieve information upon the request from the Service Layer. In the Trading 
App it consists mainly of interfaces that extend the JpaRepository interface.
* #### PSQL:
   PostgreSQL is an open-source relational database used in this application via a docker container.
* #### Alpha Vantage API:
   Alpha Vantage is a financial market data provider. The Trading App uses its API for retrieving 
real world Quotes information.

## REST API Usage

### Swagger UI
Swagger is a free based tool that allows users to interact with REST APIs. It provides visual 
representation of the API and its documentation. The Trading App can be accessed via Swagger, which 
displays all the available end-points and allows users to try out API calls on the browser. 

### Quote Controller
The Quote Controller is responsible for providing the user Quote data from the Alpha Vantage API, 
and persisting and updating this data to the Trading App's database. Alpha Quote data is fetched and 
transformed into Quote entities, which are modeled in the Trading App and reflected in the database 
Quote table.
* #### GET /quote/DailyList
   Show all tickers available to trade in this platform.
* #### PUT /quote/alphaVantageMarketData
   Fetches quotes from Alpha Vantage and updates Quote table in the database.
* #### GET /quote/alphaVantage/ticker/{ticker}
   Fetch and show a single quote from Alpha Vantage.
* #### POST /quote/ticker/{ticker}
   Create a new Quote in the database from Alpha Vantage data and add a new ticker to the Daily List.
* #### PUT /quote/
   Update a Quote from the Quote table manually (for debugging and testing purposes only.)

### TraderAccount Controller
The Trader Controller manages creation of Traders and their accounts (each trader has only one account 
and are created simultaneously.) This controller also allows for funds to be deposited or withdrawn 
from a Trader's Account.
* #### POST /trader/*
   There are two end-points that allow creating a new Trader and Account: one uses an HTTP body request, 
and the other uses a URL. 
* #### GET /trader/traderId/{traderId}
   Shows Trader and its Account information.
* #### DELETE /trader/traderId/{traderId}
   Deletes a Trader and its Account. All funds must be withdrawn to allow deletion.
* #### PUT /trader/traderId/{traderId}/deposit/amount/{amount}
   Adds funds to a Trader's Account.
* #### PUT /trader/traderId/{traderId}/withdraw/amount/{amount}
   Withdraws funds from a Trader's Account.

### Order Controller
The Order Controller allows the execution of Market Orders to buy or sell stock. It connects to the 
Order Service which in turn has access to TraderAccount data and Quote data, in order to create 
Security Orders.
* #### POST /marketOrder
   Buy or sell stock by executing a Market Order (HTTP body request.)

### Dashboard Controller
The Dashboard Controller displays information about a Trader and its portfolio. To do this it calls 
to the Dashboard Service to create View only objects displaying a Trader and Account profile, or a 
portfolio consising of list of all the Trader's positions.
* #### GET /dashboard/profile/trader/traderId/{traderId}
   Show Trader and its Account information by trader id.
* #### GET /dashboard/portfolio/trader/traderId/{traderId}
   Show a list of all the trader positions by trader id.   

## Test

The Trading App was tested for each component in the Service and Repository layers. The Controller 
layer was tested manually using Postman and Swagger.  
Integration Tests and Unit Tests were performed using JUnit5. Mockito was used for Unit Test isolation 
providing mock instances of dependencies.

## Deployment

![docker_diagram]()

The Trading App was deployed using docker. The app consists of two docker containers connected via 
a Network. 

Both images are defined using Dockerfiles and were pushed to the Docker Hub:

* #### catagalan/trading-psql
   The first image docker's postgres:9.6-alpine as its base, and it creates the databases for the 
Trading App when running the container.
* #### catagalan/trading-app
   The Trading App image is defined in two stages within the Dockerfile: the build stage with docker's 
maven:3.9.9-eclipse-temurin-22-alpine as base, and then the run stage with eclipse-temurin:22-alpine.

## Improvements
