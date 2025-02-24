# Spring Boot Trading App

## Overview

The Trading App is designed for managing traders, their accounts and portfolios; executing market 
orders for those clients; and fetching real world quote information from the Alpha Vantage Api to 
build the Daily List. 

The app can be used with the help of open.ai's Swagger UI.

## Technologies

* Java
* Spring Boot
* Maven
* PostgreSQL
* Docker
* Git / GitHub

## Quickstart

#### Disclaimer:
Due to the nature of the free Api key we are using for this application, we need to take into 
consideration that there is a maximum of 25 Api calls allowed per day, therefore some precautions 
need to be taken for the correct functioning of the application, such as avoiding updating the 
Daily List multiple times a day, for example.

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
6. Get a free Api key from Alpha Vantage:  

   https://www.alphavantage.co/support/#api-key 
\
NOTE: There are some issues when manually creating and passing a json object in the Swagger UI 
to create a new trader, execute a market order or update a quote (manually, for testing purposes 
only). For correct functioning it is necessary to add a wrapper for each of these objects. 
Detailed instructions are found in the description of each method.


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
docker run --rm -d --name trading-app-dev --network trading-net -p 5432:5432 \
   -e POSTGRES_USER -e POSTGRES_PASSWORD -e ALPHA_VANTAGE_API_KEY catagalan/trading-psql 
```   
10. Verify running containers:  
```bash
docker container ps
```
11. Access the app at:  
http://localhost:8080/swagger-ui/index.html#/

## To stop the app:
1. Stop docker containers:  
```bash
docker stop trading-psql-dev trading-app-dev
```
2. Verify:
```bash
docker container ls
```
