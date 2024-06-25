package com.codigo.ms_rodriguez_mijahuanga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsRodriguezMijahuangaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRodriguezMijahuangaApplication.class, args);
	}

}
