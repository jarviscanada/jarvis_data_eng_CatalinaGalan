# Java Grep App
## Introduction
This Java grep app is designed to mimic the function of the Linux grep command. It looks 
for a matching string or pattern within a given file or directory. 
It will return all the lines that match the given pattern.
The grep app was implemented with:
* **Java** and **IntelliJ IDEA** to implement the grep functionality, handle input/output, pattern matching using java.util.regex, and keep track of errors with log4j logger. 
* **Apache Maven** for project and dependencies management.
* **Docker** to ensure consistent runtime environments.
* **Git** for version control tracking.

## Quickstart
1- Pull the Docker image and verify:
```dtd
docker pull catagalan/grep:0.0.1
docker image ls | grep "grep"
```
2- 

