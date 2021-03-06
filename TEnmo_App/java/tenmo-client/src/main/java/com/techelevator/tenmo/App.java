package com.techelevator.tenmo;

import com.techelevator.tenmo.exceptions.InvalidTransferIdChoice;
import com.techelevator.tenmo.exceptions.InvalidUserChoiceException;
import com.techelevator.tenmo.exceptions.UserNotFoundException;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TenmoService;
import com.techelevator.view.ConsoleService;

import java.math.BigDecimal;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private TenmoService tenmoService;

	private static int transferIdNumber;

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.tenmoService = new TenmoService();
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub
		System.out.println("Your current account balance is: " + tenmoService.retrieveBalance(currentUser));

	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
		Transfer[] transfers = tenmoService.getTransfersFromUserId(currentUser, currentUser.getUser().getId());
		System.out.println("-----------------------------");
		System.out.println("----------Transfers----------");
		System.out.println("ID        From/To      Amount");
		System.out.println("-----------------------------");
		System.out.println();

		for(Transfer transfer : transfers) {
			printTransferStubDetails(currentUser, transfer);
		}

		int transferIdChoice = console.getUserInputInteger("Please enter transfer ID to view details (0 to cancel)");
		Transfer transferChoice = validateTransferIdChoice(transferIdChoice, transfers, currentUser);
		if (transferChoice != null) {
			printTransferDetails(currentUser, transferChoice);
		}
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
	}

	private void sendBucks() throws NullPointerException{
		// TODO Auto-generated method stub
		User[] users = tenmoService.getAllUsers(currentUser);
		System.out.println("-------------------------------");
		System.out.println("-------------Users-------------");
		System.out.println("ID           Name");
		System.out.println("-------------------------------");
		System.out.println();

		console.printUsers(users);

		int userIdChoice = console.getUserInputInteger("Enter ID of user you are sending to (0 to cancel)");
		if (validateUserChoice(userIdChoice, users, currentUser)) {
			String amountChoice = console.getUserInput("Enter Amount");
			createTransfer(userIdChoice, amountChoice, "Send", "Approved");
		}
	}

	private Transfer createTransfer(int accountChoiceUserId, String amountString, String transferType, String status) {

		int accountToId;
		int accountFromId;
		int transferTypeId = tenmoService.getTransferType(currentUser, transferType).getTransferTypeId();
		int transferStatusId = tenmoService.getTransferStatus(currentUser, status).getTransferStatusId();

		accountToId = tenmoService.getAccountByUserId(currentUser, accountChoiceUserId).getAccountId();
		accountFromId = tenmoService.getAccountByUserId(currentUser, currentUser.getUser().getId()).getAccountId();

		BigDecimal amount = new BigDecimal(amountString);

		Transfer transfer = new Transfer();
		transfer.setAccountFrom(accountFromId);
		transfer.setAccountTo(accountToId);
		transfer.setTransferAmount(amount);
		transfer.setTransferId(transferIdNumber);
		transfer.setTransferStatusId(transferStatusId);
		transfer.setTransferTypeId(transferTypeId);

		tenmoService.createTransfer(currentUser, transfer);

		return transfer;
	}

	private void printTransferStubDetails (AuthenticatedUser authenticatedUser, Transfer transfer) {

		String fromOrTo = "";
		int accountFrom = transfer.getAccountFrom();
		int accountTo = transfer.getAccountTo();
		if (tenmoService.getAccountByAccountId(currentUser, accountTo).getUserId() == authenticatedUser.getUser().getId()) {
			int accountFromUserId = tenmoService.getAccountByAccountId(currentUser, accountFrom).getUserId();
			String userFromName = tenmoService.getUserByUserId(currentUser, accountFromUserId).getUsername();
			fromOrTo = "From: " + userFromName;
		} else {
			int accountToUserId = tenmoService.getAccountByAccountId(currentUser, accountTo).getUserId();
			String userToName = tenmoService.getUserByUserId(currentUser, accountToUserId).getUsername();
			fromOrTo = "To: " + userToName;
		}
		console.printTransfers(transfer.getTransferId(), fromOrTo, transfer.getTransferAmount());
	}

	private void printTransferDetails(AuthenticatedUser currentUser, Transfer transferChoice) {

		int id = transferChoice.getTransferId();
		BigDecimal amount = transferChoice.getTransferAmount();
		int fromAccount = transferChoice.getAccountFrom();
		int toAccount = transferChoice.getAccountTo();

		int fromUserId = tenmoService.getAccountByUserId(currentUser, fromAccount).getUserId();
		String fromUserName = tenmoService.getUserByUserId(currentUser, fromUserId).getUsername();
		int toUserId = tenmoService.getAccountByUserId(currentUser, toAccount).getUserId();
		String toUserName = tenmoService.getUserByUserId(currentUser, toUserId).getUsername();

		console.printTransferDetails(id, fromUserName, toUserName, amount);
	}

	private boolean validateUserChoice(int userIdChoice, User[] users, AuthenticatedUser currentUser) {
		if(userIdChoice != 0) {
			try {
				boolean	validUserIdChoice = false;

				for (User user : users) {
					if(userIdChoice == currentUser.getUser().getId()) {
						throw new InvalidUserChoiceException();
					}
					if(user.getId() == userIdChoice) {
						validUserIdChoice = true;
						break;
					}
				}
				if (!validUserIdChoice) {
					throw new UserNotFoundException();
				}
				return true;
			} catch (UserNotFoundException | InvalidUserChoiceException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}

	private Transfer validateTransferIdChoice(int transferIdChoice, Transfer[] transfers, AuthenticatedUser currentUser) {

		Transfer transferChoice = null;
		if(transferIdChoice != 0) {
			try {
				boolean validTransferIdChoice = false;
				for(Transfer transfer : transfers) {
					if (transfer.getTransferId() == transferIdChoice) {
						validTransferIdChoice = true;
						transferChoice = transfer;
						break;
					}
				}
				if (!validTransferIdChoice) {
					throw new InvalidTransferIdChoice();
				}
			} catch (InvalidTransferIdChoice e) {
				System.out.println(e.getMessage());
			}
		}
		return transferChoice;
	}

	private void requestBucks() {
		// TODO Auto-generated method stub

	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
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
			} catch(AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: "+e.getMessage());
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
				//TODO set the token on the Tenmo Service
				tenmoService.setAuthToken(currentUser.getToken());

			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
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
