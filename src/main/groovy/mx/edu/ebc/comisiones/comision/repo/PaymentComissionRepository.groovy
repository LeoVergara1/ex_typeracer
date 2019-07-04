package mx.edu.ebc.comisiones.comision.repo

import mx.edu.ebc.comisiones.comision.domain.PaymentComission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.query.Procedure

interface PaymentComissionRepository extends JpaRepository<PaymentComission, Long> {
    @Procedure(name = "java_procedure_name")
    def procedureName(@Param("p_fecha_pago_ini") Date p_fecha_pago_ini, @Param("p_fecha_pago_fin") Date p_fecha_pago_fin, @Param("p_campus") String p_campus);
}