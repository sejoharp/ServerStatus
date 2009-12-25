package org.harpeng.serverstatus.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.harpeng.serverstatus.utils.LoggerSingleton;
import org.harpeng.serverstatus.utils.SocketConst;

public class SocketLogic {
	private Model model;

	public SocketLogic() {
		this.model = new Model();
		this.startServer();
	}

	private void startServer() {
		boolean running = true;
		try {
			ServerSocket serverSocket = new ServerSocket(Integer
					.valueOf(this.model.getPort()));
			LoggerSingleton.getInstance().addInfo("server online.");
			LoggerSingleton.getInstance().addInfo(
					"Listening on " + this.model.getAddress() + ":"
							+ this.model.getPort());
			while (running == true) {
				Socket clientSocket = serverSocket.accept();
				PrintWriter out = new PrintWriter(clientSocket
						.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));

				try {
					int command = Integer.valueOf(in.readLine());
					switch (command) {
					case SocketConst.SHUTDOWN:
						running = false;
						this.performShutdown();
						break;
					case SocketConst.FREESPACE:
						out.println(String.valueOf(this.getAvailableSpace()));
						out.flush();
						break;
					}
				} catch (NumberFormatException e) {
				}			
				clientSocket.close();
			}
			serverSocket.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void performShutdown() {
		LoggerSingleton.getInstance().addInfo("performing shutdown.");
		Runtime rt = Runtime.getRuntime();
		try {
			rt.exec("sudo shutdown -h now");
			System.out.println("shutdown in process.");
		} catch (IOException e) {
			e.printStackTrace();
			LoggerSingleton.getInstance().addInfo(e.getMessage());
		}
	}

	public long getAvailableSpace() {
		long ret = 0;
		LoggerSingleton.getInstance().addInfo("performing getAvailableSpace.");
		File backupDir = new File("/data");
		try {
			ret = backupDir.getUsableSpace();
		} catch (Exception e) {
			e.printStackTrace();
			LoggerSingleton.getInstance().addInfo(e.getMessage());
		}
		return ret;
	}
}
