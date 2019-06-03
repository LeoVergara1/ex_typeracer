#!/bin/sh
PROJECT_NAME=$1
PORT=$2

docker stop $PROJECT_NAME

docker rm $PROJECT_NAME

docker pull 172.31.100.23:5000/$PROJECT_NAME

docker run -dit --restart unless-stopped --network general-ebc-network --name $PROJECT_NAME -d -p $PORT:8105 172.31.100.23:5000/ebc/springboot/$PROJECT_NAME