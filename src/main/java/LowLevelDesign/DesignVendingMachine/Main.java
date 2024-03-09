package LowLevelDesign.DesignVendingMachine;

import LowLevelDesign.DesignVendingMachine.VendingStates.State;
import LowLevelDesign.DesignVendingMachine.Autentication.UsernamePasswordAuthenticationManagerImpl;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido a la Maquina Expendedora - Autenticacion");
        System.out.println("-------------------------------------------------");

        while (true) {
            System.out.println("Seleccione una opcion de autenticacion:");
            System.out.println("1. Usuario y Contraseña");
            System.out.println("2. PIN");
            System.out.println("3. Redes Sociales");
            System.out.println("0. Salir");

            VendingMachine vendingMachine = new VendingMachine();

            try {

                System.out.println("|");
                System.out.println("filling up the inventory");
                System.out.println("|");

                fillUpInventory(vendingMachine);
                displayInventory(vendingMachine);

                System.out.println("|");
                System.out.println("clicking on InsertCoinButton");
                System.out.println("|");

                State vendingState = vendingMachine.getVendingMachineState();
                vendingState.clickOnInsertCoinButton(vendingMachine);

                vendingState = vendingMachine.getVendingMachineState();
                vendingState.insertCoin(vendingMachine, Coin.NICKEL);
                vendingState.insertCoin(vendingMachine, Coin.QUARTER);
                // vendingState.insertCoin(vendingMachine, Coin.NICKEL);

                System.out.println("|");
                System.out.println("clicking on ProductSelectionButton");
                System.out.println("|");
                vendingState.clickOnStartProductSelectionButton(vendingMachine);

                vendingState = vendingMachine.getVendingMachineState();
                vendingState.chooseProduct(vendingMachine, 102);

                displayInventory(vendingMachine);

            } catch (Exception e) {
                displayInventory(vendingMachine);
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

            System.out.println("CodeNumber: " + slots[i].getCode()
                    + " Item: " + slots[i].getItem().getType().name()
                    + " Price: " + slots[i].getItem().getPrice()
                    + " isAvailable: " + !slots[i].isSoldOut());
        }
    }

}
