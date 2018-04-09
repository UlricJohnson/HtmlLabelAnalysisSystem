package ink.ulricj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * created by Ulric on 2018/3/20
 */

@SpringBootApplication
@MapperScan("ink.ulricj.mapper")
@EntityScan("ink.ulricj.entity")
@ComponentScan(basePackages = {"ink.ulricj"})
public class CrawlerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }
}
