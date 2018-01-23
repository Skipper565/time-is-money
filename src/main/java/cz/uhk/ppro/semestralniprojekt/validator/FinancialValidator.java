package cz.uhk.ppro.semestralniprojekt.validator;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class FinancialValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return FinancialEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        FinancialEntity entity = (FinancialEntity) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "NotEmpty");
        if (entity.getValue() < 0) {
            errors.rejectValue("value", "Size.financeForm.value");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "NotEmpty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "NotEmpty");
    }

}
