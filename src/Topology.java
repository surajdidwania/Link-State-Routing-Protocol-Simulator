/*****************************************************************************************
*Class Name: Topology
*
*Dependencies: Graph.java Createtopology.java
*
*			
*
*Description: Implementaion of the menu to be asked to user given in Java Swings GUI. 
*				It asks the user to select between Create topology, Build the connection table,
*				Shortest Path to Destination Router, Modify a Topology,Best Router for Broadcast and Exit
*
*	
*
*Author: Suraj Kumar Didwania <dsuraj@hawk.iit.edu> Seq No: 22
*
*************************************************************************************************/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class Topology extends JFrame {
	JFrame jf;
	createtopology ct ;
	JTextField jinput;
	int counter=0;
	JRadioButton create = new JRadioButton("1). Create a Network Topology.");
	JRadioButton build = new JRadioButton("2). Build a Connection Table.");
	JRadioButton shortest = new JRadioButton("3). Shortest Path to Destination Router.");
	JRadioButton modify = new JRadioButton("4). Modify a Topology.");
	JRadioButton route = new JRadioButton("5). Best Router for Broadcast.");
	JRadioButton exit = new JRadioButton("6). Exit.");
	JButton jbSubmit = new JButton("Submit");
	
	//Constructor for the printing all the inputs user should give
	public Topology()
	{
		setTitle("CS542 Link State Routing Simulator");
		JLabel j1 = new JLabel("CS542 Link State Routing Simulator.");
		 ButtonGroup group = new ButtonGroup();
		    group.add(create);
		    group.add(build);
		    group.add(shortest);;
		    group.add(modify);
		    group.add(route);
		    group.add(exit);
		add(j1);
		
		create.setBounds(50,50,400,30);
		build.setBounds(50, 80, 400,30);
		shortest.setBounds(50,110,400,30);
		modify.setBounds(50,140,400, 30);
		route.setBounds(50,170,400, 30);
		exit.setBounds(50, 200, 400, 30);
		add(create);
		add(build);
		add(shortest);
		add(modify);
		add(route);
		add(exit);
		add(jbSubmit);
		ActionListener sliceActionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		        AbstractButton aButton = (AbstractButton) actionEvent.getSource();
		        if(aButton == create)
		        {
		        	ct = new createtopology();
		        	ct.showingoutput();
		        	counter=1;
		        		    	    	
		        }
		        else if(aButton == build)
		        {
		        	if(counter!=1)
		        	{
		        		JOptionPane.showMessageDialog(null, "Please Create the Topology!!","Warning",JOptionPane.WARNING_MESSAGE);
		        	}
		        	else
		        	{
		        	try {
						ct.ConnectionTable();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	counter=2;
		        	}
		        }
		        else if(aButton == shortest)
		        {
		        	if(counter!=2)
		        		JOptionPane.showMessageDialog(null, "Please Select the Source router first!!","Warning",JOptionPane.WARNING_MESSAGE);
		        	else
		        	{
		        	ct.shortestpath();
		        	counter=3;
		        	}
		        }
		        else if(aButton == modify)
		        {
		        	if(counter==0)
		        	{
		        		JOptionPane.showMessageDialog(null, "Please Create the Topology First!!","Warning",JOptionPane.WARNING_MESSAGE);
		        	}
		        	else
		        	{
		        	try {
						ct.modify();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	}
		        }
		        else if(aButton == route)
		        {
		        	if(counter==0)
		        	{
		        		JOptionPane.showMessageDialog(null, "Please Create the Topology First!!","Warning",JOptionPane.WARNING_MESSAGE);
		        	}
		        	else
		        	{
		        	try {
						ct.bestrouter();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	}
		        }
		        else 
		        {
		        	JOptionPane.showMessageDialog(null, "Exit CS542-04 2016 Fall project. Good Bye!!");
		        	dispose();
		        }
		      }
		    };
		create.addActionListener(sliceActionListener);
		build.addActionListener(sliceActionListener);
		shortest.addActionListener(sliceActionListener);
		modify.addActionListener(sliceActionListener);
		route.addActionListener(sliceActionListener);
		exit.addActionListener(sliceActionListener);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setLocation(200, 200);
	    setSize(400, 400);
	    setVisible(true);
	}
	
	public static void main(String[] args) {
		new Topology();
	}
}
	