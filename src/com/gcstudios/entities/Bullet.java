package com.gcstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Bullet extends Entity{

	public Bullet(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
	}

	public void atualizar() {
		y-=speed;
		if(y < 0){
			Game.entities.remove(this);
			return;
		}
	}
	
	public void renderizar(Graphics g) {
		g.setColor(new Color(127,0,0));
		g.fillRect(this.getX(), this.getY(), width, height);
	}
	
}
