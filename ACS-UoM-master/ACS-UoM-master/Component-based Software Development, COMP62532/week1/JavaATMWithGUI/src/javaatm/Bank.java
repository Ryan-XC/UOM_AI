/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaatm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Cuong Tran
 */
public class Bank {

    private List<Account> accounts = new ArrayList<Account>();

    public Bank() {
        try {
            // TODO: create two accounts (acc1, acc2) with account numbers 1111 and 2222
            // then set initial balance to them
            Account acc1 = new Account();
            Account acc2 = new Account();
            acc1.setAccountNo("1111");
            acc2.setAccountNo("2222");
            acc1.setBalance(11111);
            acc2.setBalance(22222);
            this.accounts.add(acc1);
            this.accounts.add(acc2);
        } catch (Exception ex) {
            Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void withdraw(String accno, Integer amount) throws Exception {
        // TODO: first retrieve the right account
        // then call the account to withdraw the specified amount
        Account ac = retrieve(accno);
        ac.withdraw(amount);
    }

    public void deposit(String accno, Integer amount) throws Exception {
        // TODO: first retrieve the right account
        // then call the account to deposit the specified amount
        Account ac = retrieve(accno);
        ac.deposit(amount);

    }

    public Integer balance(String accno) throws Exception {
        // TODO: first retrieve the right account
        // then call the account to check balance
        Integer res = 0;
        Account ac = retrieve(accno);
        res = ac.balance();
        return res;
    }

    /**
     * Retrieve the account associated to the provided account number
     *
     * @param accno account number
     * @return Account
     * @throws Exception
     */
    private Account retrieve(String accno) throws Exception {
        Iterator<Account> i = accounts.iterator();
        while (i.hasNext()) {
            Account acc = i.next();
            if (acc.getAccountNo().equals(accno))
                return acc;
        }
//        for (Account acc : accounts) {
//            if ( acc.getAccountNo().equals( accno ) )
//                return acc;
//        }
        throw new Exception("Account not found.");
    }

}
