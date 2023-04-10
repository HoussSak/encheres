package fr.eni.encheres;

import fr.eni.encheres.configuration.CorsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableWebSecurity
@Import(CorsConfig.class)
public class Encheres implements CommandLineRunner {
    @Value("${jwt.secret}")
    private String jwt;
    public static void main(String[] args) {
        SpringApplication.run(Encheres.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("property jwt value is: " + jwt);

    }
}