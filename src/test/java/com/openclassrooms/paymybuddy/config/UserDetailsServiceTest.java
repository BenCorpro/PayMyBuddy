package com.openclassrooms.paymybuddy.config;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
public class UserDetailsServiceTest{

	@Bean
	public UserDetailsService userDetailsService() {
		User userTest = new User("testControllers@email.com", "password", new ArrayList<>());
		return new InMemoryUserDetailsManager(Arrays.asList(userTest));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
	   return new BCryptPasswordEncoder(10);
	}
}
