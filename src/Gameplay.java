import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
private static final String A = null;
private boolean play=false;
private int score=0;
private int totalbricks=21;
private Timer timer;
private int delay=8;
private int playerX=310;
private int ballposeX=120;
private int ballposeY=350;
private int ballXdir=-1;
private int ballYdir=-2;
private MapGernerator map;
public Gameplay() {
	map=new MapGernerator(3,7);
	addKeyListener(this);
	setFocusable(true);
	setFocusTraversalKeysEnabled(false);
	timer = new Timer(delay,this);
	timer.start();
}
public void paint(Graphics g) {
	//background
	g.setColor(Color.white);
	g.fillRect(1, 1, 692, 592);
	//drawing map
	map.draw((Graphics2D)g);
	//Boarders
	g.setColor(Color.yellow);
	g.fillRect(0,0, 4, 592);
	g.fillRect(0,0, 692, 4);
	g.fillRect(690,0, 4, 592);
	//scores
	g.setColor(Color.blue);
	g.setFont(new Font("serif",Font.BOLD,25));
	g.drawString(""+score,592,30);
	//thepaddle
	g.setColor(Color.red);
	g.fillRect(playerX, 550, 100, 8);
	
	//theball
	g.setColor(Color.black);
	g.fillOval(ballposeX, ballposeY, 20, 20);
	if (totalbricks <=0) {
		play=false;
		ballXdir=0;
		ballYdir=0;
		g.setColor(Color.RED);
		g.setFont(new Font("serif",Font.BOLD,30));
		g.drawString("you won ",260,300);
		g.setFont(new Font("serif",Font.BOLD,20));
		g.drawString("Press Enter to Restart ",230,350);
			
	}
	
	if(ballposeY>570) {
		play=false;
		ballXdir=0;
		ballYdir=0;
		g.setColor(Color.RED);
		g.setFont(new Font("serif",Font.BOLD,30));
		g.drawString("Game Over,scores: ",190,300);
		g.setFont(new Font("serif",Font.BOLD,20));
		g.drawString("Press Enter to Restart ",230,350);
			
	}
	
	
	g.dispose();
	
	
	
	
}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		timer.start();
		if (play){
			
			if(new Rectangle(ballposeX,ballposeY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
				ballYdir =- ballYdir;
			}
			A:for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX=j*map.brickwidth+80;
						int brickY=i*map.brickheight+50;
						int brickwidth=map.brickwidth;
						int brickheight=map.brickheight;
						
						Rectangle rect=new Rectangle(brickX,brickY,brickwidth,brickheight);
						Rectangle ballRect=new Rectangle(ballposeX,ballposeY,20,20);
						Rectangle brickRect=rect;
						
						if(ballRect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalbricks--;
							score+=5;
							
							if(ballposeX+19<=brickRect.x||ballposeX+1>=brickRect.x+brickRect.width) {
								ballXdir=-ballXdir;
								
							}
							else {
								ballYdir=-ballYdir;
						
							}
							break A;
						}
						
					}
				}
			}
			
			ballposeX +=ballXdir;
			ballposeY +=ballYdir;}
			if(ballposeX<0) {
				ballXdir =- ballXdir;}
			if(ballposeY<0) {
				ballYdir =- ballYdir;}
			if(ballposeX >670) {
				ballXdir =- ballXdir;
		}
		repaint();
	
	}

	public void keyTyped(KeyEvent arg0) {}
		public void keyReleased(KeyEvent arg0) {	}
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getKeyCode()==KeyEvent.VK_RIGHT) {
				if(playerX>=600) {
					playerX=600;}
					else {
						moveRight();
						
					}
					
			}
				if(arg0.getKeyCode()==KeyEvent.VK_LEFT)
					if(playerX<=10) {
						playerX=10;}
						else {
							moveLeft();
			}
	
		if(arg0.getKeyCode()==KeyEvent.VK_ENTER)
			if(!play) {
				play=true;
				ballposeX=120;
				ballposeY=350;
				ballXdir=-1;
				ballYdir=-2;
				playerX=310;
				score=0;
				totalbricks=21;
				map=new MapGernerator(3,7);
				repaint();
				}		
}
		
public void moveRight() {
	play=true;
	playerX+=20;
	
}
	
public void moveLeft() {
	play=true;
	playerX-=20;
	
}

}