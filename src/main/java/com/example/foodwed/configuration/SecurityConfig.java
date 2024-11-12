package com.example.foodwed.configuration;

import com.example.foodwed.enums.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final String[] PUBLIC_ENDPOINS = {"/auth/signup", "/auth/token", "/auth/introspect"};
    private final String[] ADMIN_AUTHEN_GET = {};
    private final String[] ADMIN_AUTHEN_POST = {"/recipe/create","/category/create"};
    private final String[] ADMIN_AUTHEN_PUT = {"/recipe/update","/category/update"};
    private final String[] ADMIN_AUTHEN_DELETE = {"/recipe/delete","/category/delete"};
    private String signerKey = "3OBF8MHgoEMo+8acrb2h2dBsegTQtbK0S8uKwMcdnTpBDbl7PKHvHS54R2uFn/aH";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINS).permitAll()
                        .requestMatchers(HttpMethod.GET, ADMIN_AUTHEN_GET)
                        .hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.POST, ADMIN_AUTHEN_POST)
                        .hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,ADMIN_AUTHEN_PUT)
                        .hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.DELETE,ADMIN_AUTHEN_DELETE)
                        .hasAnyAuthority(Role.ADMIN.name())
                        .anyRequest().authenticated());
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter())));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    JwtDecoder jwtDecoder(){
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(),"HS512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

}
