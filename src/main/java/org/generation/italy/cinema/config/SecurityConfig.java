package org.generation.italy.cinema.config;

import org.generation.italy.cinema.auth.Filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Serve per fare authenticationManager.authenticate(...) nel login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Abilita CORS (SERVE per permettere chiamate da Angular localhost:4200)
        http.cors(cors -> {});

        // Disabilitiamo CSRF perché usiamo JWT (stateless)
        http.csrf(csrf -> csrf.disable());

        // Niente sessione lato server (JWT è stateless)
        http.sessionManagement(sm ->
                sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Regole di autorizzazione
        http.authorizeHttpRequests(auth -> auth

                // Rotte pubbliche (login e register)
                .requestMatchers("/api/auth/**").permitAll()

                // 🔹 API del cinema rese pubbliche
                // Queste rotte devono essere accessibili anche dal frontend Angular
                // senza autenticazione JWT, altrimenti Angular riceverebbe sempre 403
                .requestMatchers("/api/films/**").permitAll()

                // 🔹 Endpoint gestione sale cinema
                // Servono al planner per caricare le sale disponibili
                .requestMatchers("/api/halls/**").permitAll()

                // 🔹 Endpoint gestione proiezioni
                // Il planner Angular usa queste API per:
                // - leggere le proiezioni
                // - crearle
                // - cancellarle
                .requestMatchers("/api/screenings/**").permitAll()

                // Solo ADMIN (se usate ruoli)
                .requestMatchers("/api/admin/**").hasRole("admin")

                // Tutto il resto richiede autenticazione
                .anyRequest().authenticated()
        );

        // Aggiungiamo il filtro JWT prima del filtro standard di Spring
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        // Permetti richieste dal frontend Angular
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));

        // Metodi HTTP consentiti --> "Serve solo a dire al browser:
        // Angular può fare richieste HTTP di questi tipi verso il backend."
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Header consentiti
        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
