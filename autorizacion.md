#Autorización 


##CP-25 Ver resumen de autorización

###Función

Como Director de campus
quiero ver el resumen de los ingresos por las comisiones
de tal forma que visualice cuánto y a quién se les podrá autorizar el pago

###Criterios de aceptación 

Los datos del director de campus se muestran en la parte superior izquierda de la pantalla, con la siguiente información:
- Nombre completo del director de campus Nombre(s) + Apellido Paternos + * + Apellido Materno del director que inicia sesión
- Rol: Rol del usuario que consulta (Director de campus)
- Campus al que pertenece el director que inicia sesión

- Se mostrará un recuadro para el monto total a pagar, identificado como “Comisión a pagar” 
	- El monto a mostrar es el que se obtiene en el cálculo sobre el porcentaje de comisión del Coordinador de campus respecto a las comisiones de sus promotores asignados 
- Se mostrará un recuadro para el total de ingresos, identificado como “Ingresos”
	- El total de ingresos a mostrar es el que se obtiene de la sumatoria de alumnos inscritos por los promotores asociados al coordinador del campus


##CP-16 Autorizar comisiones

###Función

Como Coordinador
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
	- Todos los criterios de búsqueda son obligatorios

**- Mostrar los resultados de la búsqueda.**
	- Los resultados se mostrarán en dos vistas: Una con todos los promotores y otra vista para cada promotor .
		- Si se muestra la vista por cada promotor se deberá mostrar el resumen por el total de sus comisiones e ingresos:
			- Se mostrará un recuadro para el monto total a pagar, identificado como “Comisión a pagar” 
			- El monto a mostrar es el que se obtiene en el cálculo sobre el porcentaje de comisión del promotor respecto a la suma del valor real de contrato de todos los alumnos inscritos en el periodo activo
			- Se mostrará un recuadro para el total de ingresos, identificado como “Ingresos” 
			- El total de ingresos a mostrar es el que se obtiene de la sumatoria de alumnos inscritos por el promotor consultado 
	- En cada vista, los resultados se mostrarán en una tabla con las siguiente columnas:

	