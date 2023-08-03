package com.ctsi.ssdc.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class LiquibaseConfiguration {

    @Value("${ctsi.saasModel:false}")
    private boolean saasModel;
    @Value("${spring.liquibase.enabled}")
    private boolean enabled;
    @Value("${spring.liquibase.change-log}")
    private String changeLog;

    /**
     *  租户模块Liquibase
     */
    @Bean
    public SpringLiquibase initTenantLiquibase(@Qualifier("dataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        //设置数据源
        liquibase.setDataSource(dataSource);
        if(enabled){
            if(saasModel){
                liquibase.setChangeLog("classpath:db/changelog/init_saas_tenant.xml");
            }else{
                liquibase.setChangeLog("classpath:db/changelog/init_tenant.xml");
            }
            return liquibase;
        }else{
            return null;
        }

    }

    /**
     *  主要模块Liquibase
     */
    @Bean
    public SpringLiquibase initMainLiquibase(@Qualifier("dataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        //设置数据源
        liquibase.setDataSource(dataSource);
        if(enabled){
            liquibase.setChangeLog(changeLog);
            return liquibase;
        }else{
            return null;
        }

    }

}
