package me.shemplo.colorspicker.frames;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;

import me.shemplo.colorspicker.ApplicationFrame;

public class MainFrame extends ControlledFrame {
	
	private static final long serialVersionUID = 9055697476741727859L;
	
	private Graphics g;
	private Rectangle rect;
	private BufferStrategy bufferStratagy;
	
	public MainFrame (ApplicationFrame parent, Rectangle rect) {
		super (parent);
		
		this.rect = rect;
	}
	
	public void run () {
		requestFocus ();
		
		long lTime = System.nanoTime ();
		long timer = System.currentTimeMillis ();
		
		@SuppressWarnings("unused") int ups = 0, fps = 0;
		double delt = 0, nanoSecs = 1_000_000_000.0 / 60.0;
		
		while (run) {
			long cTime = System.nanoTime ();
			delt += (cTime - lTime) / nanoSecs;
			lTime = cTime;
			
			while (delt >= 1) {
				update ();
				
				ups ++;
				delt --;
			}
			
			render ();
			
			fps ++;
			if (System.currentTimeMillis () - timer >= 1000) {
				timer += 1000;
				ups = fps = 0;
			}
		}
	}
	
	public void update () {
		
	}
	
	public void render () {
		bufferStratagy = getBufferStrategy ();
		if (bufferStratagy == null) {
			createBufferStrategy (3);
			return;
		}
		
		g = bufferStratagy.getDrawGraphics ();
		g.clearRect (rect.x, rect.y, rect.width, rect.height);
		//////////////////////////////////////////////////////
		
		g.setColor (Color.BLACK);
		g.fillRect (rect.x, rect.y, rect.width, rect.height);
		
		///////////////////////
		g.dispose           ();
		bufferStratagy.show ();
	}
	
	public void pause () { System.err.println ("Not implemented!"); }
	
}
