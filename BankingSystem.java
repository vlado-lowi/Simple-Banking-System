package banking;

import java.util.*;

public class BankingSystem {
    private final Map<String, String> accounts;
    private final Scanner inputScanner;

    public BankingSystem() {
        accounts = new HashMap<>();
        inputScanner = new Scanner(System.in);
    }

    public void startSystem() {
        boolean systemOnlineStatus = true;

        while (systemOnlineStatus){
            // get action from user
            int action = printMainMenu();

            switch (action) {
                case 0:
                    // Exiting system
                    systemOnlineStatus = false;
                    break;
                case 1:
                    // Create and account
                    createAccount();
                    break;
                case 2:
                    // Login to account
                    systemOnlineStatus = logIn();
                    break;
                default:
                    System.out.println();
                    System.out.println("Unknown action, please try again.");
                    break;
            }
        }
        // Exiting system
        System.out.println("Bye!");
    }

    private void createAccount() {
        StringBuilder sb = new StringBuilder(16);
        // custom Issuer Identification Number (IIN) for our system 400000
        sb.append("400000");

        Random random = new Random();
        // append 10 random digits
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }

        String accountNumber = sb.toString();

        if (accounts.containsKey(accountNumber)) {
            createAccount();
        } else {
            // generate PIN for account
            StringBuilder pinBuilder = new StringBuilder(4);
            for (int i = 0; i < 4; i++) {
                pinBuilder.append(random.nextInt(10));
            }
            String accountPin = pinBuilder.toString();
            accounts.put(accountNumber, accountPin);
            System.out.println();
            System.out.println("Your card has been created");
            System.out.println("Your card number:");
            System.out.println(accountNumber);
            System.out.println("Your card PIN:");
            System.out.println(accountPin);
            System.out.println();
        }
    }

    private int printMainMenu() {
        System.out.println("1. Create an account");
        System.out.println("2. Log into account");
        System.out.println("0. Exit");

        int action;
        try {
            action = Integer.parseInt(inputScanner.next());
        } catch (NumberFormatException e) {
            action = -1;
            // -1 prompts user to try again with correct action
        }
        return action;
    }

    private boolean logIn() {
        boolean keepSystemOnline;
        System.out.println();
        System.out.println("Enter your card number:");
        String inputAccountNumber = inputScanner.next();
        System.out.println("Enter your PIN:");
        String inputAccountPin = inputScanner.next();

        if (accounts.containsKey(inputAccountNumber) &&
            inputAccountPin.equals(accounts.get(inputAccountNumber))) {
            System.out.println();
            System.out.println("You have successfully logged in!");
            System.out.println();
            keepSystemOnline = printLoggedInMenu();
        } else {
            System.out.println();
            System.out.println("Wrong card number or PIN!");
            System.out.println();
            keepSystemOnline = true;
        }

        return keepSystemOnline;
    }

    // return true -> keep main menu loop active
    // return false -> exit system
    private boolean printLoggedInMenu() {
        boolean loggedIn = true;
        int action;
        while (loggedIn) {
            System.out.println("1. Balance");
            System.out.println("2. Log out");
            System.out.println("0. Exit");
            try {
                action = Integer.parseInt(inputScanner.next());;
            } catch (NumberFormatException e) {
                action = -1;
            }

            switch (action) {
                case 0:
                    // Exiting system
                    return false;
                case 1:
                    // Print balance
                    System.out.println();
                    System.out.println("Balance: 0");
                    System.out.println();
                    break;
                case 2:
                    loggedIn = false;
                    System.out.println();
                    System.out.println("You have successfully logged out!");
                    System.out.println();
                    break;
                default:
                    System.out.println();
                    System.out.println("Unknown action please try again!");
                    System.out.println();
                    break;
            }
        }
        return true;
    }
}
