#!/bin/bash

# start docker
sudo systemctl status docker || sudo systemctl start docker

# set CLI arguments
cmd=$1
db_username=$2
db_password=$3

# container status
docker container inspect jrvs-psql
container_status=$?

case $cmd in
	create)
	# check if container exists
	if [ $container_status -eq 0 ]; then
		echo "Container already exists"
		echo
		echo "$(docker ps -f name=jrvs-psql)"
		exit 1
	fi

	# check for correct number or arguments
	if [ "$#" -ne 3 ]; then
		echo "Create requires username and password"
		exit 1
	fi
	
	# create container [db_username] [db_password]
	docker volume create pgdata
	docker run --name jrvs-psql -e POSTGRESS_USER=$db_username -e POSTGRES_PASSWORD=$db_password -v pgdata/var/lib/postgresql/data -p 5432:5432 -d postgres:9.6-alpine
	echo 
	echo "$(docker ps -f name=jrvs-psql)"
	exit $?
	;;
	
	start|stop)
	# check instance status, exit if it hasn't been created
        if [ $container_status -eq 1 ]; then
	     	echo "Container doesn't exist"
        	exit 1
        fi

	# start|stop the container
	docker container $cmd jrvs-psql
	exit $?
	;;

	*)
	#check incorrect arguments
	echo "Illegal command"
	echo "Commands start|stop|create"
	exit 1
	;;
esac
