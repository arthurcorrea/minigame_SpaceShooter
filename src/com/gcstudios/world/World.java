package com.gcstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.gcstudios.entities.Enemy;
import com.gcstudios.entities.Entity;

import com.gcstudios.entities.Player;
import com.gcstudios.graficos.Spritesheet;
import com.gcstudios.graficos.UI;
import com.gcstudios.main.EnemySpawn;
import com.gcstudios.main.Game;

public class World {

	public static Tile[] tiles;
	public static int WIDTH,HEIGHT;
	public static final int TILE_SIZE = 16;
	
	public static int dia = 0;
	public static int noite = 1;
	public static int CICLO = dia;
	
	public World(){
		/*
		String[] tilesType = {"grama", "neve", "areia", "terra"};
		WIDTH = 200;
		HEIGHT = 30;
		int divisao = WIDTH/tilesType.length;
		tiles = new Tile[WIDTH*HEIGHT];
		for(int xx = 0; xx < WIDTH; xx++) {
			int initialHeight = Entity.rand.nextInt(20 - 16) + 16;
			for(int yy = 0; yy < HEIGHT; yy++) {
				if(yy == HEIGHT - 1 || xx == WIDTH -1 || xx == 0 || yy == 0) {
					tiles[xx+yy*WIDTH] = new WallTile(xx*16, yy*16, Tile.TILE_PEDRA);
					tiles[xx+yy*WIDTH].solid = true;
				}else {
				     if(yy>=initialHeight) {
				    	 int indexBioma = xx/ divisao;
				    	 if(tilesType[indexBioma] == "grama") {
				    		 tiles[xx+yy*WIDTH] = new WallTile(xx*16, yy*16, Tile.TILE_GRAMA);
				    	 }else if(tilesType[indexBioma] == "neve"){
				    		 tiles[xx+yy*WIDTH] = new WallTile(xx*16, yy*16, Tile.TILE_NEVE);
				    	 }else if(tilesType[indexBioma] == "areia"){
				    		 tiles[xx+yy*WIDTH] = new WallTile(xx*16, yy*16, Tile.TILE_AREIA);
				    	 }else if(tilesType[indexBioma] == "terra"){
				    		 tiles[xx+yy*WIDTH] = new WallTile(xx*16, yy*16, Tile.TILE_TERRA);
				    	 }
				     }else {
						 tiles[xx+yy*WIDTH] = new FloorTile(xx*16, yy*16, Tile.TILE_AR);
				     }
				}
			}
		}
		*/
}
	
	public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}
	
			  
	
	public static void restartGame(){		
	
		Game.score = 0;
		Game.life = 5;
		Game.entities.clear();
		Game.entities = new ArrayList<Entity>();			
		Game.spritesheet = new Spritesheet("/spritesheet.png");	
		//Game.player = new Player(WIDTH/2 - 40, HEIGHT/2, 16, 16, 1, Entity.PLAYER_SPRITE_R[0]);
		Game.player = new Player(60 , 182, 16, 16, 1, Game.spritesheet.getSprite(0, 0, 16, 16));
		Game.ui = new UI();
		Game.enemySpawn = new EnemySpawn();	
		Game.entities.add(Game.player);
		return;
		}
	
	
	public void renderizar(Graphics g){
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
}
