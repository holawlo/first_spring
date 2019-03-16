package trials;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@Getter
@Entity
public class MyElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private FoodChoice foodChoice;

    public MyElement(String message, FoodChoice foodChoice) { //didn't see the one from lombok (?)
        this.message = message;
        this.foodChoice = foodChoice;
    }

    public MyElement() {
    }

}
