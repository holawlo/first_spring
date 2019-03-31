package trials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

//    @Autowired
//    MyElementRepository myElementRepository;

    List<String> inputList = new ArrayList<>();
//    List<MyElement> allElementsList = new ArrayList<>();
    List<MyElementH2> allElementsListH2 = new ArrayList<>();
    String path = "/home/ola/Desktop/filespring.txt";

    @GetMapping("/trial")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "trial";
    }


//    @GetMapping(value = "/list")
//    public String listPage(Model model) {
//        allElementsList = myElementRepository.findAll();
//        model.addAttribute("allElementsList", allElementsList);
//        model.addAttribute("foods", FoodChoice.values());
//        return "list";
//    }

//    @PostMapping(value = "/list")
//    public String listPagePost(@RequestParam String listElement, FoodChoice foods, Model model) { //foods to name selecta w html
//        myElementRepository.save(new MyElement(listElement, foods));
////        inputList.add(listElement);
////        model.addAttribute("inputElement", listElement);
////        model.addAttribute("inputList", inputList);
//        return "redirect:list";
//    }

        @GetMapping(value = "/h2")
    public String listPage(Model model) {
        model.addAttribute("foodsH2", FoodChoice.values());
//        allElementsList = // odczyt z h2
        model.addAttribute("allElementsListH2", allElementsListH2);
        return "h2";
    }

    @PostMapping(value = "/h2")
    public String listPagePost(@RequestParam String listElementH2, FoodChoice foodsH2, Model model) { //foods to name selecta w html
//        myElementRepository.save(new MyElement(listElementH2, foodsH2)); // zapis ale do h2
//        inputList.add(listElement);
//        model.addAttribute("inputElement", listElement);
//        model.addAttribute("inputList", inputList);
        return "redirect:h2";
    }

    @GetMapping(value = "/file")
    public String filePage(@RequestParam(required = false) String fileElement, Model model) {

        if (fileElement != null && !fileElement.isEmpty()) {
            String[] split = fileElement.split(" ");
            saveToFile(split[0] + split[1]);
            List<String> fileElementList = readFile();
            model.addAttribute("fileElementList", fileElementList);
        }
        return "file";
    }

    private List<String> readFile() {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void saveToFile(String fileElement) {
        try {
            Files.write(Paths.get(path), fileElement.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
