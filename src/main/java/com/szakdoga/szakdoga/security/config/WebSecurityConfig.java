/*package com.szakdoga.szakdoga.security.config;

import com.szakdoga.szakdoga.app.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;


@Override
protected void configure(HttpSecurity http) throws Exception {
       http.
              csrf().disable().httpBasic().and()
       .authorizeRequests()*/
//       .antMatchers("/api/**", "/db/**")
/*       .permitAll()
       .anyRequest().authenticated().and()
       .formLogin();
    }

@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(deoDaoAuthenticationProvider());
    }

@Bean
    public DaoAuthenticationProvider deoDaoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }
}*/
