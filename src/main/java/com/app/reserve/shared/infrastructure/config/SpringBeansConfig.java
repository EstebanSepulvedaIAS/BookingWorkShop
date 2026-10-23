package com.app.reserve.shared.infrastructure.config;

import com.app.reserve.role.application.RoleCreator;
import com.app.reserve.role.application.RoleRetriever;
import com.app.reserve.role.domain.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringBeansConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }

    @Bean
    public RoleCreator roleCreator(RoleRepository roleRepository) {
        return new RoleCreator(roleRepository);
    }

    @Bean
    public RoleRetriever roleRetriever(RoleRepository roleRepository) {
        return new RoleRetriever(roleRepository);
    }

}
