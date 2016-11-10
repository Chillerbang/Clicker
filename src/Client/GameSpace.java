package Client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;

public class GameSpace extends JPanel {

	private Boolean Player1;
	private int LocalScore;
	private int EnemyScore;
	private File fLocal;
	private  int id;
	private int animate = 0;
	private BufferedImage[] Anuimations1;
	private BufferedImage[] Anuimations2;
	private int SpriteInt = 32;
	private boolean GameOver;
	private boolean islose;
	GameSpace(int iID, boolean go, boolean lost){
		islose = lost;
		GameOver = go;
		id = iID;
		if ((iID % 2) == 0){
			Player1 = false;
		}else
		{
			Player1 = true;
		}
		LocalScore = -1;
		EnemyScore = -1;
		fLocal = new File("Sprites//Player1.png");
		getSpriteSheet gss = new getSpriteSheet(fLocal);
		//loading images for animations
		Anuimations1 = new BufferedImage[3];
		Anuimations1[0] = gss.GetSprite(0, 0, SpriteInt, SpriteInt);
		Anuimations1[1] = gss.GetSprite(SpriteInt, 0,SpriteInt, SpriteInt);
		Anuimations1[2] = gss.GetSprite(SpriteInt*2,0, SpriteInt, SpriteInt);
		fLocal = new File("Sprites//Player2.png");
		getSpriteSheet gss2 = new getSpriteSheet(fLocal);
		Anuimations2 = new BufferedImage[3];
		Anuimations2[0] = gss2.GetSprite(0, 0, SpriteInt, SpriteInt);
		Anuimations2[1] = gss2.GetSprite(SpriteInt, 0,SpriteInt, SpriteInt);
		Anuimations2[2] = gss2.GetSprite(SpriteInt*2,0, SpriteInt, SpriteInt);
		repaint();
	}
	public void update(int LocalScore,int EnemyScore){
		this.LocalScore = LocalScore;
		this.EnemyScore = EnemyScore;
		repaint();
	}
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // setting up play field
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, this.getWidth(), this.getHeight());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		g.setColor(Color.red); 
		Graphics2D g2 = (Graphics2D) g;
	    g2.setStroke(new BasicStroke(50));
		g2.drawLine(this.getWidth() -1, 0, this.getWidth()-1, this.getHeight()-1);
		// draw image 50 from top
		// 400 from left
		// 400 form right
		if ((LocalScore != -1)&&(EnemyScore != -1)){
			// get local char
				if (GameOver){ // game end screen
					g.setColor(Color.BLACK);
					g.drawRect(0, 0, this.getWidth(), this.getHeight());
					g.fillRect(0, 0, this.getWidth(), this.getHeight());
					g.setColor(Color.RED);
					g.drawString("Game Over", this.getWidth()/2-55, this.getHeight()/2-20);
					if (!islose){
						g.setColor(Color.GREEN);
						g.drawString("You Win", this.getWidth()/2-47, this.getHeight()/2-10);
					}else{
						g.setColor(Color.ORANGE);
						g.drawString("You Lose", this.getWidth()/2-48, this.getHeight()/2-10);
					}
					
				}else{ // game is being played screen
				animate = (LocalScore + EnemyScore) %3; // controlling animations across clients !
				if (Player1){
					g.setColor(Color.white);
					g.drawString("Player 1", 10, 28);
					g.drawImage(Anuimations1[animate],LocalScore/2,30,null) ;
					
					g.setColor(Color.white);
					g.drawString("Player 2", 10, 100);
					g.drawImage(Anuimations2[animate],EnemyScore/2,115,null) ;
		
				}else{
					g.setColor(Color.white);
					g.drawString("Player 1", 10, 28);
					g.drawImage(Anuimations2[animate],LocalScore/2,115,null) ;
					
					g.setColor(Color.white);
					g.drawString("Player 2", 10, 100);
					g.drawImage(Anuimations1[animate],EnemyScore/2,30,null) ;
					
				}
			}
		}
	}
}
