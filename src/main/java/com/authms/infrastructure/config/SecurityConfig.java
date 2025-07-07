package com.authms.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class SecurityConfig {

      @Bean
      public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
            return http
                  .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((swe, e) ->
                              Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
                        )
                        .accessDeniedHandler((swe, e) ->
                              Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))
                        )
                  )
                  .csrf(ServerHttpSecurity.CsrfSpec::disable)
                  .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                  .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                  .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/auth/login").permitAll()
                        .pathMatchers("/api/auth/register").permitAll()
                        .anyExchange().authenticated()
                  )
                  .build();
      }

}
