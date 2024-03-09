package LowLevelDesign.DesignVendingMachine.Autentication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import LowLevelDesign.DesignVendingMachine.User;

public class UsernamePasswordAuthenticationManagerImpl implements Authentication {
    private List<User> users;

    public UsernamePasswordAuthenticationManagerImpl() {
        this.users = loadUsersFromFile();
        if (users.isEmpty()) {
            this.users.add(new User("admin", "12345"));
            saveUsersToFile();
        }
    }

    private List<User> loadUsersFromFile() {
        List<User> loadedUsers = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            while (scanner.hasNextLine()) {
                String[] userData = scanner.nextLine().split(",");
                loadedUsers.add(new User(userData[0], userData[1]));
            }
        } catch (FileNotFoundException e) {
            // Manejar la ausencia del archivo
        }
        return loadedUsers;
    }

    private void saveUsersToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt"))) {
            for (User user : users) {
                writer.println(user.getUsername() + "," + user.getPassword());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String username, String password) {
        if (isPasswordValid(password)) {
            users.add(new User(username, password));
            saveUsersToFile();
        } else {
            System.out.println("La contraseña debe contener al menos una letra mayúscula, una letra minúscula, un caracter especial y un número");
        }
    }

    @Override
    public boolean authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPasswordValid(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
