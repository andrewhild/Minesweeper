package cside;

import pside.GUI_main;
import javax.swing.JMenu;

public class Minesweeper {

	public static void main(String[] args) {
		int numMines=1;
		int x=9;
		int y=x;
		Board board=new Board(x,y,numMines);
		GUI_main gui=new GUI_main(board);
		gui.show();
	}
}