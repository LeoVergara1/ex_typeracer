#Cálculo comisiones corrientes (LE, LI, PG)


####1. Los registros de alumnos de nuevo ingreso deben ser consultados de acuerdo a las siguientes consideraciones de inscripción:


**No** se deben mostrar todos los registros de comisiones de los promotores asignados al coordinador que consulta siempre y cuando se cumpla con las siguientes reglas 

- Registros que no cuenten con un promotor asignado 
- Registros de alumnos que no cuenten con valor de contrato 
- Promotores que no se hayan asociado a un coordinador a través de la aplicación de comisiones Ejecutivas
- Alumnos que no se encuentren inscritos correctamente en Banner (no tienen el estatus “LEARNER”) 
- Alumnos que no cuenten con una currícula activa 
- Alumnos que no pertenezcan a la división de Licenciatura Ejecutiva
- Alumnos que no sean de nuevo ingreso   

**Sí** se deben mostrar todos los registros de comisiones de los promotores asignados al coordinador que consulta siempre y cuando se cumpla con las siguientes reglas: 

- Alumnos que cuenten con pago completo por el valor real de contrato
- Para aquellos registros que no estén dentro de los criterios mencionados en el punto anterior  
- Alumnos que no cuenten con pago completo por el valor de contrato 

####2.- Una vez identificados los registros de alumnos de nuevo ingreso que cumplan con las consideraciones de inscripción, se debe validar su tipo de pago:

#####2.1 Pago a crédito. 

Se identifica el monto por el **valor de contrato** de la inscripción de cada uno de los alumnos de nuevo ingreso  

Posteriormente, se debe verificar si el alumno cuenta con beneficios (beca):

Si cuenta **con beneficios**, se debe restar el monto del **valor de contrato menos el monto de los beneficios** , y guardar esta cantidad como el **valor real de contrato.** 

Después, se debe obtener el **monto por el pago inicial** realizado por el alumno, y verificar que el **alumno cuente con pago** y que éste sea **mayor o igual al monto requerido para el pago inicial.** 
	Si se cumple ésta última condición, se multiplica el **valor real de contrato por el porcentaje de comisión asignado al promotor.**  Esta **comisión al promotor se multiplica por el porcentaje de comisión asignado al coordinador** para determinar su comisión. 

Si el alumno no cuenta con pago, o si éste no es mayor o igual al monto del pago inicial, el tipo de pago no es válido y no se puede calcular.

Si **no cuenta con beneficios**, se debe obtener el **monto por el pago inicial** realizado por el alumno, y verificar que el alumno cuente con  pago y que éste sea mayor o igual al monto requerido para el pago inicial. 
	Si se cumple ésta última condición, se multiplica el **valor de contrato por el porcentaje de comisión asignado al promotor.**  Esta **comisión al promotor se multiplica por el porcentaje de comisión asignado al coordinador** para determinar su comisión. 

Si el alumno no cuenta con pago, o si éste no es mayor o igual al monto del pago inicial, el tipo de pago no es válido y no se puede calcular.

#####2.1 Pago de contado. 

Se identifica el monto por el **valor de contrato** de la inscripción de cada uno de los alumnos de nuevo ingreso  
Posteriormente, se debe verificar si el alumno cuenta con beneficios (beca):

Si cuenta **con beneficios**, se debe restar el monto del **valor de contrato menos el monto de los beneficios** , y guardar esta cantidad como el **valor real de contrato**. Este **valor real de contrato se divide entre 6** (por las parcialidades de pago). 

Después, se debe verificar que el **alumno cuente pago** y que éste sea **mayor o igual al valor del valor real de contrato dividido entre 6.**
	Si se cumple ésta última condición, se multiplica el **valor real de contrato dividido por el porcentaje de comisión asignado al promotor.**  Esta **comisión al promotor se multiplica por el porcentaje de comisión asignado al coordinador** para determinar su comisión. 

Si el alumno no cuenta con pago, o si éste no es mayor o igual al valor real de contrato  dividido entre 6, el tipo de pago no es válido y no se puede calcular.

***El proceso descrito anteriormente se muestra gráficamente en los diagramas 1-Cálculo-Comisiones corrientes.pdf y 2-Cálculo-Comisiones corrientes.pdf*** 