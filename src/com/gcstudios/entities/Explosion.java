package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Explosion extends Entity{

	private int frames = 0;
	private int targetFrames = 4;
	private int maxAnimation =  1;
	private int curAnimation = 0;
	
	public BufferedImage[] explosionSprites = new BufferedImage[2];
	
	public Explosion(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	 
		explosionSprites[0] = Game.spritesheet.getSprite(0, 48, 16, 16);
		explosionSprites[1] = Game.spritesheet.getSprite(16, 48, 16, 16);
	}

	public void atualizar() {
		frames++;
		if(frames == targetFrames) {
			frames = 0;
			curAnimation++;
			if(curAnimation > maxAnimation) {
				Game.entities.remove(this);
				return;
			}
		}
	}
	
	public void renderizar(Graphics g) {
		g.drawImage(explosionSprites[curAnimation], this.getX(), this.getY(), null);
	}
	
}
