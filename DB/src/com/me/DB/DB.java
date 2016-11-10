package com.me.DB;

import java.sql.*;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.DB.scene.BuildinglistScene;
import com.me.DB.scene.MainScene;
import com.me.DB.scene.Scene;
import com.me.DB.scene.StatisticsScene;
import com.me.DB.scene.TimetableScene;

public class DB implements ApplicationListener {
	
	private SpriteBatch batch;
	public static OrthographicCamera camera;
	public static Context context;
	public static Connection conn;
	
	public static Scene mainscene;
	public static Scene tablescene;
	public static Scene statisticscene;
	public static Scene buildinglistscene;
	
	@Override
	public void create()
	{		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(1, h/w);
		camera.setToOrtho(false);
		batch = new SpriteBatch();
		context = new Context(this);
		
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:sdb.db");
			mainscene = new MainScene();
			tablescene = new TimetableScene();
			statisticscene = new StatisticsScene();
			buildinglistscene = new BuildinglistScene();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		context.setScene(mainscene);
	}

	@Override
	public void dispose()
	{
		batch.dispose();
	}

	@Override
	public void render()
	{		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		context.getScene().render(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}
}
