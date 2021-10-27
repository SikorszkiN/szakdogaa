package com.szakdoga.szakdoga.security.config;

import com.szakdoga.szakdoga.app.service.AppUserService;
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

    private final AppUserService appUserService;

@Override
protected void configure(HttpSecurity http) throws Exception {
       http.
               csrf().disable().httpBasic().and()
       .authorizeRequests()
               .antMatchers("/registration/**").permitAll()
               .antMatchers("/api/webshop/**").hasAuthority("ADMIN")
               .antMatchers("/api/appuser/changerole/**").hasAuthority("ADMIN")
       .anyRequest().authenticated().and()
       .formLogin().permitAll()
       .and()
       .logout().permitAll();
    }

@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(deoDaoAuthenticationProvider());
    }

@Bean
    public DaoAuthenticationProvider deoDaoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }
}
