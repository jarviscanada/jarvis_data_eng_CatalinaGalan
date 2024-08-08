#!/bin/bash

#check if docker-deamon is active
if [ "$(systemctl is-active docker)" = "active" ]; then
	echo "$(systemctl status docker)"
else
	sudo systemctl start docker
	echo "$(systemctl status docker)"	

fi

#set arguments
export db_username=$2
export db_password=$3

#check $1 argument for next action
if [ "$1" != "create" ] && [ "$1" != "start" ] && [ "$1" != "stop" ]; then
	echo "error: invalid argument."
        exit 1
elif [ "$1" = "create" ]; then
	#check if container already exists
	if [ "$(docker container ls -a -f name=jrvs-psql | wc -l)" = 2 ]; then
		echo "error: container jrvs-psql already exists."
		exit 1
	#check if username and passwords are provided
	elif [ $# -ne 3 ]; then
		echo "error: wrong number of arguments. Please provide database username and password"
		exit 1
	#create container jrvs-psql
	else
		docker volume create pgdata
		docker run --name jrvs-psql -d -e POSTGRES_USER=$db_username -e POSTGRES_PASSWORD=$db_password  -v pgdata:/var/lib/postgresql/data -p 5433:5432 postgres:9.6-alpine
		exit $?
	fi
#error in case container was not created"
elif [ "$(docker container ls -a -f name=jrvs-psql | wc -l)" -ne 2 ]; then
	echo "container jrvs-psql was NOT created successfully."
	exit 1
elif [ "$1" = "start" ]; then
	docker container start jrvs-psql
	echo "container started"
	exit $?
elif [ "$1" = "stop" ]; then
	docker container stop jrvs-psql
	echo "container stopped"
	exit $?
fi

exit 0
