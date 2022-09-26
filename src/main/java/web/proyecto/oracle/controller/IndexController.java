package web.proyecto.oracle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {

	
	@RequestMapping(value = {"/","/index"} , method = RequestMethod.GET)
	public String index() {
		
		
		return "index";
		
	}
	
	@RequestMapping(value = "/conocenos" ,method = RequestMethod.GET )
	public String caratula() {
		
		return "contactanos";
		
	}
	
	@RequestMapping(value = "/form" , method = RequestMethod.GET)
	public String form () {
		
		return "formEvento";
	}
	

	
	@RequestMapping(value = "/regi" , method = RequestMethod.GET)
	public String registrar () {
		
		return "formRegistro";
	}
	
	@RequestMapping(value = "/prueba" , method = RequestMethod.GET)
	public String registrarEvento () {
		
		return "denuncia";
	}
}
