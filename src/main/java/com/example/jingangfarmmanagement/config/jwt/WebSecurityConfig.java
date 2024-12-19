package com.example.jingangfarmmanagement.config.jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] AUTH_WHITELIST = {
                "/swagger-resources/**",
                "/**/swagger-ui/**",
                "/v2/api-docs",
                "/webjars/**"
        };

        http.cors().and().csrf().disable();

        http.
                exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint)
                .and().authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/**/login").permitAll()
                .antMatchers("/**/user/create").permitAll()
                .antMatchers("/**/categories/client/tree").permitAll()
                .antMatchers("/**/categories/client/tree/by-id").permitAll()
                .antMatchers("/**/news/client/search/custom").permitAll()
                .antMatchers("/**/menu_config/client/search/custom").permitAll()
                .antMatchers("/**/menu_config/client/custom/by-id").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}

