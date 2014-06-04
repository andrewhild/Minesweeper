package cside;

public class Location
{
private int[] coords={0,0};
private int numAdj=0;
private boolean isMined=false;
private boolean isFlagged=false;
private boolean isClicked=false;
public Location(int x, int y) {
coords[0]=x;
coords[1]=y;
}

public int[] getCoords() {
	return coords;
}

public boolean isMined() {
	return isMined;
}

public void setMine() {
	isMined=true;
}

public boolean isFlagged() {
	return isFlagged;
}

public void flag() {
	isFlagged = !isFlagged;
}

public boolean isClicked() {
	return isClicked;
}

public void click() {
	isClicked=true;
}

public void setAdjacent(int x) {
	numAdj=x;
}

public int getMines() {
	return numAdj;
}
}