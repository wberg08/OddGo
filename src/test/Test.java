package test;

import game.Coord;

import java.util.HashSet;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		HashSet<Coord> what = new HashSet<Coord>();
		
		what.add(new Coord(1,2));
		what.add(new Coord(1,2));
		what.add(new Coord(5,5));
		
		if(what.contains(new Coord(1,2))) {
			System.out.println("Set contains 1,2.");
		}
		
		if((new Coord(1,5)).equals(new Coord(1,5))) {
//			fizzgig();
		}
		
		for (Coord c : what) {
			System.out.println("Coord: " + c.getX() + ", " + c.getY());
		}

	}

}
