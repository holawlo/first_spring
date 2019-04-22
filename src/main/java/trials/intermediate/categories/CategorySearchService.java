package trials.intermediate.categories;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategorySearchService {

    public List<CategoryDTO> filterCategories(String categoryName) {
        List<Category> categoryList = InMemoryCategoryDAO.getInstance().getCategoryList();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryDTOList.add(buildCategoryDTO(category)); //przepisujemy category na categoryDto
        }

        for (CategoryDTO categoryDTO : categoryDTOList) {
            if (categoryDTO.getParentId() == null) {
                continue;
            }
            //fixme mapa zamiast streama
            categoryDTO.setParentCat(
                    categoryDTOList.stream()
                            .filter(c -> c.getId().equals(categoryDTO.getParentId()))
                            .findFirst()
                            .orElse(null)
            );
        }

        for (CategoryDTO categoryDTO : categoryDTOList) { //ustawiamy czy kategoria ma być zaznaczona/rozwinięta
            if (categoryName!=null && categoryName.trim().equals(categoryDTO.getCategoryName())) { //dodane 'categoryName!=null'
                categoryDTO.getCategoryState().setOpen(true);
                categoryDTO.getCategoryState().setSelected(true);
                openParent(categoryDTO.getParentCat());
            }
        }

        return categoryDTOList;
    }

    private void openParent(CategoryDTO categoryDTO) { //otwieramy wszystkie nadrzędne kategorie
        categoryDTO.getCategoryState().setOpen(true);
        if (categoryDTO.getParentId() == null) {
            return;
        }
        openParent(categoryDTO.getParentCat());
    }

    private CategoryDTO buildCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName(category.getCategoryName().trim());
        categoryDTO.setId(category.getId());
        categoryDTO.setParentId(category.getParentId());
        categoryDTO.setCategoryState(new CategoryState());
        return categoryDTO;
    }

    public void moveCat(Integer newParentId, Integer movedId) {
        InMemoryCategoryDAO.getInstance()
                .getCategoryList().stream()
                .filter(c -> c.getId().equals(movedId))
                .forEach(c -> c.setParentId(newParentId));
    }
}
