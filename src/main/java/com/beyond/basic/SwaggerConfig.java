package com.beyond.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration // @Conponent : 클래스 위에 붙이는 것
@EnableSwagger2
public class SwaggerConfig {
    @Bean // 메서드 위에 붙이는 것 // 도켓(외부)라이브러리 커스텀 // @Configuration 와 함께한다.
//    Docket : swagger구성의 핵심 기능 클래스
//    Docket 을 리턴함으로서 싱글톤객체로 생성
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select() // 어떤 컨트롤러 또는 api를 swagger문서에 포함시킬지 선택
                .apis(RequestHandlerSelectors.any()) // 모든 requestHandler(Controller)를 문서화대상으로 선택한다는 설정
                .paths(PathSelectors.ant("/rest/**")) // 특정 path만 문서화 대상으로 하겠다는 설정   // *1개면 보통은 직계, **2개면 자손끼지 포함
                .build();
    }
}
