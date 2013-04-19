

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JLabel;

public class SayHelloApplet extends JApplet {
	
	private static final long serialVersionUID = 7042726180843352896L;

	public SayHelloApplet() {
	}
	
	public void init() {
		JLabel lblNewLabel = new JLabel("Take me");
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);
	}

}
