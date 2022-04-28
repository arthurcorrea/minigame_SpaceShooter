package com.gcstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.gcstudios.entities.Entity;

import com.gcstudios.entities.Player;
import com.gcstudios.graficos.Spritesheet;
import com.gcstudios.graficos.UI;

import com.gcstudios.world.World;
import com.gcstudios.main.Menu;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 130;
	public static final int HEIGHT = 200;
	public static final int SCALE = 3;
	
	public BufferedImage image;
	
    public static World world;
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static Player player;

	public static EnemySpawn enemySpawn;
	
	
	public BufferedImage background;
	public BufferedImage background2;
	public int backY = 0;
	public int backY2 = 200;
	public int backSpeed = 1;
	//private int CUR_LEVEL = 1, MAX_LEVEL = 2;
	
	public static UI ui;
	
	public static int score = 0;
	
	public static double life = 5;
	
	public static String gameState = "Menu";
	public static Random rand;
	
	public Menu menu;
	
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	
	public Game(){
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		//Inicializando objetos.
		spritesheet = new Spritesheet("/spritesheet.png");
		entities = new ArrayList<Entity>();
		player = new Player(60 , 182, 16, 16, 1, spritesheet.getSprite(0, 0, 16, 16));
		ui = new UI();
		try {
			background = ImageIO.read(getClass().getResource("/background1.png"));
			background2 = ImageIO.read(getClass().getResource("/background1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		enemySpawn = new EnemySpawn();	
		world = new World();
		menu = new Menu();
		entities.add(player);
		
	}
	
	public void initFrame(){
		frame = new JFrame("DTWR");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void atualizar(){
	
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.atualizar();		
	}
		
		 if(gameState == "Menu") {
	   		   menu.atualizar(); 		     
	    	  } 
			
   		ui.atualizar();	 
        enemySpawn.atualizar();
      
        backY-=backSpeed;        
        if(backY+200 <=0) {
        	backY = 200;
        }
        
        backY2-=backSpeed;        
        if(backY2+200 <=0) {
        	backY2 = 200;
        }
        
   } 
		
   	    
   	   
		
	
	


	
	public void renderizar(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		g.drawImage(this.background, 0,backY,null);
		g.drawImage(this.background2, 0,backY2,null);
		/*Renderização do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		//world.renderizar(g);
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.renderizar(g);
		}
		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		ui.render(g);
		
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD, 24));
			g.setColor(Color.WHITE);
			g.drawString("Game Over!", 150, 140);
			g.setFont(new Font("arial", Font.BOLD, 22));
			if(showMessageGameOver)
			       g.drawString("> Press Enter to restart <", 90, 180);
			
		}else if(gameState == "Menu") {
	    	menu.renderizar(g);
	    }
		
		/*
if(frutas_atual == frutas_contagem) {
	Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("arial", Font.BOLD, 32));
			g.setColor(Color.WHITE);
			g.drawString("You won! :)", 220, 120);
		}
*/
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				atualizar();
				renderizar();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D ||
				e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right= true;
			player.left= false;
		}else if(e.getKeyCode() == KeyEvent.VK_A ||
				e.getKeyCode() == KeyEvent.VK_LEFT) {			 
				player.left= true;
				player.right= false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_W ||
				e.getKeyCode() == KeyEvent.VK_UP) {
			if(gameState == "Menu") {
			menu.up = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_S ||
				e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(gameState == "Menu") {
			menu.down = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {			 
			player.atirando = true;
	}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameState == "Menu") {
				menu.enter = true;
			}					
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState = "Menu";
			Menu.pause = true;
		}
		
	}
	   
		

	@Override
	public void keyReleased(KeyEvent e) {
		/*
		if(e.getKeyCode() == KeyEvent.VK_D) {
			
		}else if(e.getKeyCode() == KeyEvent.VK_A) {			 
				
		}
		*/
	}
		
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	
			
		
		
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
	}

	
}
