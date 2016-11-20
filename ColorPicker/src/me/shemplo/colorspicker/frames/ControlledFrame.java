package me.shemplo.colorspicker.frames;

import java.awt.Canvas;

import me.shemplo.colorspicker.ApplicationFrame;

public abstract class ControlledFrame extends Canvas implements Runnable {

	//////////////////////////////////////////////////////////////////
	private static final long serialVersionUID = 1758318684803461192L;
	//////////////////////////////////////////////////////////////////
	
	protected Thread thread;
	protected boolean run = false;
	protected ApplicationFrame parent;
	
	public ControlledFrame (ApplicationFrame parent) {
		this.parent = parent;
	}

	public void start () {
		run = true;
		
		thread = new Thread (this, this.getClass ().getSimpleName ());
		thread.start ();
	}
	
	public void stop () {
		run = false;
		
		try   { thread.join (); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}
	
	public abstract void pause ();
	
	public abstract void run   ();
	
}
