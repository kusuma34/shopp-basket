package com.example.shoppingcart.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingcart.dto.MyProfile;
import com.example.shoppingcart.dto.ProductDTO;
import com.example.shoppingcart.global.GlobalData;
import com.example.shoppingcart.model.CustomUserDetail;
import com.example.shoppingcart.model.Role;
import com.example.shoppingcart.model.User;
import com.example.shoppingcart.repository.RoleRepository;
import com.example.shoppingcart.repository.UserRepository;
import com.example.shoppingcart.service.CustomUserDetailsService;

@Controller
//@CrossOrigin(origins = "http://localhost:8080", exposedHeaders = "token")
public class LoginController {
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private CustomUserDetailsService userdetailservice;
		
	
	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}
	
	public static boolean isLogged() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null != authentication && !("anonymousUser").equals(authentication.getName());
    }



	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@PostMapping("/register")
	public String postRegister(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
		String pswd = user.getPassword();
		user.setPassword(bCryptPasswordEncoder.encode(pswd));
		List<Role> roles = new ArrayList<>();
		roles.add(roleRepository.findById(2).get());
		user.setRoles(roles);
		userRepository.save(user);
		request.login(user.getUsername(), pswd);
		return "login";
	}
	
	@GetMapping("/changepassword")
	public  String getChangePassword() {	
		return "changePassword";
	}
	
	@PostMapping("/changepassword")
	public  String postChangePassword(@RequestParam("oldpassword") String oldpassword,@RequestParam("newpassword") String newpassword, @RequestParam("confirmpassword") String confirmpassword, Principal principal, HttpSession session) {
		String username=principal.getName();
		System.out.println("-----------currentuser------------"+username);
		User currentuser = userRepository.findByUsername(username).get();	
		if(bCryptPasswordEncoder.matches(oldpassword, currentuser.getPassword())) {
			currentuser.setPassword(bCryptPasswordEncoder.encode(newpassword));
			userRepository.save(currentuser);
			//session.setAttribute("message", new Message("Your password is updated","successs"));
		}
		System.out.println(currentuser);
		return "changePassword";
	}
	
	@GetMapping("/myprofile")
	public  String getMyProfile(Authentication authentication, Model model) {
		User currentuser=null;
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		String email = token.getPrincipal().getAttributes().get("email").toString();
		System.out.println("logged in user: ------------"+email);
		MyProfile myprofile=new MyProfile();
		try {
			currentuser= userRepository.findByEmail(email).get();
			myprofile.setEmail(currentuser.getEmail());
			myprofile.setFirstname(currentuser.getFirstName());
			myprofile.setLastname(currentuser.getLastName());
			myprofile.setUsername(currentuser.getUsername());
		}
		catch (Exception e) {
			System.out.println(e);
		}
		model.addAttribute("currentuser", myprofile);
		return "myProfile";
	}
	
	@PostMapping("/myprofile")
	public  String postMyProfile(@ModelAttribute("currentuser") MyProfile currentuser, Principal principal) {
		String username=principal.getName();
		User currentuser1 = userRepository.findByUsername(username).get();	
		currentuser1.setFirstName(currentuser.getFirstname());
		currentuser1.setLastName(currentuser.getLastname());
		userRepository.save(currentuser1);
		return "myProfile";
	}

}
