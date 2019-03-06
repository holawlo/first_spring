package trials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    MyElementRepository myElementRepository;

    List<String> inputList = new ArrayList<>();
    List<MyElement> allElementsList = new ArrayList<>();;

    @GetMapping("/trial")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "trial";
    }


    @GetMapping(value = "/list")
    public String listPage(Model model) { //TODO print list after changing between cards
        allElementsList = myElementRepository.findAll();
        model.addAttribute("allElementsList", allElementsList);

        return "list";
    }

    @PostMapping(value = "/list")
    public String listPagePost(@RequestParam String listElement, Model model) { //TODO not adding again the same element after refresh
//        inputList.add(listElement);

        myElementRepository.save(new MyElement(listElement));
//        model.addAttribute("inputElement", listElement);
//        model.addAttribute("inputList", inputList);
        return "redirect:list";
    }

}
