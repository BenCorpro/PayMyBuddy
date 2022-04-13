package com.openclassrooms.paymybuddy.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;
  
  @Autowired    
  public SecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
    this.passwordEncoder = passwordEncoder;
    this.userDetailsService = userDetailsService;
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        .antMatchers("/", "/registration", "/css/*", "/js/*").permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .formLogin()
          .loginPage("/login").permitAll()
          .defaultSuccessUrl("/user", true)
          .passwordParameter("password")
          .usernameParameter("email")
        .and()
        .rememberMe().tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(1))
                      .key("JRDVJ5mY6p9UrRyeWPGJ581IXbni0m5O")
                      .rememberMeParameter("remember-me")
         .and()
         .logout().logoutUrl("/logout")
                   .clearAuthentication(true)
                   .invalidateHttpSession(true)
                   .deleteCookies("JSESSIONID", "remember-me")
                   .logoutSuccessUrl("/login");
  }
  
  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(userDetailsService);
    return provider;
  }

}
