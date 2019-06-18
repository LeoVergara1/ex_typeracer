#Consultar archivo SICOSS

##CP-22 Consultar archivo SICOSS

![Descargar consulta](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/SICOSS/descargar-consulta.png)

###Función

Como Administración de Personal y Coordinador de Nómina
quiero consultar el archivo para SICOSS
de tal forma que pueda conocer toda la información sobre el pago que se hará a un  o varios empleados de un periodo específico 

###Criterios de aceptación 

- Se podrá consultar por periodo o por un empleado en específico, si se desea consultar por periodo no será necesario ingresar el número del empleado. 
	- Para el caso de Licenciaturas Presenciales, el periodo se refiere al rango de fechas consultado.  
		- Si en el mes consultado,  no se autorizaron comisiones se debe mostrar un mensaje al usuario informando que en ese rango no se encontraron comisiones autorizadas.
**- Para el caso del número de empleado, se debe ingresar el número sin el AD**
	- Si el número de empleado no es encontrado, o si el empleado no cuenta con comisiones procesadas anteriormente se debe mostrar un mensaje con la leyenda “El empleado no cuenta con comisiones autorizadas”
- El archivo que se muestre, contendrá las mismas columnas que las descritas en la historia de usuario “Descargar archivo para SICOSS”. 

###Especificaciones técnicas 

- Los empleados a consultar en la búsqueda se obtienen de Banner de la forma SPIDEN
- Los registros de las comisiones que se mostrarán en el archivo de sicoss son las comisiones que se identifiquen como autorizadas en el módulo de autorización.