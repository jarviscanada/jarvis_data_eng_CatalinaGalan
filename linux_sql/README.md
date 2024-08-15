# Linux Cluster Monitoring Agent

## Introduction
The Linux Cluster Monitoring Agent app is designed to record hardware specifications and usage information from multiple nodes in a Linux Cluster. It collects usage data from each node in real time and stores it in a RDBMS.

The app was created for the Jarvis Linux Cluster Administration (LCA), to manage a cluster of 10 nodes internally connected through a switch. The team needs to record information and track the usage of each machine in the network.

An instance of `psql` is created with Docker to store all data. The `bash agent`, consisting of two bash scripts, can be run in each node. It will collect the host's hardware specifications by running the `host_info.sh` script once, at install time; and it will collect the host's usage information and send it to the database, by running the `host_usage.sh` script automatically in one-minute intervals with the help of `cron`.

The program was implemented using Linux, Docker, PostgreSQL, Bash scripts, Crontab and GitHub.

## Quickstart
```bash
# start a psql instance 
./scripts/psql_docker.sh start|stop|create [db_username] [db_password]
```
```bash
# create host_agent database and tables
./sql/ddl.sql
```
```bash
# insert hardware specs into database
./scripts/host_info.sh [psql_host] [psql_port] [db_name] [psql_username] [psql_password]
```
```bash
# insert hardware usage into database
./scrpts/host_usage.sh [psql_host] [psql_port] [db_name] [psql_username] [psql_password]
```
```bash
# edit crontab job and redirect output to a temp file
# make sure to use the full path from /home to host_usage.sh file
crontab -e
* * * * * bash /home/.../scripts/host_usage.sh [psql_host] [psql_port] [db_name] [db_username] [db_password] > /tmp/host_usage.log
```

## Implementation

### Architecture

This is a diagram of a Linux Cluster with three nodes connected through a switch. Each machine sends data to the database when Bash scripts are implemented<br>
![diagram of a 3 nodes Linux cluster model connected through a switch](https://github.com/jarviscanada/jarvis_data_eng_CatalinaGalan/blob/develop/linux_sql/assets/LinuxCluster.drawio.pngß)

### Script Description and Usage
**1.** **Database and Table Initialization**

First the PostgreSQL instance needs to be created by running a Docker container. Secondly, the Database and corresponding Tables will be created by executing the `./sql/ddl.sql` script against the psql instance.
```bash
# provision psql instance
./scripts/psql_docker.sh create [db_username] [db_password]

# create host_agent database and tables 
psql -h [psql_host] -U [psql_user] -W -f ./sql/ddl.sql
```
**2.** **Collecting Hardware Info**

The `host_info.sh` script is required to be run on every node **once** at initialization, in order to insert the hardware specifications into the _host_agent_ database.
```bash
# insert hardware specs into database
./scripts/host_info.sh [psql_host] [psql_port] [db_name] [psql_username] [psql_password]
```
**3.** **Collecting Usage Info**

The `host_usage.sh` can be run on every node manually, or set up with Cronotab for automated tracking of resources usage (look for step 4. instructions). The script will capture a snapshot of the current usage and insert it into the _host_usage_ database.
```bash
# insert hardware usage into database
./scrpts/host_usage.sh [psql_host] [psql_port] [db_name] [psql_username] [psql_password]
```
**4.** **Crontab Setup**

We can create a crontab job to run the `host_usage.sh` script automatically in the background at one-minute intervals, taking snapshots of the node's resources usage and inserting them into the database.
```bash
# open the crontab editor
crontab -eß
# make sure to use the full path from /home/ to host_usage.sh file. The output will also be redirected to a log file.
* * * * * bash [path]/scripts/host_usage.sh [psql_host] [psql_port] [db_name] [psql_username] [pqsl_password] > /tmp/host_usage.log
# verify that the crontab job is running
crontab -ls
# verify that the script is running correctly by checking the log file
cat /tmp/host_usage.log
```
### Database Modeling
The `host_agent` database consists of two tables: `host_info` and `host_usage`.

_host_info table_<br>
Stores information about the user's hardware specification upon initialization.

`id`: Creates a unique id for each node.,<br>
`hostname`: The full hostname of the current node.<br>
`cpu_number`: Number of CPU cores.<br>
`cpu_architecture`: Type of CPU architecture, Intel 64-bit.<br>
`cpu_model`: Maker, family and clock speed of CPU.<br>
`cpu_mhz`: Clock speed of the CPU.<br>
`l2_cache`: Size of the L2 cache memory.<br>
`total_mem`: Size of the total memory in the system.<br>
`timestamp`: Snapshot of date of data collection, in UTC.<br>

_host_usage table_<br>
Contains information from each node's usage captured every minute.

`timestamp`: Snapshot of date of data collection, in UTC.<br>
`host_id`: Unique host ID referenced from _host_info_ table.<br>
`memory_free`: Available unused memory in the machine.<br>
`cpu_idle`: Percentage of idle CPU.<br>
`cpu_kernel`: Percentage of CPU being used for system tasks.<br>
`disk_io`: Number of processes being read/write.<br>
`disk_available`: Total size of disk storage.<br>

## Test

All functionalities of each bash script were tested and verified manually.

- `psql.docker.sh`<br>
  The script was tested for potential errors in argument inputs by the user and correct use case.<br>
  A psql instance was created, stopped and started again.<br>


-  `ddl.sql`<br>
   The script was executed by itself and against the database instance. <br>
   Upon connection to the database, the existence of the *host_agent* database and tables was corroborated.<br>


-  `host_info.sh`<br>
   Each command within the script was tested directly in the CLI to corroborate correct data collection.<br>
   After the script was executed, the successful insertion of the data was verified by connecting directly to the database and selecting all data from the _host_info_ table.<br>
   The script was also tested for wrong number of arguments or incorrect input.<br>


-  `host_usage.sh`<br>
   Each command within the script was tested directly in the CLI to corroborate correct data collection.<br>
   After the script was executed, the successful insertion of the data was verified by connecting directly to the database and selecting all data from the _host_usage_ table.<br>
   The script was also tested for wrong number of arguments or incorrect input.<br>
   After creating the crontab job, it was verified by running `crontab -ls` and `cat /temp/host_usage.log`.<br>
   Successful data insertion was also verified, after a few minutes, by connecting to the _host_agent_ database and selecting all entries from _host_usage_ table.<br>

## Deployment

The application is deployed using Docker, PostgreSQL and cron.<br>
All git feature branches were merged after testing and pushed to a GitHub repo.

## Improvements

- Refactor code in individual scripts with the use of functions.
- Implement a global script to run both, `host_info.sh` (once) and `host_usage.sh` with the inclusion of the crontab job creation.
- Implement unit testing for all use cases: normal and invalid.
