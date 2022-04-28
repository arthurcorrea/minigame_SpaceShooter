package com.gcstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.main.Sound;

public class Enemy extends Entity {

	public boolean right = true, left = false;
	
	public double vida = 1;	
		
	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
				//this.sprite1 = sprite1;
				//this.sprite2 = sprite2;
				
			
	}

	public void atualizar() {
		y+=speed;
		if(y >= Game.HEIGHT) {
			Sound.perdervidaEffect.play();
			Game.entities.remove(this);
			Game.life--;
			return;
		}
		
		for(int i = 0; i < Game.entities.size(); i ++){
			Entity e = Game.entities.get(i);
			if(e instanceof Bullet) {
				if(Entity.isColidding(this, e)) {
					Sound.destroyEffect.play();
					Game.entities.remove(e);
					vida--;
					if(vida == 0) {
						Explosion explosion = new Explosion(x, y, 16, 16, 0, null);
						Game.entities.add(explosion);
						Game.score+=10;
						Game.entities.remove(this);							
						return;
					}
						break;
				}
			}
		}
		
}
	

	
	
}
