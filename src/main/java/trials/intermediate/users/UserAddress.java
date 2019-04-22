package trials.intermediate.users;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserAddress implements Serializable {
    private static final long serialVersionUID = -25766673310720149L;
    private String city;
    private String country;
    private String street;
    private String zipCode;
}
