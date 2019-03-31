package trials;

import lombok.Getter;

@Getter
public class MyElementH2 {

    private Long id;
    private String message;
    //@Enumerated(EnumType.STRING)
    private FoodChoice foodChoice;

    public MyElementH2(String message, FoodChoice foodChoice) { //didn't see the one from lombok (?)
        this.message = message;
        this.foodChoice = foodChoice;
    }

    public MyElementH2() {
    }

}