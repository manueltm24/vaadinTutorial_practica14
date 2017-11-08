package com.example.demo;

import com.example.demo.Entidades.Customer;
import com.example.demo.Servicios.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class VaadinTutorialApplication {

	public static void main(String[] args) {
//		SpringApplication.run(VaadinTutorialApplication.class, args);
		ApplicationContext applicationContext=SpringApplication.run(VaadinTutorialApplication.class, args);


		CustomerService customerService= (CustomerService) applicationContext.getBean("customerService");
		customerService.guardar(new Customer("Manuel","Tolentino","24/08/1994","manutm24@gmail.com"));

	}
}
