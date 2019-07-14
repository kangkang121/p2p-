package cn.wolfcode;

import cn.wolfcode.p2p.ApplicationCoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Website端核心配置类
 */
@SpringBootApplication
@EnableTransactionManagement
@Import(ApplicationCoreConfig.class)
@PropertySource("classpath:application-website.properties")
public class ApplicationWebsiteConfig {



    public static void main( String[] args ){
        SpringApplication.run(ApplicationWebsiteConfig.class,args);
    }
}
