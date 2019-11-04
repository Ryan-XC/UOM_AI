package uk.man.atm;

public interface IBankServices
// start generated code
{
// end generated code
	//@todo add in the methods
	
	public void withdraw ( String accno, int amount ) throws Exception;
	public void deposit ( String accno, int amount ) throws Exception;
	public int balance ( String accno ) throws Exception;
}
