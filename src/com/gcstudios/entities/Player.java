package com.gcstudios.entities;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.main.Sound;
import com.gcstudios.world.World;


public class Player extends Entity{

	public boolean right, left;
	
	public boolean atirando = false;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);				
		
		
	}
	
	public void atualizar(){		
		
		if(Game.life == 0) {
			World.restartGame();
		}
		
		depth = 2;	
		if(right) {
			x+=speed;
		}else if(left) {
			x-=speed;
		}
		
		//códigos para "atravessar a tela"
		if(x >= Game.WIDTH) {
			x = -16;
		}else if(x+16 < 0) {
			x = Game.WIDTH;
		}
		
		if(atirando) {
			Sound.shootEffect.play();
			atirando = false;
			int xx = this.getX() + 4;
			int yy = this.getY();
			Bullet bullet = new Bullet(xx, yy, 1, 3, 2 , null);
			Game.entities.add(bullet);
		}
		
}
	
	
	
	

	

	
	
}
