package atm;

	import java.util.ArrayList;
	import java.util.Scanner;

	public class ATM {

	    private Bank bank;
	    private Scanner scanner;
	    private Account currentAccount;

	    public ATM(Bank bank) {
	        this.bank = bank;
	        scanner = new Scanner(System.in);
	    }

	    public void start() {

	        System.out.println("========================================");
	        System.out.println("          WELCOME TO JAVA ATM");
	        System.out.println("========================================");

	        if (!login()) {
	            System.out.println("\nAccess denied.");
	            System.out.println("ATM session terminated.");
	            return;
	        }

	        showMenu();
	    }

	    private boolean login() {

	        int attempts = 0;

	        while (attempts < 3) {

	            System.out.print("\nEnter User ID: ");
	            String userId = scanner.nextLine();

	            System.out.print("Enter PIN: ");
	            String pin = scanner.nextLine();

	            Account account = bank.findAccount(userId);

	            if (account != null && account.getAccountId().equals(userId)
	                    && getPinForUser(userId).equals(pin)) {

	                currentAccount = account;

	                System.out.println("\nLogin successful!");
	                System.out.println("Welcome, " +
	                        currentAccount.getAccountHolder());

	                return true;
	            }

	            attempts++;

	            System.out.println("Invalid User ID or PIN.");

	            if (attempts < 3) {
	                System.out.println("Attempts remaining: " + (3 - attempts));
	            }
	        }

	        return false;
	    }

	    private String getPinForUser(String userId) {

	        if (userId.equals("1001")) {
	            return "1234";
	        }

	        if (userId.equals("1002")) {
	            return "5678";
	        }

	        return "";
	    }

	    private void showMenu() {

	        int choice;

	        do {

	            System.out.println("\n========================================");
	            System.out.println("              ATM MENU");
	            System.out.println("========================================");
	            System.out.println("1. Transaction History");
	            System.out.println("2. Withdraw");
	            System.out.println("3. Deposit");
	            System.out.println("4. Transfer");
	            System.out.println("5. Check Balance");
	            System.out.println("6. Quit");
	            System.out.println("========================================");

	            System.out.print("Enter your choice: ");

	            try {
	                choice = Integer.parseInt(scanner.nextLine());

	                switch (choice) {

	                    case 1:
	                        showTransactionHistory();
	                        break;

	                    case 2:
	                        withdraw();
	                        break;

	                    case 3:
	                        deposit();
	                        break;

	                    case 4:
	                        transfer();
	                        break;

	                    case 5:
	                        showBalance();
	                        break;

	                    case 6:
	                        System.out.println("\nThank you for using Java ATM.");
	                        System.out.println("Have a nice day!");
	                        break;

	                    default:
	                        System.out.println("Invalid choice. Please try again.");
	                }

	            } catch (NumberFormatException e) {

	                choice = 0;
	                System.out.println("Please enter a valid number.");
	            }

	        } while (choice != 6);
	    }

	    private void showTransactionHistory() {

	        System.out.println("\n========================================");
	        System.out.println("         TRANSACTION HISTORY");
	        System.out.println("========================================");

	        ArrayList<Transaction> history =
	                currentAccount.getTransactions();

	        if (history.isEmpty()) {
	            System.out.println("No transactions available.");
	            return;
	        }

	        for (Transaction transaction : history) {
	            System.out.println(transaction);
	        }
	    }

	    private void withdraw() {

	        System.out.println("\n----------- WITHDRAW -----------");

	        System.out.print("Enter amount: ");

	        try {

	            double amount = Double.parseDouble(scanner.nextLine());

	            if (amount <= 0) {
	                System.out.println("Amount must be greater than zero.");
	                return;
	            }

	            if (amount > currentAccount.getBalance()) {
	                System.out.println("Insufficient Funds");
	                return;
	            }

	            if (currentAccount.withdraw(amount)) {

	                Transaction transaction = new Transaction(
	                        "WITHDRAW",
	                        amount,
	                        "Cash withdrawn"
	                );

	                currentAccount.addTransaction(transaction);

	                System.out.printf(
	                        "₹%.2f withdrawn successfully.%n",
	                        amount
	                );

	                System.out.printf(
	                        "Remaining balance: ₹%.2f%n",
	                        currentAccount.getBalance()
	                );
	            }

	        } catch (NumberFormatException e) {

	            System.out.println("Please enter a valid amount.");
	        }
	    }

	    private void deposit() {

	        System.out.println("\n----------- DEPOSIT -----------");

	        System.out.print("Enter amount: ");

	        try {

	            double amount = Double.parseDouble(scanner.nextLine());

	            if (amount <= 0) {
	                System.out.println("Amount must be greater than zero.");
	                return;
	            }

	            if (currentAccount.deposit(amount)) {

	                Transaction transaction = new Transaction(
	                        "DEPOSIT",
	                        amount,
	                        "Cash deposited"
	                );

	                currentAccount.addTransaction(transaction);

	                System.out.printf(
	                        "₹%.2f deposited successfully.%n",
	                        amount
	                );

	                System.out.printf(
	                        "Current balance: ₹%.2f%n",
	                        currentAccount.getBalance()
	                );
	            }

	        } catch (NumberFormatException e) {

	            System.out.println("Please enter a valid amount.");
	        }
	    }

	    private void transfer() {

	        System.out.println("\n----------- TRANSFER -----------");

	        System.out.print("Enter recipient account ID: ");
	        String recipientId = scanner.nextLine();

	        Account recipient = bank.findAccount(recipientId);

	        if (recipient == null) {
	            System.out.println("Recipient account not found.");
	            return;
	        }

	        if (recipientId.equals(currentAccount.getAccountId())) {
	            System.out.println("You cannot transfer money to your own account.");
	            return;
	        }

	        System.out.print("Enter amount: ");

	        try {

	            double amount = Double.parseDouble(scanner.nextLine());

	            if (amount <= 0) {
	                System.out.println("Amount must be greater than zero.");
	                return;
	            }

	            if (amount > currentAccount.getBalance()) {
	                System.out.println("Insufficient Funds");
	                return;
	            }

	            currentAccount.withdraw(amount);
	            recipient.deposit(amount);

	            Transaction senderTransaction = new Transaction(
	                    "TRANSFER",
	                    amount,
	                    "Transferred to " + recipientId
	            );

	            Transaction receiverTransaction = new Transaction(
	                    "TRANSFER",
	                    amount,
	                    "Received from " + currentAccount.getAccountId()
	            );

	            currentAccount.addTransaction(senderTransaction);
	            recipient.addTransaction(receiverTransaction);

	            System.out.printf(
	                    "₹%.2f transferred successfully.%n",
	                    amount
	            );

	            System.out.printf(
	                    "Remaining balance: ₹%.2f%n",
	                    currentAccount.getBalance()
	            );

	        } catch (NumberFormatException e) {

	            System.out.println("Please enter a valid amount.");
	        }
	    }

	    private void showBalance() {

	        System.out.println("\n----------- BALANCE -----------");

	        System.out.printf(
	                "Account Holder : %s%n",
	                currentAccount.getAccountHolder()
	        );

	        System.out.printf(
	                "Account ID     : %s%n",
	                currentAccount.getAccountId()
	        );

	        System.out.printf(
	                "Available Balance : ₹%.2f%n",
	                currentAccount.getBalance()
	        );
	    }

}
