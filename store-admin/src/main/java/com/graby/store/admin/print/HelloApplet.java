package com.graby.store.admin.print;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JLabel;

public class HelloApplet extends JApplet {
	
	private static final long serialVersionUID = 7042726180843352896L;

	public HelloApplet() {
		JLabel lblNewLabel = new JLabel("New label");
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);
	}

}
