package web.proyecto.oracle.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.proyecto.oracle.models.service.IServiceGeneral;

@Component
public class PaisEditor extends PropertyEditorSupport {

	@Autowired
	private IServiceGeneral general;

	@Override
	public void setAsText(String id) throws IllegalArgumentException {
	
		Long idPais = (long) Integer.parseInt(id);
		
		 setValue(general.findByIdCiudad(idPais));
		
	}
	
	
	
	
}
