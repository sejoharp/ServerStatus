package org.harpeng.serverstatus.client;

public interface IGui {

	public abstract void setServerState(boolean isOnline);

	public void showWakeUpState(boolean available);
}