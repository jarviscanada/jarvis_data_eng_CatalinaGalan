# JDBC Stock Quote App

## Overview

The Stock Quote App is designed to simulate buying and selling stock based on real time data fetched from the Alpha Vantage API. The functionality of the app is simplified to emphazise back-end tools implementation like JDBC, OkHttpHelper and PosgreSQL.

Regarding the business logic and usage of the app, we will assume that the client has no finantial limitations and that sales are  only performed on the totality of the stock selected.

### Technologies:

* Java and JDBC
* Maven
* Docker
* PostgreSQL
* Junit 5
* Mockito
* SLF4J
* Git / GitHub

## Quick Start

#### Disclaimer:
Due to the nature of the free API key we are using for this application, we need to take into consideration 
that there is a maximum of 5 API calls allowed per minute, therefore some precautions need to be 
taken for the correct functioning of the application, such as:
- Avoid running both service implementation tests back to back. 
- Avoid having more than 5 stocks saved at a time.

In the case of reaching the maximum amount of calls at any given point while using the app, 
a message will be displayed for the user: 
"*** Please wait one minute and try again ***"

#### There are 2 different ways to run this application, locally or with the docker image:

### Locally

1. From your root directory, clone this repository:
```
git clone https://github.com/jarviscanada/jarvis_data_eng_CatalinaGalan.git
```
2. Navigate to the root of the jdbc project:
```
cd /jarvis_data_eng_CatalinaGalan/core_java/jdbc
```
3. Modify the provided template and variable names in the .env.example file to create your own .env 
file in this root directory. You will need an Alpha Vantage API key (free).
```
mv .env.example .env  # => Default values are included for the database.
```
4. Package app using Maven (use the -DskipTests flag to avoid surpassing the permitted API calls 
per minute):
```
mvn clean package -DskipTests
```
5. Initialize and run the app in your command line using the provided script. This will create a 
docker container for a psql database, create and set up the stock_quote database and its tables, 
and run the application:  
\* Ensure that postgres server is **not** running locally and that port 5432 is available.
\* In the case of working with Docker Desktop, please make sure Docker is running before executing this command.
```
scripts/stock_quote_init.sh
```

### Docker
1. Pull the docker image:
```
docker pull catagalan/stock_quote:0.0.1
```
2. In the same working directory create a .env file containing the following:
```dtd
X_RAPID_API_KEY= #add your Rapid Api key here
POSTGRES_USER=postgres
POSTGRES_PASSWORD=password
```
3. Run the app from the docker container:
```dtd
docker run ---env-file ./.env catagalan/stock_quote:0.0.1
```
## Implementation
![ERD diagram of stock_quote tables](src%2Fmain%2Fresources%2FERD.png)

The database consists of two tables representing two entities: the **Quote** table persists all the 
values for each quote fetched from the API, using the unique symbol as the id. 
The **Position** table represents all the stocks the client has bought, number of shares and the total 
value for each transaction. This table is associated to the quote table in a 1:1 relationship via id 
(symbol) as both primary and foreign key, meaning that any action that a user performs on a Quote is 
persisted as a Position related specifically to said Quote. 

## Design Pattern

The app is structured in a 3 layer design that allows for isolation of the functionality of each layer:
* **Controller Layer**: for this application, since we have no real front-end, the controller acts as the 
communicator between the user and the app. It displays information and takes user input 
that is then passed on to the Service Layer.
* **Service Layer**: here we have the main functionality of the app. This layer will coordinate the use 
of all tools necessary to fetch information for the web using user input, perform business logic actions, 
send results to the controller and create the necessary entities that to be persisted in the database.
* **DAO Layer**: The DAOs are the entities in charge of communicating with the database. They establish 
the connection and pass information back and forth, performing CRUD actions on the database.

## Tests

Two layers of testing have been implemented in this application using JUnit 5.
Unit testing has been performed in isolation with the use of Mockito to facilitate mock instances 
of dependencies fo all tested entities.
Integration testing was performed on the service layer entities. It is important to note that due 
to the limited nature of the API key that was used, it is not advised to run both integration test 
classes back to back, as it will surpass the permitted calls per minute and throw errors. 

## Suggested Improvements

* Use of a mock database / database connection for unit testing.
* Dedicated database for integration testing. 
* Implementation of a simple front-end layer.