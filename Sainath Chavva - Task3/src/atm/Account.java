package atm;

	import java.util.ArrayList;

	public class Account {

	    private String accountId;
	    private String accountHolder;
	    private double balance;

	    private ArrayList<Transaction> transactions;

	    public Account(String accountId, String accountHolder, double balance) {
	        this.accountId = accountId;
	        this.accountHolder = accountHolder;
	        this.balance = balance;
	        this.transactions = new ArrayList<>();
	    }

	    public String getAccountId() {
	        return accountId;
	    }

	    public String getAccountHolder() {
	        return accountHolder;
	    }

	    public double getBalance() {
	        return balance;
	    }

	    public ArrayList<Transaction> getTransactions() {
	        return transactions;
	    }

	    public boolean withdraw(double amount) {

	        if (amount <= 0) {
	            return false;
	        }

	        if (amount > balance) {
	            return false;
	        }

	        balance -= amount;
	        return true;
	    }

	    public boolean deposit(double amount) {

	        if (amount <= 0) {
	            return false;
	        }

	        balance += amount;
	        return true;
	    }

	    public void addTransaction(Transaction transaction) {
	        transactions.add(transaction);
	    }

}
