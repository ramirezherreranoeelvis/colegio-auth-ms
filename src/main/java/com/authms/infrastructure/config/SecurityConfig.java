package com.authms.infrastructure.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.List;

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
      @Bean
      public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration configuration = new CorsConfiguration();
            // Permite peticiones desde cualquier origen. ¡CUIDADO en producción!
            configuration.setAllowedOrigins(List.of("*"));
            // Permite los métodos HTTP más comunes
            configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            // Permite cabeceras comunes, incluyendo las de autorización
            configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            // Aplica esta configuración a todas las rutas de tu API
            source.registerCorsConfiguration("/**", configuration);
            return source;
      }
}
