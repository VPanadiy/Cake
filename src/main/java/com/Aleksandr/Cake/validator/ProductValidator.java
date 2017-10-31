package com.Aleksandr.Cake.validator;

import java.math.BigDecimal;

import com.Aleksandr.Cake.model.Product;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required.name");

		if (product.getPrice()==null || product.getPrice().compareTo(new BigDecimal(0))<0) {
			errors.rejectValue("price", "required.price");
		}
		//TODO more validation

	}

}
