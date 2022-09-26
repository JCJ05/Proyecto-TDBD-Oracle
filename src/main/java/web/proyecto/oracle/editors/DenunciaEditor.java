package web.proyecto.oracle.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import web.proyecto.oracle.models.service.IServiceGeneral;

@Component
public class DenunciaEditor extends PropertyEditorSupport {

	@Autowired
	private IServiceGeneral general;

	@Override
	public void setAsText(String id) throws IllegalArgumentException {
		
		   Long idTipo = (long) Integer.parseInt(id);
		   
		   setValue(general.findByTipoDenuncia(idTipo));
		   
		
	}
	
	
	
	
}
