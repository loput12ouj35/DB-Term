package com.me.DB;

import java.sql.SQLException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.me.DB.scene.Scene;

public class Context {
	private Scene scene;
	public final Vector2 screen_size;
	public final int  menu_size= 80;
	
	public Context(DB db){
		screen_size = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
	}
	
	public void setScene(Scene scene)
	{		
		this.scene = scene;
		Gdx.input.setInputProcessor(scene);
	}
	
	public Scene getScene()
	{
		return scene;
	}
	
	public void exit() throws SQLException{
		DB.conn.close();
		Gdx.app.exit();
	}
	
}
