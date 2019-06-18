#Cálculo comisiones crecientes (LI, Posgrado)

![Diagrama cálculo comisiones crecientes](https://documentacionebc.s3.amazonaws.com/Im%C3%A1genes%20Comisiones%20EBC/LI/Autorizaci%C3%B3n/C%C3%A1lculo-Crecientes.png)

El cálculo de las comisiones crecientes se realiza siempre al final de trimestre de la campaña correspondiente y para cada campus, de acuerdo al siguiente proceso:

**1. Los registros de alumnos de nuevo ingreso en el trimestre considerado deben ser consultados de acuerdo a las siguientes consideraciones de inscripción:**

**Los registros de comisiones que estén en las siguientes condiciones se consideran como no válidos, y depende de las reglas de negocio consideradas para cada división si serán mostrados para su registro/autorización:**  

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

**2. Se deben buscar los registros autorizados válidos de cada campus y que correspondan al trimestre de  la campaña correspondiente** 

**3. Para el caso del primer trimestre de la campaña activa,  por cada campus se consulta el número de alumnos inscritos por los promotores y se verifica si el número de alumnos inscritos es igual o mayor a la meta establecida para cada campus en el módulo de las campañas de comisiones crecientes.**

Si se cumple esta condición, **se obtiene el número de alumnos inscritos por cada campus y se debe verificar para cada alumno si el valor de contrato es igual al valor real de contrato.  De lo contrario, ese número de alumnos inscritos se guarda para considerarse al siguiente trimestre de la campaña.**

Si eso sucede,  se multiplica el **valor de contrato por el porcentaje de comisión asignado al trimestre y al campus** para determinar la comisión trimestral de los promotores. Esta **comisión al promotor se multiplica por el porcentaje de comisión asignado al coordinador (Coordinador de promoción, Coordinador de Programa)** para determinar su comisión trimestral. 

Este monto correspondiente a las comisiones crecientes depende del número de alumnos meta establecidos para cada campus al que pertenecen los promotores, y si cumple con las condiciones para su cálculo, se debe almacenar como el monto correspondiente al esquema de comisiones crecientes en el trimestre y campañas activos.

***Si el valor de contrato no es igual al valor real de contrato, ese registro se almacena y se considera para el cálculo del esquema de comisiones corrientes.***   

**4. Para el caso del segundo y tercer trimestre de la campaña activa, por cada campus se consulta el número de alumnos inscritos por los promotores y se verifica si el número de alumnos inscritos es igual o mayor a la meta establecida para cada campus en el módulo de las campañas de comisiones crecientes.**

Si se cumple esta condición, **se obtiene el número de alumnos inscritos por cada campus mas los alumnos inscritos en el trimestre anterior de la campaña y se debe verificar para cada alumno si el valor de contrato es igual al valor real de contrato. De lo contrario, ese número de alumnos inscritos se guarda para considerarse al siguiente trimestre de la campaña.**

Si eso sucede,  se multiplica el **valor de contrato por el porcentaje de comisión asignado al trimestre y al campus** para determinar la comisión trimestral de los promotores. Esta **comisión al promotor se multiplica por el porcentaje de comisión asignado al coordinador (Coordinador de promoción, Coordinador de Programa)** para determinar su comisión trimestral. 

Este monto correspondiente a las comisiones crecientes depende del número de alumnos meta establecidos para cada campus al que pertenecen los promotores, y si cumple con las condiciones para su cálculo, se debe almacenar como el monto correspondiente al esquema de comisiones crecientes en el trimestre y campañas activos.

***Si el valor de contrato no es igual al valor real de contrato, ese registro se almacena y se considera para el cálculo del esquema de comisiones corrientes.***   


**5. Para el caso del último trimestre de la campaña, se obtiene el número de alumnos inscritos por cada campus mas los alumnos inscritos en el trimestre anterior de la campaña y se debe verificar para cada alumno que no haya sido dado de baja durante las primeras cuatro semanas de inicio del periodo (alumnos sentados).**  Si algún alumno fue dado de baja antes de las primeras cuatro semanas, ese registro se almacena y se considera para el cálculo del esquema de comisiones corrientes.   

Si los alumnos no han sido dados de baja, se debe **sumar para cada campus el total de alumnos que cada promotor haya inscrito.** Posteriormente, se **verifica si el número de alumnos inscritos es igual o mayor a la meta de sentados establecida para cada campus en el módulo de las campañas de comisiones crecientes.**

Si eso sucede,  se multiplica el **valor de contrato por el porcentaje de
comisión asignado al trimestre y al campus** para determinar la comisión trimestral de los promotores. Esta **comisión al promotor se multiplica por el porcentaje de comisión asignado al coordinador (Coordinador de promoción, Coordinador de Programa)** para determinar su comisión trimestral. 

Este monto correspondiente a las comisiones crecientes depende del número de alumnos meta establecidos para cada campus al que pertenecen los promotores, y si cumple con las condiciones para su cálculo, se debe almacenar como el monto correspondiente al esquema de comisiones crecientes en el trimestre y campañas activos.

**Si el campus no llega a la meta se sentados establecida, ese registro se almacena y se considera para el cálculo del esquema de comisiones corrientes.**


