package com.aughma.nacarqeqia.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // Inject the auto‑configured H2 DataSource
    private final DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Allow H2 console frames
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**") // disable CSRF on H2 Console
                )
                .authorizeHttpRequests(auth -> auth
                        // Secure all /admin routes, plus actuators and the old /orders route
                        .requestMatchers("/admin/**", "/actuator/**", "/orders").hasRole("ADMIN")

                        // Explicitly allow POST requests to /register for everyone
                        .requestMatchers(org.springframework.http.HttpMethod.POST, "/register").permitAll()

                        .requestMatchers("/", "/register", "/contact", "/about", "/css/**", "/images/**", "/h2-console/**").permitAll()

                        .requestMatchers("/order/**", "/profile/**").authenticated()

                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/profile")
                        .permitAll()
                )

                .exceptionHandling(exceptions ->
                        exceptions.accessDeniedPage("/403")
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .rememberMe(rm -> rm
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(86400)
                );
        return http.build();
    }

    @Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager() {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // DelegatingPasswordEncoder supporting bcrypt by default
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
