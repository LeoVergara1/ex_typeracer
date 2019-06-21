#Autorización 

![Resumen autorización](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/Autorizaci%C3%B3n/resumen-autorizacion_v2.png)

##CP-25 Ver resumen de autorización

###Función

Como Jefe de promoción
quiero ver el resumen de los ingresos por las comisiones
de tal forma que visualice cuánto y a quién se les podrá autorizar el pago

###Criterios de aceptación 

Los datos del director de campus se muestran en la parte superior izquierda de la pantalla, con la siguiente información:
- Nombre completo del director de campus Nombre(s) + Apellido Paternos + * + Apellido Materno del director que inicia sesión
- Rol: Rol del usuario que consulta (Jefe de promoción)
- Campus al que pertenece el director que inicia sesión
- Periodo: Rango de tiempo abarcado en los filtros de búsqueda de las comsiones calculadas  

- Se mostrará un recuadro para el monto total a pagar, identificado como “Comisión a pagar” 
	- El monto a mostrar es el que se obtiene en el cálculo sobre el porcentaje de comisión del jefe de promoción de campus respecto a las comisiones de sus promotores asignados 
- Se mostrará un recuadro para el total de ingresos, identificado como “Ingresos”
	- El total de ingresos a mostrar es el que se obtiene de la sumatoria de alumnos inscritos por los promotores asociados al coordinador del campus


### Especificaciones técnicas 

- Para la sección de “Comisión a pagar” se debe consultar el cálculo de comisiones crecientes, referente al cálculo del porcentaje de comisión asignado al coordinador del campus del Director sobre el total obtenido de las inscripciones de sus promotores durante el periodo activo.
			- Tabla: ADMIN_DE_COMISIONES
			- Entidades: COMISION_COORDINADOR, COMISION, EJECUTIVO, CAMPUS_CODE, CUOTA_FIJA

			- Tabla:ASOCIACION_PROMOTOR
			- Entidades: ID_COORDINADOR, ID, PROMOTOR, CAMPUS_CODE, RECR_CODE_MANAGER, CLAVE_EMP_COORDINADOR, CLAVE_EMP_PROMOTOR 		
 Para la sección de “Ingresos”, el total se obtiene de la suma de todos los registros mostrados de todos los promotores asignados al coordinador del campus del Diretor


##CP-16 Autorizar comisiones

![Autorizar comisiones]](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/Autorizaci%C3%B3n/autorizacion_v2.png)

###Función

Como Director de campus
quiero autorizar la comisiones
de tal forma que los promotores y coordinadores reciban su pago e inscriban a más estudiantes.


###Criterios de aceptación

La autorización de comisiones consta de tres partes:

**- La búsqueda de coordinadores y promotores con sus comisiones para autorizar.**
	- Se podrá seleccionar uno o todos los campus.
		- Nombre del campus
		- Código del campus
	- Se podrá seleccionar la fecha inicio.
		- Habilitar un modal de calendario para seleccionar la fecha en formato dd/mm/aa
	- Se podrá seleccionar la fecha fin.
		- Habilitar un modal de calendario para seleccionar la fecha en formato dd/mm/aa
	- El proceso se realizará cuando se seleccione “buscar”
	- ***Todos los criterios de búsqueda son obligatorios***

**- Mostrar los resultados de la búsqueda.**
	- Los resultados se mostrarán en dos vistas: Una con todos los promotores y otra vista para cada promotor .
		- Si se muestra la vista por cada promotor se deberá mostrar el resumen por el total de sus comisiones e ingresos:
			- Se mostrará un recuadro para el monto total a pagar, identificado como “Comisión a pagar” 
			- El monto a mostrar es el que se obtiene en el cálculo sobre el porcentaje de comisión del promotor respecto a la suma del valor real de contrato de todos los alumnos inscritos en el periodo activo
			- Se mostrará un recuadro para el total de ingresos, identificado como “Ingresos” 
			- El total de ingresos a mostrar es el que se obtiene de la sumatoria de alumnos inscritos por el promotor consultado 
	- En cada vista, los resultados se mostrarán en una tabla con las siguiente columnas:
		- Promotor: Nombre completo del Promotor asociado al Coordinador que registró las comisiones dentro del periodo de fechas y campus seleccionados.
			- Es necesario mostrar el número de empleado (AD) del promotor abajo de su nombre
		- Comisión promotor: Monto de comisión que corresponde al promotor de acuerdo al cálculo del porcentaje asignado a promotor sobre el valor real de contrato
		- Coordinador: Nombre completo del Coordinador que registró las comisiones dentro del periodo y campus seleccionados 
		- Comisión coordinador: Monto de comisión que corresponde al coordinador de acuerdo al cálculo del porcentaje asignado sobre la comisión del promotor  
		- Periodo de inscripción: Periodo en el que se inscribe el alumno a la división de Licenciaturas Presenciales
		- Alumno: Nombre completo del alumno inscrito a la división de Licenciaturas Ejecutivas, obtenido de la forma de Banner SPAIDEN.
			- Es necesario mostrar la matrícula del alumno abajo de su nombre
		- Fecha de pago: Fecha en que el alumno realiza el pago de su inscripción a la división de Licenciaturas Ejecutivas, obtenido de la forma de Banner TSAAREV
		- Tipo de pago: Especifica el método del pago del la inscripción (crédito, contado)  obtenido de la forma de Banner SGASTDN
		- Colegiatura inicial: Monto a pagar por la inscripción a la división de Licenciaturas Presenciales, obtenido de la forma de Banner TSAAREV 
		- Beneficios: Especifica el porcentaje sobre el valor de contrato equivalente a los beneficios (descuento) que tiene el alumno, si es que aplica. ver anexo de cálculo de comisiones CC-1.
		- Si el alumno inscrito no cuenta con beneficios, el monto debe mostrarse como 0%
		- Colegiatura inicial real: Se calcula sobre el valor de contrato menos los beneficios obtenidos. Es el valor que se almacenó al momento de registrar las comisiones. Ver anexo de cálculo de comisiones CC-1.
		- Autorizar comisión corriente: Checkbox para seleccionar las comisiones corrientes registradas a autorizar.

**- Autorización general de los resultados mostrados**
- Aparecerá un botón que lleve por nombre “Autorizar” que realizará el proceso de autorización de las comisiones para su pago.
- Se autorizaran las comisiones que tengan seleccionado el checkbox tanto para comisiones corrientes y comisiones crecientes.


### Especificaciones técnicas 

- Los datos necesarios para las comisiones que sean autorizadas, se guardan en el esquema de COMISIONES PRESENCIALES:
	- User: pgocomis
	- Tabla: AUTORIZACION_COMISIONES
	- Entidades: ID, CAMPUS, ID_PROMOTOR, NOMBRE_PROMOTOR, PUESTO, ID_ALUMNO, NOMBRE_ALUMNO, PAGO_INICIAL, TOTAL_DESCUENTOS, COMISION, PERIODO, FECHA_DE_PAGO, AUTORIZADO_DIRECTOR, DATE_CREATED, LAST_CREATED, ID_COORDINADOR, COMISION_COORDINADOR, FECHA_AUTORIZADO, USUARIOla tabla AUTORIZACION_COMISIONES.

Actualmente hay columnas que no existen y se deberán crear, estas son: Matrícula, Tipo de pago, Valor de contrato, Valor real de contrato, Comisión creciente, Autorizar comisión creciente.