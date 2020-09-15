package app.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
@ComponentScan(basePackages = { "app" })
public class AppConfig {
	@Bean
	public DriverManagerDataSource mysqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/springstudent");
		dataSource.setUsername("durry");
		dataSource.setPassword("sys123");
		return dataSource;
	}

	@Bean
	public DataSourceTransactionManager dtm(DriverManagerDataSource ds) {
		DataSourceTransactionManager dtm = new DataSourceTransactionManager(ds);
		return dtm;
	}
}
