package mx.edu.ebc.comisiones.comision.storedProcedure

import java.sql.Types;
import java.util.Map;

import javax.sql.DataSource;

import mx.edu.ebc.comisiones.comision.mapper.AutorizacionComisionesMapper;
import oracle.jdbc.OracleTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Component;

@Component
public class AutorizacionComisionesStoredProcedure extends StoredProcedure {

	private static final String STORED_PROCEDURE_NAME = "genpub.pc_pago_comisiones.pr_comisiones";
	private static final String CURSOR_NAME = "out_comisiones";

	@Autowired
	public AutorizacionComisionesStoredProcedure(DataSource dataSource){
		super(dataSource,STORED_PROCEDURE_NAME);
		declareParameter(new SqlParameter("p_fecha_pago_ini", Types.DATE));
		declareParameter(new SqlParameter("p_fecha_pago_fin", Types.DATE));
		declareParameter(new SqlParameter("p_campus", Types.VARCHAR));
		declareParameter(new SqlOutParameter(CURSOR_NAME, OracleTypes.CURSOR, new  AutorizacionComisionesMapper()));
		compile();
	}

	public Map execute(Map parametros){
		return super.execute(parametros);
	}

}
