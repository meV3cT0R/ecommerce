package com.vector.shop;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.vector.shop.admin.Admin;
import com.vector.shop.product.Product;
import com.vector.shop.product.ProductService;

import com.vector.shop.storage.StorageService;
import com.vector.shop.user.User;
import com.vector.shop.user.UserRepository;


@SpringBootApplication
public class ShopApplication {
	private static final Logger log = LoggerFactory.getLogger(ShopApplication.class);
	@Autowired
	private PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Bean
	public CommandLineRunner cliRunner(UserRepository userRepo,ProductService productService,StorageService storageService) {
		return args-> {
			User user = new Admin();
			Product product = new Product();

			//product
			product.setName("Hello");
			product.setPrice(1000);
			product.setQuantity(10);

			//user
			user.setFirstName("Sumit");
			user.setLastName("Shrestha");
			user.setGender('M');
			user.setUsername("vector");
			user.setPassword(passwordEncoder.encode("hello"));
			user.setDob(new Date());
			user.setJoinedDate(new Date());
			user.setAdmin(true);

			//save
			userRepo.save(user);

			storageService.deleteAll();
			storageService.init();

			Files.copy(Paths.get("/home/vector/Downloads/hello-kitty-wallpaper-37_605.webp"),Paths.get("/home/vector/Documents/vscode-workspace/shop/images/kitty.webp"));
			product.setImage("http://localhost:8080/product/images/kitty.webp");
			product.increaseRating(5);
			product.increaseRating(2);
			product.setDescription("Nice hello kitty\nkeeping this in your bedroom will bring you great luck");
			log.info(String.format("Average rating : %f",product.getAverageRating()));
			productService.save(product);	
		};
	}
}
