package trials;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MyElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;

    public MyElement(String message) {
        this.message = message;
    }

    public MyElement() {
    }

    public String getMessage() {
        return message;
    }
}
