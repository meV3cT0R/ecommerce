package com.vector.shop.configuration;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.formLogin(form->form.loginPage("/login").permitAll()).
        authorizeHttpRequests(auth->{
            auth.requestMatchers("/register","/api/**")
            .permitAll()
            .requestMatchers("/dashboard/**").hasAnyRole("ADMIN")
            .anyRequest()
            .authenticated();
        }).logout(auth-> {
            auth.logoutUrl("/logout").logoutSuccessUrl("/login");
        }).
        build();
    }
}
