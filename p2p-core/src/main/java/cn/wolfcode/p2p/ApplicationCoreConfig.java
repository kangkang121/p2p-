package cn.wolfcode.p2p;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *  core的配置类
 */
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("cn.wolfcode.p2p.*.mapper")
public class ApplicationCoreConfig{

}
