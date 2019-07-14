package cn.wolfcode;

import cn.wolfcode.p2p.ApplicationCoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 后台应用 配置类
 */
@SpringBootApplication
@EnableTransactionManagement
@Import(ApplicationCoreConfig.class)
@PropertySource("classpath:application-mgrsite.properties")
@EnableScheduling
public class ApplicationMgrSiteConfig{
    public static void main( String[] args ){
        SpringApplication.run(ApplicationMgrSiteConfig.class,args);
    }
}
