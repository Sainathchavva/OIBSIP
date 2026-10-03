package atm;

	import java.util.HashMap;

	public class Bank {

	    private HashMap<String, Account> accounts;

	    public Bank() {
	        accounts = new HashMap<>();
	    }

	    public void addAccount(Account account) {
	        accounts.put(account.getAccountId(), account);
	    }

	    public Account findAccount(String accountId) {
	        return accounts.get(accountId);
	    }
}
