package com.kgc.kmall01.kmallcartservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.kgc.kmall01.kmallcartservice.mapper")
@SpringBootApplication
public class KmallCartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KmallCartServiceApplication.class, args);
	}

}
