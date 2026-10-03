package atm;

	public class Main {

	    public static void main(String[] args) {

	        Bank bank = new Bank();

	        Account account1 =
	                new Account("1001", "Sainath", 10000.00);

	        Account account2 =
	                new Account("1002", "Rahul", 7500.00);

	        bank.addAccount(account1);
	        bank.addAccount(account2);

	        ATM atm = new ATM(bank);

	        atm.start();
	    }

}
