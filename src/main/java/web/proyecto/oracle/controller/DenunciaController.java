package web.proyecto.oracle.controller;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
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
import org.springframework.security.core.Authentication;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import web.proyecto.oracle.editors.DenunciaEditor;
import web.proyecto.oracle.editors.PaisEditor;
import web.proyecto.oracle.models.dao.ICiudadDao;
import web.proyecto.oracle.models.dao.IDenunciaDao;
import web.proyecto.oracle.models.dao.ITipoDenuncia;
import web.proyecto.oracle.models.dao.IUsuarioDao;
import web.proyecto.oracle.models.entity.Ciudad;
import web.proyecto.oracle.models.entity.Denuncia;
import web.proyecto.oracle.models.entity.TipoDenuncia;
import web.proyecto.oracle.models.entity.Usuario;
import web.proyecto.oracle.models.service.IServiceGeneral;
import web.proyecto.oracle.paginador.PaginaRender;

@Controller
@RequestMapping("/denuncia")
@SessionAttributes("denuncia")
public class DenunciaController {

	@Autowired
	private IServiceGeneral general;
	
	@Autowired
	private PaisEditor editor;
	
	@Autowired
	private DenunciaEditor denunciaEditor;
	
	@Autowired
	private IDenunciaDao denunciaDao;
	
	@Autowired
	private ITipoDenuncia tipoDenuncia;
	
	@Autowired
	private ICiudadDao ciudadDao;
	
	@Autowired
	private IUsuarioDao usuarioDao;
	
	int num;
	
	String fechaGeneral;
	
	String valorGeneral;
	
	Long idCiudadGeneral;
	
	Long idGeneral;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		
		binder.registerCustomEditor(Ciudad.class, "ciudad" , editor);
		binder.registerCustomEditor(TipoDenuncia.class, "tipoDenuncia" ,denunciaEditor);
		
		
	}
	
	@ModelAttribute("tipos")
	public List<TipoDenuncia> getDenuncias(){
		
		  return general.findAllTipoDenuncia();
		
	}
	
	
	
	@ModelAttribute("ciudades")
	public List<Ciudad> getCiudades(){
		
		return general.findAllCiudad();
		
	}
	
	
	@RequestMapping(value = "/informacion")
	public String informacion() {
		
		return "denuncia";
		
	}
	
	
	@RequestMapping(value = "/form" , method = RequestMethod.GET)
	public String formDenuncia(Model model) {
		
		Denuncia denuncia = new Denuncia();
		
		model.addAttribute("denuncia",denuncia);
		model.addAttribute("titulo","Formulario de denuncias");
		model.addAttribute("boton","Enviar denuncia");
		
		
		return "formDenuncias";
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/info/{id}")
	public String verDenuncia(@PathVariable Long id , Model model) {
		
		 Denuncia denuncia = null;
		
		 if(id>0) {
			 
			 
			 denuncia = general.findByIdDenuncia(id);
			 
			 if(denuncia == null) {
				 
				 return "redirect:/denuncia/listar";
				 
			 }
			 
		 }else {
			
			 return "redirect:/denuncia/listar";
			 
		 }
		 
		 model.addAttribute("denuncia",denuncia);
		
		 return "verDenuncia";
	}
	
	
	@RequestMapping(value = "/form" , method = RequestMethod.POST)
	public String guardarDenuncia(@Valid Denuncia denuncia , BindingResult result , @RequestParam("file")MultipartFile file ,@RequestParam(value = "habilitar" , required = false  , defaultValue = "false")Boolean habilitar,Model model , Principal principal ,SessionStatus status , Authentication authentication){
		
		
		      if(result.hasErrors()) {
		    	  
		    	  model.addAttribute("titulo","Fomulario de denuncias");
		    	  model.addAttribute("boton", "Enviar denuncia");
		    	  
		    	  return "formDenuncias";
		    	  
		      }
		      
		      
		      if(!file.isEmpty()) {
				  
				  
				  String rootPath = "C://Temp//uploads";
				  
				  try {
					  
					byte[] bytes = file.getBytes();
					Path rutaCompleta = Paths.get(rootPath + "//" + file.getOriginalFilename());
					Files.write(rutaCompleta,bytes);
					denuncia.setFoto(file.getOriginalFilename());
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				  
			  }
			  
		      Long idUsuario = null;
		      
		      if(principal == null || habilitar == true) {
		    	  
		    	 idUsuario = (long) 50001;
		    	  
		      }else {
		    	  
		    	  if(authentication != null) {
		    		  
		    		 Usuario usuario = usuarioDao.findByCorreo(authentication.getName());
		    		 idUsuario = usuario.getId();
		    	  }
		    	  
		      }
		      
		     
		      
		      Usuario usuario = general.findByIdUsuario(idUsuario);
		      Ciudad ciudad = general.findByIdCiudad(denuncia.getCiudad().getId());
		      TipoDenuncia tipoDenuncia = general.findByTipoDenuncia(denuncia.getTipoDenuncia().getId());
		      denuncia.setUsuario(usuario);
		      denuncia.setCiudad(ciudad);
		      denuncia.setTipoDenuncia(tipoDenuncia);
		      denuncia.setLeida(false);
		      denuncia.setProcesada(false);
		      denuncia.setCulminada(false);
		      
		      general.saveDenuncia(denuncia);
		      
		   
		      status.setComplete();
		
             return "redirect:/denuncia/agradecimiento";
		
		
		
	}
	
	
	
	
	@RequestMapping(value = "/agradecimiento" , method = RequestMethod.GET)
	public String informacionSuccess() {
		
		
		return "denunciaAgradecimiento";
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/listar" , method = RequestMethod.GET)
	public String listarDenuncia(@RequestParam(name = "page" , defaultValue = "0")int page , Model model) {
		
		 Pageable pageable = PageRequest.of(page,4);
		 Page<Denuncia> denuncias = general.findAllDenuncia(pageable);
		 PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/listar", denuncias);
		 
		 num = paginaRender.getPaginaActual();
		 
		 model.addAttribute("titulo","Lista de denuncias");
		 model.addAttribute("denuncias",denuncias);
		 model.addAttribute("page",paginaRender);
		
		return "listaDenuncias";
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/vista/{id}" , method = RequestMethod.GET)
	public String confirmarVista(@PathVariable Long id) {
		
		 Denuncia denuncia = general.findByIdDenuncia(id); 
		 denuncia.setLeida(true);
		 general.saveDenuncia(denuncia);
		
		return "redirect:/denuncia/listar";
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/procesar/{id}")
	public String confirmarProceso(@PathVariable Long id , RedirectAttributes attributes) {
		
		Denuncia denuncia = general.findByIdDenuncia(id);
	     
		 if(denuncia.getLeida() == false) {
			 
			 attributes.addFlashAttribute("error","No se puede procesar la denuncia aun porque no la haz leido o no lo haz marcado como leida");
			 return "redirect:/denuncia/listar";
			 
		 }else if(denuncia.getProcesada() == true){
		     
			 attributes.addFlashAttribute("error","No se puede procesar la denuncia porque ya esta procesada");
			 return "redirect:/denuncia/listar";
		 
		 }else {
			 
			 denuncia.setProcesada(true);
			 general.saveDenuncia(denuncia);
			 attributes.addFlashAttribute("success","Se proceso la denuncia correctamente");
			 
		 }
		 
		 
		 
	      return "redirect:/denuncia/listar";	
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/culminada/{id}" , method = RequestMethod.GET)
	public String culminacionDenuncia(@PathVariable Long id , RedirectAttributes attributes) {
		
		    Denuncia denuncia = general.findByIdDenuncia(id);
		    
		    if(denuncia.getLeida() == false) {
		    	
		    	 attributes.addFlashAttribute("error","No se puede culminar la denuncia aun porque no la haz leido o no lo haz marcado como leida");
				 return "redirect:/denuncia/listar";
		    	
		    	
		    }else if(denuncia.getProcesada() == false) {
		    	
		    	 attributes.addFlashAttribute("error","No se puede culminar la denuncia porque no esta procesada");
				 return "redirect:/denuncia/listar";
		    	
		    }else {
		    	
		    	denuncia.setCulminada(true);
		    	general.saveDenuncia(denuncia);
		    	attributes.addFlashAttribute("info","Denuncia culminada con exitó");
		    	
		    }
		
		    return "redirect:/denuncia/listar";
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminarDenuncia(@PathVariable Long id , RedirectAttributes attributes) {
		
		 Denuncia denuncia = general.findByIdDenuncia(id);
		 
		 if(denuncia.getLeida() == false) {
			 
			 attributes.addFlashAttribute("error","No se puede eliminar la denuncia pq a sido vista");
			 return "redirect:/denuncia/listar";
			 			 
		 }else {
			 
			 
			 general.deleleDenuncia(id);
			 attributes.addFlashAttribute("info" , "Denuncia eliminada correctamente");
		 }
		 
		
		 return "redirect:/denuncia/listar";
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtro" , method = RequestMethod.POST)
	public String filtrosDenuncia(@RequestParam(value = "dnId" , required = false)String dnId , @RequestParam(value = "fecha" , required = false)String fecha , 
			@RequestParam(value = "valor" , required = false)String valor , @RequestParam(value = "idCiudad" , required = false)String idCiudad ) {
		
		   
		if(fecha == ""  && valor.equalsIgnoreCase("0") && idCiudad.equalsIgnoreCase("0") && !dnId.equalsIgnoreCase("0")) {
			
			idGeneral = (long) Integer.parseInt(dnId);
			return "redirect:/denuncia/filtroTipo/"; 
			
		}else if(dnId.equalsIgnoreCase("0") && valor.equalsIgnoreCase("0") && idCiudad.equalsIgnoreCase("0") && fecha != "") {
			
			fechaGeneral = fecha;
			return "redirect:/denuncia/filtroFecha/";
			
		}else if(dnId.equalsIgnoreCase("0") && fecha == ""  && idCiudad.equalsIgnoreCase("0") && !valor.equalsIgnoreCase("0")) {
			
			 valorGeneral = valor;
			 return "redirect:/denuncia/filtroEstado/";
			 
		}else if(dnId.equalsIgnoreCase("0") && fecha == "" && valor.equalsIgnoreCase("0") && !idCiudad.equalsIgnoreCase("0")) {
			
			idCiudadGeneral = (long) Integer.parseInt(idCiudad); 
			return "redirect:/denuncia/filtroCiudad/";
			
		}else if(valor.equalsIgnoreCase("0") && idCiudad.equalsIgnoreCase("0") && !dnId.equalsIgnoreCase("0")) {
			
			  idGeneral = (long) Integer.parseInt(dnId);
			  fechaGeneral = fecha;
			  
			  return "redirect:/denuncia/filtro-Tipo-Fecha/";
			 
		}else if(fecha == "" && idCiudad.equalsIgnoreCase("0") && !dnId.equalsIgnoreCase("0")) {
			
			 idGeneral = (long) Integer.parseInt(dnId);
			 valorGeneral = valor;
			
			return "redirect:/denuncia/filtro-Tipo-Estado/";
			
		}else if(fecha == "" && valor.equalsIgnoreCase("0") && !dnId.equalsIgnoreCase("0")) {
			
			 idGeneral = (long) Integer.parseInt(dnId);
			 idCiudadGeneral = (long) Integer.parseInt(idCiudad); 
			
			return "redirect:/denuncia/filtro-Tipo-Ciudad/";
			
		}else if(dnId.equalsIgnoreCase("0") && idCiudad.equalsIgnoreCase("0") && fecha != "") {
			
			fechaGeneral = fecha;
			valorGeneral = valor;
			
			return "redirect:/denuncia/filtro-Fecha-Estado/";
			
		}else if(dnId.equalsIgnoreCase("0") && valor.equalsIgnoreCase("0") && fecha != "") {
			
			 fechaGeneral = fecha;
			 idCiudadGeneral = (long) Integer.parseInt(idCiudad); 
			
			return "redirect:/denuncia/filtro-Fecha-Ciudad/";
			
		}else if(dnId.equalsIgnoreCase("0") && fecha == "" && !valor.equalsIgnoreCase("0")) {
			
			  
			valorGeneral = valor;
			idCiudadGeneral = (long) Integer.parseInt(idCiudad);
			
			return "redirect:/denuncia/filtro-Estado-Ciudad/";
			
		}else if(idCiudad.equalsIgnoreCase("0") && !dnId.equalsIgnoreCase("0") && fecha != null && !valor.equalsIgnoreCase("0")) {
			
			 idGeneral = (long) Integer.parseInt(dnId);
			 fechaGeneral = fecha;
			 valorGeneral = valor;
			 
			return "redirect:/denuncia/filtro-Tipo-Fecha-Estado/";
			
		}else if(dnId.equalsIgnoreCase("0") && fecha != "" && !valor.equalsIgnoreCase("0") && !idCiudad.equalsIgnoreCase("0")) {
			
			fechaGeneral = fecha;
			valorGeneral = valor;
			idCiudadGeneral = (long) Integer.parseInt(idCiudad);
			
			return "redirect:/denuncia/filtro-Fecha-Estado-Ciudad/";
			
		}else if(fecha == "" && !dnId.equalsIgnoreCase("0")) {
			
			idGeneral = (long) Integer.parseInt(dnId);
			valorGeneral = valor;
			idCiudadGeneral = (long) Integer.parseInt(idCiudad);
			
			return "redirect:/denuncia/filtro-Tipo-Estado-Ciudad/";
			
		}else if(valor.equalsIgnoreCase("0") && !dnId.equalsIgnoreCase("0")) {
			 
			 idGeneral = (long) Integer.parseInt(dnId);
			 fechaGeneral = fecha;
			 idCiudadGeneral = (long) Integer.parseInt(idCiudad);
			
			return "redirect:/denuncia/filtro-Tipo-Fecha-Ciudad/";
			
		}else if(!idCiudad.equalsIgnoreCase("0") && fecha != "" && !dnId.equalsIgnoreCase("0") && !valor.equalsIgnoreCase("0")) {
			
			 idGeneral = (long) Integer.parseInt(dnId);
			 fechaGeneral = fecha;
			 idCiudadGeneral = (long) Integer.parseInt(idCiudad);
			 valorGeneral = valor;
			 
			 return "redirect:/denuncia/filtro-Tipo-Fecha-Estado-Ciudad/";
			
		}else {
			
			 return "redirect:/denuncia/listar";
			
		}
		
		
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtroTipo/" , method = RequestMethod.GET)
	public String filtroXtipoDenuncia(@RequestParam(value = "page" , defaultValue = "0")int page ,Model model) {
		    
		  
		     Pageable pageable = PageRequest.of(page,4);
			 Page<Denuncia> denuncias = denunciaDao.findByidDenunciaFiltro(idGeneral, pageable);
			 PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtroTipo/", denuncias);
			 
			 num = paginaRender.getPaginaActual();
			 
			 model.addAttribute("titulo","Lista de denuncias");
			 model.addAttribute("denuncias",denuncias);
			 model.addAttribute("page",paginaRender);
			 model.addAttribute("nombre",general.findByTipoDenuncia(idGeneral));
			 model.addAttribute("minus", tipoDenuncia.findAllTipoDenunciaNotId(idGeneral));
		  
		     return "listaDenuncias";
		            		
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtroFecha/")
	public String filtroXfechaDenuncia(@RequestParam(value = "page" , defaultValue = "0")int page , Model model) {
		
		 Pageable pageable = PageRequest.of(page,4);
		 Page<Denuncia> denuncias = denunciaDao.findByFechaDenunciaFiltro(fechaGeneral, pageable);
		 PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtroFecha/", denuncias);
		 
		 num = paginaRender.getPaginaActual();
		
		 model.addAttribute("titulo","Lista de denuncias");
		 model.addAttribute("denuncias",denuncias);
		 model.addAttribute("page",paginaRender);
		 model.addAttribute("fechaPuesta",fechaGeneral);
		
		return "listaDenuncias";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtroEstado/")
	public String filtroXestadoDenuncia(@RequestParam(value = "page" , defaultValue = "0")int page , Model model) {
		String nombre;
		
		Pageable pageable = PageRequest.of(page,4);
		Page<Denuncia> denuncias; 
		
		if(valorGeneral.equalsIgnoreCase("1")) {
			
			nombre = "No leidas";
			denuncias = denunciaDao.findByEstadoDenunciaFiltro1("1", pageable);
			
		}else if (valorGeneral.equalsIgnoreCase("2")) {
			
			nombre = "Leidas";
			denuncias = denunciaDao.findByEstadoDenunciaFiltro2("1", pageable);
			
		}else if(valorGeneral.equalsIgnoreCase("3")) {
			
			nombre = "Procesadas";
			denuncias = denunciaDao.findByEstadoDenunciaFiltro3("1", pageable);
			
		}else {
			
			nombre = "Culminadas";
			denuncias = denunciaDao.findByEstadoDenunciaFiltro4("1", pageable);
			
		}
		
		PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtroEstado/" , denuncias);
		num = paginaRender.getPaginaActual();
		
		model.addAttribute("titulo", "Lista de denuncias");
		model.addAttribute("denuncias",denuncias);
		model.addAttribute("page",paginaRender);
		model.addAttribute("valor",valorGeneral);
		model.addAttribute("valor2","6");
		model.addAttribute("opcion",nombre);
		model.addAttribute("valorId",valorGeneral);
		
		return "listaDenuncias";
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtroCiudad/")
	public String filtroXciudadDenuncia(@RequestParam(value = "page" , defaultValue = "0")int page ,Model model) {
		
		Pageable pageable = PageRequest.of(page, 4);
	    Page<Denuncia> denuncias = denunciaDao.findByCiudadDenunciaFiltro(idCiudadGeneral, pageable);
	    PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtroCiudad/", denuncias);
	    
	    num = paginaRender.getPaginaActual();
	    
	    model.addAttribute("titulo","Lista de denuncias");
	    model.addAttribute("denuncias", denuncias);
	    model.addAttribute("page",paginaRender);
	    model.addAttribute("nom",general.findByIdCiudad(idCiudadGeneral));
	    model.addAttribute("ciud", ciudadDao.findByDiferentIdCiudad(idCiudadGeneral));
	    
		return "listaDenuncias";
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtro-Tipo-Fecha/")
	public String filtroXtipoAndFechaDenuncia(@RequestParam(value = "page" , defaultValue = "0")int page , Model model) {
		
		Pageable pageable = PageRequest.of(page, 4);
		Page<Denuncia> denuncias = denunciaDao.findByidDenunciaAndFecha(idGeneral, fechaGeneral, pageable);
		PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtro-Tipo-Fecha/", denuncias);
		
		num = paginaRender.getPaginaActual();
		
		model.addAttribute("titulo","Lista de denuncias");
		model.addAttribute("denuncias", denuncias);
		model.addAttribute("page",paginaRender);
		model.addAttribute("nombre",general.findByTipoDenuncia(idGeneral));
		model.addAttribute("minus", tipoDenuncia.findAllTipoDenunciaNotId(idGeneral));
		model.addAttribute("fechaPuesta",fechaGeneral);
		
		return "listaDenuncias";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtro-Tipo-Estado/")
	public String filtroXtipoAndEstadoDenuncia(@RequestParam(value = "page" , defaultValue = "0")int page , Model model) {
		String nombre;
		
		Pageable pageable = PageRequest.of(page,4);
		Page<Denuncia> denuncias;
		
        if(valorGeneral.equalsIgnoreCase("1")) {
			
			nombre = "No leidas";
			denuncias = denunciaDao.findByIdDenunciaAndEstado1(idGeneral, "1", pageable);
			
		}else if (valorGeneral.equalsIgnoreCase("2")) {
			
			nombre = "Leidas";
			denuncias = denunciaDao.findByIdDenunciaAndEstado2(idGeneral,"1", pageable);
			
		}else if(valorGeneral.equalsIgnoreCase("3")) {
			
			nombre = "Procesadas";
			denuncias = denunciaDao.findByIdDenunciaAndEstado3(idGeneral,"1", pageable);
			
		}else {
			
			nombre = "Culminadas";
			denuncias = denunciaDao.findByIdDenunciaAndEstado4(idGeneral,"1", pageable);
			
		}
        
        PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtro-Tipo-Estado/",denuncias);
		
        num = paginaRender.getPaginaActual();
        
        model.addAttribute("titulo" , "Lista de denuncias");
        model.addAttribute("denuncias",denuncias);
        model.addAttribute("page",paginaRender);
        model.addAttribute("nombre",general.findByTipoDenuncia(idGeneral));
		model.addAttribute("minus", tipoDenuncia.findAllTipoDenunciaNotId(idGeneral));
		model.addAttribute("valor",valorGeneral);
		model.addAttribute("valor2","6");
		model.addAttribute("opcion",nombre);
		model.addAttribute("valorId",valorGeneral);
		
		return "listaDenuncias";
	}
	
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtro-Tipo-Ciudad/")
	public String filtroXtipoDenunciaAndCiudad(@RequestParam(value = "page" , defaultValue = "0") int page , Model model) {
		
		Pageable pageable = PageRequest.of(page,4);
		Page<Denuncia> denuncias = denunciaDao.findByIdDenunciaAndCiudad(idGeneral, idCiudadGeneral , pageable);
		
		PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtro-Tipo-Ciudad/",denuncias);
			
	    num = paginaRender.getPaginaActual();
		
	    model.addAttribute("titulo", "Lista de denuncias");
	    model.addAttribute("denuncias", denuncias);
	    model.addAttribute("page",paginaRender);
	    model.addAttribute("nombre",general.findByTipoDenuncia(idGeneral));
		model.addAttribute("minus", tipoDenuncia.findAllTipoDenunciaNotId(idGeneral));
		model.addAttribute("nom",general.findByIdCiudad(idCiudadGeneral));
		model.addAttribute("ciud", ciudadDao.findByDiferentIdCiudad(idCiudadGeneral));
	    
	    
		return "listaDenuncias";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtro-Fecha-Estado/")
	public String filtroXfechaDenunciaAndEstado(@RequestParam(value = "page" , defaultValue = "0")int page , Model model) {
		String nombre;
		
		Pageable pageable = PageRequest.of(page,4);
		Page<Denuncia> denuncias;
		
		 if(valorGeneral.equalsIgnoreCase("1")) {
				
				nombre = "No leidas";
				denuncias = denunciaDao.findByFechaAndEstado1(fechaGeneral, "1", pageable);
				
			}else if (valorGeneral.equalsIgnoreCase("2")) {
				
				nombre = "Leidas";
				denuncias = denunciaDao.findByFechaAndEstado2(fechaGeneral,"1", pageable);
				
			}else if(valorGeneral.equalsIgnoreCase("3")) {
				
				nombre = "Procesadas";
				denuncias = denunciaDao.findByFechaAndEstado3(fechaGeneral,"1", pageable);
				
			}else {
				
				nombre = "Culminadas";
				denuncias = denunciaDao.findByFechaAndEstado4(fechaGeneral,"1", pageable);
				
			}
		 
		 
		    PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtro-Fecha-Estado/",denuncias);
			
		    num = paginaRender.getPaginaActual();
		    
		    model.addAttribute("titulo", "Lista de denuncias");
		    model.addAttribute("denuncias", denuncias);
		    model.addAttribute("page",paginaRender);
		    model.addAttribute("valor",valorGeneral);
			model.addAttribute("valor2","6");
			model.addAttribute("opcion",nombre);
			model.addAttribute("valorId",valorGeneral);
			model.addAttribute("fechaPuesta",fechaGeneral);
		
		return "listaDenuncias";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtro-Fecha-Ciudad/")
	public String filtroXfechaDenunciaAndCiudad(@RequestParam(value = "page" , defaultValue = "0")int page , Model model) {
		
		
		Pageable pageable = PageRequest.of(page,4);
		Page<Denuncia> denuncias = denunciaDao.findByFechaAndCiudad(fechaGeneral, idCiudadGeneral, pageable);
		
		PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtro-Fecha-Ciudad/",denuncias);
			
	    num = paginaRender.getPaginaActual();
	    
	    model.addAttribute("titulo","Lista de denuncias");
	    model.addAttribute("denuncias",denuncias);
	    model.addAttribute("page",paginaRender);
	    model.addAttribute("fechaPuesta",fechaGeneral);
	    model.addAttribute("nom",general.findByIdCiudad(idCiudadGeneral));
		model.addAttribute("ciud", ciudadDao.findByDiferentIdCiudad(idCiudadGeneral));
		
		return "listaDenuncias";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtro-Estado-Ciudad/")
	public String filtroXestadoDenunciaAndCiudad(@RequestParam(value = "page" , defaultValue = "0")int page , Model model ) {
          String nombre;
		
		Pageable pageable = PageRequest.of(page,4);
		Page<Denuncia> denuncias;
		
		 if(valorGeneral.equalsIgnoreCase("1")) {
				
				nombre = "No leidas";
				denuncias = denunciaDao.findByEstadoAndCiudadDenuncia1("1", idCiudadGeneral, pageable);
				
			}else if (valorGeneral.equalsIgnoreCase("2")) {
				
				nombre = "Leidas";
				denuncias  = denunciaDao.findByEstadoAndCiudadDenuncia2("1", idCiudadGeneral, pageable);
				
			}else if(valorGeneral.equalsIgnoreCase("3")) {
				
				nombre = "Procesadas";
				denuncias  = denunciaDao.findByEstadoAndCiudadDenuncia3("1", idCiudadGeneral, pageable);
				
			}else {
				
				nombre = "Culminadas";
				denuncias  = denunciaDao.findByEstadoAndCiudadDenuncia4("1", idCiudadGeneral, pageable);
				
			}
		 
		    PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtro-Estado-Ciudad/",denuncias);
			
		    num = paginaRender.getPaginaActual();
		   
		    model.addAttribute("titulo","Listado de denuncias");
		    model.addAttribute("denuncias", denuncias);
		    model.addAttribute("page",paginaRender);
		    model.addAttribute("valor",valorGeneral);
			model.addAttribute("valor2","6");
			model.addAttribute("opcion",nombre);
			model.addAttribute("valorId",valorGeneral);
			model.addAttribute("nom",general.findByIdCiudad(idCiudadGeneral));
		    model.addAttribute("ciud", ciudadDao.findByDiferentIdCiudad(idCiudadGeneral));
		
		 return "listaDenuncias";
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtro-Tipo-Fecha-Estado/")
	public String filtroXtipoAndFechaAndEstado(@RequestParam(value = "page" , defaultValue = "0")int page , Model model) {
		    String nombre;
			
			Pageable pageable = PageRequest.of(page,4);
			Page<Denuncia> denuncias;
			
			 if(valorGeneral.equalsIgnoreCase("1")) {
					
					nombre = "No leidas";
					denuncias = denunciaDao.findByTipoAndFechaAndEstado1(idGeneral, fechaGeneral, "1" ,pageable);
					
				}else if (valorGeneral.equalsIgnoreCase("2")) {
					
					nombre = "Leidas";
					denuncias = denunciaDao.findByTipoAndFechaAndEstado2(idGeneral, fechaGeneral, "1" ,pageable); 
					
				}else if(valorGeneral.equalsIgnoreCase("3")) {
					
					nombre = "Procesadas";
					denuncias = denunciaDao.findByTipoAndFechaAndEstado3(idGeneral, fechaGeneral, "1" ,pageable); 
					
				}else {
					
					nombre = "Culminadas";
					denuncias = denunciaDao.findByTipoAndFechaAndEstado4(idGeneral, fechaGeneral, "1" ,pageable); 
					
				}
		
			 
			    PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtro-Tipo-Fecha-Estado/",denuncias);
				
			    num = paginaRender.getPaginaActual();
			    
			    model.addAttribute("titulo", "Lista de denuncias");
			    model.addAttribute("denuncias", denuncias);
			    model.addAttribute("page",paginaRender);
			    model.addAttribute("valor",valorGeneral);
				model.addAttribute("valor2","6");
				model.addAttribute("opcion",nombre);
				model.addAttribute("valorId",valorGeneral);
				model.addAttribute("fechaPuesta",fechaGeneral);
				model.addAttribute("nombre",general.findByTipoDenuncia(idGeneral));
			    model.addAttribute("minus", tipoDenuncia.findAllTipoDenunciaNotId(idGeneral));
		
		
		return "listaDenuncias";
		
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtro-Fecha-Estado-Ciudad/")
	public String filtroXfechaEstadoCiudad(@RequestParam(value = "page" , defaultValue = "0")int page , Model model) {
		  String nombre;
			
			Pageable pageable = PageRequest.of(page,4);
			Page<Denuncia> denuncias;
			
			 if(valorGeneral.equalsIgnoreCase("1")) {
					
					nombre = "No leidas";
					denuncias = denunciaDao.findByCiudadAndFechaAndEstado1(idCiudadGeneral, fechaGeneral, "1", pageable);
					
				}else if (valorGeneral.equalsIgnoreCase("2")) {
					
					nombre = "Leidas";
					denuncias = denunciaDao.findByCiudadAndFechaAndEstado2(idCiudadGeneral, fechaGeneral, "1", pageable);
					
				}else if(valorGeneral.equalsIgnoreCase("3")) {
					
					nombre = "Procesadas";
					denuncias = denunciaDao.findByCiudadAndFechaAndEstado3(idCiudadGeneral, fechaGeneral, "1", pageable);
					
				}else {
					
					nombre = "Culminadas";
					denuncias = denunciaDao.findByCiudadAndFechaAndEstado4(idCiudadGeneral, fechaGeneral, "1", pageable);
					
				}
		
			 
			    PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtro-Fecha-Estado-Ciudad/",denuncias);
				
			    num = paginaRender.getPaginaActual();
			    
			    model.addAttribute("titulo", "Lista de denuncias");
			    model.addAttribute("denuncias",denuncias);
			    model.addAttribute("page",paginaRender);
			    model.addAttribute("valor",valorGeneral);
				model.addAttribute("valor2","6");
				model.addAttribute("opcion",nombre);
				model.addAttribute("valorId",valorGeneral);
				model.addAttribute("fechaPuesta",fechaGeneral);
				model.addAttribute("nom",general.findByIdCiudad(idCiudadGeneral));
			    model.addAttribute("ciud", ciudadDao.findByDiferentIdCiudad(idCiudadGeneral));
		
		return "listaDenuncias";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtro-Tipo-Estado-Ciudad/")
	public String filtroXtipoEstadoCiudad(@RequestParam(value = "page" , defaultValue = "0")int page , Model model) {
		 String nombre;
			
			Pageable pageable = PageRequest.of(page,4);
			Page<Denuncia> denuncias;
			
			 if(valorGeneral.equalsIgnoreCase("1")) {
					
					nombre = "No leidas";
					denuncias = denunciaDao.findByCiudadAndTipoAndEstado1(idCiudadGeneral, idGeneral, "1", pageable);
					
				}else if (valorGeneral.equalsIgnoreCase("2")) {
					
					nombre = "Leidas";
					denuncias = denunciaDao.findByCiudadAndTipoAndEstado2(idCiudadGeneral, idGeneral, "1", pageable);
					
				}else if(valorGeneral.equalsIgnoreCase("3")) {
					
					nombre = "Procesadas";
					denuncias = denunciaDao.findByCiudadAndTipoAndEstado3(idCiudadGeneral, idGeneral, "1", pageable);
					
				}else {
					
					nombre = "Culminadas";
					denuncias = denunciaDao.findByCiudadAndTipoAndEstado4(idCiudadGeneral, idGeneral, "1", pageable);
					
				}
		
			 
			    PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtro-Tipo-Estado-Ciudad/",denuncias);
				
			    num = paginaRender.getPaginaActual();
		
			    model.addAttribute("titulo", "Lista de denuncias");
			    model.addAttribute("denuncias",denuncias);
			    model.addAttribute("page",paginaRender);
			    model.addAttribute("valor",valorGeneral);
				model.addAttribute("valor2","6");
				model.addAttribute("opcion",nombre);
				model.addAttribute("valorId",valorGeneral);
				model.addAttribute("nom",general.findByIdCiudad(idCiudadGeneral));
			    model.addAttribute("ciud", ciudadDao.findByDiferentIdCiudad(idCiudadGeneral));
			    model.addAttribute("nombre",general.findByTipoDenuncia(idGeneral));
			    model.addAttribute("minus", tipoDenuncia.findAllTipoDenunciaNotId(idGeneral));
		
		return "listaDenuncias";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtro-Tipo-Fecha-Ciudad/")
	public String filtroXtipoFechaCiudad(@RequestParam(value = "page" , defaultValue = "0")int page , Model model) {
		
		Pageable pageable = PageRequest.of(page,4);
		Page<Denuncia> denuncias = denunciaDao.findByTipoAndFechaAndCiudad(idGeneral, idCiudadGeneral, fechaGeneral , pageable);
		
		PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtro-Tipo-Fecha-Ciudad/",denuncias);
			
	    num = paginaRender.getPaginaActual();
	    
	    model.addAttribute("titulo","Lista de denuncias");
	    model.addAttribute("denuncias",denuncias);
	    model.addAttribute("page",paginaRender);
	    model.addAttribute("nom",general.findByIdCiudad(idCiudadGeneral));
	    model.addAttribute("ciud", ciudadDao.findByDiferentIdCiudad(idCiudadGeneral));
	    model.addAttribute("nombre",general.findByTipoDenuncia(idGeneral));
	    model.addAttribute("minus", tipoDenuncia.findAllTipoDenunciaNotId(idGeneral));
	    model.addAttribute("fechaPuesta",fechaGeneral);
		
		
		
		return "listaDenuncias";
	}
	
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/filtro-Tipo-Fecha-Estado-Ciudad/")
	public String allFilters(@RequestParam(value = "page" , defaultValue = "0")int page , Model model) {
		 String nombre;
			
			Pageable pageable = PageRequest.of(page,4);
			Page<Denuncia> denuncias;
			
			 if(valorGeneral.equalsIgnoreCase("1")) {
					
					nombre = "No leidas";
					denuncias = denunciaDao.findByAllFilters1(idGeneral, idCiudadGeneral, "1", fechaGeneral, pageable);
					
				}else if (valorGeneral.equalsIgnoreCase("2")) {
					
					nombre = "Leidas";
					denuncias = denunciaDao.findByAllFilters2(idGeneral, idCiudadGeneral, "1", fechaGeneral, pageable);
					
				}else if(valorGeneral.equalsIgnoreCase("3")) {
					
					nombre = "Procesadas";
					denuncias = denunciaDao.findByAllFilters3(idGeneral, idCiudadGeneral, "1", fechaGeneral, pageable);
					
				}else {
					
					nombre = "Culminadas";
					denuncias = denunciaDao.findByAllFilters4(idGeneral, idCiudadGeneral, "1", fechaGeneral, pageable);
					
				}
		
			 
			    PaginaRender<Denuncia> paginaRender = new PaginaRender<>("/denuncia/filtro-Tipo-Fecha-Estado-Ciudad/",denuncias);
				
			    num = paginaRender.getPaginaActual();
			    
			    model.addAttribute("titulo","Lista de denuncias");
			    model.addAttribute("denuncias",denuncias);
			    model.addAttribute("page",paginaRender);
			    model.addAttribute("nom",general.findByIdCiudad(idCiudadGeneral));
			    model.addAttribute("ciud", ciudadDao.findByDiferentIdCiudad(idCiudadGeneral));
			    model.addAttribute("nombre",general.findByTipoDenuncia(idGeneral));
			    model.addAttribute("minus", tipoDenuncia.findAllTipoDenunciaNotId(idGeneral));
			    model.addAttribute("fechaPuesta",fechaGeneral);
			    model.addAttribute("valor",valorGeneral);
				model.addAttribute("valor2","6");
				model.addAttribute("opcion",nombre);
				model.addAttribute("valorId",valorGeneral);
			    
		
		return "listaDenuncias";
	}
	
}
