package ru.ns.t_jobs.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.ns.t_jobs.auth.credentials.CredentialsRepository;
import ru.ns.t_jobs.auth.token.BotTokenFilter;
import ru.ns.t_jobs.auth.token.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebMvc
public class SecurityConfiguration implements WebMvcConfigurer {

    private final CredentialsRepository credentialsRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationProvider authProvider,
                                                   JwtTokenFilter tokenFilter,
                                                   BotTokenFilter botTokenFilter) throws Exception {
        String[] hrPaths = {"/user/tracks", "/vacancy/create", "/vacancy/edit/**",
                "/track/approve-application", "/track/create", "/track/set-hr", "/track/finish",
                "/interview/set-interviewer", "/interview/set-auto-interviewer", "/interview/set-date",
                "/interview/set-auto-date", "/interview/set-link", "/interview/set-feedback", "/interview/add-to-track"};
        String[] interviewerPaths = {"/user/set-interviewer-mode", "/user/competencies"};

        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/auth/login").permitAll()
                                .requestMatchers(hrPaths).hasRole("HR")
                                .requestMatchers(HttpMethod.DELETE, "/interview/**").hasRole("HR")
                                .requestMatchers("/user/follow-vacancy/**", "/user/vacancies").hasAnyRole("HR", "TL")
                                .requestMatchers(interviewerPaths).hasRole("INTERVIEWER")
//                                .requestMatchers("/bot/api/**").hasRole("BOT")
                                .anyRequest().authenticated()
                )
                .authenticationProvider(authProvider)
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(botTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return login -> credentialsRepository.findByLogin(login)
                .orElseThrow(RuntimeException::new);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter(CredentialsRepository credentialsRepository, UserDetailsService userDetailsService) {
        return new JwtTokenFilter(credentialsRepository, userDetailsService);
    }
}