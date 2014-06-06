package cside;

import pside.GameObject;

public class Minesweeper {

	public static void main(String[] args) {
		int numMines=10;
		int x=9;
		int y=9;
		Board board=new Board(x,y,numMines);
		GameObject gui=new GameObject(board);
		gui.pack();
		gui.setVisible(true);
	}
}