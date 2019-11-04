/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbatm;

import javax.ejb.Remote;

/**
 *
 * @author mbaxrdy2
 */
@Remote
public interface BankRemote {
    public Integer balance(final String accno) throws Exception;
    public void deposit(final String accno, final Integer amount) throws Exception;
    public void withdraw(final String accno, final Integer amount) throws Exception;
}
