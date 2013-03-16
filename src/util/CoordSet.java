package util;

import game.Coord;

import java.util.Iterator;
import java.util.LinkedList;

public class CoordSet implements Iterable<Coord> {
	
	private LinkedList<Coord> coords;
	
	public CoordSet () {
		
		coords = new LinkedList<Coord>();
		
	}
	
	public void add (Coord coord) {
		
		for (Coord c : coords) {
			if (c.equals(coord)) {
				return;
			}
		}
		
		coords.add(coord);
		
	}
	
	public void add (CoordSet coordSet) {
		
		for (Coord d : coordSet) {
			add(d);
		}
		
	}

	public void remove(CoordSet coordSet) {

		for(Coord c : coordSet) {
			coords.remove(c);
		}
		
	}
	
	public int size() {
		
		return coords.size();
		
	}
	
	public boolean contains (Coord coord) {
		
		for (Coord c : coords) {
			if (c.equals(coord)) {
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public Iterator<Coord> iterator() {

		return coords.iterator();
		
	}

}
