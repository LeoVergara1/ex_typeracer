FROM openjdk
WORKDIR /root/
ARG PATH_FOLDER
ADD $PATH_FOLDER/comisiones-0.0.1-SNAPSHOT.jar comisiones-0.0.1-SNAPSHOT.jar
RUN rm -f /etc/localtime
RUN ln -sf /usr/share/zoneinfo/America/Mexico_City /etc/localtime
EXPOSE 8090
CMD java -jar comisiones-0.0.1-SNAPSHOT.jar