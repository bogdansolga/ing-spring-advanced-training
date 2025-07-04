package ing.hubs.spring.advanced.training.security.config;

import ing.hubs.spring.advanced.training.security.filter.RateLimitingFilter;
import ing.hubs.spring.advanced.training.security.handler.FailedAuthHandler;
import ing.hubs.spring.advanced.training.security.handler.PostLogoutHandler;
import ing.hubs.spring.advanced.training.security.handler.SuccessfulAuthHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;

import static ing.hubs.spring.advanced.training.security.controller.ProductController.API_PREFIX;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain webHttpSecurity(HttpSecurity http,
                                               SuccessfulAuthHandler successfulAuthHandler,
                                               FailedAuthHandler failedAuthHandler,
                                               PostLogoutHandler logoutHandler) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .formLogin(form -> configureFormHandlers(form, successfulAuthHandler, failedAuthHandler))
            .logout(logout -> logout.addLogoutHandler(logoutHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, API_PREFIX +"/auth/**").permitAll()
                    .requestMatchers(HttpMethod.POST, API_PREFIX +"/login", "/register").permitAll()
                    .requestMatchers(HttpMethod.GET, API_PREFIX +"/product").authenticated()
                    .anyRequest().authenticated())
            .addFilterBefore(new RateLimitingFilter(), AuthenticationFilter.class)
            .httpBasic(withDefaults());
        return http.build();
    }

    private void configureFormHandlers(FormLoginConfigurer<HttpSecurity> form,
                                       SuccessfulAuthHandler successfulAuthHandler,
                                       FailedAuthHandler failedAuthHandler) {
        form.successHandler(successfulAuthHandler);
        form.failureHandler(failedAuthHandler);
        form.defaultSuccessUrl("/", true);
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SuccessfulAuthHandler successfulAuthHandler() {
        return new SuccessfulAuthHandler();
    }

    @Bean
    public FailedAuthHandler failedAuthHandler() {
        return new FailedAuthHandler();
    }

    @Bean
    public PostLogoutHandler postLogoutHandler() {
        return new PostLogoutHandler();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                               .username("user")
                               .password("$2a$10$D5XLRx5LcWBmaMJhL76sLeVkqVHxtQAYdmOGjlhxptlC85KW6smOq") // password
                               .roles("USER")
                               .build();
        UserDetails admin = User.builder()
                                .username("admin")
                                .password("$2a$10$D5XLRx5LcWBmaMJhL76sLeVkqVHxtQAYdmOGjlhxptlC85KW6smOq") // password
                                .roles("ADMIN") //"USER",
                                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}
