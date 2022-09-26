package web.proyecto.oracle.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import web.proyecto.oracle.models.dao.IUsuarioDao;
import web.proyecto.oracle.models.dao.IVoluntarioDao;
import web.proyecto.oracle.models.entity.EventoVoluntario;
import web.proyecto.oracle.models.entity.Usuario;
import web.proyecto.oracle.models.entity.Voluntario;
import web.proyecto.oracle.models.service.IServiceGeneral;

@Controller
@RequestMapping("/eventoVoluntario")
@SessionAttributes("eventoVoluntario")
public class EventoVoluntatioController {
	
	
	@Autowired
	private IServiceGeneral general;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	
	@Autowired
	private IVoluntarioDao voluntarioDao;
	
	 static Long idGeneral;
	
	@RequestMapping(value = "/inscribirse/{id}")
	public String verificarVoluntario(@PathVariable Long id , Model model , Authentication authentication , RedirectAttributes attributes , SessionStatus status) {
		
	
		
		if(authentication == null) {
			
			attributes.addFlashAttribute("error","Para inscribirse a un evento debe crearse un usuario o iniciar sesion");
			
			return "redirect:/evento/ver/" + id;
			
		}else {
			
			
			Usuario usuario = usuarioDao.findByCorreo(authentication.getName());
		    
			Voluntario voluntario = voluntarioDao.findByIdUsuarioXId(usuario.getId());
			
			 if(voluntario == null) {
				 
				 idGeneral = id;
				 attributes.addFlashAttribute("success","Querido Usuario " + usuario.getNombre().toUpperCase() + " " + "para registrarse como voluntario por primera vez debe llenar este pequeño formulario");
				 
				 return "redirect:/voluntario/crear";
				 
			 }else {
				 
				 EventoVoluntario eventoVoluntario = new EventoVoluntario();
				 
				 eventoVoluntario.setEvento(general.findById(id));
				 eventoVoluntario.setVoluntario(voluntario);
				 
				 general.saveEventoVoluntario(eventoVoluntario);
				 
				 status.setComplete();
				 
				 
			 }
			
			
			
		}
		
		
		return "redirect:/voluntario/agradecimiento";
		
	}
	
	
	
	
	

}
