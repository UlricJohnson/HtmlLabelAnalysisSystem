package ink.ulricj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * created by Ulric on 2018/3/16
 */

@SpringBootApplication
@MapperScan("ink.ulricj.mapper") // 添加对 mapper 接口的扫描，不然要在每个 mapper 上面加 @Mapper 注解
@EntityScan("ink.ulricj.entity") // 添加对实体类包的扫描
public class AnalysisApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnalysisApplication.class, args);
    }
}
