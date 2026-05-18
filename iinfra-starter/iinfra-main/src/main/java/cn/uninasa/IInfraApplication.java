package cn.uninasa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * IInfra 单体启动类
 *
 * 单体模式: 直接运行此类即可启动所有模块
 * 微服务模式: 添加 @EnableDiscoveryClient 和 @EnableFeignClients 注解
 */
@SpringBootApplication(scanBasePackages = "cn.uninasa")
@MapperScan("cn.uninasa.**.mapper")
public class IInfraApplication {

    public static void main(String[] args) {
        SpringApplication.run(IInfraApplication.class, args);
        System.out.println("====================================");
        System.out.println("  IInfra 启动成功!");
        System.out.println("====================================");
    }
}
