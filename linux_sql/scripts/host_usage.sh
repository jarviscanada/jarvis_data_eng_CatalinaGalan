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

vmstat_mb=$(vmstat -wt -S M)
df_out=$(df -BM /)

# parse host CPU usage
hostname=$(hostname -f)
memory_free=$(echo "$vmstat_mb" | tail -1 | awk '{print $4}' | xargs)
cpu_idle=$(echo "$vmstat_mb" | tail -1 | awk '{print $15}' | xargs)
cpu_kernel=$(echo "$vmstat_mb" | tail -1 | awk '{print $14}' | xargs)
disk_io=$(echo "$vmstat_mb" | tail -1 | awk '{print $10}' | xargs)
disk_available=$(echo "$df_out" | tail -1 | awk '{print $4}' | grep -oP "\d+" | xargs)
timestamp=$(echo "$vmstat_mb" | tail -1 | awk '{print $18} {print $19}' | xargs)

# get host_id from host_info table
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

# insert statement for database
insert_stmt="INSERT INTO host_usage (timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available) VALUES ('${timestamp}', ${host_id}, ${memory_free}, ${cpu_idle}, ${cpu_kernel}, ${disk_io}, ${disk_available});"

# execute INSERT statement
export PGPASSWORD="$psql_password"
psql -h "$psql_host" -p "$psql_port" -d "$db_name" -U "$psql_user" -c "${insert_stmt}"
exit $?

