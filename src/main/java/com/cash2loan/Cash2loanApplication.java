package com.cash2loan;

import com.cash2loan.domain.AppUser;
import com.cash2loan.domain.Role;
import com.cash2loan.services.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class Cash2loanApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cash2loanApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(AppUserService appUserService) {
		return args -> {
			// save roles
			appUserService.saveRole(new Role(null, "ROLE_ADMIN"));
			appUserService.saveRole(new Role(null, "ROLE_USER"));

			// save users
			appUserService.saveUser(new AppUser(null, "John smith", "john@gmail.com", "1234", new ArrayList<>(), new ArrayList<>()));
			appUserService.saveUser(new AppUser(null, "Admin user", "admin@gmail.com", "admin", new ArrayList<>(), new ArrayList<>()));

			// add roles to users
			appUserService.addRoleToUser("john@gmail.com", "ROLE_USER");
			appUserService.addRoleToUser("admin@gmail.com", "ROLE_ADMIN");
		};
	}
}
