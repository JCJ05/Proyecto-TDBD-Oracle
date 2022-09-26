package web.proyecto.oracle.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import web.proyecto.oracle.models.dao.IUsuarioDao;
import web.proyecto.oracle.models.entity.Usuario;
import web.proyecto.oracle.models.entity.Voluntario;
import web.proyecto.oracle.models.service.IServiceGeneral;

@Controller
@RequestMapping("/voluntario")
@SessionAttributes("voluntario")
public class VoluntarioController {
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private IServiceGeneral general;
	
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/crear" , method = RequestMethod.GET)
	public String crearVoluntario(Authentication authentication , Model model) {
		
		
		Usuario usuario = usuarioDao.findByCorreo(authentication.getName());
		
		Voluntario voluntario = new Voluntario();
		
		model.addAttribute("titulo", "Gracias por querer ser parte de la causa " + usuario.getNombre());
		model.addAttribute("usuario", usuario);
		model.addAttribute("voluntario",voluntario);
		
		return "formVoluntario";
		
		
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/form" , method = RequestMethod.POST)
	public String guardarVoluntario(@Valid Voluntario voluntario,BindingResult result,Authentication authentication , Model model) {
		
		Usuario usuario = usuarioDao.findByCorreo(authentication.getName());
		
		 if(result.hasErrors()) {
			 
			 model.addAttribute("titulo", "Gracias por querer ser parte de la causa " + usuario.getNombre());
			 return "formVoluntario";
			 
		 }
		
		 voluntario.setUsuario(usuario);
		 general.saveVoluntario(voluntario);
		
		 
		 return "redirect:/evento/ver/" + EventoVoluntatioController.idGeneral;
		
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(value = "/agradecimiento")
	public String paginaAgradecimiento() {
		
		return "voluntarioAgradecimiento";
	}
	

}
