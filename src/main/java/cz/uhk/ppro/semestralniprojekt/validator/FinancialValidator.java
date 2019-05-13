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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date", "NotEmpty");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "NotEmpty");
        if (!errors.hasErrors()) {
            if (entity.getValue() < 0) {
                errors.rejectValue("value", "Size.financeForm.value");
            }

            if (entity.getMonthDay() != null && (entity.getMonthDay() < 1 || entity.getMonthDay() > 28)) {
                errors.rejectValue("monthDay", "Size.financeForm.monthDay");
            }
        }
    }

}
