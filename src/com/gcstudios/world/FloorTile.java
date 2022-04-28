package com.gcstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class FloorTile extends Tile{

	public FloorTile(int x, int y, BufferedImage sprite) {
		super(x, y, sprite);
	}

	public void renderizar(Graphics g) {
		if(World.CICLO == 1) {
			g.drawImage(Tile.TILE_AR, x - Camera.x, y - Camera.y, null);
		}else if(World.CICLO == 0) {
			g.drawImage(Tile.TILE_NOITE, x - Camera.x, y - Camera.y, null);
		}
		
	}
	
}
