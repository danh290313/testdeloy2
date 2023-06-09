package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("count-users")
	public String getCountUsers(){
		long numberUsers = userRepository.count();
		return "Number of users = "+numberUsers;
	}

	@GetMapping("add-user")
	public String addUser() {
		User user = new User();
		user.setFirstName("Render");
		user.setLastName("Spring Boot");
		userRepository.save(user);
		return "User added successfully";
	}
	
	@GetMapping("user/{id}")
	public ResponseEntity<Object> add(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty())
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(user.get());
	}
	
	@GetMapping("users")
	public ResponseEntity<Object> getUsers() {

		List<User> users = userRepository.findAll();
		if (users.isEmpty())
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(users);
	}
	
	@DeleteMapping("user/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty())
			return ResponseEntity.notFound().build();
		
		userRepository.deleteById(id);
		return ResponseEntity.ok("Delete successfully");	
	
	}


}
