package tramwatch.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by krzysztofowczarek on 16/06/15.
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfiguration {

    @Autowired
    EntityManagerFactory emf;

    @Autowired
    DataSource dataSource;

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        transactionManager.setDataSource(dataSource);

        return transactionManager;
    }
}
