package com.gcstudios.main;

import com.gcstudios.entities.Enemy;
import com.gcstudios.entities.Entity;

public class EnemySpawn {

	public int targetTime = 60*1;
	public int curTime = 0;
	
	
	public void atualizar() {
		curTime++;
		if(curTime == targetTime) {
			curTime = 0;
			int yy = 0;
			int xx = Entity.rand.nextInt(Game.WIDTH-16);
			Enemy enemy = new Enemy(xx,yy,16, 16, 1, Entity.ENEMY);
			Game.entities.add(enemy);			
		}if(curTime == targetTime) {
			curTime = 0;
			int yy = 0;
			int xx = Entity.rand.nextInt(Game.WIDTH-16);
			Enemy enemy2 = new Enemy(xx,yy,16, 16, 1, Entity.ENEMY2);
			Game.entities.add(enemy2);
	}
	
	}
	
}
