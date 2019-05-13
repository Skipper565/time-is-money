package cz.uhk.ppro.semestralniprojekt.validator;

import cz.uhk.ppro.semestralniprojekt.service.UserService;
import cz.uhk.ppro.semestralniprojekt.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matchingPassword", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "initialDeposit", "NotEmpty");
        if (!errors.hasErrors()) {
            if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
                errors.rejectValue("username", "Size.userForm.username");
            }

            if (userService.findByUsername(user.getUsername()) != null) {
                errors.rejectValue("username", "Duplicate.userForm.username");
            }

            if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
                errors.rejectValue("password", "Size.userForm.password");
            }

            if (!user.getMatchingPassword().equals(user.getPassword())) {
                errors.rejectValue("matchingPassword", "Diff.userForm.matchingPassword");
            }

            if (user.getInitialDeposit() < 0) {
                errors.rejectValue("initialDeposit", "Size.userForm.initialDeposit");
            }
        }
    }

}
