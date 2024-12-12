package de.htwberlin.budgetapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Erlaube alle Endpunkte
                registry.addMapping("/**")
                        .allowedOrigins("https://budget-app-frontend-tpct.onrender.com") // Erlaube Frontend-URLs (Render und Local)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Erlaubte HTTP-Methoden
                        .allowedHeaders("*") // Alle Header erlauben
                        .allowCredentials(true) // Cookies/Authtoken erlauben
                        .maxAge(3600); // Die CORS-Anfrage kann 1 Stunde zwischengespeichert werden
            }
        };
    }
}
