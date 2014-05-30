package pside;

import cside.Board;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;

public class GUI_main {
	//Graphics objects
	private JFrame frame;
	private JPanel menu,data,field;
	//private JLabel mines, time;
	private GridLayout minefield;
	private ArrayList<JButton> tiles = new ArrayList<JButton>();
	//Game objects
	//private Board board;
	public GUI_main(Board board) {
		//this.board=board;
		frame=new JFrame("Minesweeper by Andrew Hild");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		minefield=new GridLayout(board.getNumRows(),board.getNumCols());
		field=new JPanel();
		field.setLayout(minefield);
		for(int q=0; q<(board.getNumRows()*board.getNumCols());q++)
		{
			JButton j = new JButton(new ImageIcon("src/pside/tile.png"));
			tiles.add(j);
		}
		for(JButton button:tiles) {
			field.add(button);
		}
		menu=new JPanel();
		menu.add(new JLabel("Menu to go here"));
		data=new JPanel();
		data.add(new JLabel("Time elapsed and mines remaining data to go here"));
		
		
		frame.getContentPane().add(menu, BorderLayout.PAGE_START);
		frame.getContentPane().add(data, BorderLayout.CENTER);
		frame.getContentPane().add(field, BorderLayout.PAGE_END);
	}
	
	public void show() {
		frame.pack();
		frame.setVisible(true);
	}

}