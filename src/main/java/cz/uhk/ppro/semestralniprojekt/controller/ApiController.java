package cz.uhk.ppro.semestralniprojekt.controller;

import cz.uhk.ppro.semestralniprojekt.model.api.MonthFinanceOverview;
import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import cz.uhk.ppro.semestralniprojekt.service.FinancialService;
import cz.uhk.ppro.semestralniprojekt.model.user.User;
import cz.uhk.ppro.semestralniprojekt.model.user.UserRepository;
import cz.uhk.ppro.semestralniprojekt.validator.FinancialValidator;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.beans.PropertyEditor;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final UserRepository users;

    @Autowired
    private FinancialService financialService;

    @Autowired
    private FinancialValidator financialValidator;

    @Autowired
    public ApiController(UserRepository users) {
        this.users = users;
    }

    @RequestMapping("/monthFinanceOverview")
    public MonthFinanceOverview monthFinanceOverview(
            @RequestParam(value = "month", required = false) String month,
            Principal principal
    ) {
        DateTimeFormatter monthDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        User user = users.findByUsername(principal.getName());
        LocalDate date = LocalDate.now();

        if (month != null) {
            try {
                date = LocalDate.parse("01-" + month, monthDateFormatter);
            } catch (java.time.format.DateTimeParseException e) {
                date = LocalDate.now();
            }
        }

        ValueRange range = date.range(ChronoField.DAY_OF_MONTH);
        Long min = range.getMinimum();
        Long max = range.getMaximum();
        LocalDate startDate = date.withDayOfMonth(min.intValue());
        LocalDate endDate = date.withDayOfMonth(max.intValue());

        List<FinancialEntity> finance =
                financialService.getIntervalFinancialEntitiesForUser(user.getId(), startDate, endDate);
        Float previousSumBalance = financialService.sumBalanceForUserDueDate(user.getId(), startDate);
        Float currentMonthBalance = financialService.countBalance(finance);
        Float startBalance = user.getInitialDeposit() + previousSumBalance;
        Float endBalance = startBalance + currentMonthBalance;

        return new MonthFinanceOverview(startBalance, endBalance, finance);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Void> add(@RequestBody FinancialEntity financialEntity, UriComponentsBuilder ucBuilder,
                                    BindingResult bindingResult, Principal principal) throws BindException {
        User user = users.findByUsername(principal.getName());
        financialEntity.setUser(user);
        financialValidator.validate(financialEntity, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        financialService.save(financialEntity);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/monthFinanceOverview").build().toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

}
