package com.ctsi.ssdc;

import cn.hutool.system.HostInfo;
import com.ctsi.ssdc.database.annotation.MapperByDataBaseScan;
import com.ctsi.ssdc.util.DefaultProfileUtil;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.UnknownHostException;

/**
 * Web Application
 *
 * @author ctsi biyi generator
 */
@SpringBootApplication(scanBasePackages = {"com.ctsi.*","com.ctsi.ssdc.*"})
@MapperScan(basePackages = {"com.ctsi.ssdc","com.ctsi.activiti","com.ctsi.flow","com.ctsi.monitor"}, annotationClass = Mapper.class)
@MapperByDataBaseScan(basePackages ={"com.ctsi.ssdc.admin.repository"} )
@EnableConfigurationProperties({LiquibaseProperties.class})
@EnableTransactionManagement
@EnableDiscoveryClient
public class WebApplication {

    private static final Logger log = LoggerFactory.getLogger(WebApplication.class);

    private static final String SERVER_SSL_KEY_STORE = "server.ssl.key-store";

    private static final String SWAGGER_PAGE = "swagger-ui.html";


    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(String[] args) throws UnknownHostException {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication app = new SpringApplication(WebApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        String serverPort = env.getProperty("server.port");
        String protocol = "http";
        if (env.getProperty(SERVER_SSL_KEY_STORE) != null) {
            protocol = "https";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}\n\t" +
                        "External: \t{}://{}:{}\n\t" +
                        "API doc: \t{}://{}:{}/{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                protocol,
                new HostInfo().getAddress(),
                serverPort,
                protocol,
                new HostInfo().getAddress() ,
                serverPort,
                SWAGGER_PAGE,
                env.getActiveProfiles());
    }
}
