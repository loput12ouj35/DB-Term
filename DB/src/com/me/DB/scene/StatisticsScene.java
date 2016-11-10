package com.me.DB.scene;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.DB.DB;
import com.me.DB.ExtendedSprite;

public class StatisticsScene implements Scene {
	
	private BitmapFont font;
	
	private ExtendedSprite back_button;
	private ExtendedSprite reset_button;
	
	
	private ArrayList<beverage> beverage;
	
	private int prevY = 0;
	private int scroll = 0;
	private int show = 0;
	
	private boolean load = false;
	
	public class beverage{
		public String name;
		public int price;
		public int searched;
	}
	
	public StatisticsScene() throws SQLException {
		
		font = new BitmapFont(Gdx.files.internal("data/fnt2.fnt"));
		font.setColor(0,0,0,1);
		
		beverage = new ArrayList<StatisticsScene.beverage>();
		
		set();		
		
		back_button = new ExtendedSprite(new Texture(Gdx.files.internal("data/more.png")));
		back_button.rotate(180);
		
		back_button.setCenter(10 ,DB.context.screen_size.y / 2);
		
		reset_button = new ExtendedSprite(new Texture(Gdx.files.internal("data/reset.png")));
		reset_button.setCenter(900, DB.context.screen_size.y / 2);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACKSPACE){
			DB.context.setScene(DB.mainscene);
			load = false;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(back_button.overlaps(screenX, DB.context.screen_size.y - screenY)){
			DB.context.setScene(DB.mainscene);
			load = false;
		}
		
		if(reset_button.overlaps(screenX, DB.context.screen_size.y - screenY)){
			try {
				reset();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		prevY = 0;
		scroll = 0;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		scroll = - prevY + screenY;
		if(prevY != 0)
			show += scroll;
		prevY = screenY;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		show += amount * 5;
		return false;
	}

	@Override
	public void render(SpriteBatch batch) {
		if(!load){
			try {
				set();
				load = true;
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}
		
		
		int x = 50;
		int y = (int)DB.context.screen_size.y - 50 + show;
		
		font.draw(batch, "이름", x, y);
		font.draw(batch, "가격", x + 300, y);
		font.draw(batch, "검색횟수", x + 600, y);
		
		y -= 40;
		
		for(int i=0 ; i < beverage.size(); ++i){
			
			font.draw(batch, beverage.get(i).name, x, y);
			font.draw(batch, String.valueOf(beverage.get(i).price), x + 300, y);
			font.draw(batch, String.valueOf(beverage.get(i).searched), x + 600, y);
			
			y -= 20;
		}
		
		back_button.draw(batch);
		reset_button.draw(batch);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	public void reset() throws SQLException {
		DB.conn.setAutoCommit(false);
		String query = "UPDATE beverage SET SearchNumber = 0";
		PreparedStatement prep = DB.conn.prepareStatement(query);
		prep.executeUpdate();
		DB.conn.commit();
		
		set();
	}
	
	public void set() throws SQLException {
		beverage.clear();
		
		String query = "SELECT * FROM beverage ORDER BY SearchNumber DESC, Price";
		PreparedStatement prep = DB.conn.prepareStatement(query);
		
		ResultSet rs = prep.executeQuery();
			
		while(rs.next()){
			beverage bev = new beverage();
			
			bev.name = rs.getString(1);
			bev.price = rs.getInt(2);
			bev.searched = rs.getInt(3);
			
			beverage.add(bev);
		}
	}
}
