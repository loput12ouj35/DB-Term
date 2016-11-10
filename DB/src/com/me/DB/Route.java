package com.me.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;

public class Route {
	public int x1 = 0;
	public int y1 = 0;
	public int x2 = 0;
	public int y2 = 0;
	
	public boolean start = false;
	public boolean dst = false;
	
	public class Dot {
		public final int id;
		public final int x;
		public final int y;
		public Dot(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
		
		public double len(double xx, double yy) {
			return Math.sqrt(Math.pow(xx - x, 2) + Math.pow(yy - y, 2));
		}
		
		public double len(Dot d) {
			return len(d.x, d.y);
		}
	}
	
	private class Edge {
		public final int n1;
		public final int n2;
		public Edge(int n1, int n2) {
			super();
			this.n1 = n1;
			this.n2 = n2;
		}
	}
	
	public class FoundRoute {
		public final ArrayList<Dot> nodes;
		public double dis;
		public int time;
		
		public FoundRoute() {
			this(new ArrayList<Dot>());
		}
		
		public FoundRoute(ArrayList<Dot> nodes) {
			super();
			this.nodes = nodes;
			this.dis = 0;
			this.time = 0;
			
			boolean anamstation = false;
			boolean fourwaystreet = false;
			boolean fucking = false;
			
			if (nodes.isEmpty()) {
				this.dis = Float.POSITIVE_INFINITY;
				this.time = Integer.MAX_VALUE;
			}
			
			if (nodes.size() < 2) {
				return;
			}
			
			for (int i = 0; i < nodes.size() - 1; ++i) {
				Dot d1 = nodes.get(i);
				Dot d2 = nodes.get(i+1);
				
				if (!anamstation && (d1.id == 63 || d2.id == 63)) {
					anamstation = true;
				}
				if (!fourwaystreet && (d1.id == 76 || d2.id == 76)){
					fourwaystreet = true;
				}
				if (!fucking && (d1.id == 13 || d2.id == 13)){
					fourwaystreet = true;
				}
				
				dis += d1.len(d2);
			}
			
			double ddd = dis;
			
			ddd *= 125;
			ddd /= 96;	
			ddd *= 15;
			ddd /= 1000;

			if (anamstation) {
				ddd += 2;
			}
			if (fourwaystreet) {
				ddd += 3;
			}
			if (fucking) {
				ddd += 2;
			}
			
			time = (int)ddd;
		}
	}

	Dot dot1 = null;
	Dot dot2 = null;

	public HashMap<Integer, Dot> dots = new HashMap<Integer, Route.Dot>();
	
	HashMap<Integer, ArrayList<Edge>> edges = new HashMap<Integer, ArrayList<Edge>>();
	
	public Route() {
		try {
			DB.conn.setAutoCommit(false);
			String query = "SELECT * FROM `path_node`";
			PreparedStatement prep = DB.conn.prepareStatement(query);
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) {
				dots.put(rs.getInt(1), new Dot(rs.getInt(1), rs.getInt(2), rs.getInt(3)));
			}
		} catch (SQLException e) {
		}
		

		try {
			DB.conn.setAutoCommit(false);
			String query = "SELECT * FROM `path_edge`";
			PreparedStatement prep = DB.conn.prepareStatement(query);
			ResultSet rs = prep.executeQuery();
			
			while (rs.next()) {
				if (!edges.containsKey(rs.getInt(2))) {
					edges.put(rs.getInt(2), new ArrayList<Edge>());
				}
				
				edges.get(rs.getInt(2)).add(new Edge(rs.getInt(2), rs.getInt(3)));
				
				if (!edges.containsKey(rs.getInt(3))) {
					edges.put(rs.getInt(3), new ArrayList<Edge>());
				}
				
				edges.get(rs.getInt(3)).add(new Edge(rs.getInt(3), rs.getInt(2)));
			}
		} catch (SQLException e) {
		}
	}
	
	public void clear() {
		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 0;
		start = false;
		dst = false;
	}
	
	public void setStart(int x, int y) {
		x1 = x;
		y1 = y;
		start = true;
		
		if(dst)
			calcTime();
	}
	
	public void setDst(int x, int y) {
		x2 = x;
		y2 = y;
		dst = true;
		
		if(start)
			calcTime();
	}

	private class RT {
		public final Dot prev;
		public final double dst;
		public RT(Dot prev, double dst) {
			super();
			this.prev = prev;
			this.dst = dst;
		}
	}
	
	private FoundRoute findroute(Dot start, Dot end) {
		HashMap<Dot, RT> prevs = new HashMap<Route.Dot, Route.RT>();
		Queue<Dot> queue = new LinkedList<Route.Dot>();
		
		queue.add(start);
		
		while (!queue.isEmpty()) {
			Dot d = queue.poll();
			
//			System.out.println(d.id);
			
			for (Edge e : edges.get(d.id)) {
				Dot t = dots.get(e.n2);
				
				double md = Double.POSITIVE_INFINITY;
				double dst = d.len(t);
				
				if (prevs.containsKey(d)) {
					md = prevs.get(d).dst;
				}
				
				dst += md;
								
				if (!prevs.containsKey(t) || dst < prevs.get(t).dst) {
					prevs.put(t, new RT(d, dst));
					queue.add(t);
				}
			}
		}
		
		ArrayList<Dot> ret = new ArrayList<Route.Dot>();
		
		Dot cur = end;
		
		 while (cur != start) {
			ret.add(cur);
			
			cur = prevs.get(cur).prev;
			
		}
		
		ret.add(cur);
		
		Collections.reverse(ret);
		
		return new FoundRoute(ret);
	}
//	
//	private FoundRoute findroute(Dot start, Dot end, ArrayList<Dot> nodes) {
//		nodes.add(start);
//		
////		System.out.println("sz: " + nodes.size());
////		System.out.println("id: " + start.id);
////		System.out.println("ed: " + end.id);
//		
//		for (int i = 0; i < nodes.size(); ++i) {
//			System.out.print(String.valueOf(nodes.get(i).id) + " ");
//		}
//		
//		if (!nodes.isEmpty()) {
//			System.out.println();
//		}
//		
//		if (start == end) {
//			return new FoundRoute(nodes);
//		}
//		
//		HashMap<Dot, FoundRoute> routes = new HashMap<Route.Dot, Route.FoundRoute>();
//		
////		System.out.println(start.id);
//		
//		for (Edge e : edges.get(start.id)) {
//			Dot nstart = dots.get(e.n2);
//			
//			if (nodes.contains(nstart)) {
//				continue;
//			}
//			
//			ArrayList<Dot> nused = (ArrayList<Dot>) nodes.clone();
//			
//			routes.put(nstart, findroute(nstart, end, nused));
//		}
//
//		FoundRoute ret = null;
//		double rtime = 0;
//		
//		if (routes.isEmpty()) {
//			return new FoundRoute();
//		}
//		
//		for (Entry<Dot, FoundRoute> r : routes.entrySet()) {
//			
//			double dis = start.len(r.getKey());
//			
//			dis *= 125;
//			dis /= 96;	
//			dis *= 15;
//			dis /= 1000;
//			
//			if (start.id == 28) {
//				dis += 2;
//			}
//			
//			double ntime = dis + r.getValue().time;
//			
////			System.out.println("rtime: " + ntime);
//			
//			if (ret == null) {
//				ret = r.getValue();
//				rtime = ntime;
//				continue;
//			}
//			
//			if (rtime > ntime) {
//				ret = r.getValue();
//				rtime = ntime;
//			}
//		}
//		
//		if (ret.nodes.isEmpty()) {
//			return new FoundRoute();
//		}
//		
//		for (int i = 0; i < ret.nodes.size(); ++i) {
//			System.out.print(String.valueOf(ret.nodes.get(i).id) + " ");
//		}
//		
//		if (!ret.nodes.isEmpty()) {
//			System.out.println();
//		}
//		
////		System.out.println(ret.nodes.size());
//		
//		return ret;
//	}
	
	public FoundRoute calcTime() {
		Dot start = null;
		Dot end = null;
		
		for (Entry<Integer, Dot> d : dots.entrySet()) {
			if (start == null) {
				start = d.getValue();
				end = d.getValue();
				continue;
			}
			
			if (start.len(x1, y1) > d.getValue().len(x1, y1)) {
				start = d.getValue();
			}
			
			if (end.len(x2, y2) > d.getValue().len(x2, y2)) {
				end = d.getValue();
			}
		}
				
		return findroute(start, end);
	}
	
}


