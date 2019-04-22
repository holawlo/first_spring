package trials.intermediate.categories;
import com.google.common.io.Resources;
import lombok.Getter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

//Singleton in old good way
public class InMemoryCategoryDAO {

    @Getter
    private List<Category> categoryList = new ArrayList<>();
    private static InMemoryCategoryDAO instance;

    public static InMemoryCategoryDAO getInstance() {
        if (instance == null) { //1
            synchronized (InMemoryCategoryDAO.class) { //2
                if (instance == null) { //3
                    instance = new InMemoryCategoryDAO();
                }
            }
        }
        return instance;
    }

    public InMemoryCategoryDAO() {
        initializeCategories();
    }

    private List<String> loadCategoriesFromFile() {
        try {
            return Resources.readLines(Resources.getResource("kategorie.txt"), Charset.forName("UNICODE"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void initializeCategories() {
        List<String> categoriesFromFile = loadCategoriesFromFile(); //tu odczytujemy plik z dysku
        AtomicInteger counter = new AtomicInteger(1);
        List<Category> categories = categoriesFromFile.stream() //tu zamieniamy napisy z pliku na kategorie
                .map(s -> {
                    Category category = new Category();
                    category.setCategoryName(s);
                    category.setId(counter.getAndIncrement()); //ustawiamy kolejne id
                    return category;
                })
                .collect(Collectors.toList());

        Map<Integer, List<Category>> categoriesMap =
                categories.stream()
                        .collect(Collectors.groupingBy(
                                c -> calculateDepth(c.getCategoryName()))); //grupujemy kategorie po głębokości zagnieżdżenia (do mapy)

//        for (Category category : categories) { alternatywna wersja z pętlami
//            Integer depth = calculateDepth(category.getCategoryName());
//            if (categoriesMap.containsKey(depth)) {
//                categoriesMap.get(depth).add(category);
//            } else {
//                List<Category> depthCategoryList = new ArrayList<>();
//                depthCategoryList.add(category);
//                categoriesMap.put(depth,depthCategoryList);
//            }
//        }
        populateParentId(categoriesMap, 0); //rekurencyjnie uzupełniamy parentId kategorii używając do tego mapy i zaczynając od zerowego zagnieżdżenia
        categoryList = categories; //przypisujemy kategorie do listy w klasie (to jest niejako cache tych kategorii)
    }

    private void populateParentId(Map<Integer, List<Category>> categoriesMap, int depth) {
        List<Category> children = categoriesMap.get(depth);
        children.stream()
                .forEach(c -> findAndSetParentId(categoriesMap, depth, c));
        if (categoriesMap.containsKey(depth + 1)) {
            populateParentId(categoriesMap, depth + 1);
        }

    }

    private void findAndSetParentId(Map<Integer, List<Category>> categoriesMap, int depth, Category c) { //Szukamy parentId w mapie (z poziomu wyżej)
        List<Category> potentialParents = categoriesMap.get(depth - 1);

        Integer parentId = potentialParents == null ? null : potentialParents.stream()
                .map(Category::getId)
                .filter(id -> id < c.getId())
                .sorted((a, b) -> b - a)
                .findFirst()
                .orElse(null);
        c.setParentId(parentId);
    }

    private Integer calculateDepth(String categoryName) { //na podstawie spacji liczymy zagnieżdżenie kategorii
        return categoryName.startsWith(" ")
                ? categoryName.split("\\S+")[0].length()
                : 0;
    }
}