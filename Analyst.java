	import java.awt.*;
	import java.awt.event.*;
	import java.io.*;
	import java.security.SecureRandom;
	

   /*
    * Analyst.java 10.0 07 Jan 2016
    *
    * Copyright (c) School of Geography.
    * University of Leeds, Leeds, West Yorkshire, YK.LS2 9JT
    * All rights reserved.
    *
    * This code is provided under the Academic Free License v.3.0
    * For details, please see the http://opensource.org/licenses/AFL-3.0
    */


    /**
    * This class analyses geographical data.
    * It contains a class constructor and .
    * @author Dennis Macharia : dennismacharia59@yahoo.co.uk
    * @version 10.0 07 Jan 2016
    */


	public class Analyst extends Frame implements ActionListener{
	    Storage store = new Storage();
		InOut io = new InOut();
		Canvas canvas = new Canvas();
		double home = 0;
		double bar = 0;
		double [][] input = new double [300][300];
		
		/**
		 * Calls the Main class constructor
         */	
		public Analyst() {
			// Our Analyst code this practical will go here.
			setSize(300,300);
			MenuBar menuBar = new MenuBar();
			Menu fileMenu = new Menu("File");
			menuBar.add(fileMenu);
			MenuItem openMenuItem = new MenuItem("Open...");
			fileMenu.add(openMenuItem);
			MenuItem runMenuItem = new MenuItem("Run...");
			fileMenu.add(runMenuItem);
			MenuItem saveMenuItem = new MenuItem("Save...");
			fileMenu.add(saveMenuItem);
			setMenuBar(menuBar);
			openMenuItem.addActionListener(this);
			runMenuItem.addActionListener(this);
			saveMenuItem.addActionListener(this);
			
		    
			add(canvas);
			
			
			setVisible(true);
			
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
				((Frame)e.getSource()).dispose();
				// or
				System.exit(0);
			} 
			});
			addMouseListener(new MouseAdapter() { 
				public void mousePressed(MouseEvent me) { 
					System.out.println(me); 
				} 
				public void mouseClicked(MouseEvent ae) {
					System.out.println(ae);
				}
			
			}); 	
							
		}
		
		/**
		 *
		 */
		public void actionPerformed(ActionEvent e) {
			MenuItem clickedMenuItem = (MenuItem)e.getSource();
			if (clickedMenuItem.getLabel().equals("Open...")) { 
				FileDialog fd = new FileDialog(this, "Open File", FileDialog.LOAD); 
				fd.setVisible(true);
				File f = null;
				if((fd.getDirectory() != null)||( fd.getFile() != null)) {
					f = new File(fd.getDirectory() + fd.getFile());
					store.setData(io.readData(f));
				}
			}
			if (clickedMenuItem.getLabel().equals("Run...")){
				store.setData(runFile());	
			}
			if (clickedMenuItem.getLabel().equals("Save...")){
				io.writeData(store.getData());
			}
				repaint ();		
		}
		
		//public void actionPerformed(ActionEvent f)
		
		/**
		 *
		 *
		 */
		public double [][] runFile (){
			input = store.getData();
			double [][] density = new double[input.length][input[0].length];
				for (int i = 0; i < input.length; i++){
					for (int j = 0; j < input[i].length; j++){
						if (input[i][j] <= 25)
						home = input[i][j]*10;
						bar = 1;
						input[i][j] = bar;
						input[i][j] = home;
						long moves = 0;
						SecureRandom random = new SecureRandom();
							while(input[i][j] != home){
								switch (random.nextInt(4)){
								case 0 : i++;break;
								case 1 : i--;break;
								case 2 : j++;break;
								case 3 : j--;break;
								}
								if (i < 0) i = 0;
								if (j < 0) j = 0;
								if (i > input.length) i = input.length;
								if (j > input.length) j = input.length;
								moves++;
								
								// Add this to keep track of no moves through each square
                                 density [i][j] = moves;
							}	
					
					}
				}
		    return density;
		}
		
		/**
		 *
		 *
		 */
        public void paint (Graphics g){
			Image image = store.getDataAsImage();
			if (image != null){
			Graphics gc = canvas.getGraphics();
			//Resize Analyst here if we want
			gc.drawImage(image, 0, 0, this);
			int width = image.getWidth(this);
			int height = image.getHeight(this);
			setSize(getInsets().top+getInsets().bottom+width-40,getInsets().left+getInsets().right+height+40);
			}    
		}
		   
		// Main method.
        public static void main (String args[]) {
			new Analyst();
	    }	
			
			
	}
		
		
	