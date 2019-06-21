# AUTORIZACIÓN DE COMISIONES CRECIENTES  

## CP-30 Visualizar comisiones crecientes 

### Función

Como Director de Campus y Administrador 
quiero saber que comisiones crecientes existen 
de tal forma que pueda validar las comisiones crecientes que se deben pagar


### Criterios de aceptación 

- Criterios de búsqueda.
	- Campus
		- Como Administrador de la plataforma debe contar con la opción de elección de campus.
		- Como Director de Campus, debe contar únicamente con el campus al que pertenece.
	- Fecha inicio
		- Debe contar con un modal con un calendario
		- Formato dd/mm/aaaa
	- Fecha fin
		- Debe contar con un modal con un calendario
		- Formato dd/mm/aaaa
	- Debe contar con el botón “Buscar”
		- Al dar clic sobre el botón, debe mostrarse un modal con las campañas pertenecientes al rango de fechas seleccionado.
			- Solo se podrá seleccionar una a la vez
			- Se deben visualizar las campañas que ya hayan concluido y que se encuentren en el rango de fechas seleccionadas. 

- Se debe mostrar una tabla con las siguientes columnas:
	- Todos los datos mostrados en esta tabla deben ser pertenecientes a la división de Licenciatura Presencial.
		- Campaña: Mostrar el nombre de la campaña consultada.
		- Promotor: Mostrar el nombre completo del promotor y su número de empleado, obtenido de la forma de Banner SPRIDEN
			- Administrativo que comisiona sobre el porcentaje registrado.
	- Comisión promotor: Monto obtenido del cálculo.
	- Jefe: Nombre completo y número de empleado del Jefe de promoción.
		- Administrativo que comisiona sobre el monto de comisión del promotor
	- Comisión jefe: Monto obtenido del cálculo.
	- Periodo de inscripción
	- Alumno: Nombre completo del alumno y matricula, obtenida de la forma de Banner SPRIDEN
	- Fecha de pago: Fecha en la que el alumno liquido su pago inicial, obtenido de forma de Banner TSAAREV. 
	- Colegiatura inicial: Monto total del pago inicial, obtenido de forma de Banner TSAAREV.
	- % Beneficios: Porcentaje de descuento asignado al alumno, obtenido de la forma de Banner TWADESC.
	- Colegiatura inicial real: Resultado del cálculo entre la colegiatura inicial – los beneficios asignados.
	- Autorizar: Debe mostrar un checkbox para la selección de la comisión.
	- Botón de rechazar: Al dar clic sobre el botón, debe mostrar un modal en donde solicite la razón del rechazo.
		- Caracteres aceptados
			- Máx 300
			- Min 15
		- Debe contar con un botón
			- Aceptar
				- Para poder realizar esta acción es necesario contar con la razón del rechazo, de lo contrario mostrar mensaje “Obligatorio ingresar motivo de rechazo”.
				- Al dar clic en el botón ebe desaparecer de la vista de autorización.
			- Cerrar
				- Regresa a la vista de comisiones previamente consultadas
	- Todas las comisiones mostradas en la tabla deben cumplir con las reglas del cálculo de comisiones crecientes. 


### Especificaciones técnicas 

- Los datos necesarios para las comisiones que sean autorizadas, se guardan en el esquema de COMISIONES PRESENCIALES:
	- User: pgocomis
	- Tabla: AUTORIZACION_COMISIONES
	- Entidades: ID, CAMPUS, ID_PROMOTOR, NOMBRE_PROMOTOR, PUESTO, ID_ALUMNO, NOMBRE_ALUMNO, PAGO_INICIAL, TOTAL_DESCUENTOS, COMISION, PERIODO, FECHA_DE_PAGO, AUTORIZADO_DIRECTOR, DATE_CREATED, LAST_CREATED, ID_COORDINADOR, COMISION_COORDINADOR, FECHA_AUTORIZADO, USUARIO



## CP-31 Autorizar comisiones crecientes 

### Función

Como Director de Campus
quiero saber que comisiones crecientes existen 
de tal forma que pueda autorizar las comisiones a pagar

### Criterios de aceptación 

- Para autorizar las comisiones crecientes es necesario seleccionar los checks de la columna “Autorizar”.
	- Todas las comisiones que se hayan seleccionado deben contener el estatus “Autorizadas” y serán consideradas en el procesamiento del archivo de SICOSS para su pago.
	- Si una comisión ya fue autorizada ya no debe mostrarse en el módulo de Autorización de comisiones
	- Si una comisión no ha sido seleccionada debe recalcularse cada que se realice la consulta.
- Debe tener un botón de “Actualizar”, una vez seleccionados los checks de las comisiones es necesario dar clic en este botón para considerar las comisiones como autorizadas.
- Únicamente puede autorizar el rol de Director de campus

### Especificaciones técnicas 

- Los datos necesarios para las comisiones que sean autorizadas, se guardan en el esquema de COMISIONES PRESENCIALES:
	- User: pgocomis
	- Tabla: AUTORIZACION_COMISIONES
	- Entidades: ID, CAMPUS, ID_PROMOTOR, NOMBRE_PROMOTOR, PUESTO, ID_ALUMNO, NOMBRE_ALUMNO, PAGO_INICIAL, TOTAL_DESCUENTOS, COMISION, PERIODO, FECHA_DE_PAGO, AUTORIZADO_DIRECTOR, DATE_CREATED, LAST_CREATED, ID_COORDINADOR, COMISION_COORDINADOR, FECHA_AUTORIZADO, USUARIO



## CP-32 Ver resumen de autorización - Director de campus

### Función

Como Director de campus
quiero ver el resumen de los ingresos por las comisiones
de tal forma que visualice cuánto y a quién se les podrá autorizar el pago


### Criterios de aceptación 

Los datos del director de campus se muestran en la parte superior izquierda de la pantalla, con la siguiente información:
	- Nombre completo del director de campus Nombre(s) + Apellido Paternos + * + Apellido Materno del director que inicia sesión
	- Rol: Rol del usuario que consulta (Director de campus)
	- Campus al que pertenece el director que inicia sesión
	- Periodo: Rango de tiempo abarcado en los filtros de búsqueda de las comisiones calculadas  
	- Se mostrará un recuadro para el monto total a pagar, identificado como “Comisión a pagar” 
		- El monto a mostrar es el que se obtiene en la sumatoria de las comisiones de los promotores 
	- Se mostrará un recuadro para el total de ingresos, identificado como “Ingresos” 
		- El total de ingresos a mostrar es el que se obtiene de la sumatoria de alumnos inscritos por los promotores correspondientes al campus asignado.
		- Los resultados se mostrarán en dos vistas: Una con todos los promotores y otra vista para cada promotor.
			- Si se muestra la vista por cada promotor se deberá mostrar el resumen por el total de sus comisiones e ingresos:
				- Se mostrará un recuadro para el monto total a pagar, identificado como “Comisión a pagar” 
				- El monto a mostrar es el que se obtiene en el cálculo sobre el porcentaje de comisión del promotor respecto a la suma del valor real de contrato de todos los alumnos inscritos en el periodo activo
				- Se mostrará un recuadro para el total de ingresos, identificado como “Ingresos”
				- El total de ingresos a mostrar es el que se obtiene de la sumatoria de alumnos inscritos por el promotor consultado 

### Especificaciones técnicas 

- Para la sección de “Comisión a pagar” se debe consultar el cálculo de comisiones crecientes, referente al cálculo del porcentaje de comisión asignado al coordinador del campus del Director sobre el total obtenido de las inscripciones de sus promotores durante el periodo activo.
			- Tabla: ADMIN_DE_COMISIONES
			- Entidades: COMISION_COORDINADOR, COMISION, EJECUTIVO, CAMPUS_CODE, CUOTA_FIJA

			- Tabla:ASOCIACION_PROMOTOR
			- Entidades: ID_COORDINADOR, ID, PROMOTOR, CAMPUS_CODE, RECR_CODE_MANAGER, CLAVE_EMP_COORDINADOR, CLAVE_EMP_PROMOTOR 		
 Para la sección de “Ingresos”, el total se obtiene de la suma de todos los registros mostrados de todos los promotores asignados al coordinador del campus del Diretor


## CP-33 Ver resumen de autorización - Jefe de promoción

### Función

Como Jefe de promoción
quiero ver el resumen de los ingresos por las comisiones
de tal forma que visualice cuánto y a quién se les podrá autorizar el pago

### Criterios de aceptación 


- Los datos del jefe de campus se muestran en la parte superior izquierda de la pantalla, con la siguiente información:
	- Nombre completo del jefe de campus Nombre(s) + Apellido Paternos + * + Apellido Materno del director que inicia sesión
	- Rol: Rol del usuario que consulta (Jefe de promoción)
	- Campus al que pertenece el jefe que inicia sesión
	- Periodo: Rango de tiempo abarcado en los filtros de búsqueda de las comisiones calculadas
	- Se mostrará un recuadro para el monto total a pagar, identificado como “Comisión a pagar”
		- El monto a mostrar es el que se obtiene en el cálculo sobre el porcentaje de comisión del Jefe de campus respecto a las comisiones de sus promotores asignados
	- Se mostrará un recuadro para el total de ingresos, identificado como “Ingresos”
		- El total de ingresos a mostrar es el que se obtiene de la sumatoria de alumnos inscritos por los promotores asociados al Jefe del campus
		- Los resultados se mostrarán en dos vistas: Una con todos los promotores y otra vista para cada promotor.
			- Si se muestra la vista por cada promotor se deberá mostrar el resumen por el total de sus comisiones e ingresos:
				- Se mostrará un recuadro para el monto total a pagar, identificado como “Comisión a pagar”
				- El monto a mostrar es el que se obtiene en el cálculo sobre el porcentaje de comisión del promotor respecto a la suma del valor real de contrato de todos los alumnos inscritos en el periodo activo
				- Se mostrará un recuadro para el total de ingresos, identificado como “Ingresos”
				- El total de ingresos a mostrar es el que se obtiene de la sumatoria de alumnos inscritos por el promotor consultado 

### Especificaciones técnicas 

- Para la sección de “Comisión a pagar” se debe consultar el cálculo de comisiones crecientes, referente al cálculo del porcentaje de comisión asignado al coordinador del campus del Jefe de promoción sobre el total obtenido de las inscripciones de sus promotores durante el periodo activo.
			- Tabla: ADMIN_DE_COMISIONES
			- Entidades: COMISION_COORDINADOR, COMISION, EJECUTIVO, CAMPUS_CODE, CUOTA_FIJA

			- Tabla:ASOCIACION_PROMOTOR
			- Entidades: ID_COORDINADOR, ID, PROMOTOR, CAMPUS_CODE, RECR_CODE_MANAGER, CLAVE_EMP_COORDINADOR, CLAVE_EMP_PROMOTOR 		
 Para la sección de “Ingresos”, el total se obtiene de la suma de todos los registros mostrados de todos los promotores asignados al coordinador del campus del Jefe de promoción


