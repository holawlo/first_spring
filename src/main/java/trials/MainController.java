package trials;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import trials.intermediate.categories.CategorySearchService;
import trials.intermediate.users.*;
import trials.intermediate.weather.WeatherService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public String listPage(Model model) {
        allElementsList = myElementRepository.findAll();
        model.addAttribute("allElementsList", allElementsList);
        model.addAttribute("foods", FoodChoice.values());
        return "list";
    }

    @PostMapping(value = "/list")
    public String listPagePost(@RequestParam String listElement, FoodChoice foods, Model model) { //foods to name selecta w html
        myElementRepository.save(new MyElement(listElement, foods));
//        inputList.add(listElement);
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

    // INTERMEDIATE

    //DEPENDENCY INJECTION
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserLoginService usl;
    @Autowired
    private CategorySearchService categorySearchService;
    @Autowired
    private UserValidationService userValidationService;
    @Autowired
    private UserRegistrationService userRegistrationService;

    @RequestMapping("/categories") //pod takim urlem dostępna jest strona z kategoriami
    public String categories(String searchText, Model model) {
        model.addAttribute("catsdata", categorySearchService.filterCategories(searchText));
        return "catspage";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterForm(Model model) {
        model.addAttribute("form", new UserRegistrationDTO());
        model.addAttribute("countries", Countries.values());
        return "registerForm";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    //ta metoda (POST) obsluguje wyslanie danych z frontu
    public String retrieveRegisterForm(UserRegistrationDTO userRegistrationDTO, Model model) {
        Map<String, String> errorMap = userValidationService.validate(userRegistrationDTO);
        model.addAttribute("form", userRegistrationDTO);
        model.addAttribute("countries", Countries.values());
        if (errorMap.isEmpty()) {
            try {
                userRegistrationService.register(userRegistrationDTO);
            } catch (UserExistsException e) {
                model.addAttribute("userExistsExceptionMessage", e.getMessage());
                return "registerForm";
            }
        } else {
            model.addAllAttributes(errorMap);
            return "registerForm";
        }
        return "registerEffect";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm(Model model) {
        model.addAttribute("form", new UserLoginDTO());
        return "loginForm";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(UserLoginDTO dto, Model model) {
        if (usl.login(dto)) {
            UserContextHolder.logUserIn(dto);
        }
        return "index";
    }

    @ResponseBody//to mowi, że będzie jsonem a nie nazwą htmla
    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public String showWeather() {
        WeatherService weatherService = new WeatherService(userDAO);
        return new Gson().toJson(weatherService.getWeather());
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        UserContextHolder.logout();
        return "index";
    }

    @ResponseBody //ta adnotacja mowi, że nie będzie przekierowania
    @RequestMapping(value = "/moveCat", method = RequestMethod.POST)
    public void moveCategory(Integer newParentId, Integer movedId) {
        categorySearchService.moveCat(newParentId, movedId);

    }


}
