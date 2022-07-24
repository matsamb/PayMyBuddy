package com.paymybuddy.service;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailPatternValidation implements 
		ConstraintValidator<ValidatedEmailPattern, String> {

	private final static String EMAIL_FORMAT=
"(?:[A-Za-z0-9!#$%&'*+\\/=?.^_`{|}~-]+(?:.[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*|\\\"(?:[x01-x08x0bx0cx0e-x1fx21x23-x5bx5d-x7f]|[x01-x09x0bx0cx0e-x7f])*\\\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|[(?:(?:(2(5[0-5]|[0-4][0-9])"
			;
	
	@Override
	public boolean isValid(String value
			, ConstraintValidatorContext context) {
		
		return Pattern.compile(EMAIL_FORMAT).matcher(value).matches();

	}

}
