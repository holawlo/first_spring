//package trials;
//
//import lombok.Getter;
//
//import javax.persistence.*;
//import javax.print.DocFlavor;
//
//@Getter
//@Entity
//public class MyElement {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String message;
//    @Enumerated(EnumType.STRING)
//    private FoodChoice foodChoice;
//
//    public MyElement(String message, FoodChoice foodChoice) { //didn't see the one from lombok (?)
//        this.message = message;
//        this.foodChoice = foodChoice;
//    }
//
//    public MyElement() {
//    }
//
//}
