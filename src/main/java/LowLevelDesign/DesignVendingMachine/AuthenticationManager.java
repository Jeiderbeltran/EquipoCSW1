package LowLevelDesign.DesignVendingMachine;

public interface AuthenticationManager {
    boolean authenticate(String username, String password);
    boolean authenticatePin(String pin);
    boolean authenticateSocialNetworks(String socialMedia);
}
