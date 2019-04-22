package trials.intermediate.users;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    UserDAO userDAO;

    public UserLoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean login(UserLoginDTO userDTO){
        return userDAO.getUserList().stream()
                .filter(u -> u.getLogin().equals(userDTO.getEmail()))
                .findAny()
                .map(u -> u.getPasswordHash()
                        .equals(DigestUtils.sha512Hex(userDTO.getPassword())))
                .orElse(false);
    }
}
