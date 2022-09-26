package web.proyecto.oracle.validadores;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import web.proyecto.oracle.models.entity.Evento;



@Component
public class ValidadorGenerak implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		 return Evento.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

	
	
}
