package uk.man.atm;

import java.util.Random;

public class CardReader
// start generated code
	// main port
 implements uk.man.atm.ICardReader
{
// end generated code

	public int readAccountNo() {
		int[] accno = { 1111, 2222};
		Random r = new Random ();
		return accno[ r.nextInt(2) ];
	}
}
