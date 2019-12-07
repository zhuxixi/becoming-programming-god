package org.zhuzhenxi.test;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhuzhenxi
 * @date 2018.05.02
 */
@SpringBootApplication
@EnableMethodCache(basePackages = "org.zhuzhenxi.test")
@EnableCreateCacheAnnotation
public class TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}



}
