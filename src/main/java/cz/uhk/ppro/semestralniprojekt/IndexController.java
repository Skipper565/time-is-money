package cz.uhk.ppro.semestralniprojekt;

import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import cz.uhk.ppro.semestralniprojekt.service.FinancialService;
import cz.uhk.ppro.semestralniprojekt.user.User;
import cz.uhk.ppro.semestralniprojekt.user.UserRepository;
import cz.uhk.ppro.semestralniprojekt.validator.FinancialValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.Locale;

@Controller
public class IndexController {

    private final UserRepository users;

    @Autowired
    private FinancialService financialService;

    @Autowired
    private FinancialValidator financialValidator;

    @Autowired
    public IndexController(UserRepository users) {
        this.users = users;
    }

    @RequestMapping(value={"*", "/", "/index", "index.php", "index.html"})
    public String index(@RequestParam(value = "month", required = false) String month, Model model, Principal principal) {
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
        setDatesToModel(model, startDate, endDate);

        List<FinancialEntity> finance = financialService.getIntervalFinancialEntitiesForUser(user.getId(), startDate, endDate);
        model.addAttribute("finance", finance);
        setMonthBalancesToModel(model, user, finance, startDate);
        return "index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("record", new FinancialEntity());

        return "addEdit";
    }

    @RequestMapping(value = "/edit/{entityType}/{entityId}", method = RequestMethod.GET)
    public String edit(
            @PathVariable String entityType,
            @PathVariable Integer entityId,
            Model model,
            Principal principal
    ) {
        User user = users.findByUsername(principal.getName());
        FinancialEntity entity = financialService.resolveCostOrRevenue(user, entityType, entityId);

        if (entity == null) {
            return "redirect:/";
        }

        model.addAttribute("record", entity);
        return "addEdit";
    }

    @RequestMapping(value = "/addEdit", method = RequestMethod.POST)
    public String addEdit(
            @ModelAttribute("record") FinancialEntity entity,
            BindingResult bindingResult,
            Principal principal
    ) {
        financialValidator.validate(entity, bindingResult);
        if (bindingResult.hasErrors()) {
            return "addEdit";
        }

        User user = users.findByUsername(principal.getName());
        if (entity.getId() == null
                || financialService.resolveCostOrRevenue(user, entity.getType(), entity.getId()) != null) {
            entity.setUser(user);
            financialService.save(entity);
        }

        return "redirect:/";
    }

    @RequestMapping(value = "/delete/{entityType}/{entityId}", method = RequestMethod.GET)
    public String delete(
            @PathVariable String entityType,
            @PathVariable Integer entityId,
            Principal principal
    ) {
        User user = users.findByUsername(principal.getName());
        financialService.deleteCostOrRevenue(user, entityType, entityId);

        return "redirect:/";
    }

    private void setDatesToModel(Model model, LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter linkDateFormatter = DateTimeFormatter.ofPattern("MM-yyyy");
        DateTimeFormatter humanDateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate prevMonth = startDate.minusMonths(1);
        LocalDate nextMonth = startDate.plusMonths(1);
        String monthName = startDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        Integer yearName = startDate.getYear();
        model.addAttribute("prevMonth", prevMonth.format(linkDateFormatter));
        model.addAttribute("nextMonth", nextMonth.format(linkDateFormatter));
        model.addAttribute("startDate", startDate.format(humanDateFormatter));
        model.addAttribute("endDate", endDate.format(humanDateFormatter));
        model.addAttribute("monthName", monthName.toLowerCase());
        model.addAttribute("yearName", yearName);
    }

    private void setMonthBalancesToModel(Model model, User user, List<FinancialEntity> finance, LocalDate startDate) {
        Float previousSumBalance = financialService.sumBalanceForUserDueDate(user.getId(), startDate);
        Float currentMonthBalance = financialService.countBalance(finance);
        Float startBalance = user.getInitialDeposit() + previousSumBalance;
        Float endBalance = startBalance + currentMonthBalance;
        model.addAttribute("startBalance", startBalance);
        model.addAttribute("endBalance", endBalance);
    }

}
