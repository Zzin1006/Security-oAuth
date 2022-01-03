package com.cos.Security.config;

import com.samskivert.mustache.Mustache;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        MustacheViewResolver resolver = new MustacheViewResolver();
        //해당 Mustache를 재설정 할 수 있음
        resolver.setCharset("UTF-8"); // 인코딩 기본적 UTF-8
        resolver.setContentType("text/html; charset=UTF-8"); // 내가 너한테 던지는 데이터는 html 파일이다 
        resolver.setPrefix("classpath:/templates/"); // classpath: 이 부분이 우리의 project라고 생각
        resolver.setSuffix(".html"); // .html을 만들어도 이제 mustache가 인식함

        registry.viewResolver(resolver); // viewResolver등록
        //localhost:8080/login 들어가면 security id,pw창이 뜬다.
    }
}
