#Administrador de comisiones
#Comisiones corrientes 

##CP-4 Asignar porcentaje de comisión fija a coordinador

![Porcentaje coordinador](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/Asignar%20comisiones/asignar-comisiones_LI.png)

###Función

Como administrador
quiero asignar el porcentaje de comisión fija a los coordinadores 
de tal forma que incremente la inscripción de alumnos.

###Criterios de aceptación

- Se mostrará la leyenda “Porcentaje de comisión Coord. de Mercadotecnia”  
- Se mostrará una caja de texto en la cual se ingresará el porcentaje
	- En las cajas de texto se ingresarán datos numéricos máximo de tres caracteres 
	- El valor no puede ser mayor o igual 100.
	- Los valores son del 0 al 99
	- No se permite ingresar números con decimales
- Se mostrará un botón de “Actualizar” que permitirá modificar este porcentaje y guardarlo.

![Estructura administrativos](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/Asignar%20comisiones/Estructura%20de%20usuarios%20LP.png)


###Especificaciones técnicas 

- Los montos de porcentaje de comisión para coordinadores se guardan en el esquema de comisiones: 
	- Tabla ADMIN_DE_COMISIONES
	- Entidad: COMISION_COORDINACION  



##CP-3 Asignar porcentaje de comisión a promotores

![Porcentaje promotor](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/Asignar%20comisiones/asignar-comisiones_LI.png)

###Función

Como administrador
quiero asignar el porcentaje de comisión fija a los promotores
de tal forma que incremente la inscripción de alumnos.

###Criterios de aceptación

- Se mostrará la leyenda “Porcentaje de comisión promotores”
- Se mostrará  una caja de texto en la cual se ingresara el porcentaje
	- En las cajas de texto se ingresarán datos numéricos máximo de tres caracteres 
	- El valor no puede ser mayor o igual 100.
	- Los valores son del 0 al 99
	- No se permite ingresar números con decimales 
	***- En el caso del campus CMX, el porcentaje se asigna a dos coordinadores***
- Se mostrará un botón de “Actualizar” que permitirá modificar este porcentaje y guardarlo.

###Especificaciones técnicas 

- Los montos de porcentaje de comisión para promotores se guardan en el esquema de comisiones: 
	- Tabla ADMIN_DE_COMISIONES
	- Entidad: COMISION_EJECUTIVO


##CP-2 Asignar cuota fija por pago de inscripción por campus

![Cuotas campus](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/Asignar%20comisiones/comisiones-campus.png)

###Función

Como administrador
quiero asignar la cuota fija por pago de inscripción a cada campus
de tal forma que los promotores conozcan el valor de contrato.

###Criterios de aceptación

- Se mostrará la leyenda de “Cuota fija por pago de inscripción por campus”
- Se mostrará el listado de todos los campus
- Se mostrará junto a cada campus una caja de texto en la cual se ingresara la cuota fija 
por pago de inscripción.
	- En las cajas de texto se ingresarán datos numéricos máximo de cinco dígitos
	- No se permite ingresar números con decimales
- Se mostrará un botón de “Actualizar” que permitirá modificar este porcentaje y guardarlo.

###Especificaciones técnicas 

- El listado de campus se guardan en el esquema de comisiones: 
	- Tabla ADMIN_DE_COMISIONES
	- Entidad: CAMPUS_DESC
- La cuota fija por pago de inscripción se guardan en el esquema de comisiones:
	- Tabla ADMIN_DE_COMISIONES
	- Entidad: CUOTA_FIJA