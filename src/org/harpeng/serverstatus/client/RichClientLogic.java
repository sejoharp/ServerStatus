package org.harpeng.serverstatus.client;

public class RichClientLogic extends CommonLogicSocket {
	public RichClientLogic() {
		this.setModel(new Model());
		this.setGui(new NextGui(this));
		this.start();
	}

	public void closeProgram() {
		System.exit(0);
	}

	public void wakeup() {
		if (this.wakeUpServer() == false)
			this.getGui().showWakeUpState(false);
		else
			this.getGui().showWakeUpState(true);
		this.closeProgram();
	}

}
