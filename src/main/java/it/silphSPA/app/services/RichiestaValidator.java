package it.silphSPA.app.services;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import it.silphSPA.app.model.*;


@Component
public class RichiestaValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Richiesta.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomeDestinatario", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cognomeDestinatario", "required");
	}

}