#! /bin/bash

# set up CLI arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# validate correct number of arguments
if [ "$#" -ne 5 ]; then
	echo "Illegal number of arguments"
	exit 1
fi

# set up commands variables
lscpu_out=$(lscpu)
cpuinfo_out=$(cat /proc/cpuinfo)
vmstat_out=$(vmstat -wt -S M)
meminfo_out=$(cat /proc/meminfo)
date_out=$(date -u +"%Y-%m-%d %H:%M:%S")

# parse host CPU info
hostname=$(hostname -f)
cpu_number=$(echo "$lscpu_out" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu_out" | egrep "^Model\sname:" | echo $(awk '{for (i=3; i<=NF; i++) print $i}') | xargs)
cpu_mhz=$(echo "$cpuinfo_out" | egrep -m 1 "^cpu MHz" | awk '{print $4}'| xargs)
l2_cache=$(echo "$lscpu_out" | egrep "^L2 cache:" | echo $(awk '{print $3}') | xargs)
total_mem=$(echo "$meminfo_out" | head -1 | awk '{print $2}' | xargs)
#free_mem=$(echo "$vmstat_out" | tail -1 | awk '{print $4}' | xargs)
timestamp=$(echo "$date_out" | xargs)

# insert statement for database
insert_stmt="INSERT INTO host_info (hostname, cpu_number, cpu_architecture, cpu_model, cpu_mhz, l2_cache, total_mem, timestamp) VALUES ('${hostname}', ${cpu_number}, '${cpu_architecture}','${cpu_model}', ${cpu_mhz}, ${l2_cache}, ${total_mem}, '${timestamp}');"

# execute INSERT statement
export PGPASSWORD="$psql_password"
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "${insert_stmt}"
exit $?
