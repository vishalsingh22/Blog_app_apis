package com.blog;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepo;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@OpenAPIDefinition
@SecurityScheme(
		name = "blog_app_apis",
		scheme = "bearer",
		type = SecuritySchemeType.HTTP,
		in = SecuritySchemeIn.HEADER,
		bearerFormat = "JWT"
)
public class BlogAppApisApplication implements CommandLineRunner {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	@Bean
public ModelMapper modelMapper(){
		return new ModelMapper();
}

	@Override
	public void run(String... args) throws Exception {

		System.out.println(this.passwordEncoder.encode("qwerty"));

		try {


			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");

			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");
			List<Role> roles = List.of(role, role1);

			List<Role> savedRoles =	this.roleRepo.saveAll(roles);
			savedRoles.forEach(r -> {
				System.out.println(r.getName());
			});

		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
