/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbclient;

import ejbatm.BankRemote;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author Ken
 */
public class Client {
    @EJB
    private static BankRemote bank;

    /**
     * The class simulate the card reader device in ATM machine
     */
    private class CardReader {
        
        /**
         * Simulate card reading operation.
         * 
         * @return an account number
         */
        public String readAccountNo () {
            String[] accno = { "1111", "2222" };

            Random r = new Random ();

            return accno[ r.nextInt(2) ];
        }
    }
    
    public void run () {
        System.out.println ("ATM System Start");
        
        CardReader cardReader = new CardReader();
        String accno = cardReader.readAccountNo();
        
        // begin loop asking for service 
        // and dispatch the selected service to Bank
        BufferedReader in = new BufferedReader ( 
                    new InputStreamReader (System.in ) );
        while ( true ) {
            System.out.println ( "Account number: "  + accno);
            System.out.println ( "Available services: " );
            System.out.println ( "  (B) or (b) Balance" );
            System.out.println ( "  (W) or (w) Withdraw" );
            System.out.println ( "  (D) or (d) Deposit" );
            System.out.println ( "  (C) or (c) Cancel" );
            System.out.print   ( "Choice: " );

            try {
                String service = in.readLine();
                service = service.trim().toUpperCase();
                
                // Processing the service choice
                if ( service.equals( "B" ) ) {
                    //TODO: call Bank to check balance and print the balance
                    System.out.println ( "Balance: " + bank.balance ( accno ) );
                }
                else if ( service.equals ( "D" ) ) {
                    System.out.print ( "Amount: " );
                    String s = in.readLine();
                    Integer val = new Integer ( s );
                    
                    //TODO: call Bank to perform deposit
                    bank.deposit ( accno, val );
                    System.out.println ( "Deposit is complete." );
                }
                else if ( service.equals( "W" ) ) {
                    System.out.print ( "Amount: " );
                    String s = in.readLine();
                    Integer val = new Integer ( s );
                    
                    //TODO: call Bank to perform withdraw
                    bank.withdraw ( accno, val );
                    System.out.println ( "Withdraw is complete." );
                }
                else if ( service.equals ( "C" ) )
                    break;
                else {
                    System.out.println ( "Unrecognised service." );
                }

            } catch (Exception ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
        // end loop
        
        System.out.println ("ATM System Stop");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Client c = new Client();
        c.run();
    }
}
