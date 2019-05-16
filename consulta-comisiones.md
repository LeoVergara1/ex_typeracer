#Consulta de comisiones

##CP-17 Ver reporte general de comisiones

###Función

Como Jefe de promoción, 
quiero ver el reporte general de comisiones registradas/autorizadas
de tal forma pueda conocer el total de alumnos inscritos y monto de comisión de los promotores y coordinadores de un campus

###Criterios de aceptación

Los criterios de búsqueda son los siguientes: 
	- Tipo de reporte: Existen dos tipos de reportes (tablas con información) que se pueden generar con los datos de las comisiones:
		- General: Señala el total de alumnos inscritos en un periodo y del monto de comisión por promotor y coordinador de un campus seleccionado.
		- Detallado: Brinda información sobre el estatus de las comisiones de los promotores y coordinadores de un campus seleccionado, mostrando el detalle de las inscripciones por la que se paga la comisión
		**- Para esta historia de usuario, se selecciona la opción “General”**
	***- Este criterio de búsqueda es obligatorio***
	- Fecha inicio: Fecha de inicio en la que se deben buscar los registros
		- El formato de la fecha es dd/mm/aa, habilitando un modal de calendario para seleccionar las fechas.
		***- Este criterio de búsqueda es obligatorio*** 
	- Fecha fin:  Fecha de fin en la que se deben buscar los registros
		- El formato de la fecha es dd/mm/aa, habilitando un modal de calendario para seleccionar las fechas.
		***- Este criterio de búsqueda es obligatorio***
	- Campus: Lista de campus en los que se registran comisiones  
		***- Este criterio de búsqueda es obligatorio***
	- Estatus: Los estatus que pueden tener las comisiones son los siguientes:
		- Autorizada: Comisiones que se autorizaron en el módulo de Autorización
		- Rechazada: Comisiones que se denegaron por algún motivo en el módulo de Autorización
		- Por autorizar: Comisiones que se muestran en el módulo de autorización, pero que aún no se autorizan
		***- Este criterio de búsqueda es obligatorio sólo si se selecciona el tipo de reporte detallado***


La información del reporte, de acuerdo a los criterios de búsqueda seleccionador por el usuario es la siguiente:
	- Puesto: Promotor o coordinador con registro de comisiones encontrado de acuerdo a los criterios de búsqueda 
	- Nombre: Nombre completo del promotor, coordinador. Se muestran todos los promotores y coordinadores del campus seleccionado en la búsqueda   
		- Es necesario mostrar el número de empleado (AD) del usuario abajo de su nombre
	- No. Alumnos inscritos: Total de alumnos inscritos por el promotor durante el periodo de tiempo seleccionado en la búsqueda. 
		**- En el caso de los coordinadores, el número de alumnos inscritos debe ser la sumatoria de los alumnos inscritos por los promotores que tiene asociados en el periodo de tiempo seleccionado en la búsqueda**
	- Mes: Mes en el que fue registrada la comisión. 
		**- Los meses mostrados deben corresponder a los meses válidos dentro del rango de fechas seleccionados en el criterio de búsqueda**
	- Comisión total: Sumatoria del monto total de la comisión a pagar a los promotores y coordinadores por todos los alumnos inscritos, de acuerdo a los resultados del cálculo de comisiones en el rango de fechas seleccionado en la búsqueda

**La información mostrada en las tablas se podrá descargar en un archivo .xls al hacer click en el botón “Exportar XLS”**


###Especificaciones técnicas 

- No. Alumnos inscritos: Se requiere mostrar el total de alumnos inscritos por el promotor en periodo de tiempo seleccionado en la búsqueda. En el caso de los coordinadores, el número de alumnos inscritos debe ser la sumatoria de los alumnos inscritos por los promotores que tiene asociados en el periodo de tiempo seleccionado en la búsqueda
- Comisión total: Se requiere hacer la sumatoria del monto almacenado del cálculo que corresponde a la comisión del promotor y coordinador de acuerdo al periodo de tiempo seleccionado en la búsqueda, y todos los alumnos que inscribió en este tiempo. Para calcular el monto de comisión correspondiente, se sigue el proceso del cálculo de comisiones corrientes.   Ver anexo de cálculo de comisiones CC-1


##CP-18 Ver reporte detallado de comisiones

###Función

Como Jefe de promoción, 
quiero ver el reporte detallado de comisiones registradas/autorizadas
de tal forma pueda conocer toda la información de las comisiones por la inscripción de alumnos


###Criterios de aceptación

- Los criterios de búsqueda son los siguientes: 
	- Tipo de reporte: Existen dos tipos de reportes (tablas con información) que se pueden generar con los datos de las comisiones:
		- General: Señala el total de alumnos inscritos en un periodo y del monto de comisión por promotor y coordinador de un campus seleccionado.
		- Detallado: Brinda información sobre el estatus de las comisiones de los promotores y coordinadores de un campus seleccionado, mostrando el detalle de las inscripciones por la que se paga la comisión
		**- Para esta historia de usuario, se selecciona la opción “General”**
		***- Este criterio de búsqueda es obligatorio***
	- Fecha inicio: Fecha de inicio en la que se deben buscar los registros
		- El formato de la fecha es dd/mm/aa, habilitando un modal de calendario para seleccionar las fechas.
		***- Este criterio de búsqueda es obligatorio*** 
	- Fecha fin:  Fecha de fin en la que se deben buscar los registros
		- El formato de la fecha es dd/mm/aa, habilitando un modal de calendario para seleccionar las fechas.
		***- Este criterio de búsqueda es obligatorio***
	- Campus: Lista de campus en los que se registran comisiones  
		***- Este criterio de búsqueda es obligatorio***
	 Estatus: Los estatus que pueden tener las comisiones son los siguientes:
		- Autorizada: Comisiones que se autorizaron en el módulo de Autorización
		- Rechazada: Comisiones que se denegaron por algún motivo en el módulo de Autorización
		- Por autorizar: Comisiones que se muestran en el módulo de autorización, pero que aún no se autorizan
		***- Este criterio de búsqueda es obligatorio sólo si se selecciona el tipo de reporte detallado***


- La información del reporte, de acuerdo a los criterios de búsqueda seleccionador por el usuario es la siguiente:
	- Promotor: Nombre completo del promotor. Se muestran todos los promotores del campus seleccionado en la búsqueda.
		- Es necesario mostrar el número de empleado (AD) del promotor abajo de su nombre
	- Comisión promotor: Se obtiene del cálculo con respecto al porcentaje de comisión que el promotor tiene asignado sobre el valor real de contrato, para cada uno de los alumnos inscritos. Ver anexo de cálculo de comisiones CC-1
	- Coordinador: Nombre completo del coordinador. Se muestran todos los coordinadores asociados al los promotores del campus seleccionado en la búsqueda.
	- Comisión coordinador: Se obtiene del cálculo con respecto al porcentaje de comisión que el coordinador tiene asignado la comisión que reciben sus promotores asociados. Ver anexo de cálculo de comisiones CC-1
	- Periodo de inscripción: Periodo en el que se inscribe el alumno a la división de Licenciaturas Presenciales, obtenido de la forma de Banner SOVLCUR
	- Alumno: Nombre completo del alumno inscrito a la división de Licenciaturas Presenciales , obtenido de la forma de Banner SPAIDEN
		- Es necesario mostrar la matrícula del alumno abajo de su nombre
	- Fecha de pago: Fecha en que el alumno realiza el pago de su inscripción a la división de Licenciaturas Presenciales, obtenido de la forma de Banner TSAAREV
	- Tipo de pago: Especifica el método del pago del la inscripción (crédito, contado) a la división de Licenciaturas Presenciales, obtenido de la forma de Banner SGASTDN
	- % Beneficios: Especifica el porcentaje sobre el valor de contrato equivalente a los beneficios (descuento) que tiene el alumno, si es que aplica.
		- Si el alumno inscrito no cuenta con beneficios, el monto debe mostrarse como 0
	- Valor de contrato: Monto a pagar por la inscripción a la división de Licenciaturas Presenciales , obtenido de la forma de Banner TSAAREV
		- Deberá contener un link al estado de cuenta del alumno
	- Valor real de contrato: Se calcula sobre el valor de contrato menos los beneficios obtenidos. Se debe almacenar este valor para los cálculos de comisiones.
	- Fecha de autorización: Fecha en la que el Director de Campus autorizó la comisión en el módulo de Autorización 
		- La fecha deberá aparecer en formato dd/mm/aa
	- Estatus: Se refiere al estatus de la comisión mostrada, pudiendo ser alguno de los siguientes:
		- Autorizada: Comisiones que se autorizaron en el módulo de Autorización
		- Rechazada: Comisiones que se denegaron por algún motivo en el módulo de Autorización
		- Por autorizar: Comisiones que se muestran en el módulo de autorización, pero que aún no se autorizan


**La información mostrada en las tablas se podrá descargar en un archivo .xls al hacer click en el botón “Exportar XLS”**


###Especificaciones técnicas 

- No. Alumnos inscritos: Se requiere mostrar el total de alumnos inscritos por el promotor en periodo de tiempo seleccionado en la búsqueda. En el caso de los coordinadores, el número de alumnos inscritos debe ser la sumatoria de los alumnos inscritos por los promotores que tiene asociados en el periodo de tiempo seleccionado en la búsqueda
- Comisión total: Se requiere hacer la sumatoria del monto almacenado del cálculo que corresponde a la comisión del promotor de acuerdo al periodo de tiempo seleccionado en la búsqueda, y todos los alumnos que inscribió en este tiempo. Para calcular el monto de comisión correspondiente, se sigue el proceso del cálculo de comisiones corrientes.   Ver anexo de cálculo de comisiones CC-1