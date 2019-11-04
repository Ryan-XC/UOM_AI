package uk.man.atm;

public interface IBankServices
// start generated code
{
// end generated code
	//@todo add in the methods
	
	public void withdraw ( int accNo, int amount ) throws Exception;
	public void deposit (int accNo, int amount ) throws Exception;
	public int balance (int accNo) throws Exception;
}
