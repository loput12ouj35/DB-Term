package com.me.DB.scene;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.DB.DB;
import com.me.DB.ExtendedSprite;

public class TimetableScene implements Scene {

	public final int datesize = 6;
	public final int daysize = 10;
	
	private BitmapFont font;
	private String[][] classname;
	private String[][] buildingname;
	
	private ExtendedSprite black;
	private ExtendedSprite back_button;
	
	public TimetableScene() throws SQLException {
		
		font = new BitmapFont(Gdx.files.internal("data/fnt2.fnt"));
		font.setColor(0,0,0,1);
		
		classname = new String[datesize][daysize];
		buildingname = new String[datesize][daysize];
		
		for(int i = 0; i < datesize; ++i){
			for(int j = 0; j < daysize; ++j){
				classname[i][j] = " ";
				buildingname[i][j] = " ";				
			}	
		}
		
		/* setting table */
		String query = "SELECT * FROM timetable WHERE day = ? ORDER BY time";
		PreparedStatement prep = DB.conn.prepareStatement(query);
		
		for(int i = 0; i < datesize; ++i){
			prep.setInt(1, i+1);
			
			ResultSet rs = prep.executeQuery();
			
			while(rs.next()){
				int j = rs.getInt(2);
				classname[i][j] = rs.getString(3);
				buildingname[i][j] = rs.getString(4);
			}
		}
		
		black = new ExtendedSprite(new Texture(Gdx.files.internal("data/black.png")));
		back_button = new ExtendedSprite(new Texture(Gdx.files.internal("data/more.png")));
		back_button.rotate(180);
		
		back_button.setCenter(10 ,DB.context.screen_size.y / 2);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.BACKSPACE){
			DB.context.setScene(DB.mainscene);
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
		if(screenX > 140 && screenX < 1040 && screenY > 75 && screenY < 525){
			int i = screenX - 140;
			int j = screenY - 75;
			
			i /= 150;
			j /= 50;
			j++;
						
			insert(i,j);
		}
		
		if(back_button.overlaps(screenX, DB.context.screen_size.y - screenY)){
			DB.context.setScene(DB.mainscene);
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render(SpriteBatch batch) {
		int x = 150;
		int y = (int)DB.context.screen_size.y - 50;
		
		for(int i = 0; i < datesize; ++i){
			for(int j = 0; j < daysize; ++j){
				font.draw(batch, classname[i][j], x, y + 20);
				font.draw(batch, buildingname[i][j], x, y);
				y -= 50;
			}
			x += 150;
			y = (int)DB.context.screen_size.y - 50;
		}
		
		
		font.draw(batch, "월", 150, DB.context.screen_size.y - 50);
		font.draw(batch, "화", 300, DB.context.screen_size.y - 50);
		font.draw(batch, "수", 450, DB.context.screen_size.y - 50);
		font.draw(batch, "목", 600, DB.context.screen_size.y - 50);
		font.draw(batch, "금", 750, DB.context.screen_size.y - 50);
		font.draw(batch, "토", 900, DB.context.screen_size.y - 50);
		
		for(int i = 1; i < daysize; ++i)
			font.draw(batch, String.valueOf(i), 50, DB.context.screen_size.y - 50 * i - 50);
		
		black.setSize(1000, 1);
		for(int i = 1; i <= daysize; ++i){
			black.setCenter(540, (int)DB.context.screen_size.y - 50 * i - 25);
			black.draw(batch);
		}
		
		black.setSize(1, 500);
		for(int i = 0; i <= datesize; ++i){
			black.setCenter(140 + 150 * i, 278);
			black.draw(batch);
		}
		
		back_button.draw(batch);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}
	
	public void insert(final int i, final int j) {
		

		Gdx.input.getTextInput(new TextInputListener() {
			
			@Override
			public void input(String text) {
				int cut = text.indexOf("/");
				
				if(cut != -1){
					classname[i][j] = text.substring(0, cut);
					buildingname[i][j]= text.substring(cut + 1);
					
					try {
						DB.conn.setAutoCommit(false);
						String query = "DELETE FROM timetable WHERE day = ? AND time = ?";
						String query2 = "INSERT INTO `timetable` (`day`, `time`, 'classname', 'buildingname') VALUES (?,?,?,?)";
						PreparedStatement prep = DB.conn.prepareStatement(query);
						prep.setInt(1, i+1);
						prep.setInt(2, j);

						prep.executeUpdate();
						
						if(!classname[i][j].equals(" ") && !buildingname[i][j].equals(" ")){
							PreparedStatement prep2 = DB.conn.prepareStatement(query2);
							prep2.setInt(1, i+1);
							prep2.setInt(2, j);
							prep2.setString(3, classname[i][j]);
							prep2.setString(4, buildingname[i][j]);

							prep2.executeUpdate();
						}
						DB.conn.commit();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void canceled() {
				// TODO Auto-generated method stub				
			}
		}, "강의명/건물명을 입력. 또는 ' / ' (삭제)", (buildingname[i][j].equals(" "))? "예) 도시와국토/아산이학관" : classname[i][j] + "/" + buildingname[i][j]);
	}

}
