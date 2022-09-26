package web.proyecto.oracle.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.proyecto.oracle.models.service.IServiceGeneral;

 @Component
public class TipoEditor extends PropertyEditorSupport{

	 
	@Autowired
	private IServiceGeneral general;

	@Override
	public void setAsText(String ids) throws IllegalArgumentException {
	     
		 Long id = (long) Integer.parseInt(ids);
		 
		 setValue(general.findByIdTipoPublicacion(id));
		
	
	}
	 
	 
	 
	 
}
