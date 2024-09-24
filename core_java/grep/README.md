# Java Grep App
## Introduction
This Java grep app is designed to mimic the function of the Linux grep command. It looks 
for a matching string or pattern within a given file or directory (recursively).
It will return all the lines that match the given pattern and write them into a given output file.  
The grep app was implemented with:
* **Java** and **IntelliJ IDEA** to implement the grep functionality, handle input/output, pattern matching using java.util.regex, and keep track of errors with log4j logger. 
* **Apache Maven** for project and dependencies management.
* **Docker** to ensure consistent runtime environments.
* **Git** for version control tracking.

## Quickstart
* ``<pattern>``: Search pattern, which can be a string or a regular expression.
  * **If using a regex pattern, wrap it in double quotes (" ").**
* ``<search-target>``: root path of file or directory where the search will be performed.
* ``<output-file>``: The file where the matching lines will be written.
### To use locally:
#### Setup:  
1. Clone this repository:
```bash
git@github.com:jarviscanada/jarvis_data_eng_CatalinaGalan.git
```
2. In your terminal, navigate to the app directory: <br>
```bash
cd path/to/core_java/grep
```
3. Before running the application you need to compile and create a .jar file using Maven. To do this run the 
following command:
```bash
mvn clean package
```
#### Usage:  
To use the application run the following command:
```bash
java -jar target/grep-1.0-SNAPSHOT.jar <pattern> <search-target> <output-file>
```
### To use with Docker:
#### Setup:
Pull the Docker image and verify:
```bash
docker pull catagalan/grep:0.0.1
docker image ls | grep "grep"
```
#### Usage:  
Run the following command to:  
1. Create a Docker container that will be automatically removed upon completion of the 
application execution.  
2. Create a `log` folder containing the output file within your working directory.
```bash
docker run --rm \
-v `pwd`/<search-target>:/<search-target> -v`pwd`/log:/log \
catagalan/grep:0.0.1 <pattern> /<search-target> /log/<output-file> 
```

### Examples:  
You can use the provided `data/txt/shakespeare.txt` file to test the correct usage:  
* **Locally**
```bash
java -jar ./target/grep-1.0-SNAPSHOT.jar "Romeo.*Juliet" ./data ./out/grep.out
```
* **With Docker**
```bash
docker run --rm \
-v `pwd`/data:/data -v `pwd`/log:/log \
catagalan/grep:0.0.1 "Romeo.*Juliet" /data /log/grep.out
```

## Implementation
The app takes three arguments: `regex`, `rootPath` and `outFile`.   
It executes the process() method to iterate and scan all files in the given `rootPath`,
checking each line for matches to the `regex` pattern. If matches are found, they are collected in
an array and written to the provided `outFile`.
```bash
# Pseudocode
# implement process() method:
matchedLines = []
for files in listFilesRecursevly(rootPath) 
  for line in readLines(file)
    if containsPattern(regex)
      matchedLines.add(line)
writeToFile(matchedLines)
```
## Test
The app was tested manually, both locally and using the Docker image. It was tested against various 
regex patterns and strings using the built-in data/txt/shakespeare.txt file, as well as files in 
different text formats such as .sql, .md, .sh, .xml, and others. The app was run against single 
files and nested files within directories where recursive iterations were necessary.

## Deployment
#### Deployment instructions using Maven and Docker

1. **Login to Docker:**  
```bash
docker_user=your_docker_id
docker login -u ${docker_user} --password-stdin 
```
2. **Create a Dockerfile in working /grep directory:**
```bash
cat > Dockerfile << EOF
FROM openjdk:8-alpine
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]
EOF
```
3. **Package with Maven:**
```bash
mvn clean package
```
4. **Build new Docker image locally and verify:**
```bash
docker build -t ${docker_user}/grep .
docker image ls |grep "grep"
```
5. **Tag image and push to Docker Hub:**
```bash
docker image tag ${docker_user}/grep ${docker_user}/grep[:TAG]
docker push ${docker_user}/grep[:TAG]
```
## Improvement
The Java Grep app can be improved by:
1. a
2. b
3. c


