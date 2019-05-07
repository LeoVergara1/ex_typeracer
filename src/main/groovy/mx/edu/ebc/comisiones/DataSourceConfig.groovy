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

@Configuration
public class DataSourceConfig {
	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties firstDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("spring.datasource.configuration")
	public HikariDataSource firstDataSource() {
		return firstDataSourceProperties().initializeDataSourceBuilder()
				.type(HikariDataSource.class).build();
	}

	@Bean
	@ConfigurationProperties("db2.datasource")
	public HikariDataSource secondDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

@Bean
public LocalContainerEntityManagerFactoryBean firstEntityManagerFactory(
		EntityManagerFactoryBuilder builder) {
	return builder
			.dataSource(firstDataSource())
			.packages("mx.edu.ebc.comisiones.comision.data")
			.persistenceUnit("first")
			.build();
}

@Bean
public LocalContainerEntityManagerFactoryBean secondEntityManagerFactory(
		EntityManagerFactoryBuilder builder) {
	return builder
			.dataSource(secondDataSource())
			.packages("mx.edu.ebc.comisiones.seguridad.data")
			.persistenceUnit("second")
			.build();
}


}