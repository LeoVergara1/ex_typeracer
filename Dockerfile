FROM java:8
WORKDIR /root/
ARG PATH_FOLDER
ADD $PATH_FOLDER/comisiones-0.0.1-SNAPSHOT.jar comisiones-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD java -jar comisiones-0.0.1-SNAPSHOT.jar