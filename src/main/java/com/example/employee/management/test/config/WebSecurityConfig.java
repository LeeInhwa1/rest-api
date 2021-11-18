package com.example.employee.management.test.config;

import com.example.employee.management.test.model.Authentication;
import com.example.employee.management.test.service.MariaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private MariaService mariaService;

    private static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // CSRF 없어도 무방함

        setAntMatchers(httpSecurity);
        httpSecurity.csrf().disable()
                .authorizeRequests()
                // ROLE 별로 페이지 접근 허용 설정
//                .antMatchers("/adminOnly").hasAuthority("ROLE_ADMIN")
//                .antMatchers("/userOnly/**").hasAnyAuthority("ROLE_GUEST", "ROLE_ADMIN")
//                .antMatchers("/authenticate", "db/**/**/**/**").permitAll() // 모든 사용자 접근 가능
                .anyRequest().authenticated().and()
                // 세션 사용하는지 확인
                // 세션은 사용자 상태를 저장하는데 사용하지 않음
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);


        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore((Filter) jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    protected void setAntMatchers(HttpSecurity http) throws Exception {
        List<Authentication> authentications = mariaService.getAuthentications();

        for(Authentication authentication : authentications) {

            String[] roles = authentication.getHasAuthority().split(",");

            for(int i=0; i< roles.length; i++) {
                roles[i] = ROLE_PREFIX + roles[i].toUpperCase();
                log.debug("roles[" + i + "] : " + ROLE_PREFIX + roles[i].toUpperCase());
            }

            String url = authentication.getUrl();
            if (url.charAt(0) != '/') {
                url = "/" + url;
                log.debug("url : " + url);
            }

            // 세팅한 정보 넣기
            http.authorizeRequests().antMatchers(url).hasAnyAuthority(roles);
        }

        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated();

    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/resources/**");
    }
}
