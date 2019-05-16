#Alta de usuarios

##CP-10 Registrar usuarios 

###Función

Como Administrador
quiero dar de alta a usuarios
de tal forma que puedan utilizar la aplicación de comisiones 

###Criterios de aceptación

- El alta de usuarios se realiza sobre el usuario buscado 
	- Se debe mostrar el nombre completo y matrícula (ID) del usuario encontrado
	- Si el usuario no es encontrado en banner, se debe mostrar un mensaje con la leyenda “Usuario inexistente” 
- Para el alta de usuario se debe asignar:
	- Campus: Mostrando nombre y código del campus
	- Rol: Nombre del rol a asignar:
	- Administrador: Asignar roles, asignar comisiones fijas, campañas, asignar comisiones crecientes, consulta y reportes de comisiones, SICOSS. 
	- Director de campus: Autorización, consulta y reportes de comisiones
	- Administrador SICOSS: SICOSS
	- Coordinador: Encargado de la coordinación de promotores dentro del campus al que pertenece, y por lo que recibe una comisión.
	- Promotor: Encargado del trato con los alumnos para su inscripción, y por lo que recibe una comisión 
		- **Este rol sólo se puede sigan a usuarios que tengan un código (ID) de promotor válido y que están en la forma de Banner SORAROL**
		- **Si el usuario no está registrado como promotor y/o el código no es válido, se debe mostrar un mensaje con la leyenda “El usuario no cumple la precondición en Banner para este rol”**
	- Coordinador de mercadotecnia:Encargado de la coordinación del proceso de promoción de inscripciones , y por lo cuál recibe una comisión.  
- El alta de usuarios se realiza haciendo click en el botón “Registrar”
- Si el usuario ya fue registrado, se podrá eliminar, o si se registró como Coordinador, se puede asociar a un promotor.  

###Especificaciones técnicas

-  Los usuarios a consultar en la búsqueda se obtienen de Banner de la forma SPRIDEN
- El nombre completo y matrícula del usuario buscado se obtiene de Banner en la forma SPRIDEN
- Los usuarios promotores y los códigos de promotor están registrados en la forma de Banner SORAROL
- Los roles asignados a los usuarios se almacenan en la Aplicación de Seguridad
- Los usuarios registrados como promotores, se almacenan en el esquema de promotores 
	- Tabla: PROMOTER
	- Entidad:  PIDM, USER_NAME, RECR_CODE

##CP-5 Disponibilidad de asociación 

###Función

Como Administrador
quiero saber si puedo asociar un usuario a un promotor 
de tal forma que el usuario se pueda asociar a un coordinador (jefe de programa)


###Criterios de aceptación

- Sólo se pueden asociar promotores a usuarios con el rol de Jefe de Programa CE
	- Si el usuario encontrado en la búsqueda, tiene asignado el rol de Jefe de Programa CE, aparecerá un botón con el nombre “Asociar”


###Criterios de aceptación

- La asociación de promotores a coordinadores se almacenan en el esquema de promotores 
- Tabla: PROMOTER
- Entidad:  PIDM, USER_NAME, RECR_CODE, PIDM_MANAGER, USER_NAME_MANAGER, RECR_CODE_MANAGER

##CP-6 Asociar promotores 

###Función

Como Administrador
quiero asociar promotores a un coordinador
de tal forma que los coordinadores y promotores puedan cobrar su porcentaje de comisión 

###Criterios de aceptación

- Si el usuario encontrado en la búsqueda, tiene asignado el rol de coordinador, aparecerá un botón con el nombre “Asociar”, que lo llevará a una vista para la asociación
	- En la vista para asociación, aparecerá la siguiente información del coordinador:
		- Nombre completo del coordinador: Por nombres y apellidos
		- ID: Matrícula de banner
		- Campus: Campus del coordinador
		- Rol: Jefe de Programa CE (Coordinador)
- Sólo se pueden asociar promotores a usuarios con el rol de coordinador. Los promotores que se asocien deben ser del mismo campus que el coordinador.
- Es necesario conocer el estatus de los promotores que se quiere asociar: 
	- “Asociado” si ya está asociado a otro coordinador del mismo campus
	- “Sin asociar” si no ha sido asociado a ningún coordinador
	- “Asociado” (color en verde), si ya fue asociado al promotor consultado
- **Si el promotor ya fue asignado a otro jefe de programa, no se puede asociar y debe mostrar el nombre de usuario del jefe de programa al que ya fue asociado**
- En la vista para asociación, aparecerá la siguiente información de los promotores disponibles en el mismo campus del coordinador:
	- Usuario: Nombre de usuario de banner
	- Nombre: Nombre completo del promotor, por nombres y apellidos
	- Campus: Campus al que pertenece el promotor 
	- Rol: Promotor
	- Estatus: Estatus de la asociación del promotor: 
		- “Asociado” si ya está asociado a otro coordinador del mismo campus
		- “Sin asociar” si no ha sido asociado a ningún coordinador
		- “Asociado” (color en verde), si ya fue asociado al promotor consultado
	- Asociar: Checkbox para que se seleccione a los promotores que se desea asociar al coordinador 
		- **Si el promotor ya fue asignado a otro jefe de programa, no se puede asociar y debe mostrar el nombre de usuario del jefe de programa al que ya fue asociado**
- La asociación de promotores se ejecuta al dar click en el botón “Guardar” 

###Especificaciones técnicas 

- La asociación de promotores a coordinadores se almacenan en el esquema de promotores 
	- Tabla: PROMOTER
	- Entidad:  PIDM, USER_NAME, RECR_CODE, PIDM_MANAGER, USER_NAME_MANAGER, RECR_CODE_MANAGER
- La información de los coordinadores se toma del esquema de jefes de programa
	- Tabla: PROGRAM_MANAGER
	- Entidad:  PIDM, USER_NAME, RECR_CODE

