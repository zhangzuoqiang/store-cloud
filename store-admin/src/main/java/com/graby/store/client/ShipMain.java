package com.graby.store.client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;

import com.graby.store.client.swing.VFlowLayout;

public class ShipMain {

	private JFrame frame;
	private JTable table;
	/**
	 * @wbp.nonvisual location=87,-13
	 */
	private final JRadioButton radioButton = new JRadioButton("New radio button");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShipMain window = new ShipMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ShipMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 641, 542);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		// 顶部
		createTop();
		
		// 左边部分
		createLeft();
		
		table = new JTable();
		frame.getContentPane().add(table, BorderLayout.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		frame.getContentPane().add(lblNewLabel_1, BorderLayout.SOUTH);
		
		
	}

	private void createLeft() {
		JPanel panel = new JPanel();
		panel.setLayout(new VFlowLayout());
		ButtonGroup group = new ButtonGroup();
		JButton btnYUNDA = new JButton("韵达快递发往湖南");
		JButton btnOTY = new JButton("圆通快递发往广东省");
		group.add(btnYUNDA);
		group.add(btnOTY);
		panel.add(btnYUNDA);
		panel.add(btnOTY);
		frame.getContentPane().add(panel, BorderLayout.WEST);
	}

	private void createTop() {
		JLabel title = new JLabel("订单处理中心");
		frame.getContentPane().add(title, BorderLayout.NORTH);
	}

}
