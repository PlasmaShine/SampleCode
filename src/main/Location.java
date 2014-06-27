package main;

public class Location {

	public int x;
	public int y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Location aLocation) {
		return this.x == aLocation.x && this.y == aLocation.y;
	}

}
