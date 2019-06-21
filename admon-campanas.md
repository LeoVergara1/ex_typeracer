# ADMINISTRACIÓN DE CAMPAÑAS 

## CP-27 Registrar campañas 

### Función

Como Administrador
quiero crear campañas
de tal forma que se establezca los periodos de comisiones crecientes


### Criterios de aceptación 

- Se debe ingresar un nombre para identificar la campaña.
	- Debe permitir ingresar letras y números
		- Longitud máx 40
		- Longitud min 15
- Se debe ingresar una clave única para identificar la campaña.
	- Esta debe permitir ingresar letras y números
	- Es necesario que cuente con 5 caracteres
- Los datos de la campaña deben ser almacenados al dar clic sobre el botón “Registrar”.
	- Al momento de registrar la campaña se podrá ingresar las fechas correspondientes.
		- Año: Debe considerar el año actual y el próximo.
		- Fecha inicio: Debe mostrarse un modal en donde se visualice un calendario. 
			- Formato: dd/mm/aaaa
		- Fecha cierre: Debe mostrarse un modal en donde se visualice un calendario. 
			- Formato: dd/mm/aaaa
- Los datos y fechas deben ser almacenadas al dar clic en el botón “Guardar”. 

### Especificaciones técnicas 

- No se cuenta con una base de datos ni estructura.




## CP-28 Buscar campañas 

### Función

Como Administrativo 
quiero buscar campañas previamente registradas 
de tal forma que pueda consultar el detalle de cada una de ellas

### Criterios de aceptación 

- Los criterios de búsqueda será el nombre de la campaña o la clave. 
	- Todos los criterios son opcionales
- Mostrar resultados de búsqueda después de colocar los primeros 5 caracteres dentro del apartado de Nombre y los primeros 2 para el apartado de clave.
- Debe ser posible realizar una búsqueda general al dar clic en el botón “Buscar”.
- Los resultados de la búsqueda se deben mostrar en una tabla con las siguientes columnas: 
	- Clave: Debe mostrarse la clave que se registró para la campaña.
	- Nombre de campaña: Nombre completo de la campaña registrada.
	- Fecha inicio.
	- Fecha cierre.
	- Botón Eliminar.
		- Al dar clic sobre el botón debe mostrar un modal de confirmación.
		- Si en la campaña a eliminar ya cuenta con un registro de comisiones, NO debe permitir la eliminación y se debe mostrar una notificación “Campaña con comisiones activas”.
	- Botón Edita.
		- Al dar clic sobre el botón debe permitir realizar la edición de todas las columnas.
			- Clave
			- Nombre
			- Fechas
		- Si la campaña ya concluyo NO debe permitir la edición y debe mostrar una notificación indicando “Campaña finalizada”. 

### Especificaciones técnicas 

- No se cuenta con una base de datos ni estructura.


## CP-29 Administrar metas por campaña 

### Función

Como Administrador 
quiero asignar metas por campaña 
de tal forma que pueda establecerse las condiciones para el pago de comisiones crecientes


### Criterios de aceptación 

- Criterios de búsqueda.
	- Año: Debe mostrarse el año actual y el posterior.
	- Campaña: Debe mostrase el listado de campañas registradas en el año seleccionado.
- Al dar clic en el botón “Buscar” debe mostrarse una tabla con las siguientes columnas: 
	- Clave
	- Nombre de campaña
	- Fecha inicio
	- Fecha cierre
	- Botón Agregar
		- Al dar clic en el botón agregar debe abrir una nueva pestaña en donde se visualice una tabla con las siguientes columnas:
		- Campus: Deben mostrarse todos los campus pertenecientes a la división de Escuela de Graduados, obtenida de la forma de Banner STVCAMP 
		- Tipo: Deben mostrarse dos radiobuttom con clasificaciones.
			- Inscritos
			- Sentados
		- Número de registros: Debe mostrarse un textbox para ingresar el número de alumnos considerados para comisionar en la campaña.
			- Únicamente debe aceptar números
			- Longitud máx 3 dígitos
			- Longitud min 1 dígito
		- Porcentaje de comisión: Debe mostrarse un textbox para ingresar el porcentaje a comisionar.
			- Debe aceptar números con un decimal
			- Longitud máx 3 caracteres
			- Longitud min 1 caracter
		- Botón de Guardar: Al dar clic debe almacenar los datos ingresados en las columnas anteriores.
			- Si algún campo se encuentra vacío NO podrá ser almacenado y mostrará un mensaje en donde se indique “Todos los campos son obligatorios”.

### Especificaciones técnicas 

- No se cuenta con una base de datos ni estructura.

