package trials;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    MyElementRepository myElementRepository;

    List<String> inputList = new ArrayList<>();
    List<MyElement> allElementsList = new ArrayList<>();
    String path = "/home/ola/Desktop/filespring.txt";


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
    public String listPagePost(@RequestParam String listElement, Model model) {
//        inputList.add(listElement);
        myElementRepository.save(new MyElement(listElement));
//        model.addAttribute("inputElement", listElement);
//        model.addAttribute("inputList", inputList);
        return "redirect:list";
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
            return Files.readAllLines(Paths.get(this.getClass().getClassLoader().getResource("filespring.txt").toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void saveToFile(String fileElement) {
        try {
            Files.write(Paths.get(this.getClass().getClassLoader().getResource("filespring.txt").toURI()), Arrays.asList(fileElement), StandardOpenOption.APPEND);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

//    @PostMapping(value = "/list")
//    public String filePagePost(@RequestParam String fileElement, Model model) {
//
//
//        // model.addAttribute("myFile", myFile);
//
//        return "file";
//    }


}
