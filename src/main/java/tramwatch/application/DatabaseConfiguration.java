package tramwatch.application;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by krzysztofowczarek on 04/04/15.
 */
@Configuration
public class DatabaseConfiguration {

    @Bean(name="localDS")
    @Primary
    @ConfigurationProperties(prefix="ds.local")
    public DataSource getLocalDS() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="localEntityManager")
    @Primary
    @PersistenceContext(unitName = "localDS")
    public LocalContainerEntityManagerFactoryBean localEntityManagerFactory() {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getLocalDS());
        em.setPersistenceUnitName("localDS");
        em.setPackagesToScan(new String[] {"*"});

        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(jpaVendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }
}
