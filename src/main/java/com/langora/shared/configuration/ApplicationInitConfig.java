package com.langora.shared.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    // @Bean
    // @ConditionalOnProperty(
    //         prefix = "spring",
    //         value = "datasource.driverClassName",
    //         havingValue = "org.postgresql.Driver")
    // ApplicationRunner applicationRunner(UserRepository userRepository) {
    //     log.info("Init application.....");
    //     return args -> {
    //         if (userRepository.findByUsername("admin").isEmpty()) {
    //             var roles = new HashSet<String>();
    //             roles.add(Role.ADMIN.name());
    //             User user = User.builder()
    //                     .username("admin")
    //                     //                       .roles(roles)
    //                     .password(passwordEncoder.encode("admin"))
    //                     .build();
    //
    //             userRepository.save(user);
    //             log.warn("admin user has been created with default password: admin, please change it!");
    //         }
    //     };
    // }
}
