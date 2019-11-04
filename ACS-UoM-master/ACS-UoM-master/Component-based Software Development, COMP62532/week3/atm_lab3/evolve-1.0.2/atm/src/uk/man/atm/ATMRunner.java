package uk.man.atm;

import com.intrinsarc.backbone.runtime.api.*;

public class ATMRunner
// start generated code
	// main port
//end generated code

implements IRun
{
	// required ports
	private uk.man.atm.IAtmCli atmCli;

	// port setters and getters
	public void setAtmCli(uk.man.atm.IAtmCli atmCli) { this.atmCli = atmCli; }

	@Override
	public boolean run(String[] arg0) {
		atmCli.show();
		return false;
	}



}
