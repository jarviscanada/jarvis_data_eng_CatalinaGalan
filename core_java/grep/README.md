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

[//]: # (1- Pull the Docker image and verify:)

[//]: # (```dtd)

[//]: # (docker pull catagalan/grep:0.0.1)

[//]: # (docker image ls | grep "grep")

[//]: # (```)
### To use locally:
**Setup:**  
1- Clone this repository:
```bash
git@github.com:jarviscanada/jarvis_data_eng_CatalinaGalan.git
```
2- In your terminal, navigate to the app directory. <br>
```bash
cd path/to/core_java/grep
```
3- Before running the application you need to compile and create a .jar file using Maven. To do this run the 
following command:
```bash
mvn clean package
```
**Usage:**  
To use the application run the following command:
```bash
java -jar target/grep-1.0-SNAPSHOT.jar <pattern> <search-target> <output-file>
```
* ``<pattern>``: Search pattern, which can be a string or a regular expression.
  * **If using a regex pattern, wrap it in double quotes (" ").**
* ``<search-target>``: root path of file or directory where the search will be performed.
* ``<output-file>``: The file where the matching lines will be written.  

**Example:**  
You can use the provided <data/txt/shakespeare.txt> file to test the correct usage:
```bash
java -jar ./target/grep-1.0-SNAPSHOT.jar "Romeo.*Juliet" ./data ./out/grep.out
```
### Use with Docker:


