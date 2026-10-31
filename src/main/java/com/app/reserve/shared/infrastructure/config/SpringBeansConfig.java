package com.app.reserve.shared.infrastructure.config;

import com.app.reserve.booking.application.BookingCreator;
import com.app.reserve.booking.application.BookingDeleter;
import com.app.reserve.booking.application.BookingRetriever;
import com.app.reserve.booking.application.BookingUpdater;
import com.app.reserve.booking.domain.BookingRepository;
import com.app.reserve.offer.application.OfferCreator;
import com.app.reserve.offer.application.OfferDeleter;
import com.app.reserve.offer.application.OfferRetriever;
import com.app.reserve.offer.application.OfferUpdater;
import com.app.reserve.offer.domain.OfferRepository;
import com.app.reserve.role.application.RoleCreator;
import com.app.reserve.role.application.RoleRetriever;
import com.app.reserve.role.domain.RoleRepository;
import com.app.reserve.user.application.UserCreator;
import com.app.reserve.user.application.UserRetriever;
import com.app.reserve.user.application.UserUpdater;
import com.app.reserve.user.domain.UserRepository;
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

    @Bean
    public UserCreator userCreator(UserRepository userRepository){return new UserCreator(userRepository);}

    @Bean
    public UserRetriever userRetriever(UserRepository userRepository){return new UserRetriever(userRepository);}

    @Bean
    public UserUpdater userUpdater(UserRepository userRepository, RoleRepository roleRepository){return new UserUpdater(userRepository, roleRepository);}

    @Bean
    public OfferCreator offerCreator(OfferRepository offerRepository){return new OfferCreator(offerRepository);}

    @Bean
    public OfferRetriever offerRetriever(OfferRepository offerRepository){return new OfferRetriever(offerRepository);}

    @Bean
    public OfferUpdater offerUpdater(OfferRepository offerRepository){return new OfferUpdater(offerRepository);}

    @Bean
    public OfferDeleter offerDeleter(OfferRepository offerRepository){return new OfferDeleter(offerRepository);}

    @Bean
    public BookingCreator bookingCreator(BookingRepository bookingRepository){return new BookingCreator(bookingRepository);}

    @Bean
    public BookingRetriever bookingRetriever(BookingRepository bookingRepository){return new BookingRetriever(bookingRepository);}

    @Bean
    public BookingUpdater bookingUpdater(BookingRepository bookingRepository){return new BookingUpdater(bookingRepository);}

    @Bean
    public BookingDeleter bookingDeleter(BookingRepository bookingRepository){return new BookingDeleter(bookingRepository);}
}
