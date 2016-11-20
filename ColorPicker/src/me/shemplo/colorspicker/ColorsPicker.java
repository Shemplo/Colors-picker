package me.shemplo.colorspicker;

import me.shemplo.handlers.ColorHandler;

public class ColorsPicker {

	public static void main (String [] args) {
		ColorHandler ch = new ColorHandler ();
		ch.offset = Integer.parseInt (args.length > 0 ? args [0] : "0");
		
		Thread t = new Thread (ch, "Handler");
		t.start ();
	}
	
}
