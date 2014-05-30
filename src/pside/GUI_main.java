package pside;

import cside.Board;
import cside.CoordButton;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class GUI_main implements ActionListener {
	//Graphics objects
	private JFrame frame;
	private JPanel menu,data,field;
	//private JLabel mines, time;
	private GridLayout minefield;
	private ArrayList<CoordButton> tiles = new ArrayList<CoordButton>();
	//Game objects
	private Board board;
	public GUI_main(Board dmz) {
		board=dmz;
		frame=new JFrame("Minesweeper by Andrew Hild");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		minefield=new GridLayout(board.getNumRows(),board.getNumCols());
		field=new JPanel();
		field.setLayout(minefield);
		int r=0;
		int c=0;
		for(int q=0; q<(board.getNumRows()*board.getNumCols());q++)
		{
			if((q%(board.getNumCols())==0)&&q!=0) {
				r++;
				c=0;
			}
			CoordButton j = new CoordButton(new ImageIcon("src/pside/tile.png"),new int[] {r,c});
			j.addActionListener(this);
			tiles.add(j);
			c++;
		}
		for(CoordButton button:tiles) {
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

	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		String s = source.getClass().getName();
		if(s.equalsIgnoreCase("cside.CoordButton"))
		{
			int[] xy=((CoordButton)source).getXY();
			board.self()[xy[0]][xy[1]].click();
			board.dig(xy);
			JOptionPane.showMessageDialog(frame, ""+xy[0]+","+xy[1], "You clicked", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

}