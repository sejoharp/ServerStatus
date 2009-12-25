package org.harpeng.serverstatus.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NoRouteToHostException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.harpeng.serverstatus.utils.SocketConst;

public abstract class CommonLogicSocket {
	private IGui gui;
	private IModel model;

	public void start() {
		this.check();
	}

	public void setGui(IGui gui) {
		this.gui = gui;
	}

	public IGui getGui() {
		return this.gui;
	}

	public void setModel(IModel model) {
		this.model = model;
	}

	public void shutdownServer() {
		this.sendShutdown();
		this.closeProgram();
	}

	private Socket getConnection() throws UnknownHostException, IOException {
		Socket socket = new Socket(this.model.getAddress(), Integer
				.valueOf(this.model.getPort()));
		return socket;
	}

	private void sendShutdown() {
		try {
			Socket socket = this.getConnection();

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(SocketConst.SHUTDOWN);
			out.flush();
			socket.close();
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
	}

	public long getFreeSpaceFromServer() {
		long freespace = 0;
		Socket socket;
		try {
			socket = this.getConnection();

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));
			out.println(SocketConst.FREESPACE);
			out.flush();
			freespace = Long.valueOf(in.readLine());
			socket.close();
		} catch (UnknownHostException e) {
		} catch (IOException e) {
		}
		return freespace;
	}

	public abstract void closeProgram();

	private void check() {
		if (this.isTargetOnline())
			this.gui.setServerState(true);
		else
			this.gui.setServerState(false);
	}

	private boolean isTargetOnline() {
		boolean ret = false;
		Socket socket = new Socket();
		try {
			socket.bind(null);
			socket.connect(new InetSocketAddress(this.model.getAddress(),
					Integer.valueOf(this.model.getPort())), 500);
			socket.close();
			ret = true;
		} catch (NumberFormatException e) {
		} catch (IOException e) {
		}
		return ret;
	}

	// Rückgabewert boolean mit status.
	public boolean wakeUpServer() {
		boolean available = false;
		int index;

		try {
			this.wakeUpPc(this.model.getMAC());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		index = 0;
		while (available == false
				&& index < Integer.valueOf(this.model.getRetries())) {
			if (this.isTargetOnline())
				available = true;
			else {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
				}
			}
			index++;
		}
		return available;
		// if (available == false)
		// this.gui.showWakeUpState(false);
		// else
		// this.gui.showWakeUpState(true);
		// this.closeProgram();
	}

	private void wakeUpPc(String macStr) throws UnknownHostException,
			SocketException, IOException {
		final int port = 9;
		String ipStr = "255.255.255.255";
		byte[] macBytes = getMacBytes(macStr);
		byte[] bytes = new byte[6 + 16 * macBytes.length];
		for (int i = 0; i < 6; i++) {
			bytes[i] = (byte) 0xff;
		}

		for (int i = 6; i < bytes.length; i += macBytes.length) {
			System.arraycopy(macBytes, 0, bytes, i, macBytes.length);
		}

		InetAddress address = InetAddress.getByName(ipStr);
		DatagramPacket packet = new DatagramPacket(bytes, bytes.length,
				address, port);
		DatagramSocket socket = new DatagramSocket();
		socket.send(packet);
		socket.close();
	}

	private static byte[] getMacBytes(String macStr)
			throws IllegalArgumentException {
		byte[] bytes = new byte[6];
		String[] hex = macStr.split("(\\:|\\-)");
		if (hex.length != 6) {
			throw new IllegalArgumentException("Invalid MAC address.");
		}

		try {
			for (int i = 0; i < 6; i++) {
				bytes[i] = (byte) Integer.parseInt(hex[i], 16);
			}

		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Invalid hex digit in MAC address.");
		}
		return bytes;
	}
}
