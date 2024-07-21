package com.beyond.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication // 해당 어노테이션을 통해 (내장된 @ComponentScan 으로) ComponentScanning 수행
@ServletComponentScan
//주로 web서블릿 기반의 구성요소를 스캔하고, 자동으로 등록하는 기능
//예를 들어 ) @WebServlet @WebFilter @WebListener 등
public class BasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}

}
