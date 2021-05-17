package com.techelevator.tenmo;

import com.techelevator.tenmo.models.*;
import com.techelevator.tenmo.services.*;
import com.techelevator.view.ConsoleService;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
    private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
    private static final String[] LOGIN_MENU_OPTIONS = {LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};
    private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
    private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
    private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
    private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};

    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private Accounts accounts;
    private AccountService accountService;
    private TransferService transferService = new TransferService("http://localhost:8080");
    private Transfers transfers = new Transfers();




    public static void main(String[] args) throws TransferServiceException {
        App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
        app.run();
    }

    RestTemplate restTemplate = new RestTemplate();


    public App(ConsoleService console, AuthenticationService authenticationService) {
        this.console = console;
        this.authenticationService = authenticationService;
        accountService = new AccountService(API_BASE_URL);

    }

    public void run() throws TransferServiceException {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");

        registerAndLogin();
        mainMenu();
    }

    private void mainMenu() throws TransferServiceException {
        while (true) {
            String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
                viewCurrentBalance();
            } else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
                viewTransferHistory();
            } else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
                viewPendingRequests();
            } else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
                sendBucks();
            } else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
                requestBucks();
            } else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else {
                // the only other option on the main menu is to exit
                exitProgram();
            }
        }
    }

    private void viewCurrentBalance() {
        // TODO Auto-generated method stub
        accountService.getBalance(currentUser.getToken());
        System.out.println("Username: " + currentUser.getUser().getUsername());
        System.out.println("User ID: " + currentUser.getUser().getId());
//		System.out.println("Account ID " + accounts.getAccount_id());
    }

    private void viewTransferHistory() throws TransferServiceException {
        // TODO Auto-generated method stub


        Transfers[] transfers = transferService.getTransferInfo(currentUser);

        displayReceived(transfers);
    }


    private void viewPendingRequests() {
        // TODO Auto-generated method stub
    }


    private void sendBucks() throws TransferServiceException {
        //ANDY TODO: Display a list of users, possible hint: you need to make a call
        // first to an endpoint on the server that gives you a list of users.
        // You might need to make this requestMapping.
        // Ideally when the objects on this list contain an account_id.
        System.out.println("___________________________________________");
        System.out.println("ID " + " " + "NAME");
        System.out.println("___________________________________________\n");

        //displaying users via method
        User[] users = displayUserList();


        Scanner scanner = new Scanner(System.in);

        int recipientId = -999;
        try {
            System.out.println("----------");
            System.out.println("\nEnter ID of user you are sending to (0 to cancel)");
            recipientId = Integer.parseInt( scanner.nextLine() );
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid ID");
        }

        BigDecimal amountToSend = null;
        try {
            System.out.println("Enter amount");

            amountToSend = new BigDecimal(scanner.nextLine());

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid amount");
        }

        // Let's make a Transfer object

        Transfers transfer = new Transfers();
        transfer.setTransfer_type(2);
        transfer.setTransfer_status(1);
        transfer.setAccount_from(currentUser.getUser().getId());
        transfer.setAccount_to(recipientId);
        transfer.setAmount(amountToSend);

        // You need to send this request to the server, and the
        // server needs to have a POST mapping listening to the request
        // In the body of the request is going to be the Transfers
        // object described above.
        if(accountService.getBalance(currentUser.getToken()).getBalance().compareTo(transfer.getAmount()) != -1) {
            transferService.sendTransfer(transfer, currentUser);
        } else {
            System.out.println("Not enough funds.");
        }
    }

    private void requestBucks() {
        // TODO Auto-generated method stub
    }


    private void exitProgram() {
        System.exit(0);
    }

    private void registerAndLogin() {
        while (!isAuthenticated()) {
            String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
            if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
                register();
            } else {
                // the only other option on the login menu is to exit
                exitProgram();
            }
        }
    }

    private void displayReceived(Transfers[] received) {

//        Transfers [] transferArr = null;
        System.out.println("-----------------");
        System.out.println("Transfers");
        System.out.println("Transfer ID" + " " + "From/To" + " " + "Amount");
        System.out.println("-----------------");

        for (Transfers transfers : received) {
            System.out.println( transfers.getTransferId() + " " + "From: " +
                    currentUser.getUser().getUsername() + ", " + "To Account # : " + transfers.getAccount_to() + " " + "$ " + transfers.getAmount());

            System.out.println("-----------------");
        }
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter transfer ID to view details(0 to cancel)");
 //       int transfertest = console.getUserInputInteger("Enter transfer ID to view details(0 to cancel");
        String transferId = scanner.nextLine();
        int transferId1 = Integer.parseInt(transferId);

        for(Transfers transfers : received) {
            if (transfers.getTransferId() == transferId1) {

                System.out.println("------------------------------------------------");
                System.out.println("Transfer Details");
                System.out.println("------------------------------------------------");
                System.out.println("Id: " + transfers.getTransferId());
                System.out.println("From: " + transfers.getAccount_from());
                System.out.println("To: " + transfers.getAccount_to());
                System.out.println("Type: " + transfers.getTransfer_type());
                System.out.println("Status: " + transfers.getTransfer_status());
                System.out.println("Amount: " + transfers.getAmount());
                System.out.println("-------------------------------------------------");
            }
        }
    }

    public User[] displayUserList() {
        User[] userArr = restTemplate.getForObject("http://localhost:8080/all-users", User[].class);

        for (User user : userArr) {
            if (user.getId() != currentUser.getUser().getId()) {
                System.out.println(user.getId() + " " + user.getUsername());
            }
        }
        return userArr;
    }

    private boolean isAuthenticated() {
        return currentUser != null;
    }

    private void register() {
        System.out.println("Please register a new user account");
        boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                authenticationService.register(credentials);
                isRegistered = true;
                System.out.println("Registration successful. You can now login.");
            } catch (AuthenticationServiceException e) {
                System.out.println("REGISTRATION ERROR: " + e.getMessage());
                System.out.println("Please attempt to register again.");
            }
        }
    }

    private void login() {
        System.out.println("Please log in");
        currentUser = null;
        while (currentUser == null) //will keep looping until user is logged in
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                currentUser = authenticationService.login(credentials);
            } catch (AuthenticationServiceException e) {
                System.out.println("LOGIN ERROR: " + e.getMessage());
                System.out.println("Please attempt to login again.");
            }
        }
    }

    private UserCredentials collectUserCredentials() {
        String username = console.getUserInput("Username");
        String password = console.getUserInput("Password");
        return new UserCredentials(username, password);
    }
}
