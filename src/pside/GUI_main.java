package pside;

import cside.Board;
import java.util.ArrayList;
import javax.swing.*;

public class GUI_main {
	//Graphics objects
	private JFrame frame;
	private JPanel menu,data,field;
	private JLabel mines, time;
	private ArrayList<JLabel> tiles = new ArrayList<JLabel>();
	//Game objects
	private Board board;
	public GUI_main(Board board) {
		this.board=board;
		frame=new JFrame("Minesweeper by Andrew Hild");
		for(int q=0; q<(board.getNumRows()*board.getNumCols());q++)
		{
			JLabel j = new JLabel(new ImageIcon("src.pside.tile.png"));
			tiles.add(j);
		}
	}
	
	public void show() {
		frame.pack();
		frame.setVisible(true);
	}

}
