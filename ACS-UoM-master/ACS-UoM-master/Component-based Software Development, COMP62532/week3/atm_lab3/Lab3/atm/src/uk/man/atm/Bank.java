package uk.man.atm;

import java.util.Iterator;

import javax.security.auth.login.AccountException;

import com.intrinsarc.backbone.runtime.api.PortHelper;

public class Bank
// start generated code
	// main port
 implements uk.man.atm.IBankServices
{
	// attributes
	private String name;

	// attribute setters and getters
	public String getName() { return name; }
	public void setName(String name) { this.name = name;}

	// required ports
	private java.util.List<uk.man.atm.IAccount> accounts = new java.util.ArrayList<uk.man.atm.IAccount>();

	// port setters and getters
	public void setAccount(uk.man.atm.IAccount accounts, int index) { PortHelper.fill(this.accounts, accounts, index); }
	public void addAccount(uk.man.atm.IAccount accounts) { PortHelper.fill(this.accounts, accounts, -1); }
	public void removeAccount(uk.man.atm.IAccount accounts) { PortHelper.remove(this.accounts, accounts); }

// end generated code
	
	@Override
	public void withdraw(int accNo, int amount) throws Exception {
		Account acc = retrieveAccount(accNo);
		acc.setBalance(acc.getBalance() - amount);	
	}
	@Override
	public void deposit(int accNo, int amount) throws Exception {
		Account acc = retrieveAccount(accNo);
		acc.setBalance(acc.getBalance() + amount);			
	}
	@Override
	public int balance(int accNo) throws Exception {
		Account acc = retrieveAccount(accNo);
		return acc.getBalance();
	}
	
	
	 /**
     * Retrieve the account associated to the provided account number
     * 
     * @param accno account number
     * @return Account
     * @throws Exception 
     */
    private Account retrieveAccount (int accno ) throws Exception {
        Iterator<IAccount> i = accounts.iterator();
        while ( i.hasNext() ) {
            Account acc = (Account) i .next();
            if ( acc.getNumber() == accno)
                return acc;
        }
        throw new Exception ( "Account not found." );
    }
}
