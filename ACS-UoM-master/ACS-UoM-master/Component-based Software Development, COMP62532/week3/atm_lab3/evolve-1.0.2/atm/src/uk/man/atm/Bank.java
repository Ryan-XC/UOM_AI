package uk.man.atm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bank
// start generated code
	// main port
 implements uk.man.atm.IBankServices
{
// end generated code
	// attributes
	private ArrayList<IAccount> accounts;

	// attribute setters and getters
	public ArrayList<IAccount> getAccounts() { return accounts; }
	public void setAccounts(ArrayList<IAccount> accounts) { this.accounts = accounts;}

	
	public Bank () {
        try {
            // create two accounts (acc1, acc2) with account numbers 1111 and 2222
            // then set initial balance to them
            Account acc1 = new Account();
            acc1.setAccountNo("1111");
            acc1.setBalance(1000);
            
            Account acc2 = new Account();
            acc2.setAccountNo("2222");
            acc2.setBalance(5000);
            
            accounts = new ArrayList<IAccount>();
            accounts.add( acc1 );
            accounts.add( acc2 );
            
        } catch (Exception ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	 public void withdraw ( String accno, int amount ) throws Exception {
	        // first retrieve the right account
	        // then call the account to withdraw the specified amount
	        Account acc = retrieve ( accno );
	        acc.withdraw ( amount );
	    }
	    
	    public void deposit ( String accno, int amount ) throws Exception {
	        // first retrieve the right account
	        // then call the account to deposit the specified amount
	        Account acc = retrieve ( accno );
	        acc.deposit ( amount );
	    }
	    
	    public int balance ( String accno ) throws Exception {
	        // first retrieve the right account
	        // then call the account to check balance
	        Account acc = retrieve ( accno );
	        return acc.balance();
	    }
	    
	    /**
	     * Retrieve the account associated to the provided account number
	     * 
	     * @param accno account number
	     * @return Account
	     * @throws Exception 
	     */
	    private Account retrieve ( String accno ) throws Exception {
	        Iterator<IAccount> i = accounts.iterator();
	        while ( i.hasNext() ) {
	            Account acc = (Account) i .next();
	            if ( acc.getAccountNo().equals( accno ) )
	                return acc;
	        }
	        throw new Exception ( "Account not found." );
	    }

}
