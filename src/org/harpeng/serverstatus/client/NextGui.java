package org.harpeng.serverstatus.client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class NextGui extends javax.swing.JFrame implements IGui {
	private javax.swing.JButton jBAbort;
	private javax.swing.JButton jBDo;
	private javax.swing.JLabel jLFreeSpace;
	private javax.swing.JLabel jLFreeSpaceLabel;
	private javax.swing.JLabel jLStateLabel;
	private javax.swing.JLabel jLState;
	private RichClientLogic logic;
	private boolean isOnline = false;

	/** Creates new form StatusGui */
	public NextGui(RichClientLogic logic) {
		this.logic = logic;
		this.initComponents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.harpeng.serverstatus.client.IGui#setServerState(java.lang.String)
	 */
	public void setServerState(boolean isOnline) {
		this.setVisible(true);
		if (isOnline == true) {
			this.jLState.setText("server is online.");
			this.jBDo.setText("shutdown server");
			this.jLFreeSpace.setText(String.valueOf(this.logic
					.getFreeSpaceFromServer()
					/ (1024 * 1024 * 1024))
					+ " GB");
			this.isOnline = true;
		} else {
			this.jLState.setText("server is offline.");
			this.jBDo.setText("wakeup server");
			this.isOnline = false;
		}
	}

	public void showWakeUpState(boolean available) {
		if (available == false)
			JOptionPane.showMessageDialog(new JFrame(), "wake up failed.",
					"error", JOptionPane.ERROR_MESSAGE);
		else
			JOptionPane.showMessageDialog(new JFrame(), "server is online.",
					"Info", JOptionPane.INFORMATION_MESSAGE);
	}

	private void initComponents() {

		jBDo = new javax.swing.JButton();
		jBAbort = new javax.swing.JButton();
		jLFreeSpaceLabel = new javax.swing.JLabel();
		jLFreeSpace = new javax.swing.JLabel();
		jLStateLabel = new javax.swing.JLabel();
		jLState = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jBDo.setText("wake up server");
		jBDo.addActionListener(new DoActionListener());
		jBAbort.setText("abort");
		jBAbort.addActionListener(new AbortActionListener());
		jLFreeSpaceLabel.setText("free space:");
		jLFreeSpace.setText("unknown");
		jLStateLabel.setText("state:");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGap(
																				40,
																				40,
																				40)
																		.addComponent(
																				jBDo)
																		.addGap(
																				18,
																				18,
																				18)
																		.addComponent(
																				jBAbort))
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLStateLabel)
																						.addComponent(
																								jLFreeSpaceLabel))
																		.addGap(
																				41,
																				41,
																				41)
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addComponent(
																								jLFreeSpace)
																						.addComponent(
																								jLState))))
										.addGap(39, 39, 39)));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addGap(20, 20, 20)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jLStateLabel)
														.addComponent(jLState))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																layout
																		.createSequentialGroup()
																		.addGap(
																				41,
																				41,
																				41)
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.BASELINE)
																						.addComponent(
																								jBAbort,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								33,
																								javax.swing.GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								jBDo,
																								javax.swing.GroupLayout.PREFERRED_SIZE,
																								34,
																								javax.swing.GroupLayout.PREFERRED_SIZE)))
														.addGroup(
																layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jLFreeSpaceLabel)
																		.addComponent(
																				jLFreeSpace)))
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		this.setTitle("ServerStatus");
		pack();
		// sets the window to the center of the screen.
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int width = this.getWidth();
		int height = this.getHeight();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);

	}

	public class DoActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (isOnline == true) {
				logic.shutdownServer();
			} else {
				setVisible(false);
				logic.wakeup();
			}
		}
	}

	public class AbortActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			logic.closeProgram();
		}
	}
}
