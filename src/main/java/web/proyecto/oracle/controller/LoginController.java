package web.proyecto.oracle.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginVista(@RequestParam(value = "error" , required = false)String error, Model model , Principal principal) {
		
		
		  if(principal != null) {
			  
			  return "redirect:/index";
			  
		  }
		  
		  if(error != null) {
			  
			  model.addAttribute("error", "Nombre de usuario o contraseña incorrecto vuelva a intentarlo");
			  
		  }
		
		  return "login";
		
		
	}
	
	
	
}
