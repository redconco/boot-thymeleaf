package idu.cs.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import idu.cs.domain.User;
import idu.cs.exception.ResourceNotFoundException;
import idu.cs.repository.UserRepository;


@Controller
/* @Controller, @Service, @Repository 모르면 @Component로 Bean 객체를 사용한다고 알림
 * Spring Framework에게 이 클래스로 부터 작성된 객체는 Controller 역할을 함을 알려줌
 * Spring 이 이 클래스로 부터 Bean 객체를 생성해서 등록할 수 있음
 * */
public class UserController {
	@Autowired UserRepository userRepo; // Dependency Injection
	
	@GetMapping("/")
	public String home(Model model) {
		return "index";
	}
	
	@GetMapping("/user-login-form") // 추가한거
	public String getLoginForm(Model model) {
		return "login";
	}
	@PostMapping("/login")
	public String loginUser(@Valid User user, HttpSession session) {
		System.out.println("login process" + user.getUserId());
		User sessionUser = userRepo.findByUserId(user.getUserId());
		if(sessionUser == null) {
			System.out.println("id error" + user.getUserId());
			return "redirect:/user-login-form";
		}
		if(!sessionUser.getUserPw().equals(user.getUserPw())) {
			System.out.println("pw error" + user.getUserPw());
			return "redirect:/user-login-form";
		}
		session.setAttribute("user", sessionUser);
		return "redirect:/userlist-form";
	}
	@GetMapping("/user-logout")
	public String logoutUser(HttpSession session) {
		System.out.println("logout process");
		session.removeAttribute("user");
		//session.invalidate(); 모두 삭제
		return "redirect:/";
	}
	@GetMapping("/user-regist-form") // 추가한거
	public String getRegForm(Model model) {
		return "register";
	}
	@PostMapping("/regist")
	public String registUser(@Valid User user, Model model) {
		if(userRepo.save(user)==null) {
			return "redirect:/user-regist-form";
		}
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/userlist-form";
	}
	
	
	@GetMapping("/userlist-form")
	public String getAllUser(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "userlist";
	}

	@GetMapping("/user-update-form")
	public String getUpdateUser(HttpSession session) {
		return "update";
	}
	
	@PostMapping("/update")
	public String updateUser(@Valid User user, HttpSession session) {
		if(userRepo.save(user)==null) {
			return "redirect:/";
		}
		else {
			session.setAttribute("user", user);
			return "redirect:/userlist-form";
		}
	}

	@PutMapping("/users/{id}") //@PatchMapping 수정한 필드만 고침
	public String updateUser(@PathVariable(value = "id") Long userId, @Valid User userDetails, Model model) {
		/*//System.out.println(userDetails.getName());
		//userRepo.save();
		User user = userRepo.findById(userId).get(); 
		// user는 DB로 부터 읽어온 객체
		user.setName(userDetails.getName()); // userDetails는 전송한 객체
		user.setCompany(userDetails.getCompany());*/
		userRepo.save(userDetails);
		return "redirect:/users";
	}
	
	
	@PostMapping("/users")
	public String createUser(@Valid @RequestBody User user, Model model) {
		userRepo.save(user);
		model.addAttribute("users", userRepo.findAll());
		return "redirect:/users";
	}
/*
	@PutMapping("/users/{id}") //@PatchMapping 수정한 필드만 고침
	public String updateUser(@PathVariable(value = "id") Long userId, @Valid User userDetails, Model model) {
		//System.out.println(userDetails.getName());
		//userRepo.save();
		//User user = userRepo.findById(userId).get(); 
		// user는 DB로 부터 읽어온 객체
		//user.setName(userDetails.getName()); // userDetails는 전송한 객체
		//user.setCompany(userDetails.getCompany());
		userRepo.save(userDetails);
		return "redirect:/users";
	}
*/
	
	@DeleteMapping("/users/{id}")
	public String deleteUser(@PathVariable(value = "id") Long userId, Model model) {
		User user = userRepo.findById(userId).get();
		userRepo.delete(user);
		model.addAttribute("name", user.getName());
		return "user-deleted";
	}

	@GetMapping("/users/{id}")
	public String getUserById(@PathVariable(value = "id") Long userId, Model model)
			throws ResourceNotFoundException {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
		model.addAttribute("user", user);
		/*
		model.addAttribute("id", user.getId());
		model.addAttribute("name", user.getName());
		model.addAttribute("company", user.getCompany());
		*/
		return "user"; // user
		//return ResponseEntity.ok().body(user);
	}
	
	@GetMapping("/users/fn")
	public String getUserByName(@Param(value="name") String name, Model model)
			throws ResourceNotFoundException {
		List<User> users = userRepo.findByName(name);
		model.addAttribute("users", users);
		return "userlist"; // user
		//return ResponseEntity.ok().body(user);
	}
}
