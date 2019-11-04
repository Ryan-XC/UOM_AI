/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javaatm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * @author Cuong Tran
 */
public class JavaATM {

    private JavaFrame frame;
    private Bank bank;
    public String accno;
    /**
     * The class simulate the card reader device in ATM machine
     */
    private class CardReader {

        /**
         * Simulate card reading operation.
         *
         * @return an account number
         */
        public String readAccountNo() {
            String[] accno = {"1111", "2222"};
            Random r = new Random();
            return accno[r.nextInt(2)];
        }
    }

    private class balanceAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frame.acc.setText("Account: " + accno);
            try {
                frame.balance.setText("Current Balance: " + bank.balance(accno).toString());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    private class withdrawAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frame.acc.setText("Account: " + accno);
            String input = frame.usrInput.getText();
            try {
                int value = Integer.parseInt(input);
                bank.withdraw(accno, value);
                frame.balance.setText("Current Balance: " + bank.balance(accno).toString());
            } catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }

    private class depositAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            frame.acc.setText("Account: " + accno);
            String input = frame.usrInput.getText();
            try {
                int value = Integer.parseInt(input);
                bank.deposit(accno, value);
                frame.balance.setText("Current Balance: " + bank.balance(accno).toString());
            } catch (Exception e1){
                e1.printStackTrace();
            }
        }
    }
    public JavaATM(Bank bank, JavaFrame frame) throws Exception {
        this.bank = bank;
        this.frame = frame;

        CardReader reader = this.new CardReader();
        this.accno = reader.readAccountNo();
        frame.cmdBalance.addActionListener(new balanceAction());
        frame.cmdDeposit.addActionListener(new depositAction());
        frame.cmdWithdraw.addActionListener(new withdrawAction());
    }
}
