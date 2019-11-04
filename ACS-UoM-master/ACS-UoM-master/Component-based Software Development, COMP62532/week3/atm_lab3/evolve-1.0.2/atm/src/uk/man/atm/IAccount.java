package uk.man.atm;

public interface IAccount
// start generated code
{
// end generated code
	//@todo add in the methods

	public void setBalance (int val) throws Exception;
	public String getAccountNo ();
	public void setAccountNo (String val) throws Exception;
	public void withdraw (int val ) throws Exception;
    public void deposit (int val) throws Exception;
    public int balance ();   
}
