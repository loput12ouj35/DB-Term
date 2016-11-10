package com.me.DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Building {
	public String Ename = " ";
	public String Kname = " ";
	public String picture = " ";
	public int LocationX = 0;
	public int LocationY = 0;
	public int BuildingNumber = 0;
	
	public void set(ResultSet rs) throws SQLException {
		Ename = rs.getString(1);
		Kname = rs.getString(2);
		picture = rs.getString(3);
		LocationX = rs.getInt(4);
		LocationY = rs.getInt(5);
		BuildingNumber = rs.getInt(6);
		
		LocationX *= 3;
		LocationX /= 4;
		LocationY *= 3;
		LocationY /= 4;
		LocationY = (int) (DB.context.screen_size.y -  LocationY);
	}
	
	public void clear(){
		Ename = " ";
		Kname = " ";
		picture = "0.jpg";
		LocationX = 0;
		LocationY = 0;
		BuildingNumber = 0;
	}
}
