package cn.edu.ncc.zsh;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableEncryptableProperties
@MapperScan("cn.edu.ncc.zsh.dao")//指定dao接口的所在包
public class ZshApplication {

    public static void main(String[] args) {

        SpringApplication.run(ZshApplication.class, args);
    }

}
