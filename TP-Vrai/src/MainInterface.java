import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;


public class MainInterface {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainInterface window = new MainInterface();
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
	public MainInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblLelu = new JLabel("Lelu");
		frame.getContentPane().add(lblLelu, BorderLayout.NORTH);
		
		JButton btnLel = new JButton("Lel");
		btnLel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblLelu.setText("Lelu intesifies");
			}
		});
		frame.getContentPane().add(btnLel, BorderLayout.CENTER);
	}

}
