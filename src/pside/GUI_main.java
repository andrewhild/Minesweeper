package pside;

import cside.Board;
import cside.CoordButton;
import cside.Location;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

public class GUI_main implements ActionListener {
	//General variables
	private final int tileside=21;
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
		//initialize the frame
		frame=new JFrame("Minesweeper by Andrew Hild");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frame.setResizable(false);
		//initialize the mine field
		minefield=new GridLayout(board.getNumRows(),board.getNumCols());
		field=new JPanel();
		field.setPreferredSize(new Dimension(tileside*board.getNumRows(), tileside*board.getNumCols()));
		field.setLayout(minefield);
		//Initialize the mine buttons themselves
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
		
		if(source instanceof CoordButton)
		{
			int[] xy=((CoordButton)source).getXY();
			//board.self()[xy[0]][xy[1]].click();
			board.dig(xy);
			//JOptionPane.showMessageDialog(frame, ""+xy[0]+","+xy[1], "You clicked", JOptionPane.INFORMATION_MESSAGE);
			update();
		}
		
	}
	
	private void update() {
		for(CoordButton c:tiles)
		{
			int[] xy=c.getXY();
			if(board.self()[xy[0]][xy[1]].isClicked())
				if(board.self()[xy[0]][xy[1]].isMined()){
					ImageIcon i= new ImageIcon("src/pside/tilemine.png");
					c.setIcon(i);
				}

				else if(board.self()[xy[0]][xy[1]].getMines()>0){
					ImageIcon i = new ImageIcon("src/pside/tile"+board.self()[xy[0]][xy[1]].getMines()+".png");
					c.setIcon(i);
				}

				else {
					ImageIcon i= new ImageIcon("src/pside/tileclicked.png");
					c.setIcon(i);
				}

		}
		field.validate();
	
	}

}