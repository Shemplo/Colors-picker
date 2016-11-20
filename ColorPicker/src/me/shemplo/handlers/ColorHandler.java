package me.shemplo.handlers;


public class ColorHandler implements Runnable {

	public int offset = 0;
	
	public void run () {
		while (true) {
			try { Thread.sleep (offset); }
			catch (Exception e) {}
		}
	}
	
}
