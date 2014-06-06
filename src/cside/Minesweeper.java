package cside;

import pside.GUI_main;

public class Minesweeper {

	public static void main(String[] args) {
		int numMines=99;
		int x=16;
		int y=30;
		Board board=new Board(x,y,numMines);
		GUI_main gui=new GUI_main(board);
		gui.pack();
		gui.setVisible(true);
	}
}