package cside;

import pside.GUI_main;

public class Minesweeper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numMines=10;
		int x=9;
		int y=x;
		Board board=new Board(x,y,numMines);
		GUI_main gui=new GUI_main(board);
		gui.show();
		/*
		for(int i=0;i<x;i++)
		{
			for(int j=0;j<y;j++)
			{
				if(board.Self()[i][j].isMined())
					System.out.print(" * ");
				else
				System.out.print(" "+board.Self()[i][j].getMines()+" ");
			}
			System.out.println();
		}
		*/

	}
}
