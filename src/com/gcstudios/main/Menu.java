package com.gcstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.gcstudios.main.Game;
import com.gcstudios.main.Sound;
import com.gcstudios.world.World;

public class Menu {
	
	public String[] options = {"Play","Exit"};
	
	public int currentOption = 0;
	public int maxOption = options.length - 1;
	
	public boolean up, down, enter;
	
	public static boolean pause = false;
	
	public static boolean saveExists = false;
	public static boolean saveGame = false;
				
	public void atualizar() {
		File file = new File("save.txt");
		if(file.exists()) {
			saveExists = true;
		}else {
			saveExists = false;
		}
		if(up) {
			up = false;
			currentOption--;
			if(currentOption < 0)
				currentOption = maxOption;
		}
		if(down) {
			down = false;
			currentOption++;
			if(currentOption > maxOption)
				currentOption = 0; 
			}
		if(enter) {
			//Sound.music.loop();
			enter = false;
			if(options[currentOption] == "Play" || options[currentOption] == "Continue") {
				Game.gameState = "Normal";			
				pause = false;
				file = new File("save.txt");
				file.delete();
			}else if(options [currentOption] == "Exit") {
				System.exit(1);
			}
		}
	}
     
	

	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0])
			{
			  case "level":
				 // World.restartGame("level"+spl2[1]+".png");
				  Game.gameState = "Normal";
				  pause = false;
				  break;
			  case "vida":
				  //Game.player.life = Integer.parseInt(spl2[1]);
				  break;
			}
		}
	}
	
	public static String LoadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {			
		 try {
			 String singleLine = null;
			 BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
		     try {
		    	 while((singleLine = reader.readLine()) != null) {
		    		 String[] trans = singleLine.split(":");
		    		 char[] valor = trans[1].toCharArray();
		    		 trans[1] = "";
		    		 for(int i = 0; i < valor.length; i++) {
		    			 valor[i]-=encode;
		    			 trans[1]+= valor[i];
		    		 }
		    		 line+=trans[0];
		    		 line+=":";
		    		 line+=trans[1];
		    		 line+="/";
		    	 }
		     }catch(IOException e) {}
		 }catch(FileNotFoundException e) {}
		}		
		return line;
	}
	
        public static void saveGame(String[] valor1, int[] valor2, int encode) {
        	BufferedWriter write = null;
        	try {
        		write = new BufferedWriter(new FileWriter("save.txt"));
        	}catch(IOException e) {
        		e.printStackTrace();
        	}
               for(int i = 0; i < valor1.length; i++) {
            	   String current = valor1[i];
            	   current+=":";
            	   char[] value = Integer.toString(valor2[i]).toCharArray();
                   for(int n = 0; n < value.length; n++) {
                	   value[n]+= encode;
                	   current += value[n];
                   }
                   try {
                	   write.write(current);
                	   if(i < valor1.length - 1)
                		   write.newLine();
                   }catch(IOException e) {}
               }
               try {
            	   write.flush();
            	   write.close();
               }catch(IOException e) {}
        }

	public void renderizar(Graphics g) {
		//Graphics2D g2 =  (Graphics2D) g;
		g.setColor(new Color(33,33,33));
		g.fillRect(0,0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.setColor(Color.WHITE);
		g.setFont(new Font("georgia", Font.BOLD, 36 ));
		g.drawString("DTWR", (Game.WIDTH*Game.SCALE) /2 - 60, (Game.HEIGHT*Game.SCALE)/2 - 180 );
		
	
		//Opções de menu
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.BOLD, 24 ));
		if(pause == false)
		    g.drawString("Play", (Game.WIDTH*Game.SCALE) /2 - 25, (Game.HEIGHT*Game.SCALE)/2 - -10 );
		else	
			g.drawString("Continue", (Game.WIDTH*Game.SCALE) /2 - 35, (Game.HEIGHT*Game.SCALE)/2 - -10 );		
		
		g.drawString("Exit", (Game.WIDTH*Game.SCALE) /2 - 25, (Game.HEIGHT*Game.SCALE)/2 - -60 );
	
	    if(options[currentOption] == "Play") {
	    	g.drawString(">", (Game.WIDTH*Game.SCALE) /2 - 50, (Game.HEIGHT*Game.SCALE)/2 - -10 );
	    }else if(options[currentOption] == "Exit") {
	    	g.drawString(">", (Game.WIDTH*Game.SCALE) /2 - 50, (Game.HEIGHT*Game.SCALE)/2 - -60 );
	    }
	}
	
}
