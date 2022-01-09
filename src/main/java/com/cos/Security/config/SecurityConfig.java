package com.cos.Security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // 메모리에 떠야하니까 Configuration붙히기
@EnableWebSecurity // 활성화하면 스프링 시큐리티 필터가 스프링 필터체인에 등록이됨 , 스프링시큐리티필터는 -> SecurityConfig말함
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true) //secured 어노테이션 활성화 , preAuthorize,postAuthorize 어노테이션활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //패스워드 비밀번호 암호화
    @Bean // bean요청시 해당메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // csrf 비활성화
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() // /user/** 이런주소로 들어오면 인증이 필요하단 것 , 인증만되면 들어갈 수 있는 주소!
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm") // 권한 없는 페이지요청들어올 때 로그인페이지로 이동되도록!
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줍니다. 그래서 controller에 /login을 안만들어도 됨
                .defaultSuccessUrl("/")// 메인페이지로 가도록 , 특정페이지 요청하면서 로그인하면 원래가려던페이지로
                .and()
                .oauth2Login()
                .loginPage("/loginForm"); // 구글 로그인이 완료된 뒤의 후처리가 필요함.
        

    }
}
