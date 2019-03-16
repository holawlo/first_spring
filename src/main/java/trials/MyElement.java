package trials;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Entity
public class MyElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @Enumerated
    private FoodChoice foodChoice;

    public MyElement(String message, FoodChoice foodChoice) { //didn't see the one from lombok (?)
        this.message = message;
        this.foodChoice = foodChoice;
    }

    public MyElement() {
    }

}
