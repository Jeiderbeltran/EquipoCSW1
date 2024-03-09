package LowLevelDesign.DesignVendingMachine;

import java.util.Scanner;

import LowLevelDesign.DesignVendingMachine.Autentication.UsernamePasswordAuthenticationManagerImpl;

public class Main {

    public static void main(String args[]) throws Exception {

        Scanner scanner = new Scanner(System.in);
        try {
            int intentosFallidos = 0;
            VendingMachine vendingMachine = new VendingMachine();

            while (true) {
                System.out.println("Bienvenido a la Máquina Expendedora");
                System.out.println("1. Iniciar sesión");
                System.out.println("2. Consultar productos");
                System.out.println("3. Salir");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Seleccione el método de autenticación:");
                        System.out.println("1. Usuario y contraseña");
                        System.out.println("2. Pin");
                        System.out.println("3. Redes sociales");

                        int authChoice = scanner.nextInt();

                        switch (authChoice) {
                            case 1:
                                if (intentosFallidos >= 3) {
                                    System.out.println("Ha excedido el número de intentos permitidos");
                                    break;
                                }
                                System.out.println("Autenticación por usuario y contraseña\n");
                                UsernamePasswordAuthenticationManagerImpl authentication = new UsernamePasswordAuthenticationManagerImpl();
                                System.out.println("Ingrese el nombre de usuario: ");
                                String usernameAuthenticate = scanner.next();
                                System.out.println("Ingrese la contraseña: ");
                                String passwordAuthenticate = scanner.next();

                                if (authentication.authenticate(usernameAuthenticate, passwordAuthenticate)) {
                                    while (true) {
                                        intentosFallidos = 0;
                                        System.out.println("Menú de Admin");
                                        System.out.println("1. Llenar inventario");
                                        System.out.println("2. Ver inventario");
                                        System.out.println("3. Gestionar monedas");
                                        System.out.println("4. Agregar usuario");
                                        System.out.println("5. Cerrar sesión");

                                        int adminChoice = scanner.nextInt();

                                        switch (adminChoice) {
                                            case 1:
                                                fillUpInventory(vendingMachine);
                                                displayInventory(vendingMachine);
                                                break;
                                            case 2:
                                                displayInventory(vendingMachine);
                                                break;
                                            case 3:
                                                manageCoins(vendingMachine);
                                                break;
                                            case 4:
                                                System.out.println("Ingrese el nombre de usuario: ");
                                                String username = scanner.next();
                                                System.out.println("Ingrese la contraseña: ");
                                                String password = scanner.next();
                                                authentication.addUser(username, password);
                                                System.out.println("Usuario agregado con éxito");
                                                break;
                                            case 5:
                                                System.out.println("Saliendo...");
                                                System.exit(0);
                                                break;
                                            default:
                                                System.out.println("Opción no válida");
                                                break;
                                        }
                                    }

                                } else {
                                    intentosFallidos++;
                                    System.out.println("Usuario o contraseña incorrectos");
                                }
                                break;
                            case 2:
                                throw new Exception("Autenticación por pin aún no implementada");
                            case 3:
                                throw new Exception("Autenticación por redes sociales aún no implementada.");
                            default:
                                System.out.println("Opción no válida");
                                break;
                        }
                        break;

                    case 2:
                        try {
                            fillUpInventory(vendingMachine);
                            displayInventory(vendingMachine);
                            displayInventory(vendingMachine);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        System.out.println("Saliendo...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción no válida");
                        break;
                }
            }
        } finally {
            scanner.close();
        }
    }

    private static void fillUpInventory(VendingMachine vendingMachine) {
        ItemShelf[] slots = vendingMachine.getInventory().getInventory();
        for (int i = 0; i < slots.length; i++) {
            Item newItem = new Item();
            if (i >= 0 && i < 3) {
                newItem.setType(ItemType.COKE);
                newItem.setPrice(12);
            } else if (i >= 3 && i < 5) {
                newItem.setType(ItemType.PEPSI);
                newItem.setPrice(9);
            } else if (i >= 5 && i < 7) {
                newItem.setType(ItemType.JUICE);
                newItem.setPrice(13);
            } else if (i >= 7 && i < 10) {
                newItem.setType(ItemType.SODA);
                newItem.setPrice(7);
            }
            slots[i].setItem(newItem);
            slots[i].setSoldOut(false);
        }
    }

    private static void displayInventory(VendingMachine vendingMachine) {

        ItemShelf[] slots = vendingMachine.getInventory().getInventory();
        for (int i = 0; i < slots.length; i++) {

            System.out.println("CodeNumber: " + slots[i].getCode() +
                    " Item: " + slots[i].getItem().getType().name() +
                    " Price: " + slots[i].getItem().getPrice() +
                    " isAvailable: " + !slots[i].isSoldOut());
        }
    }

    public static void manageCoins(VendingMachine vendingMachine) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("|");
        System.out.println("Gestionar Monedas");
        System.out.println("|");
        System.out.println("1. Agregar moneda");
        System.out.println("2. Retirar moneda");
        System.out.println("|");

        int coinOperationChoice = scanner.nextInt();
        switch (coinOperationChoice) {
            case 1:
                System.out.println("Seleccione el tipo de moneda a agregar:");
                System.out.println("1. NICKEL");
                System.out.println("2. DIME");
                System.out.println("3. QUARTER");
                System.out.println("4. PENNY");

                int coinTypeChoice = scanner.nextInt();

                switch (coinTypeChoice) {
                    case 1:
                        vendingMachine.getCoinList().add(Coin.NICKEL);
                        break;
                    case 2:
                        vendingMachine.getCoinList().add(Coin.DIME);
                        break;
                    case 3:
                        vendingMachine.getCoinList().add(Coin.QUARTER);
                        break;
                    case 4:
                        vendingMachine.getCoinList().add(Coin.PENNY);
                        break;
                    default:
                        System.out.println("Tipo de moneda no válido");
                        break;
                }
                System.out.println("Moneda agregada con éxito.");
                break;

            case 2:
                System.out.println("Operación de retirada de moneda aún no implementada.");
                break;

            default:
                System.out.println("Opción no válida");
                break;
        }
    }

}
