package dev.jtm.library;

import dev.jtm.library.entities.security.AppRole;
import dev.jtm.library.repositories.security.AppRoleRepository;
import dev.jtm.library.security.request.RegisterRequest;
import dev.jtm.library.services.security.AppUsersService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CommandLineRunner runner(AppUsersService userService, AppRoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findAll().isEmpty()) {
                AppRole role = new AppRole();
                role.setRoleName("ROLE_ADMIN");
                roleRepository.save(role);

                AppRole role2 = new AppRole();
                role2.setRoleName("ROLE_USER");
                roleRepository.save(role2);

            }

      /*       RegisterRequest request = new RegisterRequest();
                request.setUsername("James");
                request.setEmail("devjava207@gmail.com");
                request.setPassword("james@Keen");
                request.setRoleName("ROLE_ADMIN");

            userService.storeUser(request);*/
        };
    }

    @Bean
    public CorsWebFilter corsWebFilter() {

        final CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
        corsConfig.setMaxAge(3600L);
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfig.addAllowedHeader("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }

}
