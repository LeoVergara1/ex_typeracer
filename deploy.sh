#!/bin/sh
PROJECT_NAME=$1
PORT=$2

docker stop $PROJECT_NAME

docker rm $PROJECT_NAME

docker pull 172.31.100.23:5000/ebc/springboot/$PROJECT_NAME

docker run -dit --restart unless-stopped --network general-ebc-network --add-host apr-test.ebc.edu.mx:10.31.23.14 --add-host apr.ebc.edu.mx: --name $PROJECT_NAME -d -p $PORT:8090 172.31.100.23:5000/ebc/springboot/$PROJECT_NAME