package com.example.web_flux_demo.validator;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

public class ValidatorUtil {
	 public static <T> void validate(T obj, Validator validator) {
	        Set<ConstraintViolation<T>> violations =
	                validator.validate(obj);

	        if (!violations.isEmpty()) {
	            throw new RuntimeException(
	                violations.iterator().next().getMessage());
	        }
	    }
}
