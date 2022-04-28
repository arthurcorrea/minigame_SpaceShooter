package com.gcstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;

public class Tile {
	
	public static BufferedImage TILE_AR = Game.spritesheet.getSprite(0, 16, 16, 16);
	public static BufferedImage TILE_NOITE = Game.spritesheet.getSprite(0, 16, 16, 16);
	
	private BufferedImage sprite;
	protected int x;
	protected int y;
	
	public boolean solid = false;
	
	public Tile(int x,int y,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g){
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}

}
