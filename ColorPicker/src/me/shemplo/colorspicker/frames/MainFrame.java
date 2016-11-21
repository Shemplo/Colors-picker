package me.shemplo.colorspicker.frames;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;

import me.shemplo.colorspicker.ApplicationFrame;
import me.shemplo.handlers.ColorHandler;

public class MainFrame extends ControlledFrame {
	
	private static final long serialVersionUID = 9055697476741727859L;
	
	private Graphics       g;
	private Rectangle      rect;
	private FrameCanvas    canvas;
	private BufferStrategy bufferStratagy;
	
	private Thread       handThread;
	private ColorHandler handler;
	
	public MainFrame (ApplicationFrame parent, Rectangle rect) {
		super (parent);
		setLayout (null);
		
		this.rect   = rect;
		this.canvas = new FrameCanvas ();
		this.canvas.setBounds (new Rectangle (0, 0, rect.width, rect.height));
		
		try                 { this.handler = new ColorHandler (8, 8); } 
		catch (Exception e) { /*                                   */ }
		
		this.thread  = new Thread (this.handler, "Handler");
		this.thread.start ();
		add (this.canvas);
	}
	
	public void stop () {
		run = false;
		
		try {
			handThread.join   ();
			thread.join       ();
		} catch (Exception e) {} 
	}
	
	public void run () {
		requestFocus ();
		
		long lTime = System.nanoTime ();
		long timer = System.currentTimeMillis ();
		
		@SuppressWarnings("unused") int ups = 0, fps = 0;
		double delt = 0, nanoSecs = 1_000_000_000.0 / 60.0;
		
		while (run) {
			try                 { Thread.sleep (4); }
			catch (Exception e) { /*             */ }
			
			long cTime = System.nanoTime ();
			delt += (cTime - lTime) / nanoSecs;
			lTime = cTime;
			
			while (delt >= 1) {
				update ();
				
				ups ++;
				delt --;
			}
			
			canvas.render ();
			
			fps ++;
			if (System.currentTimeMillis () - timer >= 1000) {
				timer += 1000;
				ups = fps = 0;
			}
		}
	}
	
	public void update () {
		if (handler.ready ()) { handler.setPoint (MouseInfo.getPointerInfo ().getLocation ()); }
	}
	
	public void pause () { System.err.println ("Not implemented!"); }
	
	private class FrameCanvas extends Canvas {
		
		private static final long serialVersionUID = -9011633957715993243L;

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
			g.drawRect (rect.width / 32, rect.width / 32, rect.width * 11 / 32 + 1, rect.width * 11 / 32 + 1);
			g.drawRect (rect.width * 13 / 32, rect.width / 32, rect.width * 17 / 32 + 1, rect.width * 16 / 32 + 1);
			
			g.drawString ("Red:   " + handler.color.getRed   (), rect.width / 32 + 2, rect.width * 14 / 32);
			g.drawString ("Green: " + handler.color.getGreen (), rect.width / 32 + 2, rect.width * 15 / 32 + 5);
			g.drawString ("Blue:  " + handler.color.getBlue  (), rect.width / 32 + 2, rect.width * 16 / 32 + 10);
			
			g.setColor (handler.color);
			g.fillRect (rect.width / 32 + 1, rect.width / 32 + 1, rect.width * 11 / 32, rect.width * 11 / 32);
			
			g.drawImage (handler.image,
							rect.width * 13 / 32 + 1, 
							rect.width / 32 + 1, 
							rect.width * 17 / 32, 
							rect.width * 16 / 32, 
							null);
			
			g.setColor (Color.BLACK);
			g.drawLine (rect.width * 21 / 32 + 14, rect.width / 32, rect.width * 21 / 32 + 14, rect.width * 17 / 32);
			g.drawLine (rect.width * 13 / 32, rect.width * 9 / 32 + 8, rect.width * 30 / 32, rect.width * 9 / 32 + 8);
			
			///////////////////////
			g.dispose           ();
			bufferStratagy.show ();
		}
		
	}
	
}
