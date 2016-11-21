package me.shemplo.handlers;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class ColorHandler implements Runnable {

	public  Color color;
	private Color color2;
	public  BufferedImage image;
	private BufferedImage image2;
	
	private Robot robot;
	private Point point;
	private boolean work;
	private Rectangle shot;
	
	public ColorHandler (int width, int height) throws Exception {
		work = false;
		robot = new Robot ();
		point  = new Point (0, 0);
		shot    = new Rectangle (0, 0, width, height);
		
		color = Color.BLACK;
		image = new BufferedImage (width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	public synchronized boolean ready () {
		return !work;
	}
	
	public synchronized void setPoint (Point p) {
		if (!work) {
			this.point = p;
			work       = true;
		}
	}
	
	public void run () {
		while (true) {
			
			try                 { Thread.sleep (4); }
			catch (Exception e) {}
			
			if (work) {
				color2 = robot.getPixelColor (point.x, point.y);
				
				shot.x = point.x - shot.width / 2;
				shot.y = point.y - shot.height / 2;
				image2  = robot.createScreenCapture (shot);
				
				color = color2;
				image = image2;
				work  = false;
			}
			
		}
	}
	
}
