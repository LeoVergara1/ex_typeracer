# CONSULTA DE COMISIONES CRECIENTES  

## CP-34 Consulta de comisiones crecientes 

### Función

Como Usuario dentro de la plataforma
quiero consultar los reportes de comisiones 
de tal forma que pueda exportar y saber las comisiones y sus estatus


### Criterios de aceptación 

- Dentro del módulo de consulta de comisiones debe contar con filtros de búsqueda.
	- Criterios de búsqueda.
		- Campus
			- Para el rol de administración, debe contar con la posibilidad de visualizar todos los campus.
			- Rol Jefe / Director: Debe visualizar únicamente el campus al que pertenece y se encuentra registrado.
		- Tipo de reporte: Listado de reportes
			- Detallado: Reporte con las siguientes columnas:
				- Tipo de comisión
				- Promotor
				- Comisión promotor
				- Jefe
				- Comisión jefe
				- Periodo
				- Alumno
				- Fecha de pago
				- Tipo de pago
				- Valor de contrato
				- % Beneficio
				- Valor real de contrato
				- Estatus
					- Autorizado
					- Por autorizar
					- Rechazado
						- Al dar clic en este estatus debe mostrar los motivos de rechazo que fueron colocados en el módulo de “autorización de comisiones “.
			- Global: Debe mostrarse la sumatoria de comisión por administrativo a comisionar, debe contener las siguientes columnas:
				- Puesto
				- Nombre
				- N° de alumnos inscritos
				- Periodo: Mes al que pertenece el pago
				- Comisión total
					- Debe mostrarse el resultado de la sumatoria de todas las comisiones autorizadas del administrativo a comisionar
			- Por usuario
				- Detallado: Reporte del administrativo seleccionado, debe mostrar las siguientes columnas:
					- Tipo de comisión
					- Promotor
					- Comisión promotor
					- Jefe
					- Comisión jefe
					- Periodo
					- Alumno
					- Fecha de pago
					- Tipo de pago
					- Valor de contrato
					- % Beneficio
					- Valor real de contrato
					- Estatus
						- Autorizado
						- Por autorizar
						- Rechazado
							- Al dar clic en este estatus debe mostrar los motivos de rechazo que fueron colocados en el módulo de “autorización de comisiones “.
				- Global: Debe mostrarse el resultado de la sumatoria de todas las comisiones autorizadas del administrativo a comisionar
		- Tipo de comisión: Debe mostrar Comisión creciente y Comisión creciente.
		- Estatus: Debe contar con tres estatus
			- Autorizado: Mostrar registros de comisiones que ya cuenta con la autorización del director de campus.
			- Por autorizar: Mostrar registro de comisiones que se encuentra sin check de autorización.
			- Rechazado: Mostrar registro de comisiones que fue rechazado en el módulo de “Autorización de comisiones”.
		- Fecha inicio: Debe contar con un modal de un calendario para seleccionar la fecha inicio. 
			- Formato dd/mm/aaaa
			- Fecha fin: Debe contar con un modal de un calendario para seleccionar la fecha fin.
			- Formato dd/mm/aaaa

### Especificaciones técnicas 

- No se cuenta con una base de datos ni estructura.
