/**
 * CSCI-C 212 
 * Solves Lab 8, Driver for the Shape Class
 * @author Maureen Lynch
 * version 0.01 03-05-2020
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Scanner;

public class TownWindow extends JFrame {
	
	JPanel townDriver;
	
	public TownWindow(int population) {
        super();
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        JPanel townDriver = new TownDriver(population);
        townDriver.setBackground(Color.WHITE);
        frame.add(townDriver);
        frame.setVisible(true);
        Timer timer = new Timer(75, (TownDriver)townDriver);
        timer.start();
        try {
            Thread.sleep(50000);  
        } 
        catch (InterruptedException e) {
        }
        timer.stop();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	 public static void main(String[] args) {
		 // Create a JFrame and invoke the constructor
		 Scanner in = new Scanner(System.in);
		 System.out.println("Please enter a population size (a whole number): ");
		 int population = in.nextInt(); 
		 in.close();
		 JFrame townWindow = new TownWindow(population);
	    }
	
}
