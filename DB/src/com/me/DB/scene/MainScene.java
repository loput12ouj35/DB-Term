/* 
 * 건물 전체
 * 통계
 * 시간표
 */

package com.me.DB.scene;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.DB.Building;
import com.me.DB.DB;
import com.me.DB.ExtendedSprite;
import com.me.DB.Route;
import com.me.DB.Route.Dot;
import com.me.DB.Route.FoundRoute;

public class MainScene implements Scene {

	final static int infoboxsizeX = 300;
	final static int infoboxsizeY = 400;
	
	private ExtendedSprite map;
	private ExtendedSprite infobox;
	private ExtendedSprite more_infobox;
	private ExtendedSprite more_more_infobox;
	private ExtendedSprite image;
	private ExtendedSprite search_point;
	
	private ExtendedSprite route_start_point;
	private ExtendedSprite route_dst_point;
	private ExtendedSprite route_start_button;
	private ExtendedSprite route_dst_button;
	private ExtendedSprite route_start_global_button;
	private ExtendedSprite route_dst_global_button;
	private ExtendedSprite route_box;
	
	private ExtendedSprite show_button1;
	private ExtendedSprite show_button2;
	private ExtendedSprite show_button3;
	private ExtendedSprite show_button4;
	private ExtendedSprite show_button5;
	
	private ExtendedSprite building_icon;
	private ExtendedSprite beverage_icon;
	private ExtendedSprite ATM_icon;
	private ExtendedSprite PC_icon;
//	private ExtendedSprite water_icon;
//	private ExtendedSprite vending_icon;
	private ExtendedSprite bank_icon;
	private ExtendedSprite cafeteria_icon;
	private ExtendedSprite more;
	private ExtendedSprite clear_button;
	private ExtendedSprite bus_icon;
	private ExtendedSprite lounge;
	private ExtendedSprite shower;
	private ExtendedSprite readingroom;
	private ExtendedSprite cvstore;
	private ExtendedSprite internet_button;
	
	private ExtendedSprite tableset;
	private ExtendedSprite mon;
	private ExtendedSprite tue;
	private ExtendedSprite wed;
	private ExtendedSprite tus;
	private ExtendedSprite fri;
	private ExtendedSprite sat;
	
	private ExtendedSprite stat;
	private ExtendedSprite buildinglist;
	
	private ExtendedSprite redline;
	
	
	private BitmapFont infofont;
	private BitmapFont infofont2;
	private BitmapFont infofont3;
	
	private int clickedX;
	private int clickedY;
	
	private int[] pointLocation;
	
	private Building building;
	private ArrayList<Building> search_building;
	
	private Route route;
	private FoundRoute r;
	
	private int menu_preX = 0;
		
	private String[] convenients;
	private String[] beverages;
	
	private boolean infoloaded = false;
	private boolean more_infoloaded = false;
	private boolean search_pointshown = false;
	private boolean icon_searched = false;
	private boolean global_start = false;
	private boolean global_dst = false;
	private boolean beverage_shown = false;
	private boolean noload = false;
	private boolean route_found = false;
	
	
	/*
	private class Dot {
		public final int id;
		public final int x;
		public final int y;
		public Dot(int id, int x, int y) {
			super();
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}

	Dot dot1 = null;
	Dot dot2 = null;
	HashSet<Dot> dots = new HashSet<MainScene.Dot>();
	*/
	
	public MainScene() throws ClassNotFoundException, SQLException{
		map = new ExtendedSprite(new Texture(Gdx.files.internal("data/map.png")));
		infobox = new ExtendedSprite(new Texture(Gdx.files.internal("data/info.png")));
		more_infobox = new ExtendedSprite(new Texture(Gdx.files.internal("data/info.png")));
		more_more_infobox = new ExtendedSprite(new Texture(Gdx.files.internal("data/info.png")));
		image = new ExtendedSprite(new Texture(Gdx.files.internal("data/0.jpg")));
		search_point = new ExtendedSprite(new Texture(Gdx.files.internal("data/point.png")));
		
		route_start_point = new ExtendedSprite(new Texture(Gdx.files.internal("data/start.png")));
		route_dst_point = new ExtendedSprite(new Texture(Gdx.files.internal("data/dst.png")));
		route_start_button = new ExtendedSprite(new Texture(Gdx.files.internal("data/start.png")));
		route_dst_button = new ExtendedSprite(new Texture(Gdx.files.internal("data/dst.png")));
		route_start_global_button = new ExtendedSprite(new Texture(Gdx.files.internal("data/start_button.png")));
		route_dst_global_button = new ExtendedSprite(new Texture(Gdx.files.internal("data/dst_button.png")));
		route_box = new ExtendedSprite(new Texture(Gdx.files.internal("data/routebox.png")));
		
		show_button1 = new ExtendedSprite(new Texture(Gdx.files.internal("data/show.png")));
		show_button2 = new ExtendedSprite(new Texture(Gdx.files.internal("data/show.png")));
		show_button3 = new ExtendedSprite(new Texture(Gdx.files.internal("data/show.png")));
		show_button4 = new ExtendedSprite(new Texture(Gdx.files.internal("data/show.png")));
		show_button5 = new ExtendedSprite(new Texture(Gdx.files.internal("data/show.png")));
		
		building_icon = new ExtendedSprite(new Texture(Gdx.files.internal("data/building.png")));
		beverage_icon = new ExtendedSprite(new Texture(Gdx.files.internal("data/beverage.png")));
		ATM_icon = new ExtendedSprite(new Texture(Gdx.files.internal("data/ATM.png")));
		PC_icon = new ExtendedSprite(new Texture(Gdx.files.internal("data/PC.png")));
//		water_icon = new ExtendedSprite(new Texture(Gdx.files.internal("data/water.png")));
//		vending_icon = new ExtendedSprite(new Texture(Gdx.files.internal("data/vending.png")));
		bank_icon = new ExtendedSprite(new Texture(Gdx.files.internal("data/bank.png")));
		cafeteria_icon = new ExtendedSprite(new Texture(Gdx.files.internal("data/cafeteria.png")));
		more = new ExtendedSprite(new Texture(Gdx.files.internal("data/more.png")));
		clear_button = new ExtendedSprite(new Texture(Gdx.files.internal("data/clear.png")));
		
		internet_button = new ExtendedSprite(new Texture(Gdx.files.internal("data/homepage.png")));
		cvstore = new ExtendedSprite(new Texture(Gdx.files.internal("data/cvstore.png")));
		bus_icon = new ExtendedSprite(new Texture(Gdx.files.internal("data/bus.png")));
		lounge = new ExtendedSprite(new Texture(Gdx.files.internal("data/lounge.png")));
		shower = new ExtendedSprite(new Texture(Gdx.files.internal("data/shower.png")));
		readingroom = new ExtendedSprite(new Texture(Gdx.files.internal("data/readingroom.png")));
		
		tableset = new ExtendedSprite(new Texture(Gdx.files.internal("data/tableset.png")));
		mon = new ExtendedSprite(new Texture(Gdx.files.internal("data/mon.png")));
		tue = new ExtendedSprite(new Texture(Gdx.files.internal("data/tue.png")));
		wed = new ExtendedSprite(new Texture(Gdx.files.internal("data/wed.png")));
		tus = new ExtendedSprite(new Texture(Gdx.files.internal("data/tus.png")));
		fri = new ExtendedSprite(new Texture(Gdx.files.internal("data/fri.png")));
		sat = new ExtendedSprite(new Texture(Gdx.files.internal("data/sat.png")));
		
		stat = new ExtendedSprite(new Texture(Gdx.files.internal("data/stat.png")));
		buildinglist = new ExtendedSprite(new Texture(Gdx.files.internal("data/list.png")));
		
		pointLocation = new int[125];
		float x = DB.context.screen_size.x;
		float y = DB.context.screen_size.y;
		
		redline = new ExtendedSprite(new Texture(Gdx.files.internal("data/red.png")));
		
		
		convenients = new String[60];
		beverages = new String[60];
		
		map.setCenter(x / 2, y / 2 + DB.context.menu_size / 2);
		
		image.setScale(0.5f);
		
		infofont = new BitmapFont(Gdx.files.internal("data/fnt.fnt"));
		infofont.setColor(0,0,0,1);
		
		infofont2 = new BitmapFont(Gdx.files.internal("data/fnt2.fnt"));
		infofont2.setColor(0,0,0,1);
		
		infofont3 = new BitmapFont(Gdx.files.internal("data/fnt.fnt"));
		infofont3.setColor(0,0,0,1);
		infofont3.setScale(0.5f);
		

		

		int padding = 30;
		final int rest = 65;
		buildinglist.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		building_icon.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		beverage_icon.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		stat.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		ATM_icon.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		PC_icon.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
//		water_icon.setCenter(padding, DB.context.menu_size / 2);
//		padding += rest;
//		vending_icon.setCenter(padding, DB.context.menu_size / 2);
//		padding += rest;
		bank_icon.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		cafeteria_icon.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		route_start_global_button.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		route_dst_global_button.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;		
		internet_button.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		cvstore.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		bus_icon.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		lounge.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		shower.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		readingroom.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		
		clear_button.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		
		tableset.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		mon.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		tue.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		wed.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		tus.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		fri.setCenter(padding, DB.context.menu_size / 2);
		padding += rest;
		sat.setCenter(padding, DB.context.menu_size / 2);
		
		building = new Building();
		search_building = new ArrayList<Building>();
		
		route = new Route();
		r = route.new FoundRoute();
		/*
		// DELETE
		try {
			DB.conn.setAutoCommit(false);
			String query = "SELECT * FROM `path_node`";
			PreparedStatement prep = DB.conn.prepareStatement(query);
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) {
				dots.add(new Dot(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
			
		}*/
	}
	
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.ENTER){
			DB.context.setScene(DB.buildinglistscene);
		}
		
		if(keycode == Keys.BACKSPACE){
			clear();
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
		System.out.println(screenX+","+screenY);
		
		search_pointshown = false;
		icon_searched = false;
		route_found = false;
		
		if(infoloaded && more_infoloaded){
			more_infoloaded = false;
		}
		
		if(infoloaded && beverage_shown){
			beverage_shown = false;
		}

		if(global_start && screenY <= DB.context.screen_size.y - DB.context.menu_size){
			route_start_point.setCenter(screenX, DB.context.screen_size.y - screenY + 20);
			route.setStart(screenX, (int) screenY);
			global_start=false;
			noload = true;
		}
		
		if(global_dst && screenY <= DB.context.screen_size.y - DB.context.menu_size){
			route_dst_point.setCenter(screenX, DB.context.screen_size.y - screenY + 20);
			route.setDst(screenX, (int) screenY);
			global_dst = false;
			noload = true;
			
		}
		
		if(!infoloaded && route_start_global_button.overlaps(screenX, DB.context.screen_size.y - screenY)){
			global_start = true;
		}

		if(!infoloaded && route_dst_global_button.overlaps(screenX, DB.context.screen_size.y - screenY)){
			global_dst = true;
		}
		
		if(infoloaded && route_start_button.overlaps(screenX, DB.context.screen_size.y - screenY)){
			route_start_point.setCenter(building.LocationX, building.LocationY + 20);
			route.setStart(building.LocationX, (int) (DB.context.screen_size.y - building.LocationY));
		}
		
		if(infoloaded && route_dst_button.overlaps(screenX, DB.context.screen_size.y - screenY)){
			route_dst_point.setCenter(building.LocationX, building.LocationY + 20);
			route.setDst(building.LocationX, (int) (DB.context.screen_size.y - building.LocationY));
		}
		
		if(infoloaded && more.overlaps(screenX,  DB.context.screen_size.y - screenY)){
			try {
				moreinfo();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		showbutton(screenX, (int)DB.context.screen_size.y - screenY);
		
		if(internet_button.overlaps(screenX, DB.context.screen_size.y - screenY)){
			try {
				Desktop.getDesktop().browse(new java.net.URI("http://m.korea.ac.kr"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
		
		if(bus_icon.overlaps(screenX, DB.context.screen_size.y - screenY)){
			try {
				showbusstop();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(clear_button.overlaps(screenX, DB.context.screen_size.y - screenY)){
			clear();
		}
		
		if(stat.overlaps(screenX, DB.context.screen_size.y - screenY)){
			DB.context.setScene(DB.statisticscene);			
		}
		
		if(buildinglist.overlaps(screenX, DB.context.screen_size.y - screenY)){
			DB.context.setScene(DB.buildinglistscene);			
		}
		
		if(tableset.overlaps(screenX, DB.context.screen_size.y - screenY)){
			DB.context.setScene(DB.tablescene);
		}
		
		tablesearch(screenX, (int)DB.context.screen_size.y - screenY);
		
		if(!more_infoloaded && !beverage_shown && !noload){
			infoloaded = false;
			
			try {
				if(screenY >= DB.context.screen_size.y - DB.context.menu_size){
					icon_search(screenX, screenY);
				} else{
					showinfo(screenX, screenY);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		/*
		Dot curdot = null;
		
		for (Dot d : dots) {
			if (curdot == null) {
				curdot = d;
			}
			
			double x = screenX;
			double y = screenY;
			
			if (Math.sqrt(Math.pow(x - d.x, 2) + Math.pow(y - d.y, 2)) < Math.sqrt(Math.pow(curdot.x - x, 2) + Math.pow(curdot.y - y, 2))) {
				curdot = d;
			}
		}
		
		System.out.println("FUCK: " + curdot.id);
		
		if (dot1 == null) {
			dot1 = curdot;
		} else {
			try {
				DB.conn.setAutoCommit(false);
				String query = "INSERT INTO `path_edge` (`node1`, `node2`) VALUES (?,?)";
				PreparedStatement prep = DB.conn.prepareStatement(query);
				prep.setInt(1, dot1.id);
				prep.setInt(2, curdot.id);

				prep.executeUpdate();
				DB.conn.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			dot1 = null;
		} */
		
		noload = false;
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		menu_preX = 0;
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		int k = - screenX + menu_preX;
				
		if(k > -3 && k < 3){
			k = 0;
		}
		
		if((menu_preX != 0 && screenY >= DB.context.screen_size.y - DB.context.menu_size)){
			
			route_start_global_button.moveX(k);
			route_dst_global_button.moveX(k);
			building_icon.moveX(k);
			beverage_icon.moveX(k);
			ATM_icon.moveX(k);
			PC_icon.moveX(k);
//			water_icon.moveX(k);
//			vending_icon.moveX(k);
			bank_icon.moveX(k);
			cafeteria_icon.moveX(k);
			clear_button.moveX(k);
			internet_button.moveX(k);
			cvstore.moveX(k);
			bus_icon.moveX(k);
			lounge.moveX(k);
			shower.moveX(k);
			readingroom.moveX(k);
			tableset.moveX(k);
			mon.moveX(k);
			tue.moveX(k);
			wed.moveX(k);
			tus.moveX(k);
			fri.moveX(k);
			sat.moveX(k);
			stat.moveX(k);
			buildinglist.moveX(k);
		}
		
		menu_preX = screenX;

		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void render(SpriteBatch batch) {
		map.draw(batch);
		route_start_global_button.draw(batch);
		route_dst_global_button.draw(batch);
		building_icon.draw(batch);
		beverage_icon.draw(batch);
		ATM_icon.draw(batch);
		PC_icon.draw(batch);
//		water_icon.draw(batch);
//		vending_icon.draw(batch);
		bank_icon.draw(batch);
		cafeteria_icon.draw(batch);
		clear_button.draw(batch);
		internet_button.draw(batch);
		cvstore.draw(batch);
		bus_icon.draw(batch);
		lounge.draw(batch);
		shower.draw(batch);
		readingroom.draw(batch);
		tableset.draw(batch);
		mon.draw(batch);
		tue.draw(batch);
		wed.draw(batch);
		tus.draw(batch);
		fri.draw(batch);
		sat.draw(batch);
		stat.draw(batch);
		buildinglist.draw(batch);
		
		
		if(route.start){
			route_start_point.draw(batch);
		}
		if(route.dst){
			route_dst_point.draw(batch);
		}
		
		if(search_pointshown){
			for(int i=0; i < search_building.size(); i++){
				search_point.setCenter(search_building.get(i).LocationX, search_building.get(i).LocationY + 20);
				search_point.draw(batch);
			}
		}
		
		/*show Info*/
		if(infoloaded){
			infobox.draw(batch);
			infofont.draw(batch, String.valueOf(building.BuildingNumber) +". " + building.Kname, infobox.getX() + 20,
							infobox.getY() + infoboxsizeY - 25);
			infofont2.drawWrapped(batch, building.Ename, infobox.getX() + 20,
					infobox.getY() + infoboxsizeY - 90, 280);
			image.draw(batch);
			route_start_button.draw(batch);
			route_dst_button.draw(batch);
			more.draw(batch);
		}
		if(more_infoloaded){
			int j = 0;
			int space = 0;
			int spaceX = 0;
			
			more_infobox.draw(batch);
			if(building.Kname.equals("과학도서관")){
				more_more_infobox.draw(batch);
				infofont.draw(batch, "편의시설", more_more_infobox.getX() + 20,
						more_more_infobox.getY() + infoboxsizeY - 25 - space);
				infofont.draw(batch, "층", more_more_infobox.getX() + 150,
						more_more_infobox.getY() + infoboxsizeY - 25 - space);
				infofont.draw(batch, "비고", more_more_infobox.getX() + 200,
						more_more_infobox.getY() + infoboxsizeY - 25 - space);
			}
			infofont.draw(batch, "편의시설", more_infobox.getX() + 20,
					more_infobox.getY() + infoboxsizeY - 25 - space);
			infofont.draw(batch, "층", more_infobox.getX() + 150,
					more_infobox.getY() + infoboxsizeY - 25 - space);
			infofont.draw(batch, "비고", more_infobox.getX() + 200,
					more_infobox.getY() + infoboxsizeY - 25 - space);
			while(convenients[j]!="End"){
				infofont2.draw(batch, convenients[j], more_infobox.getX() + 20 + spaceX,
						more_infobox.getY() + infoboxsizeY - 90 - space);
				infofont2.draw(batch, convenients[j+1], more_infobox.getX() + 160 + spaceX,
						more_infobox.getY() + infoboxsizeY - 90 - space);
				if(convenients[j+2]!=null){
					if(convenients[j + 2].endsWith("1")){
						show_button1.setCenter(more_infobox.getX() + 220 + spaceX, more_infobox.getY() + infoboxsizeY - 100 - space);
						show_button1.draw(batch);
					}
					else if(convenients[j + 2].endsWith("2")){
						show_button2.setCenter(more_infobox.getX() + 220 + spaceX, more_infobox.getY() + infoboxsizeY - 100 - space);
						show_button2.draw(batch);
					}
					else if(convenients[j + 2].endsWith("3")){
						show_button3.setCenter(more_infobox.getX() + 220 + spaceX, more_infobox.getY() + infoboxsizeY - 100 - space);
						show_button3.draw(batch);
					}
					else if(convenients[j + 2].endsWith("4")){
						show_button4.setCenter(more_infobox.getX() + 220 + spaceX, more_infobox.getY() + infoboxsizeY - 100 - space);
						show_button4.draw(batch);
					}
					else if(convenients[j + 2].endsWith("5")){
						show_button5.setCenter(more_infobox.getX() + 220 + spaceX, more_infobox.getY() + infoboxsizeY - 100 - space);
						show_button5.draw(batch);
					}
					else{
						infofont2.draw(batch, convenients[j+2], more_infobox.getX() + 210 + spaceX,
								more_infobox.getY() + infoboxsizeY - 90 - space);
					}
				}
				j++;
				j++;
				j++;
				space += 30;
				
				if(space >= 300){
					 spaceX += 300;
					 space = 0;
				}
			}
		}
		if(beverage_shown){
			int k = 0;
			int space = 0;
			more_infobox.draw(batch);
			infofont.draw(batch, "이름", more_infobox.getX() + 20,
					more_infobox.getY() + infoboxsizeY - 25);
			infofont.draw(batch, "가격", more_infobox.getX() + 200,
					more_infobox.getY() + infoboxsizeY - 25);
			while(beverages[k]!="End"){
				infofont3.draw(batch, beverages[k], more_infobox.getX() + 20,
						more_infobox.getY() + infoboxsizeY - 90 - space);
				infofont3.draw(batch, beverages[k+1], more_infobox.getX() + 210,
						more_infobox.getY() + infoboxsizeY - 90 - space);
				k++;
				k++;
				space += 20;
			}
			
		}
		
		if(icon_searched){
			int i=0;
			while(pointLocation[i] != -1){
				search_point.setCenter(pointLocation[i] * 3 / 4, DB.context.screen_size.y - pointLocation[i+1] * 3 / 4 + 20);
				search_point.draw(batch);
				i++;
				i++;
			}
		}
		
		if(route.start && route.dst && !route_found){
			routeinfo();
		}
		
		if(route_found){			
			route_box.draw(batch);
			String s = "이동거리 : 약 " + (int)r.dis + "m"+ "\n" + "예상시간 : 약 " + r.time + "min";
			
			infofont.drawMultiLine(batch, s, DB.context.screen_size.x / 2 - infofont.getMultiLineBounds(s).width / 2,
					DB.context.screen_size.y - 10);
			
//			ShapeRenderer sr = new ShapeRenderer();
//			sr.setColor(Color.RED);
//			sr.begin(ShapeType.Line);
			
			for (int i = 0; i < r.nodes.size() - 1; ++i) {
				Dot d1 = r.nodes.get(i);
				Dot d2 = r.nodes.get(i+1);
				
				redline.setSize((float) d1.len(d2), 3);
				redline.setRotation(-(float) (Math.atan2(d2.y - d1.y, d2.x - d1.x) * 180.0 / Math.PI));
				redline.setCenter(((float)d1.x + (float)d2.x)/2, DB.context.screen_size.y - ((float)d1.y + (float)d2.y)/2);
				
				redline.draw(batch);
				
				System.out.println(redline.getX());
				System.out.println(redline.getY());
				
//				sr.line(d1.x, DB.context.screen_size.y - d1.y, d2.x, DB.context.screen_size.y - d2.y);
			}
			
//			sr.end();
			
		}
		
		
//		for (java.util.Map.Entry<Integer, Dot> d : route.dots.entrySet()) {
//			infofont.draw(batch, Integer.toString(d.getValue().id), d.getValue().x, DB.context.screen_size.y - d.getValue().y);
//		}
		/*
		try {
			String query = "SELECT `a`.`x` as `x1`, `a`.`y` as `y1`, `b`.`x` as `x2`, `b`.`y` as `y2` FROM"
					+ " (SELECT * FROM `path_edge` `e` LEFT OUTER JOIN `path_node` `a` ON `e`.`node1` = `a`.`id`) `a`"
					+ " LEFT OUTER JOIN `path_node` `b` ON `a`.`node2` = `b`.`id`";
			PreparedStatement prep;
			prep = DB.conn.prepareStatement(query);
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) {
				ShapeRenderer sr = new ShapeRenderer();
				
				sr.begin(ShapeType.Line);
				sr.setColor(1, 0, 0, 1);
				sr.line(rs.getFloat("x1"), DB.context.screen_size.y - rs.getFloat("y1"), rs.getFloat("x2"),DB.context.screen_size.y - rs.getFloat("y2"));
				
				sr.end();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	public void showinfo(int x, int y) throws SQLException {
		/*Infobox Part*/
		
		
		clickedX = (int) ((x < infoboxsizeX / 2)? infoboxsizeX /2 :
			((x < DB.context.screen_size.x - infoboxsizeX / 2)? x :
			(DB.context.screen_size.x - infoboxsizeX / 2)));
		clickedY = (int) ((DB.context.screen_size.y - y < DB.context.menu_size + infoboxsizeY / 2)? DB.context.menu_size + infoboxsizeY /2 + 10:
			((DB.context.screen_size.y - y < DB.context.screen_size.y - infoboxsizeY / 2)? DB.context.screen_size.y - y :
			(DB.context.screen_size.y  - infoboxsizeY / 2)));
		
		infobox.setCenter(clickedX, clickedY);
		if(clickedX <= 580){
			more_infobox.setCenter(clickedX + 300, clickedY);
			more_more_infobox.setCenter(clickedX + 600, clickedY);
		}
		else{
			more_infobox.setCenter(clickedX - 300, clickedY);
		}
		image.setCenter(clickedX, clickedY - 40);
		route_start_button.setCenter(clickedX + 80, clickedY - 175);
		route_dst_button.setCenter(clickedX + 130, clickedY - 175);
		more.setCenter(clickedX + 140, clickedY);
		
		building.clear();
		/*SQL Query Part*/
		final int zone = 10;
		
		x *= 4;
		x /= 3;
		y *= 4;
		y /= 3;
		
		String query = "SELECT * FROM buildings WHERE LocationX = ? AND LocationY = ?";
		PreparedStatement prep = DB.conn.prepareStatement(query);
		for(int i = -zone; i < zone; ++i){
			prep.setInt(1, x + i);
			for(int j = -zone; j < zone; ++j){
				prep.setInt(2, y + j);
			

				ResultSet rs = prep.executeQuery();
				
				while(rs.next()){
					building.set(rs);
					infoloaded = true;
				}
			}
		}
		
		if(!building.picture.equals("NULL"))
			image.setTexture(new Texture(Gdx.files.internal("data/" + building.picture)));
		
	}
	
	public void moreinfo() throws SQLException {
		int i=0;
		more_infoloaded = true;
		String query = "SELECT Facility, Floor, Other FROM convenientLocation WHERE BuildingName = ? ORDER BY Floor";
		PreparedStatement prep = DB.conn.prepareStatement(query);
		prep.setString(1, building.Kname);
		ResultSet rs = prep.executeQuery();
				
		while(rs.next()){
			convenients[i]=rs.getString("Facility");
			i++;
			convenients[i]=rs.getString("Floor");
			i++;
			convenients[i]=rs.getString("Other");
			i++;
		}
		convenients[i]="End";
	}
	
	public void showbeverage(int num) throws SQLException {
		int i=0;
		
		more_infoloaded = false;
		String query = "SELECT Beverage, Price FROM vending WHERE VendingMachineNumber = ?";
		PreparedStatement prep = DB.conn.prepareStatement(query);
		prep.setInt(1, num);
		ResultSet rs = prep.executeQuery();
		
		while(rs.next()){
			beverage_shown = true;
			beverages[i]=rs.getString("Beverage");
			beverages[i+1]=rs.getString("Price");
			i++;
			i++;
		}
		beverages[i]="End";
	}
	
	public void search_input() {
		building.clear();
		search_building.clear();
		infoloaded = false;
		
		Gdx.input.getTextInput(new TextInputListener() {
			
			@Override
			public void input(String text) {
				try {
					search_pointshown = false;
					
					String query = "SELECT * FROM buildings WHERE KoreanName LIKE ?";
					PreparedStatement prep = DB.conn.prepareStatement(query);
					prep.setString(1, "%" + text + "%");
					ResultSet rs = prep.executeQuery();
										
					while(rs.next()){
						search_pointshown = true;
						Building b = new Building();
						b.set(rs);
						search_building.add(b);
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void canceled() {
				// TODO Auto-generated method stub				
			}
		}, "건물 찾기", "예) 아산이학관");
	}
	
	public void icon_search(int x, int y) throws SQLException {
		String icon_text = null;
		int i=0;
		if(building_icon.overlaps(x, DB.context.screen_size.y - y)){
			infoloaded = false;
			search_input();
		} else if(beverage_icon.overlaps(x, DB.context.screen_size.y - y)){
			beverage_search();
		} else if(ATM_icon.overlaps(x, DB.context.screen_size.y - y)){
			icon_text = "ATM";
		} else if(PC_icon.overlaps(x, DB.context.screen_size.y - y)){
			icon_text = "공용PC";
//		} else if(water_icon.overlaps(x, DB.context.screen_size.y - y)){
//			icon_text = "정수기";
//		} else if(vending_icon.overlaps(x, DB.context.screen_size.y - y)){
//			icon_text = "자판기";
		} else if(cafeteria_icon.overlaps(x, DB.context.screen_size.y - y)){
			icon_text = "학식";
		} else if(bank_icon.overlaps(x, DB.context.screen_size.y - y)){
			icon_text = "은행";			
		} else if(cvstore.overlaps(x, DB.context.screen_size.y - y)){
			icon_text = "편의점";	
		} else if(lounge.overlaps(x, DB.context.screen_size.y - y)){
			icon_text = "휴게실";
		} else if(shower.overlaps(x, DB.context.screen_size.y - y)){
			icon_text = "샤워실";
		} else if(readingroom.overlaps(x, DB.context.screen_size.y - y)){
			icon_text = "열람실";
		}
		if(!building_icon.overlaps(x, DB.context.screen_size.y - y) && !beverage_icon.overlaps(x, DB.context.screen_size.y - y)){

			if(icon_text== "은행" || icon_text == "편의점"){
				try {
					String query = "SELECT x, y FROM externalConvenients WHERE Name = ?";
					PreparedStatement prep = DB.conn.prepareStatement(query);
					prep.setString(1, icon_text);
					ResultSet rs = prep.executeQuery();
	
					while(rs.next()){
						icon_searched = true;
						pointLocation[i]=rs.getInt("x")*4/3;
						pointLocation[i+1]=rs.getInt("y")*4/3;
						i++;
						i++;
					}
					pointLocation[i]=-1;
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			else{
				try {
					String query = "SELECT LocationX, LocationY FROM buildings WHERE KoreanName IN (SELECT BuildingName FROM convenientLocation WHERE Facility = ?)";
					PreparedStatement prep = DB.conn.prepareStatement(query);
					prep.setString(1, icon_text);
					ResultSet rs = prep.executeQuery();
	
					while(rs.next()){
						icon_searched = true;
						pointLocation[i]=rs.getInt("LocationX");
						pointLocation[i+1]=rs.getInt("LocationY");
						i++;
						i++;
					}
					pointLocation[i]=-1;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void beverage_search() throws SQLException {
		String best;
		String query3 = "SELECT BeverageName FROM beverage WHERE NOT SearchNumber = 0 AND SearchNumber = (SELECT MAX(SearchNumber) FROM beverage)";
		PreparedStatement prep3 = DB.conn.prepareStatement(query3);
		ResultSet rs3 = prep3.executeQuery();
		if(rs3.next()){
			best = rs3.getString("BeverageName");
		}
		else{
			best = "없음";
		}
		
		Gdx.input.getTextInput(new TextInputListener() {
			int i=0;
			
			@Override
			public void input(String text) {
				try {
					icon_searched = false;
					DB.conn.setAutoCommit(false);
					String query = "SELECT LocationX, LocationY FROM buildings WHERE KoreanName IN(SELECT BuildingName FROM convenientLocation WHERE Facility = '자판기' AND Other IN (SELECT VendingmachineNumber FROM vending WHERE Beverage LIKE ?))";
					String query2 = "UPDATE beverage SET SearchNumber = SearchNumber+1 WHERE BeverageName = ?";
					PreparedStatement prep = DB.conn.prepareStatement(query);
					PreparedStatement prep2 = DB.conn.prepareStatement(query2);
					prep.setString(1, text);
					prep2.setString(1, text);
					ResultSet rs = prep.executeQuery();
					prep2.executeUpdate();
					DB.conn.commit();

					while(rs.next()){
						icon_searched = true;
						pointLocation[i]=rs.getInt("LocationX");
						pointLocation[i+1]=rs.getInt("LocationY");
						i++;
						i++;
					}
					pointLocation[i]=-1;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void canceled() {
				// TODO Auto-generated method stub				
			}
		}, "가장 많이 찾은 음료수 : " + best, "예) 데자와");
	}
	
	public void clear() {
		infoloaded = false;
		more_infoloaded = false;
		search_pointshown = false;
		icon_searched = false;
		global_start = false;
		global_dst = false;
		beverage_shown = false;
		noload = false;
		route_found = false;
		route.clear();
		building.clear();
	}
	
	public void routeinfo() {
		r = route.calcTime();
		route_box.setCenter(DB.context.screen_size.x / 2, DB.context.screen_size.y - 20);
		route_found = true;
	}
	
	public void showuserbuilding(int date) throws SQLException {
		/* Mon = 1 ~ Sat = 6 */
		
		search_building.clear();
		String query = "SELECT * FROM buildings WHERE KoreanName IN(SELECT buildingname FROM timetable WHERE day = ?)";
		PreparedStatement prep = DB.conn.prepareStatement(query);
		prep.setInt(1, date);
		
		ResultSet rs = prep.executeQuery();
		
		while(rs.next()){
			search_pointshown = true;
			Building b = new Building();
			b.set(rs);
			search_building.add(b);
		}
	}
	
	public void showbutton(int x, int y){
		int k = 0;
		if(show_button1.overlaps(x,y))
			k = 1;
		if(show_button2.overlaps(x,y))
			k = 2;
		if(show_button3.overlaps(x,y))
			k = 3;
		if(show_button4.overlaps(x,y))
			k = 4;
		if(show_button5.overlaps(x,y))
			k = 5;
		
		if(k != 0)
			try {
				showbeverage(k);
			} catch (SQLException e) {
				e.printStackTrace();
			}		
	}
	
	public void tablesearch(int x, int y) {
		int k = 0;
		if(mon.overlaps(x, y))
			k = 1;
		if(tue.overlaps(x, y))
			k = 2;
		if(wed.overlaps(x, y))
			k = 3;
		if(tus.overlaps(x, y))
			k = 4;
		if(fri.overlaps(x, y))
			k = 5;
		if(sat.overlaps(x, y))
			k = 6;
		
		if(k !=0)
			try {
				showuserbuilding(k);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public void showbusstop() throws SQLException {
		search_building.clear();
		String query = "SELECT x,y FROM externalConvenients WHERE Name = '버스'";
		PreparedStatement prep = DB.conn.prepareStatement(query);
				
		ResultSet rs = prep.executeQuery();
		
		while(rs.next()){
			search_pointshown = true;
			Building b = new Building();
			b.LocationX = rs.getInt(1);
			b.LocationY = (int)DB.context.screen_size.y - rs.getInt(2);
			search_building.add(b);
		}
	}
}
