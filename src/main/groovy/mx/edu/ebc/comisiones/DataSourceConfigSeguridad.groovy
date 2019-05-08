package mx.edu.ebc.comisiones.config

import org.springframework.transaction.annotation.*
import org.springframework.data.jpa.repository.config.*
import org.springframework.context.annotation.*
import org.springframework.jdbc.datasource.*
import org.springframework.boot.context.properties.*
import javax.persistence.EntityManagerFactory
import org.springframework.orm.jpa.*
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import javax.sql.DataSource
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import com.zaxxer.hikari.HikariDataSource
import org.apache.commons.dbcp.BasicDataSource

@Configuration
public class DataSourceConfigSeguridad {

@Bean
@Primary
@ConfigurationProperties("db2.datasource")
public DataSourceProperties secondDataSourceProperties() {
	return new DataSourceProperties();
}

@Bean
@Primary
@ConfigurationProperties("db2.datasource.configuration")
public BasicDataSource secondDataSource() {
	return secondDataSourceProperties().initializeDataSourceBuilder()
			.type(BasicDataSource.class).build();
}

@Bean
@Primary
public LocalContainerEntityManagerFactoryBean secondEntityManagerFactory(
		EntityManagerFactoryBuilder builder) {
	return builder
			.dataSource(secondDataSource())
			.packages("mx.edu.ebc.comisiones.seguridad.domain")
			.persistenceUnit("second")
			.build();
}


}