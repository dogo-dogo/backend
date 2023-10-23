package com.example.tooktook.config;

import com.example.tooktook.component.jwt.JwtTokenFilter;
import com.example.tooktook.component.jwt.JwtTokenProvider;
import com.example.tooktook.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v2 */
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api/kakao/**",
            "/"
    };
    private final KakaoService kakaoService;
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers()
                .frameOptions()
                .sameOrigin().and()
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider,kakaoService), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers(PERMIT_URL_ARRAY).permitAll()
                    .antMatchers("/api/**").authenticated()
                    .anyRequest().authenticated()
                .and().formLogin().disable()
                .httpBasic().disable();
    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.headers()
//            .frameOptions()
//            .sameOrigin().and()
//            .csrf().disable()
//                .authorizeRequests((authorizeRequests)
//                        -> authorizeRequests
//                        .antMatchers("/","/api/kakao/**","/api/auth/**","/api/member/checkTest").permitAll()
//                        .antMatchers(PERMIT_URL_ARRAY).permitAll()
//                        .antMatchers("/api/**").authenticated())
//
//            .cors().configurationSource(corsConfigurationSource())
//            .and()
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider,kakaoService), UsernamePasswordAuthenticationFilter.class)
//            .formLogin().disable()
//            .httpBasic().disable();
//
//        return http.build();
//    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));

        configuration.setAllowedMethods(
            Arrays.asList("OPTIONS", "HEAD", "GET", "POST", "PUT", "PATCH", "DELETE"));

        configuration.setAllowedHeaders(
            Arrays.asList("Authorization", "Cache-Control", "Content-Type", "Set-Cookie"));

        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}