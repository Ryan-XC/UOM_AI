package javaatm;

/**
 * Created by Martin on 02/02/2016.
 */
public class atmSimulator {

    public static void main(String[] args) throws Exception {
        Bank bank = new Bank();
        JavaFrame frame = new JavaFrame();
        new JavaATM(bank, frame);
    }
}
