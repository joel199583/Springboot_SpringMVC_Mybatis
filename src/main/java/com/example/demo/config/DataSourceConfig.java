package com.example.demo.config;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.mysql.cj.jdbc.MysqlXADataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import javax.transaction.UserTransaction;
import java.sql.SQLException;

/**
 * 數據源配置類
 */
@Configuration//標註為配置類
public class DataSourceConfig {
    // 注入數據源1配置項
    @Value("${spring.datasource.db1.jdbc-url}")
    private String db1Url;
    @Value("${spring.datasource.db1.username}")
    private String db1Username;
    @Value("${spring.datasource.db1.password}")
    private String db1Password;
    // 注入數據源2配置項
    @Value("${spring.datasource.db2.jdbc-url}")
    private String db2Url;
    @Value("${spring.datasource.db2.username}")
    private String db2Username;
    @Value("${spring.datasource.db2.password}")
    private String db2Password;

    /**
     * 數據源1
     */
    @Bean // 返回值註冊為組件
    public DataSource db1() throws SQLException {
        MysqlXADataSource dataSource = new MysqlXADataSource();
        dataSource.setUrl(db1Url);
        dataSource.setUser(db1Username);
        dataSource.setPassword(db1Password);
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(dataSource);
        atomikosDataSourceBean.setUniqueResourceName("db1");
        return atomikosDataSourceBean;

    }

    /**
     * 數據源2
     */
    @Bean // 返回值註冊為組件
    public DataSource db2() {
        MysqlXADataSource dataSource = new MysqlXADataSource();
        dataSource.setURL(db2Url);
        dataSource.setUser(db2Username);
        dataSource.setPassword(db2Password);
        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(dataSource);
        atomikosDataSourceBean.setUniqueResourceName("db2");
        return atomikosDataSourceBean;
    }

    /**
     * 分布式事務管理器
     */
    @Bean(name = "jtaTransactionManager")
    public JtaTransactionManager jtaTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction, userTransactionManager);
    }
}