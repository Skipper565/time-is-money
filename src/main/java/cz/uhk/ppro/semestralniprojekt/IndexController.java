package cz.uhk.ppro.semestralniprojekt;

import cz.uhk.ppro.semestralniprojekt.cost.CostRepository;
import cz.uhk.ppro.semestralniprojekt.model.FinancialEntity;
import cz.uhk.ppro.semestralniprojekt.revenue.RevenueRepository;
import cz.uhk.ppro.semestralniprojekt.service.FinancialService;
import cz.uhk.ppro.semestralniprojekt.user.User;
import cz.uhk.ppro.semestralniprojekt.user.UserRepository;
import cz.uhk.ppro.semestralniprojekt.validator.FinancialValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {

    private final UserRepository users;
    private final RevenueRepository revenues;
    private final CostRepository costs;

    @Autowired
    private FinancialService financialService;

    @Autowired
    private FinancialValidator financialValidator;

    @Autowired
    public IndexController(UserRepository users, RevenueRepository revenues, CostRepository costs) {
        this.users = users;
        this.revenues = revenues;
        this.costs = costs;
    }

    @RequestMapping(value={"*", "/", "/index", "index.php", "index.html"})
    public String index(
            Model model,
            Principal principal
    ) {
        User user = users.findByUsername(principal.getName());
        List<FinancialEntity> finance = new ArrayList<>(revenues.findByUserId(user.getId()));
        finance.addAll(costs.findByUserId(user.getId()));
        model.addAttribute("finance", finance);
        return "index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("record", new FinancialEntity());

        return "add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("record") FinancialEntity entity, BindingResult bindingResult, Model model) {
        financialValidator.validate(entity, bindingResult);

        if (bindingResult.hasErrors()) {
            return "add";
        }

        financialService.save(entity);

        return "redirect:/";
    }

}
