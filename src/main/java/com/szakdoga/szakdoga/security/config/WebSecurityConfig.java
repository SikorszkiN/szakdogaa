package com.szakdoga.szakdoga.security.config;

import com.szakdoga.szakdoga.app.exception.NoEntityException;
import com.szakdoga.szakdoga.app.repository.AppUserRepository;
import com.szakdoga.szakdoga.app.service.AppUserService;
import com.szakdoga.szakdoga.security.JwtTokenFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AppUserService appUserService;

    private final JwtTokenFilter jwtTokenFilter;

    private final AppUserRepository appUserRepository;


@Override
protected void configure(HttpSecurity http) throws Exception {
    http = http.cors().and().csrf().disable();

    http = http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and();

    http = http
            .exceptionHandling()
            .authenticationEntryPoint(
                    ((request, response, e) -> {
                        response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                e.getMessage()
                        );
                    })
            )
            .and();
            http.authorizeRequests().antMatchers("/registration/**").permitAll().antMatchers("/api/public/login").permitAll().anyRequest().authenticated();

           http.addFilterBefore(
                   jwtTokenFilter,
                   UsernamePasswordAuthenticationFilter.class
           );


/*       http.
               csrf().disable().httpBasic().and()
       .authorizeRequests()
               .antMatchers("/registration/**").permitAll()
               .antMatchers("/api/webshop/**").hasAuthority("ADMIN")
               .antMatchers("/api/appuser/changerole/**").hasAuthority("ADMIN")
       .anyRequest().authenticated().and()
       .formLogin().permitAll()
       .and()
       .logout().permitAll();*/
    }

@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(email -> appUserRepository.findByEmail(email).orElseThrow(
            () -> new NoEntityException("User :%s, not found:" +  email)));
      //  auth.authenticationProvider(deoDaoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider deoDaoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(appUserService);
        return provider;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
