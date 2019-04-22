package trials.intermediate.categories;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

    private Integer id;
    private Integer parentId;
    private String categoryName;
    private CategoryState categoryState;
    private CategoryDTO parentCat;

    public String getText(){ //biblioteka frontendowa wymaga takiej metody
        return categoryName;
    }

    public String getParent(){ //biblioteka frontendowa wymaga takiej metody
        return parentId == null ? "#" : parentId.toString();
    }

    public CategoryState getState(){ //biblioteka frontendowa wymaga takiej metody
        return categoryState;
    }

}
