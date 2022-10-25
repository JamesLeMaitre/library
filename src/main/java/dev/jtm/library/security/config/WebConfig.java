package dev.jtm.library.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
<<<<<<< Updated upstream
        registry.addMapping("*")
                .allowedOrigins("http://localhost:5031");


    }
}
=======

        registry.addMapping("*")
                .allowedOrigins("http://localhost:5031")
                .allowedMethods("PUT", "DELETE","POST","GET")
                .allowCredentials(true).maxAge(3600);


        // Add more mappings...
    }
}
>>>>>>> Stashed changes