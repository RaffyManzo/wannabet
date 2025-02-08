package is.project.wannabet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    private static DataSource dataSource;

    private DatabaseConfig() {}

    @Bean
    public static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
            ds.setUrl("jdbc:mysql://localhost:3306/dbis");
            ds.setUsername("root");
            ds.setPassword("abcde");
            dataSource = ds;
        }
        return dataSource;
    }
}
