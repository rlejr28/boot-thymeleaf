package idu.cs.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import idu.cs.domain.User;
import idu.cs.exception.ResourceNotFoundException;
import idu.cs.repository.UserRepository;

@Controller
public class HomeController {
	@Autowired UserRepository userRepo; // Dependency Injection
	
	@GetMapping("/test")
	public String home(Model model) {
		model.addAttribute("test", "인덕 컴소");
		model.addAttribute("egy", "유응구");
		return "index";
	}
	@GetMapping("/users")
	public String getAllUser(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "userlist";
	}
	@GetMapping("/users/{id}")
	   public String getUserById(@PathVariable(value = "id") Long userId, 
	         Model model ) throws ResourceNotFoundException {
	      User user = userRepo.findById(userId)
	            .orElseThrow(() -> 
	            new ResourceNotFoundException("not found " + userId ));
	      model.addAttribute("id", user.getId());
	      model.addAttribute("name", user.getName());
	      model.addAttribute("company", user.getCompany());
	      return "user";
	   }
	
	@GetMapping("/")
	public String loadWelcome(Model model) {
		return "welcome";
	}
	
	@GetMapping("/regform")
	public String loadRegForm(Model model) {
		return "regform";
	}
	
	@PostMapping("/users")
	public String createUser(@Valid @RequestBody User user, Model model) {
		userRepo.save(user);
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/users";
	}
	@DeleteMapping("/users/{id}")
	//@RequestMapping(value=""/users/{id}" method=RequestMethod.DELETE)
	   public ResponseEntity<User> updateUserById(@PathVariable(value = "id") Long userId, 
	         @Valid @RequestBody User userDetails, Model model ) throws ResourceNotFoundException {
	    //userDetails 폼을 통해 전송된 객체, user는 id로 jpa를 통해서 가져온 객체  
		User user = userRepo.findById(userId) //userDetails.getId())
	            .orElseThrow(() -> 
	            new ResourceNotFoundException("not found " + userId ));
		user.setName(userDetails.getName());
		user.setCompany(userDetails.getCompany());
	    User userUpdate = userRepo.save(user); // 객체 삭제 -> jpa : record 삭제로 적용
	    
	    return ResponseEntity.or(userUpdate);
	   }
	@DeleteMapping("/users/{id}")
	//@RequestMapping(value=""/users/{id}" method=RequestMethod.DELETE)
	   public String deleteUserById(@PathVariable(value = "id") Long userId, 
	         Model model ) throws ResourceNotFoundException {
	      User user = userRepo.findById(userId)
	            .orElseThrow(() -> 
	            new ResourceNotFoundException("not found " + userId ));
	      userRepo.delete(user); // 객체 삭제 -> jpa : record 삭제로 적용
	      model.addAttribute("name", user.getName());
	      return "disjoin";
	   }
	
}