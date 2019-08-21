package com.project.chess.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.project.chess.model.dto.UsersDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

   @Override
   public void initialize(final PasswordMatches constraintAnnotation) {
   }

   @Override
   public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
      final UsersDto user = (UsersDto) obj;
      return user.getPassword().equals(user.getMatchingPassword());
   }

}
