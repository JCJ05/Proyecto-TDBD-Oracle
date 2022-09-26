package web.proyecto.oracle.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import web.proyecto.oracle.editors.PaisEditor;
import web.proyecto.oracle.models.entity.Ciudad;
import web.proyecto.oracle.models.entity.Rol;
import web.proyecto.oracle.models.entity.Usuario;
import web.proyecto.oracle.models.service.IServiceGeneral;

@Controller
@RequestMapping("/usuario")
@SessionAttributes("usuario")
public class UsuarioController {
    
	
	@Autowired
	private IServiceGeneral general;
	
	@Autowired
	private PaisEditor editor;
	
	@Autowired
	private BCryptPasswordEncoder paswordEncoder;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(Ciudad.class,"ciudad",editor);
		
		
	}
	
	@ModelAttribute("ciudade")
	public List<Ciudad> getCiudades(){
		
		return general.findAllCiudad();
		
	}
	
	
	@RequestMapping(value = "/form" , method =  RequestMethod.GET)
	public String formUsuario(Model model ,Principal principal ) {
		
		if(principal != null) {
			
			return "redirect:/index";
			
		}
		
		
		Usuario usuario = new Usuario();
		
		model.addAttribute("titulo", "Formulario de registro");
		model.addAttribute("usuario", usuario);
		
		
		return "formRegistro";
		
	}
	
	@RequestMapping(value = "/form" , method = RequestMethod.POST)
	public String guardarUsuario(@Valid Usuario usuario , BindingResult result , Model model , SessionStatus status) {
		   
		  if(result.hasErrors()) {
			  
			  model.addAttribute("titulo","Formulario de Registro");
			  
			  return "formRegistro";
			  
			  
		  }
		  
		  
		  List<Rol> vRoles = new ArrayList<>();
		  

		  Ciudad ciudad = general.findByIdCiudad(usuario.getCiudad().getId());
		  String password = usuario.getPassword(); 
		  String byCripPaswword = paswordEncoder.encode(password);
		  Rol rol = new Rol();
		  rol.setAuthoritie("ROLE_USER");
		  vRoles.add(rol);
		  
		  
		  usuario.setRoles(vRoles);
		  usuario.setCiudad(ciudad);
		  usuario.setEnabled(true);
		  usuario.setPassword(byCripPaswword);
		  
		  
		  general.saveUsuario(usuario);
		  
		  status.setComplete();
		
		return "redirect:/login";
		
	}
	
	
	
}
