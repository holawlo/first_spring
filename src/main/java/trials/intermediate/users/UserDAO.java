package trials.intermediate.users;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Service //to mowi ze bedzie jedna instancja (singleton)
public class UserDAO { //DATA ACCESS OBJECT - klasa dostepowa do zrodla danych
    private final String path = "/home/ola/users_data.txt";
    private List<User> userList = readUsers();

    private List<User> readUsers() {
        try (FileInputStream fis = new FileInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void saveUser(User user) {
        userList.add(user);
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(userList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
