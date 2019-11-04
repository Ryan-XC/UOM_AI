package uk.man.atm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UI
// start generated code
	// main port
 implements uk.man.atm.IAtmCli
{
	// required ports
	private uk.man.atm.IBankServices bank;
	private uk.man.atm.ICardReader reader;

	// port setters and getters
	public void setBank(uk.man.atm.IBankServices bank) { this.bank = bank; }
	public void setReader(uk.man.atm.ICardReader reader) { this.reader = reader; }

// end generated code

	@Override
	public void show() {
		// Read account number from a card reader by calling its provided
		// service
		String accno = reader.readAccountNo();
		// begin loop asking for service
		// and dispatch the selected service to Bank
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println("Account number: " + accno);
			System.out.println("Available services: ");
			System.out.println("  (B) or (b) Balance");
			System.out.println("  (W) or (w) Withdraw");
			System.out.println("  (D) or (d) Deposit");
			System.out.println("  (C) or (c) Cancel");
			System.out.print("Choice: ");
			try {
				String service = in.readLine();
				service = service.trim().toUpperCase();
				// Processing the service choice
				if (service.equals("B")) {
					// call a service of Bank to check balance and then print
					// the result
					System.out.println("Balance: " + bank.balance(accno));
				} else if (service.equals("D")) {
					System.out.print("Amount: ");
					String s = in.readLine();
					int val = Integer.parseInt(s);
					// call another service of Bank to perform deposit
					bank.deposit(accno, val);
					System.out.println("Deposit is complete.");
				} else if (service.equals("W")) {
					System.out.print("Amount: ");
					String s = in.readLine();
					int val = Integer.parseInt(s);
					// call another service Bank to perform withdraw
					bank.withdraw(accno, val);
					System.out.println("Withdraw is complete.");
				} else if (service.equals("C"))
					break;
				else {
					System.out.println("Unrecognised service.");
				}
			} catch (Exception ex) {
				Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		// end loop
	}
}
