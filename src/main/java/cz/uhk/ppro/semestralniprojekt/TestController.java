package cz.uhk.ppro.semestralniprojekt;

import cz.uhk.ppro.semestralniprojekt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    private final UserRepository users;

    @Autowired
    public TestController(UserRepository users) {
        this.users = users;
    }

    @RequestMapping("/test")
    public String index(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("users", this.users.findAll());
        return "test";
    }

}
