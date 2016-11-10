package com.me.DB;

import java.sql.*;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "DB";
		cfg.width = 1160;
		cfg.height = 554;
		cfg.resizable = false;
		
	
		new LwjglApplication(new DB(), cfg);
		
	}
}
