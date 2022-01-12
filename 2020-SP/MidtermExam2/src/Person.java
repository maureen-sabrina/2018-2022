/**
 * CSCI-C 212 Midterm Exam 2 Part 2
 * @author Maureen Lynch
 * version 0.01 04-01-2020
 */

import java.awt.Color;
import java.awt.Point;
import java.util.Random;
import java.awt.Graphics;

public class Person {
	
	private final int radius = 10;
	private Color color = Color.blue;
	private int timeSick = 0;
	private int x;
	private int y;
	private int direction;
	private int id = 0;
	
	public Person() {
		Random random = new Random();
		this.x = random.nextInt(775);
		this.y = random.nextInt(450) + 100;
		this.direction = random.nextInt(8);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getTimeSick() {
		return this.timeSick;
	}
	
	public void setTimeSick(int t) {
		this.timeSick = t;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	void drawPerson(Graphics g) {
		g.setColor(this.getColor());
		g.fillOval(x, y, radius, radius);	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
