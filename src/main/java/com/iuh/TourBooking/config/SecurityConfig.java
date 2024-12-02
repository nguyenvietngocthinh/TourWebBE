package com.iuh.TourBooking.config;


import com.iuh.TourBooking.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // Mang chua api public
    private final String[] PUBLIC_ENDPOINTS = {
            "/users",
            "/auth/login",
            "/auth/introspect",
            "/auth/logout",
            "/bookings",
            "/customers",
            "/payment/payment-link",
            "/pay/webhook"
    };

    private final String[] PUBLIC_ENDPOINTS_GET = {
            "/tours",
            "/typetours",
            "/tours/by-type/**",
            "/tours/by-typetourname/**",
            "/tours/by-tourcode/**",
            "/tours/by-id/**",
            "/bookings/by-bookingcode/**",
            "/tours/searchTour/**",
            "/tours/searchTourTypeId2/**",
            "/tours/searchTourTypeId1/**",
            "/tours/searchTourTypeTour/**",
            "/tours-description",
            "/tours-description/by-tourcode/**",
            "/payment/payment-link",
            "/pay/done",
            "/coupons/by-codecoupon/**",
            "/coupons",
            "/coupons/couponCancel/**",
    };

    private final String[] PUBLIC_ENDPOINTS_PUT = {
            "/bookings/pendingcancel/**",
    };

    // Dung de tao token va chong hacker
    @Value("${jwt.signerKey}")
    private String signerKey;


    //Phan quyen
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Cấu hình CORS
                .csrf(csrf -> csrf.disable())  // Tắt CSRF nếu không cần thiết
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS).permitAll()  // Các endpoint public
                                .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS_GET).permitAll()  // Các endpoint public
                                .requestMatchers(HttpMethod.PUT, PUBLIC_ENDPOINTS_PUT).permitAll()
                                .anyRequest().authenticated()  // Bắt buộc authentication với các endpoint còn lại
                );

        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return httpSecurity.build();
    }

    // Cấu hình CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // Cho phép origin của React
        configuration.addAllowedMethod("*");  // Cho phép tất cả các phương thức (GET, POST, PUT, DELETE, v.v.)
        configuration.addAllowedHeader("*");  // Cho phép tất cả các header
        configuration.setAllowCredentials(true);  // Cho phép gửi thông tin xác thực (nếu cần thiết)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);  // Áp dụng cho tất cả các đường dẫn API
        return source;
    }

    JwtAuthenticationConverter jwtAuthenticationConverter(){
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    // Tao token
    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    // Ma hoa password
    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder(10);
    }
}
