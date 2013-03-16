package game;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class ExitAction implements WindowListener {
	
	private JFrame frame;
	private GoGUI gui;
	
	public ExitAction(JFrame frame, GoGUI gui) {
		
		this.frame = frame;
		this.gui = gui;
		
	}
	
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {

		System.exit(0);
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		
		if (gui.canClose()) {
			frame.dispose();
		}
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
