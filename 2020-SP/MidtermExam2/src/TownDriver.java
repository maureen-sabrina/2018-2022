/**
 * CSCI-C 212 Midterm Exam 2 Part 2
 * @author Maureen Lynch
 * version 0.01 04-01-2020
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import java.awt.Rectangle;

public class TownDriver extends JPanel implements ActionListener{

	//Person[] people = new Person[20];
	Person[] people;
	int width 		= 800;
	int height 		= 600;
	int linearSpeed = 5;
	int shortSpeed  = 3;
	int longSpeed   = 4;
	
	private int numRecovered = 0;
	private int numHealthy;
	private int numSick = 1;
	
	Color sick = Color.GREEN;
	Color healthy = Color.BLUE;
	Color recovered = Color.MAGENTA;
	
	public TownDriver(int population) {
        super(); 
        JPanel background = new JPanel();
        people = new Person[population];
        for(int i=0; i<people.length; i++) {
    		people[i] = new Person();
    	}
        people[0].setColor(sick);
        numHealthy = people.length-1;
        
    }
	
	@Override
    public void paintComponent(Graphics g) {
        // calling super class paintComponent method
        // background will not be colored otherwise
        super.paintComponent(g);
        g.drawString("Recovered (pink): " + this.numRecovered, 50, 25);
        g.drawString("Healthy (blue):       " + this.numHealthy, 50, 50);
        g.drawString("Sick (green):          " + this.numSick, 50, 75);
        g.drawLine(0, 100, 900, 100);
        for(Person p: people) {
        	p.drawPerson(g);
        }
      }
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// move the people here and change them when actions happen
		for(int i=0; i<people.length; i++) {
			if(people[i].getDirection() == 0) {
				people[i].setX(people[i].getX() + linearSpeed);
			}
			else if(people[i].getDirection() == 1) {
				people[i].setX(people[i].getX() + longSpeed);
				people[i].setY(people[i].getY() + shortSpeed);
			}
			else if(people[i].getDirection() == 2) {
				people[i].setY(people[i].getY() + linearSpeed);
			}
			else if(people[i].getDirection() == 3) {
				people[i].setX(people[i].getX() - longSpeed);
				people[i].setY(people[i].getY() + shortSpeed);
			}
			else if(people[i].getDirection() == 4) {
				people[i].setX(people[i].getX() - linearSpeed);
			}
			else if(people[i].getDirection() == 5) {
				people[i].setX(people[i].getX() - longSpeed);
				people[i].setY(people[i].getY() - shortSpeed);
			}
			else if(people[i].getDirection() == 6) {
				people[i].setY(people[i].getY() - linearSpeed);
			}
			else if(people[i].getDirection() == 7) {
				people[i].setX(people[i].getX() + longSpeed);
				people[i].setY(people[i].getY() - shortSpeed);
			}
		}
		
		for(int i=0; i<people.length; i++) {
			if(people[i].getX() >= width-30
					|| people[i].getX() <= 5
					|| people[i].getY() >= height-50
					|| people[i].getY() <= 100) 
			{
				int newDirection = (people[i].getDirection()+3);
				if(newDirection > 7) 
				{
					newDirection = newDirection-8;
				}
				people[i].setDirection(newDirection);
			}
		}
		
		Person[] peopleCopy = new Person[people.length];
		for(int p=0; p<peopleCopy.length; p++) 
		{
			peopleCopy[p] = new Person();
			peopleCopy[p].setX(people[p].getX());
			peopleCopy[p].setY(people[p].getY());
			peopleCopy[p].setDirection(people[p].getDirection());
			peopleCopy[p].setColor(people[p].getColor());
			peopleCopy[p].setTimeSick(people[p].getTimeSick());
			
		}
		
		for(int i=0; i<peopleCopy.length; i++) 
		{
			for(int j=0; j<peopleCopy.length; j++) 
			{
				if(Math.sqrt(Math.pow(peopleCopy[i].getX() - peopleCopy[j].getX(), 2)
								+ Math.pow(peopleCopy[i].getY() - peopleCopy[j].getY(), 2))
						<= 10
						&& peopleCopy[i] != peopleCopy[j]) 
					{
						int d1 = peopleCopy[i].getDirection() + 4;
						int d2 = peopleCopy[j].getDirection() + 4;
						if(d1 > 7) {d1 = d1 - 8;}
						if(d2 > 7) {d2 = d2 - 8;}
						people[i].setDirection(d1);
						people[j].setDirection(d2);
						if(peopleCopy[i].getColor() == sick && peopleCopy[j].getColor() != recovered)
						{
							people[j].setColor(sick);
						}
					}
			}
			if(peopleCopy[i].getColor() == sick) {
				people[i].setTimeSick(people[i].getTimeSick() + 1);
			}
			if (peopleCopy[i].getTimeSick() > 100) {
				people[i].setColor(recovered);
			}
		}
		
		int currentHealthy=0;
		int currentSick=0;
		int currentRecovered=0;
		for(Person p: people) {
			if(p.getColor() == healthy) {currentHealthy++;}
			if(p.getColor() == sick) {currentSick++;}
			if(p.getColor() == recovered) {currentRecovered++;}
		}
		numHealthy	 = currentHealthy;
		numSick	   	 = currentSick;
		numRecovered = currentRecovered;
		
		this.repaint();
	}
	
	

}










