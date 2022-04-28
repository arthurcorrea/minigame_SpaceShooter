package com.gcstudios.graficos;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.entities.Entity;
import com.gcstudios.main.Game;



public class UI {

	public static BufferedImage LIFE = Game.spritesheet.getSprite(16, 0, 16, 16);
	
	public static int seconds = 0;
	public static int minutes = 0;
	public static int frames = 0;

     public void atualizar() {
		
}
	
	public void render(Graphics g) {      
	        
		for(int i = 0; i < (int)(Game.life); i++) {
			g.drawImage(LIFE,240 + (i*25), -50, 80, 90, null );
		}
	        String formatTime = "";
	        if(minutes < 10) {
	        	formatTime += "0" +minutes+":" ;       	
	        }else {
	        	formatTime +=minutes;       
	        }
	        if(seconds < 10) {
	        	formatTime += "0" +seconds;       	
	        }else {
	        	formatTime +=seconds;       
	        }
	        
	        g.setColor(Color.WHITE);
	        g.setFont(new Font("arial", Font.BOLD,20));
	        g.drawString("Score", 14, 24);
	        g.setFont(new Font("arial", Font.BOLD, 18));
	        g.drawString(""+Game.score, 35, 50);
	        
	}
	
}
