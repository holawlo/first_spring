package trials;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    List<String> inputList = new ArrayList<>();

    @GetMapping("/trial")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "trial";
    }

    @GetMapping(value = "/list")
    public String listPage() {
        return "list";
    }

    @PostMapping(value = "/list")
    public String listPagePost(@RequestParam String listElement, Model model) {
        inputList.add(listElement);
        model.addAttribute("inputElement", listElement);
        model.addAttribute("inputList", inputList);
        return "list";
    }

}
