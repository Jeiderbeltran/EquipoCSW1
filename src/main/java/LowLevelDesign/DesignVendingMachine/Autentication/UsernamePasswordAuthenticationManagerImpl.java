package LowLevelDesign.DesignVendingMachine.Autentication;

import java.util.ArrayList;
import java.util.List;

import LowLevelDesign.DesignVendingMachine.User;

public class UsernamePasswordAuthenticationManagerImpl implements Authentication {
    private List<User> users;
    private int intentosFallidos = 0;

    public UsernamePasswordAuthenticationManagerImpl() {
        this.users = new ArrayList<>();
        this.users.add(new User("admin", "12345"));
    }

    @Override
    public boolean authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                intentosFallidos = 0;
                return true;
            }
        }
        intentosFallidos++;
        if (intentosFallidos >= 3) {
            System.out.println("Usuario bloqueado. Contacte al administrador");
        }
        return false;
    }
}
