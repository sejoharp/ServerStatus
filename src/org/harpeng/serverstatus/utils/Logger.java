package org.harpeng.serverstatus.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	private String logFileName;

	public Logger(String logFileName) {
		this.logFileName = logFileName;
	}

	private String getCurrentDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	private String getCurrentTime() {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date());
	}

	private void writeLogToFile(String msg) {
		try {
			BufferedWriter file = new BufferedWriter(new FileWriter(
					this.logFileName + "_" + this.getCurrentDate() + ".txt",
					true));
			file.write(msg);
			file.newLine();
			file.flush();
			file.close();
		} catch (IOException e) {

		}
	}

	public void addInfo(String msg) {
		String currentTime = this.getCurrentTime() + " Uhr:  ";
		System.out.println(currentTime + msg);
		this.writeLogToFile(currentTime + msg);
	}
}