package com.arun.myRetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import com.arun.myRetail.entity.CurrentPrice;
import com.arun.myRetail.entity.Product;
import com.arun.myRetail.repository.ProductRepository;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableMongoRepositories("com.arun.myRetail.repository")
public class MyRetailApplication implements CommandLineRunner{

	@Autowired
	private ProductRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(MyRetailApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Override
	public void run(String... args) throws Exception {
		seedData();
	}
	
	/**
	 * This method is used to provide initial seed data when the app starts.
	 */
	public void seedData() {
		repository.deleteAll();

		CurrentPrice c1 = new CurrentPrice("1", "USD");
		CurrentPrice c2 = new CurrentPrice("2", "USD");
		CurrentPrice c3 = new CurrentPrice("3", "USD");
		CurrentPrice c4 = new CurrentPrice("4", "INR");
		CurrentPrice c5 = new CurrentPrice("5", "USD");
		CurrentPrice c6 = new CurrentPrice("6", "INR");

		repository.save(new Product(13860428, c1));
		repository.save(new Product(13860429, c2));
		repository.save(new Product(13860430, c3));
		repository.save(new Product(13860431, c4));
		repository.save(new Product(13860432, c5));
		repository.save(new Product(13860433, c6));

		System.out.println("Product ids with findAll():");
		System.out.println("---------------------------");
		for (Product product : repository.findAll()) {
			System.out.println(product.getProductId());
		}
	}
}
