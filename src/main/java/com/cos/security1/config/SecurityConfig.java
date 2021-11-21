package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터 체인에 등록됨
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
    @Bean
    BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated() //로그인한 사람 모두
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") //로그인하고 admin, manager 권한만
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") //로그인하고 admin 권한만
                .anyRequest().permitAll() //로그인 안해도 인덱스페이지 갈 수 있음
                .and()
                .formLogin()
                .loginPage("/loginForm");

    }
}
