package web.proyecto.oracle.controller;



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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import web.proyecto.oracle.editors.TipoEditor;
import web.proyecto.oracle.models.dao.IPublicacion;
import web.proyecto.oracle.models.dao.ITipoDao;
import web.proyecto.oracle.models.entity.Publicacion;
import web.proyecto.oracle.models.entity.TipoPublicacion;
import web.proyecto.oracle.models.service.IServiceGeneral;
import web.proyecto.oracle.paginador.PaginaRender;

@Controller
@RequestMapping("/publicacion")
@SessionAttributes("publicacion")
public class PublicacionController {
    
	@Autowired
	private IServiceGeneral general;
	
	@Autowired
	private IPublicacion publicacionDao;
	
	@Autowired
	private ITipoDao tipoDao;
	
	int num;
	
	String fechaGlobal;
	
	Long idGlobal;
	
	@Autowired
	private TipoEditor editor;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		
		binder.registerCustomEditor(TipoPublicacion.class,"publicacion" , editor);
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form" , method = RequestMethod.GET)
	public String formPublicacion(Model model) {
		
		Publicacion publicacion = new Publicacion();
		
		model.addAttribute("titulo","Formulario de publicacion");
		model.addAttribute("publicacion",publicacion);
		model.addAttribute("boton", "Realizar publicacion");
		
		return "formPublicacion";
		
	}
	

	
	@ModelAttribute("tipos")
	public List<TipoPublicacion> getVectorPublicacion(){
		
		return general.findAllTipo();
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/form" , method = RequestMethod.POST)
	public String guardarPublicacion(@Valid Publicacion publicacion , BindingResult result , @RequestParam("file") MultipartFile file , Model model , SessionStatus status) {
		String cortarDesc;
		
		if(result.hasErrors()) {
			
			model.addAttribute("titulo","Formulario de evento");
			model.addAttribute("boton" , "Realizar publicacion");
			return "formPublicacion";
			
		}
		
		
		if(!file.isEmpty()) {
			
			
			 String rootPath = "C://Temp//uploads";
			  
			  try {
				  
				byte[] bytes = file.getBytes();
				Path rutaCompleta = Paths.get(rootPath + "//" + file.getOriginalFilename());
				Files.write(rutaCompleta,bytes);
				publicacion.setFoto(file.getOriginalFilename());
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			
		}
		
		
		TipoPublicacion tipoPublicacion = general.findByIdTipoPublicacion(publicacion.getTipoPublicacion().getId());
		cortarDesc = publicacion.getDescripcion().substring(0,90);
		publicacion.setSubdescripcion(cortarDesc);
		publicacion.setFecha(new Date());
		publicacion.setTipoPublicacion(tipoPublicacion);
		
	    general.save(publicacion);
		
	    status.setComplete();
	    
		return "redirect:/publicacion/lista";
		
	}
	
	@RequestMapping(value = "/lista")
	public String listaPublicaciones(@RequestParam(name = "page" , defaultValue = "0")int page, Model model) {
		
		   Pageable pageable = PageRequest.of(page,4);
		   
		   Page<Publicacion> publicaciones = general.findAll(pageable);
		   PaginaRender<Publicacion> paginaRender = new PaginaRender<>("/publicacion/lista",publicaciones);
		   
		   num = paginaRender.getPaginaActual();
		  
		   model.addAttribute("titulo","Lista de publicaciones");
		   model.addAttribute("publicaciones",publicaciones);
		   model.addAttribute("page", paginaRender);
		   
		   return "listaPublicaciones";
		   
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/ver/{id}" , method = RequestMethod.GET)
	public String editarEvento(@PathVariable Long id , Model model) {
		
	   Publicacion publicacion = null;

	if(id>0) {
		
		 publicacion = general.findByIdPublicacion(id);
		
		if(publicacion == null) {
			
			 return  "redirect:/publicacion/lista";
			
		}
		
	}else {
		
		return  "redirect:/publicacion/lista";
		
	}
		 
	    
	    model.addAttribute("titulo","Modificar publicacion");
	    model.addAttribute("publicacion",publicacion);
	    model.addAttribute("boton" , "Editar Publicacion");
	    
	   
		return "formPublicacion";
		  
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/batman/{id}" , method =  RequestMethod.GET)
	public String eliminarPublicacion(@PathVariable() Long id , Model model) {
		
		      if(id>0) {
		    	  
		    	  general.deletePublicacion(id); 
		    	  
		      }else {
		    	  
		    	  return  "redirect:/publicacion/lista"; 
		    	  
		      }
		   
		
		      return  "redirect:/publicacion/lista"; 
		
	}
	
	
	@RequestMapping(value = "/info/{id}" , method = RequestMethod.GET)
	public String infoPublicacion(@PathVariable Long id , Model model) {
		
		Publicacion publicacion = new Publicacion();
		   
		   if(id>0) {
			   
			  publicacion = general.findByIdPublicacion(id);
			  
			  if(publicacion == null) {
				  
				  return  "redirect:/publicacion/lista"; 
				  
			  }
			  
		   }else {
			   
			   
			   return  "redirect:/publicacion/lista"; 
			   
		   }
		
		   
		   model.addAttribute("publicacion" , publicacion);
		   
		   return "verPublicacion";
		
	}
	
	
	@RequestMapping(value = "/filtro" , method = RequestMethod.POST)
	public String filtroFormulario(@RequestParam(value = "tipoId" ,required = false)String tipoId , @RequestParam(value = "fecha" , required = false) String fecha , Model model) {
		
		  
		if(tipoId.equalsIgnoreCase("0") && fecha == "") {
			
			return "redirect:/publicacion/lista";
			
		}else if(fecha == null || fecha == "") {
			
			
			if(tipoId != null) {
				
				idGlobal = (long) Integer.parseInt(tipoId);
			}
			
			
			return "redirect:/publicacion/filtroLong/" + tipoId;
			
		}else if(tipoId .equalsIgnoreCase("0") || tipoId == null) {
			
			fechaGlobal = fecha;
			return "redirect:/publicacion/filtroFecha/" + fecha;
			
		}else {
			
			if(tipoId != null) {
				
				idGlobal = (long) Integer.parseInt(tipoId);
				
			}
			
			
			fechaGlobal = fecha;
			return "redirect:/publicacion/filtroTotal/" + tipoId + "/" + fecha;
			
		}
		
		
		
	}
	
	@RequestMapping(value = "/filtroLong/{tipoId}" , method = RequestMethod.GET)
	public String filtroXtipoPublicacion(@RequestParam(name = "page" , defaultValue = "0")int page,@PathVariable String tipoId , Model model) {
		
		   Pageable pageable = PageRequest.of(page,4);
		   
		   Page<Publicacion> publicaciones = publicacionDao.findByPublicacionIdFiltro(idGlobal , pageable);
		   PaginaRender<Publicacion> paginaRender = new PaginaRender<>("/publicacion/filtroLong/{tipoId}",publicaciones);
		   
		   num = paginaRender.getPaginaActual();
		
		
		model.addAttribute("titulo","Lista de publicaciones");
		model.addAttribute("publicaciones",publicaciones);
		model.addAttribute("page", paginaRender);
		model.addAttribute("nombre",general.findByIdTipoPublicacion(idGlobal));
		model.addAttribute("minus",tipoDao.findByIdTipoPublicacionNotId(idGlobal));
		
		return "listaPublicaciones";
		
	}
	
	@RequestMapping(value = "/filtroFecha/{fecha}" , method = RequestMethod.GET)
	public String filtroXFecha(@RequestParam(name = "page" , defaultValue = "0")int page,@PathVariable String fecha , Model model) {
		
		   Pageable pageable = PageRequest.of(page,4);
		   Page<Publicacion> publicaciones = publicacionDao.findByPublicacionByFecha(fechaGlobal , pageable); 
		   
		  
		   PaginaRender<Publicacion> paginaRender = new PaginaRender<>("/publicacion/filtroFecha/{fecha}",publicaciones);
		   
		   num = paginaRender.getPaginaActual();
		
		
		model.addAttribute("titulo","Lista de publicaciones");
		model.addAttribute("publicaciones",publicaciones);
		model.addAttribute("page", paginaRender);
		model.addAttribute("fechaPuesta",fechaGlobal);
		
		return "listaPublicaciones";
		
	}
	
	
	@RequestMapping(value = "/filtroTotal/{tipoId}/{fecha}" , method = RequestMethod.GET)
	public String filtroTotal(@RequestParam(name = "page" , defaultValue = "0")int page,@PathVariable String tipoId , @PathVariable String fecha , Model model) {
		
		
		    Pageable pageable = PageRequest.of(page,4);
		   
		   Page<Publicacion> publicaciones = publicacionDao.findByPublicacionIdAndFecha(idGlobal, fechaGlobal , pageable);
		   PaginaRender<Publicacion> paginaRender = new PaginaRender<>("/publicacion/filtroTotal/{tipoId}/{fecha}",publicaciones);
		   
		   num = paginaRender.getPaginaActual();
		
		model.addAttribute("titulo","Lista de publicaciones");
		model.addAttribute("nombre",general.findByIdTipoPublicacion(idGlobal));
		model.addAttribute("minus",tipoDao.findByIdTipoPublicacionNotId(idGlobal));
		model.addAttribute("fechaPuesta",fechaGlobal);
		model.addAttribute("publicaciones",publicaciones);
		model.addAttribute("page",paginaRender);
		
	
		
		return "listaPublicaciones";
		
	}
	
	
	
	
	
	
}
