package pside;
import cside.Board;
import cside.CoordButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
		frame.setPreferredSize(new Dimension(tileside*board.getNumRows()+10, tileside*board.getNumCols()+90));
		frame.setResizable(false);
		
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
			CoordButton b = new CoordButton(new ImageIcon("src/pside/tile.png"),new int[] {r,c});
			b.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					//System.out.println("You used mouse button "+e.getButton());
					if(e.getComponent() instanceof CoordButton)
					{
						Object source=e.getComponent();
						int[] xy=((CoordButton)(source)).getXY();
						if(e.getButton()==3)
						{
							board.self()[xy[0]][xy[1]].flag();
							update();
						}
						else
						{
							board.dig(xy);
							update();
						}		
					}
				}
			});
			tiles.add(b);
			c++;
		}
		for(CoordButton button:tiles) {
			field.add(button);
		}
		menu=new JPanel();
		menu.add(new JLabel("Menu to go here"));
		data=new JPanel();
		data.add(new JLabel("Time elapsed and mines remaining data to go here"));
		
		//Pack the frame
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
		System.out.println(source.hashCode());
		
	}
	
	public void update() {
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
			else if(board.self()[xy[0]][xy[1]].isFlagged())
			{
				ImageIcon i= new ImageIcon("src/pside/tileflag.png");
				c.setIcon(i);
			}
		}
	
	}

}