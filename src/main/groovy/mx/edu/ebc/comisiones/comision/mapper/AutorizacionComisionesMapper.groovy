package mx.edu.ebc.comisiones.comision.mapper

import java.sql.ResultSet;
import java.sql.SQLException;

import mx.edu.ebc.comisiones.comision.domain.AutorizacionComisiones;

import org.springframework.jdbc.core.RowMapper;

class AutorizacionComisionesMapper implements RowMapper<AutorizacionComisiones> {

	@Override
	AutorizacionComisiones mapRow(ResultSet rs, int rowNum) throws SQLException {
		AutorizacionComisiones autorizacionComisiones = new AutorizacionComisiones();
		autorizacionComisiones.setCampus(rs.getString("CAMPUS"));
		autorizacionComisiones.setIdPromotor(rs.getString("ID_PROMOTOR"));
		autorizacionComisiones.setNombrePromotor(rs.getString("NOMBRE_PROMOTOR"));
		autorizacionComisiones.setPuesto(rs.getString("puesto"));
		autorizacionComisiones.setIdAlumno(rs.getString("id_alumno"));
		autorizacionComisiones.setNombreAlumno(rs.getString("nombre_alumno"));
		autorizacionComisiones.setPagoInicial(rs.getDouble("pago_inicial"));
		autorizacionComisiones.setTotalDescuentos(rs.getDouble("total_descuentos"));
		//TODO: No sé porque el *2
		autorizacionComisiones.setComision(rs.getDouble("comision") / 6 * 2);
		autorizacionComisiones.setPeriodo(rs.getString("periodo"));
		autorizacionComisiones.setFechaDePago(rs.getDate("fecha_de_pago"));
		autorizacionComisiones.setIdCoordinador(rs.getString("id_coordinador"));
		autorizacionComisiones.setNombreCoordinador(rs.getString("nombre_coordinador"));
		autorizacionComisiones.setComisionCoordinador(rs.getDouble("comision_coordinador"));
		autorizacionComisiones.setValorContratoReal(rs.getDouble("valor_contrato"));
		autorizacionComisiones.setTipoPago(rs.getString("tipo_pago"));
		autorizacionComisiones.setAdPromotor(rs.getString("ad_promotor"));
		autorizacionComisiones.setAdCoordinador(rs.getString("ad_coordinador"));
		autorizacionComisiones.setAutorizadoDirector('N');
		autorizacionComisiones.setPidm(rs.getString("SGBSTDN_PIDM").toLong());
		autorizacionComisiones.setDiscountPercent(rs.getString("discount_percent"));
		return autorizacionComisiones;
	}

}
