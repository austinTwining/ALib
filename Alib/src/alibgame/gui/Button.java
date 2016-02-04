package alibgame.gui;

import java.awt.Graphics;
import java.awt.Rectangle;

import alibgame.gfx.Sprite;

public abstract class Button {
	
	protected int width, height, x, y;
	protected Sprite pSprite, sSprite;
	protected boolean pressed = false;
	
	public Button(int width, int height, int x, int y, Sprite sprite){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.pSprite = sprite;
	}
	
	public Button(int width, int height, int x, int y, Sprite pSprite, Sprite sSprite){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.pSprite = pSprite;
		this.sSprite = sSprite;
	}
	
	public abstract void draw(Graphics g);
	
	public void update(int mouseX, int mouseY, boolean buttonState){
		if(getRect().contains(mouseX, mouseY)) onEntered();
		if(buttonState && getRect().contains(mouseX, mouseY)){
			onPressed();
			pressed = true;
		}
		
		if(!(buttonState && getRect().contains(mouseX, mouseY)) && pressed){
			onReleased();
			pressed = false;
		}
	}
	
	public abstract void onEntered();
	public abstract void onPressed();
	public abstract void onReleased();
	
	public boolean isPressed(){return pressed;}
	
	private Rectangle getRect(){
		return new Rectangle(x, y, width, height);
	}
}
