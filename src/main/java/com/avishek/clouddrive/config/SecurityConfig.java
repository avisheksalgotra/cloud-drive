package com.avishek.clouddrive.config;

import com.avishek.clouddrive.user.entity.AppRole;
import com.avishek.clouddrive.user.entity.Role;
import com.avishek.clouddrive.user.entity.User;
import com.avishek.clouddrive.user.repository.RoleRepository;
import com.avishek.clouddrive.user.repository.UserRepository;
import com.avishek.clouddrive.user.security.service.UserDetailServiceImpl;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private  UserDetailServiceImpl userDetailService;
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){

        DaoAuthenticationProvider authenticationProvider =
                new DaoAuthenticationProvider(userDetailService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth -> auth
//                .requestMatchers("/api/v1/users").permitAll()
                   .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                    .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                     .anyRequest().authenticated()
        ));
//        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(req ->
                req.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
       return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository,
                                  PasswordEncoder passwordEncoder) {
        return args -> {
            Role user = roleRepository.findByRoleName(AppRole.ROLE_USER).
                    orElseGet(() ->{
                        Role newRole = new Role(AppRole.ROLE_USER);
                        return roleRepository.save(newRole);
                    });
            Role admin = roleRepository.findByRoleName(AppRole.ROLE_ADMIN).
                    orElseGet(() ->{
                        Role newRole = new Role(AppRole.ROLE_ADMIN);
                        return roleRepository.save(newRole);
                    });
            Set<Role> userRoles = Set.of(user);
            Set<Role> adminRoles = Set.of(admin);


            if (!userRepository.existsByEmail("user123@gmail.com")){
                User newUser = new User("user","user123@gmail.com", passwordEncoder.encode("password"), userRoles);
                userRepository.save(newUser);
            }
            if (!userRepository.existsByEmail("admin123@gmail.com")){
                User newadmin = new User("admin","admin123@gmail.com", passwordEncoder.encode("password"), adminRoles);
                userRepository.save(newadmin);
            }

        };
    }
}
