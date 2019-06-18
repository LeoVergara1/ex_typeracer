#Procesamiento SICOSS

##CP-22 Procesar archivo SICOSS

![Procesar archivo sicoss](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/SICOSS/procesar-archivo.png)

###Función

Como Administración de Personal y Coordinador de Nómina
quiero procesar el archivo para SICOSS
de tal forma que se puedan pagar las comisiones a promotores, coordinadores y jefes 


###Criterios de aceptación

- El criterio de búsqueda para las comisiones que se mostrarán en el archivo para SICOSS es el periodo:
	- Para el caso de Licenciaturas Presenciales, el periodo se refiere al mes del año.  
		- Si en el mes consultado,  no se autorizados comisiones se debe mostrar un mensaje al usuario informado que en ese mes no se encontraron comisiones autorizadas.
- Si el archivo ya fue procesado en ese periodo o mes, se debe mostrar un mensaje al usuario con la siguiente información:
	- Usuario que procesó anteriormente el archivo
	- Fecha en la que el usuario procesó anteriormente el archivo, en formato dd/mm/aa  hh:mm
	- En la bitácora de BD, se debe almacenar la información del usuario y fecha en la que se procesa el archivo 
- El archivo para SICOSS puede volver a ser procesado por el usuario que consulta
	- En la bitácora de BD, se debe actualizar la información del usuario y fecha en la que se vuelve a procesa el archivo

###Criterios de aceptación

- Se debe crear una tabla para la bitácora de BD de los usuarios que procesan el archivo para SICOSS

- Los registros de las comisiones que se mostrarán en el archivo para SICOSS, se obtiene del esquema de SICOSS de Comisiones DVL8

![Ejemplo esquema sicoss](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/SICOSS/ejemplo-esquema.png)


##CP-23 Volver a procesar SICOSS

![Reprocesamiento archivo sicoss](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/SICOSS/volver-procesar.png)

###Función

Como Administración de Personal y Coordinador de Nómina
quiero procesar volver a procesar el archivo para SICOSS
de tal forma que se puedan pagar las comisiones a promotores, coordinadores y jefes 

###Criterios de aceptación 

- Si ya se ha realizado el procesamiento de SICOSS se deberá mostrar:
	- Leyenda : “El archivo ya fue procesado”
	- Usuario: El usuario de banner
	- Fecha: El registro de la fecha del proceso de SICOSS , así como hora.
- Se mostrará un botón de “Volver a Procesar SICOSS”
	- Al dar clic en este botón se Procesa SICOSS (Procesar archivo SICOSS)

###Especificaciones técnicas 

Se debe hacer un update a los registros del archivo sicoss almacenados en el esquema de SICOSS de Comisiones DVL8


##CP-24 Descargar archivo para SICOSS

![Descargar archivo](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/SICOSS/descargar-sicoss.png)

###Función

Como Administración de Personal y Coordinador de Nómina
quiero descargar el archivo para SICOSS
de tal forma que vea las comisiones a pagar a promotores, coordinadores y jefes

###Criterios de aceptación 

- El archivo para SICOSS se procesará en un archivo csv con las siguientes columnas, que no llevarán ningún título de encabezado: 
	- Número de empleado: Sin el prefijo “AD”
	- Nómina abierta : Será una constante “1”
	- Clave : Será una constante “0”
	- Concepto: Mostrar el registro 422
	- Fecha: Mostrar la fecha del primer día del mes de acuerdo a la fecha de exportación.
		- Si un número de empleado tiene dos registros o más, al segundo registro se le incrementa un día.
		- Formato: dd/mm/aaaa
	- Departamento: Mostrar siempre “0”
	- Referencia 2: Mostrar siempre “0”
	- Dato: Mostrar siempre “0”
	- Importe: Corresponde al monto a pagar por comisiones
		- Se deberá redondear a 2 decimales.
	- Saldo: Mostrar siempre “0”
	- Quincena: Mostrar el número de quincena de acuerdo a la fecha que se exporta. Son 24 quincenas por año.

###Especificaciones técnicas  

- Los registros de las comisiones que se mostrarán en el archivo para SICOSS, se obtiene del esquema de SICOSS de Comisiones DVL8

![Ejemplo esquema sicoss](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/SICOSS/ejemplo-esquema.png)