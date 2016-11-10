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
import com.me.DB.Building;
import com.me.DB.DB;
import com.me.DB.ExtendedSprite;

public class BuildinglistScene implements Scene {
	
	private BitmapFont font;
	
	private ExtendedSprite back_button;	
	
	private ArrayList<Building> building;
	
	private int prevY = 0;
	private int scroll = 0;
	private int show = 0;
	
	private boolean load = false;
	
	
	public BuildinglistScene() throws SQLException {
		
		font = new BitmapFont(Gdx.files.internal("data/fnt2.fnt"));
		font.setColor(0,0,0,1);
		
		building = new ArrayList<Building>();
		
		set();		
		
		back_button = new ExtendedSprite(new Texture(Gdx.files.internal("data/more.png")));
		back_button.rotate(180);
		
		back_button.setCenter(10 ,DB.context.screen_size.y / 2);
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
		
		font.draw(batch, "건물번호", x, y);
		font.draw(batch, "국문이름", x + 300, y);
		font.draw(batch, "영문이름", x + 600, y);
		
		y -= 40;
		
		for(int i=0 ; i < building.size(); ++i){
			
			font.draw(batch, String.valueOf(building.get(i).BuildingNumber), x, y);
			font.draw(batch, building.get(i).Kname, x + 300, y);
			font.draw(batch, building.get(i).Ename, x + 600, y);
			
			y -= 20;
		}
		
		back_button.draw(batch);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	public void set() throws SQLException {
		building.clear();
		
		String query = "SELECT * FROM buildings ORDER BY BuildingNumber";
		PreparedStatement prep = DB.conn.prepareStatement(query);
		
		ResultSet rs = prep.executeQuery();
			
		while(rs.next()){
			Building b = new Building();
			b.set(rs);			
			building.add(b);
		}
	}
}
