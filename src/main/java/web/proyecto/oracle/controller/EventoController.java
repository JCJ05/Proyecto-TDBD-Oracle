package web.proyecto.oracle.controller;


import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;


import web.proyecto.oracle.editors.PaisEditor;
import web.proyecto.oracle.models.entity.Ciudad;
import web.proyecto.oracle.models.entity.Evento;
import web.proyecto.oracle.models.service.IServiceGeneral;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/evento")
@SessionAttributes("evento")
public class EventoController {

	@Autowired
	private IServiceGeneral general;
	
	@Autowired
	private PaisEditor editor;
	
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(Ciudad.class,"ciudad",editor);
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form" , method = RequestMethod.GET)
	public String crearFormulario(Model model) {
		
		Evento evento = new Evento();
		
		model.addAttribute("titulo","Formulario de Evento");
		model.addAttribute("evento",evento);
	    
		
		return "formEvento";
	}
	
	
	@ModelAttribute("hoy")
	public String fechaHoy() {
		
		Date date = new Date();
		String fecha;
		
		fecha = new SimpleDateFormat("yyyy-MM-dd").format(date).toString();
		
		
		return  fecha;
		
	}
	
	@ModelAttribute("ciudades")
	public List<Ciudad> getCiudades(){
		
		return general.findAllCiudad();
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form/" , method = RequestMethod.POST)
	public String guardarFormulario(@Valid Evento evento, BindingResult result , @RequestParam("file") MultipartFile foto ,Model model , SessionStatus status) {
		
		if(result.hasErrors()) {
			
			model.addAttribute("titulo","Formulario de Evento");
			return "formEvento";
		}
		
		
		
		 if(!foto.isEmpty()) {
			  
			  
			  String rootPath = "C://Temp//uploads";
			  
			  try {
				  
				byte[] bytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
				Files.write(rutaCompleta,bytes);
				evento.setFoto(foto.getOriginalFilename());
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			  
		  }
		  
		  
		
	   Ciudad ciudad = general.findByIdCiudad(evento.getCiudad().getId());
		
	 
	   
	   evento.setCiudad(ciudad);
	   general.save(evento);
	   
	   status.setComplete();
	   
	     return  "redirect:/evento/listaEventos";
	     
	}
	
	@RequestMapping(value = "/listaEventos" , method = RequestMethod.GET)
	public String listarEventos(Model model) {
		
		model.addAttribute("titulo", "Lista de Eventos");
		model.addAttribute("eventos",general.findAll());
		
		return "listaEventos";
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/info/{id}" , method = RequestMethod.GET)
	public String editarEvento(@PathVariable Long id , Model model) {
		
		Evento evento = null;

	if(id>0) {
		
		 evento = general.findById(id);
		
		if(evento == null) {
			
			 return  "redirect:/evento/listaEventos";
			
		}
		
	}else {
		
		return  "redirect:/evento/listaEventos";
		
	}
		 
	    
	    model.addAttribute("titulo","Modificar evento");
	    model.addAttribute("evento",evento);
	   
		return "formEvento";
		  
		
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/spiderman/{id}")
	public String deleteEvento(@PathVariable Long id , Model model) {
		
		if(id>0) {
			
			general.delete(id);
			
		}
		
		return  "redirect:/evento/listaEventos";
		
	}
	
	@RequestMapping(value = "/ver/{id}")
	public String verEvento(@PathVariable Long id , Model model) {
		
		
		model.addAttribute("evento", general.findById(id));
		
		return "verEvento";
		
	}
	
}
