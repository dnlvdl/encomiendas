package com.encomiendas.autenticacion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AutenticacionMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutenticacionMsApplication.class, args);
    }

    // Cifra las claves con BCrypt. Se usa al crear usuarios y al validar el login.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
