package trials.intermediate.users;
public class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }
}