package me.shemplo.colorspicker;

import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JFrame;

import me.shemplo.colorspicker.frames.ControlledFrame;
import me.shemplo.colorspicker.frames.MainFrame;

public class ApplicationFrame {

	private Rectangle rect;
	private int width, height;
	private int offsetTop, offsetLeft;
	
	private JFrame screen;
	private ControlledFrame current;
	
	public ApplicationFrame (int width, int height, String title) {
		screen = new JFrame (title);
		
		screen.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		screen.setBounds (100, 100, width, height);
		screen.setResizable (false);
		screen.setLayout (null);
		
		rect = _initGraphics (width, height);
		switchFrame (new MainFrame (this, rect));
		
		screen.setVisible (true);
	}
	
	private Rectangle _initGraphics (int width, int height) {
		Insets insets   = screen.getInsets ();
		this.offsetTop  = insets.top;
		this.offsetLeft = insets.left;
		
		this.width  = width - insets.left - insets.right;
		this.height = height - insets.top - insets.bottom;
		
		return new Rectangle (offsetLeft, offsetTop, this.width, this.height);
	}
	
	public synchronized void switchFrame (ControlledFrame drawable) {
		if (current != null) { 
			current.stop   ();
			screen .remove (current);
		}
		
		drawable.setBounds (rect);
		screen.getContentPane ().add (drawable);
		screen.setVisible (true);

		drawable.start ();
	}
	
}
