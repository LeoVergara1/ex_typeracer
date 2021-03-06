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
import org.springframework.orm.jpa.JpaTransactionManager

@Configuration
class DataSourceConfig {

@Bean
@Primary
@ConfigurationProperties("spring.datasource")
DataSourceProperties firstDataSourceProperties() {
	return new DataSourceProperties();
}

@Bean
@Primary
@ConfigurationProperties("spring.datasource.configuration")
BasicDataSource firstDataSource() {
	return firstDataSourceProperties().initializeDataSourceBuilder()
			.type(BasicDataSource.class).build();
}
//
@Bean
@Primary
LocalContainerEntityManagerFactoryBean firstEntityManagerFactory(
		EntityManagerFactoryBuilder builder) {
	return builder
			.dataSource(firstDataSource())
			.packages("mx.edu.ebc.comisiones.comision.domain")
			.persistenceUnit("first")
			.build();
}

@Bean
JpaTransactionManager transactionManager(){
	new JpaTransactionManager(firstEntityManagerFactory().getObject())
}

@Bean
@ConfigurationProperties("banner.datasource")
DataSourceProperties bannerDataSourceProperties() {
	return new DataSourceProperties();
}

@Bean
@ConfigurationProperties("banner.datasource.configuration")
BasicDataSource bannerDataSource() {
	return bannerDataSourceProperties().initializeDataSourceBuilder()
			.type(BasicDataSource.class).build();
}

@Bean
LocalContainerEntityManagerFactoryBean bannerEntityManagerFactory(
		EntityManagerFactoryBuilder builder) {
	return builder
			.dataSource(bannerDataSource())
			.packages("mx.edu.ebc.comisiones.banner.domain")
			.persistenceUnit("banner")
			.build()
}


}