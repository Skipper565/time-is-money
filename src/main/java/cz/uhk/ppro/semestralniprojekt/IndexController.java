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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

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
    public String index(Model model, Principal principal) {
        User user = users.findByUsername(principal.getName());

        List<FinancialEntity> finance = financialService.getAllFinancialEntitiesForUser(user.getId());

        model.addAttribute("finance", finance);
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

}
