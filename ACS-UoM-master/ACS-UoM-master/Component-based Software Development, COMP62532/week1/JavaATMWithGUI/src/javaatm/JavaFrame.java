package javaatm;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Martin on 01/02/2016.
 */
public class JavaFrame extends JFrame {

    public JTextArea usrInput;
    public JButton cmdBalance;
    public JButton cmdWithdraw;
    public JButton cmdDeposit;
    public JLabel acc;
    public JLabel balance;


    public JavaFrame() {
        super("JavaATM");
        this.init();
        this.setSize(400, 150);
        this.setVisible(true);
    }

    public void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout grid = new GridBagLayout();
        this.acc = new JLabel("Account: ");
        grid.setConstraints(acc, new GridBagConstraints(5, 1, 4, 1,
                0.4, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2), 0, 0));
        this.balance = new JLabel("Current Balance: ");
        grid.setConstraints(balance, new GridBagConstraints(10, 1, 4, 1,
                0.4, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2), 0, 0));
        this.cmdBalance = new JButton("Balance");
        grid.setConstraints(cmdBalance, new GridBagConstraints(5, 3, 4, 1,
                0.4, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2), 0, 0));
        this.cmdWithdraw = new JButton("Withdraw");
        grid.setConstraints(cmdWithdraw, new GridBagConstraints(10, 3, 4, 1,
                0.25, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2), 0, 0));
        this.cmdDeposit = new JButton("Deposit");
        grid.setConstraints(cmdDeposit, new GridBagConstraints(15, 3, 4, 1,
                0.25, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2), 0, 0));
        this.usrInput = new JTextArea();
        grid.setConstraints(usrInput, new GridBagConstraints(5, 10, 5, 1,
                0.6, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(2, 2, 2, 2), 0, 0));
        this.setLayout(grid);
        this.add(cmdBalance);
        this.add(cmdWithdraw);
        this.add(cmdDeposit);
        this.add(usrInput);
        this.add(acc);
        this.add(balance);
    }
}
