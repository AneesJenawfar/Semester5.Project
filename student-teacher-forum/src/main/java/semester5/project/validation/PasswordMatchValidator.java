package semester5.project.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import semester5.project.model.entity.AppUser;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, AppUser> {

	@Override
	public void initialize(PasswordMatch constraintAnnotation) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isValid(AppUser user, ConstraintValidatorContext context) {
		String plainPassword = user.getPlainPassword();
		String repeatPassword = user.getRepeatPassword();
		if (plainPassword == null || plainPassword.length() == 0) {
			return true;
		}
		if (plainPassword == null || !plainPassword.equals(repeatPassword)) {
			return false;
		}
		return true;
	}

}
