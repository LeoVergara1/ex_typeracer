#Alta de usuarios

##CP-10 Registrar usuarios 

![Registrar usuarios](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/Registrar%20usuarios/registro.png)

###Función

Como Administrador
quiero dar de alta a usuarios
de tal forma que puedan utilizar la aplicación de comisiones con un rol dado 

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
		**-Sólo se puede asignar un usuario con el rol de coordinador por campus, EXCEPTO EN EL CAMPUS CMX** 
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
- Los usuarios registrados como promotores, se almacenan en el esquema de asociación de promotores
	- User: pgocomis 
	- Tabla: ASOCIACION_PROMOTOR
	- Entidad: ID_PROMOTOR, NOMBRE_PROMOTOR, APELLIDOS_PROMOTOR, CLAVE_EMP_PROMOTOR, PUESTO_PROMOTOR

- Los usuarios registrados como coordinadores, se almacenan en el esquema de asociación de promotores 
	- User: pgocomis 
	- Tabla: ASOCIACION_PROMOTOR
	- Entidad: ID_COORDINADOR, NOMBRE_COORDINADOR, APELLIDOS_COORDINADOR, CLAVE_EMP_COORDINADOR

- Los usuarios registrados como jefes de programa, se almacenan en el esquema de asociación de promotores 
	- User: pgocomis 
	- Tabla: ASOCIACION_PROMOTOR
	- Entidad:  PIDM_MANAGER, USER_NAME_MANAGER ID_COORDINADOR, RECR_CODE_MANAGER

**Reglas para dar de alta a un usuario:***

- Válida si es promotor  o manager (coordinador) 
- Válida registro de promotor (Va a banner y verifica el código para los dos casos)
- Válida registra el rol en la aplicación de seguridad
- Verifica el username exista en seguridad 
- Verifica si el username se encuentra en banner
- Intenta crear el usuario en la aplicación de seguridad
- Asigna el rol al usuario en la aplicación de seguridad
- Verifica el código de rol para crear un manager o un promotor
- Si es promotor valida que sea candidato para registro con recrCode
- Valida que el usuario se encuntre en seguridad
- Validad que se pueda crear el usuario en seguridad
- Valida promotersRadmCode=EMRECR

##CP-5 Disponibilidad de asociación 

![Disponibilidad para asociar](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/Registrar%20usuarios/disponibilidad.png)

###Función

Como Administrador
quiero saber si puedo asociar promotores a un coordinador
de tal forma que el usuario se pueda asociar para el pago de comisiones


###Criterios de aceptación

- Sólo se pueden asociar promotores a usuarios con el rol de Coordinador
	- Si el usuario encontrado en la búsqueda, tiene asignado el rol de Jefe de Programa CE, aparecerá un botón con el nombre “Asociar”


![Estructura administrativos LI](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/Asignar%20comisiones/Estructura%20de%20usuarios%20LP.png)

###Especificaciones técnicas

- La asociación de promotores a coordinadores y jefes de programa, se almacenan en el esquema de asociación de promotores
	- User: pgocomis 
	- Tabla: ASOCIACION_PROMOTOR
	- Entidad: ID_PROMOTOR, NOMBRE_PROMOTOR, APELLIDOS_PROMOTOR, CLAVE_EMP_PROMOTOR, PUESTO_PROMOTOR, IDM_MANAGER, USER_NAME_MANAGER ID_COORDINADOR, RECR_CODE_MANAGER, NOMBRE_COORDINADOR, APELLIDOS_COORDINADOR, CLAVE_EMP_COORDINADOR, PIDM_MANAGER, USER_NAME_MANAGER ID_COORDINADOR, RECR_CODE_MANAGER

##CP-6 Asociar promotores 

![Asociación](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/Registrar%20usuarios/asociacion.png)

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
		- Rol: Coordinador
- Sólo se pueden asociar promotores a usuarios con el rol de coordinador. Los promotores que se asocien deben ser del mismo campus que el coordinador.
- Es necesario conocer el estatus de los promotores que se quiere asociar: 
	- “Asociado” si ya está asociado a otro coordinador del mismo campus
	- “Sin asociar” si no ha sido asociado a ningún coordinador
	- “Asociado” (color en verde), si ya fue asociado al promotor consultado
- **Si el promotor ya fue asignado a otro coordinador, no se puede asociar y debe mostrar el nombre de usuario del jefe de programa al que ya fue asociado**
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

- La asociación de promotores a coordinadores y jefes de programa, se almacenan en el esquema de asociación de promotores
	- User: pgocomis 
	- Tabla: ASOCIACION_PROMOTOR
	- Entidad: ID_PROMOTOR, NOMBRE_PROMOTOR, APELLIDOS_PROMOTOR, CLAVE_EMP_PROMOTOR, PUESTO_PROMOTOR, IDM_MANAGER, USER_NAME_MANAGER ID_COORDINADOR, RECR_CODE_MANAGER, NOMBRE_COORDINADOR, APELLIDOS_COORDINADOR, CLAVE_EMP_COORDINADOR, PIDM_MANAGER, USER_NAME_MANAGER ID_COORDINADOR, RECR_CODE_MANAGER


##CP-24 Búsqueda de usuarios registrados

![Búsqueda de usuarios](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/Registrar%20usuarios/busqueda.png)

###Función

Como Administrador
quiero buscar usuarios registrados
de tal forma que pueda ver su información y poder eliminarlo o asociarlo

###Criterios de aceptación 

Para la búsqueda del usuario, se deben de considerar los siguientes criterios de búsqueda obligatorios: 

- Usuario: Nombre de usuario de banner 
- Campus: Mostrando nombre y código del campus
- Rol: Nombre del rol asignado:
	- Administrador comisiones: Asignar roles, asignar comisiones fijas, campañas, asignar comisiones crecientes, consulta y reportes de comisiones, SICOSS.
	- Director de campus: Autorización, consulta y reportes de comisiones
	- Administrador SICOSS: SICOSS
	- Coordinador: Encargado de la coordinación de promotores dentro del campus al que pertenece, y por lo que recibe una comisión.
	- Promotor: Encargado del trato con los alumnos para su inscripción, y por lo que recibe una comisión

Una vez que encuentra al usuario, se debe mostrar la siguiente información sobre éste en una tabla: 
- Usuario: Nombre de usuario de banner 
- Nombre: Nombre completo del usuario, empezando por nombre, apellido paterno y apellido materno 
- Campus: Mostrando nombre del campus
- Rol: Nombre del rol asignado
- Eliminar: Botón para dar de baja al usuario buscado 
- Asociar: Este botón sólo se muestra si el usuario buscado es un coordinador

###Especificaciones técnicas

-  Los usuarios a consultar en la búsqueda se obtienen de Banner de la forma SPRIDEN
- El nombre completo y matrícula del usuario buscado se obtiene de Banner en la forma SPRIDEN
- Los usuarios promotores y los códigos de promotor están registrados en la forma de Banner SORAROL
- Los roles asignados a los usuarios se almacenan en la Aplicación de Seguridad
- Los usuarios registrados como promotores, se almacenan en el esquema de asociación de promotores
	- User: pgocomis 
	- Tabla: ASOCIACION_PROMOTOR
	- Entidad: ID_PROMOTOR, NOMBRE_PROMOTOR, APELLIDOS_PROMOTOR, CLAVE_EMP_PROMOTOR, PUESTO_PROMOTOR

- Los usuarios registrados como coordinadores, se almacenan en el esquema de asociación de promotores 
	- User: pgocomis 
	- Tabla: ASOCIACION_PROMOTOR
	- Entidad: ID_COORDINADOR, NOMBRE_COORDINADOR, APELLIDOS_COORDINADOR, CLAVE_EMP_COORDINADOR

- Los usuarios registrados como jefes de programa, se almacenan en el esquema de asociación de promotores 
	- User: pgocomis 
	- Tabla: ASOCIACION_PROMOTOR
	- Entidad:  PIDM_MANAGER, USER_NAME_MANAGER ID_COORDINADOR, RECR_CODE_MANAGER