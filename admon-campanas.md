# ADMINISTRACIÓN DE CAMPAÑAS 

## CP-27 Registrar campañas 

### Función

Como Administrador
quiero crear campañas
de tal forma que contengan los trimestres para el pago de comisiones crecientes


### Criterios de aceptación 

- Para cada año, se requieren dos campañas asignadas a un periodo:
  - Enero-Agosto: Corresponde al periodo 20 de licenciaturas presenciales en Banner.
  - Julio-Diciembre: Corresponde al periodo 40 de licenciaturas presenciales en Banner 
- Las campañas contendrán la siguiente información mostrada en una tabla:
  - Nombre: Nombre asignado a la campaña 
    - Debe permitir ingresar letras y números
      - Longitud máx 40
      - Longitud min 15
  - Periodo: Periodo de clases relacionado a la campaña
  - Año: Año de creación de la campaña
  - Estado: Indica si la campaña está activa para el cálculo de comisiones crecientes 
  - Fecha inicio: Fecha en la que empieza a correr la campaña
  - Fecha fin: Fecha en la que termina la campaña
  - Editar: Acción que permite modificar el nombre y fecha de incio y fin de la campaña.
    - **Se deben mostrar los datos de las campañas creadas en el año en curso, el anterior y del año siguiente que abarque el periodo de tiempo de las campañas disponibles.**    

### Especificaciones técnicas 

- Los datos solicitados para registrar las campañas, se guardan en el esquema de COMISIONES PRESENCIALES:
	- User: pgocomis
	- Tabla: CAMPAIGN
	- EntidadES: ID, STATUS, INIT_DATE, END_DATE, NAME, CLAVE, USERNAME, PERIOD, DATE_CREATED, LAST_UPDATED, YEAR



# ADMINISTRACIÓN DE TRIMESTRES 



## CP-2 Registrar trimestres

### Función

Como Administrador
quiero crear trimestres
de tal forma que se establezca los periodos de pago de las campañas de comisiones crecientes

### Criterios de aceptación    

- **Los trimestres serán asociados a las campañas existentes por medio del periodo que se les asigne:**
  - Enero-Agosto: Corresponde al periodo 20 de licenciaturas presenciales en Banner.
  - Julio-Diciembre: Corresponde al periodo 40 de licenciaturas presenciales en Banner 
- Se debe ingresar un nombre para identificar cada trimestre.

  - Debe permitir ingresar letras y números
    - Longitud máx 40
    - Longitud min 15
- Se debe ingresar una clave única para identificar la campaña.

  - Esta debe permitir ingresar letras y números
  - Es necesario que cuente con 5 caracteres
- Se debe seleccionar el periodo al que pertenece el trimestre creado (20 o 40)
- Se debe verificar primeramente que la campaña no se encuentre registrada previamente, a través del "Buscar"

  - Si el trimestre ya se encuentra registrado, se mostrará en una tabla el detalle de si información,
  - Si el trimestre no está registrado,  los datos de la campaña deben ser almacenados al dar clic sobre el botón “Guardar”.

  - Al momento de registrar el trimestre se podrá ingresar las fechas correspondientes.
    - Año: Debe considerar el año actual y el próximo.
    - Fecha inicio: Debe mostrarse un modal en donde se visualice un calendario. 
      - Formato: dd/mm/aaaa
    - Fecha fin: Debe mostrarse un modal en donde se visualice un calendario. 
      - Formato: dd/mm/aaaa
- Los datos y fechas deben ser almacenadas al dar clic en el botón “Guardar”. 

### Especificaciones técnicas 

- Los datos solicitados para registrar los trimestres, se guardan en el esquema de COMISIONES PRESENCIALES:
  - User: pgocomis
  - Tabla: TRIMESTER
  - Entidades: ID, STATUS, INIT_DATE, END_DATE, NAME, YEAR, CLAVE, USERNAME, PERIOD, DATE_CREATED, LAST_UPDATED



## CP-28 Trimestres registrados

### Función

Como Administrativo 
quiero buscar trimestres previamente registrados 
de tal forma que pueda consultar el detalle de cada una de ellos

### Criterios de aceptación 

- Los criterios de búsqueda será el nombre del trimestre o la clave. 
	- Todos los criterios son opcionales
- Mostrar resultados de búsqueda después de colocar los primeros 5 caracteres dentro del apartado de Nombre y los primeros 2 para el apartado de clave.
- Debe ser posible realizar una búsqueda general al dar clic en el botón “Buscar”.
- Los resultados de la búsqueda se deben mostrar en una tabla con las siguientes columnas: 
	- Clave: Debe mostrarse la clave que se registró para el trimestre.
	- Nombre del trimestre: Nombre completo de la campaña registrada.
	- Fecha inicio.
	- Fecha cierre.
	- Botón Eliminar.
		- Al dar clic sobre el botón debe mostrar un modal de confirmación.
		- Si el trimestre a eliminar ya cuenta con un registro de comisiones, NO debe permitir la eliminación y se debe mostrar una etiqueta “Trimestre con comisiones activas”.
	- Botón Editar.
		- Al dar clic sobre el botón debe permitir realizar la edición de todas las columnas.
			- Clave
			- Nombre
			- Fechas
		- Si el trimestre ya concluyó NO debe permitir la edición y debe mostrar una notificación indicando “Trimestre finalizado”. 

### Especificaciones técnicas 

- Los datos solicitados para registrar los trimestres, se guardan y actualizan (editar, eliminar) en el esquema de COMISIONES PRESENCIALES:
	- User: pgocomis
	- Tabla: TRIMESTER
	- Entidades: ID, STATUS, INIT_DATE, END_DATE, NAME, YEAR, CLAVE, USERNAME, PERIOD, DATE_CREATED, LAST_UPDATED


## CP-29 Administrar metas por trimestre 

### Función

Como Administrador 
quiero asignar metas por trimestre
de tal forma que pueda establecerse las condiciones para el pago de comisiones crecientes


### Criterios de aceptación 

- Criterios de búsqueda.
	- Año: Debe mostrarse el año actual y el posterior.
	- Trimestre: Debe mostrase el listado de trimestres registrados en el año seleccionado, **concatenando la clave y el nombre del trimestre**
- Al dar clic en el botón “Buscar” debe mostrarse una tabla con las siguientes columnas: 
	- Clave
	- Nombre de trimestre
	- Fecha inicio
	- Fecha fin
	- Metas: botón Agregar
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

- Los datos solicitados para registrar las metas, se guardan en el esquema de COMISIONES PRESENCIALES:
	- User: pgocomis
	- Tabla: GOAL
	- EntidadES: ID, STATUS, TYPE, CAMPUS, NUM_REGISTERS, PERCET_COMMISSION, USERNAME, DATE_CREATED, LAST_UPDATED, CAMPAING_ID
