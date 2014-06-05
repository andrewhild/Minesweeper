package pside;
import cside.Board;
import cside.CoordButton;
import cside.Location;

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
	private JLabel mines, time;
	private int seconds=0;
	private GridLayout minefield;
	private ArrayList<CoordButton> tiles = new ArrayList<CoordButton>();
	
	//Game objects
	private Board board;
	private Timer timer;
	private Object eSource;
	
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
						if(((CoordButton)(source)).isEnabled())
						{
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
				}
			});
			b.addActionListener(this);
			tiles.add(b);
			c++;
		}
		for(CoordButton button:tiles) {
			field.add(button);
		}
		
		//Initialize game state informing objects
		menu=new JPanel();
		menu.add(new JLabel("Menu to go here"));
		data=new JPanel();
		mines=new JLabel("Mines:\n"+board.getNumMines());
		time=new JLabel("Time:\n"+seconds);
		data.add(time, BoxLayout.X_AXIS);
		data.add(mines, BoxLayout.Y_AXIS);
		//data.add(new JLabel("Time elapsed and mines remaining data to go here"));
		timer=new Timer(1000,this);
		
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
		if(source instanceof CoordButton)
		{
			if(seconds==0)
				timer.start();
			//record the last button pressed
			eSource=source;
		}
		else if(source==timer)
		{
			seconds++;
			time.setText("Time elapsed:\n"+seconds);
			//frame.validate();
		}
	}
	
	public void update() {
		int won=board.isWon();
		if(won==2)
		{
			for(CoordButton c:tiles)
			{
				int[] xy=c.getXY();
				if(board.self()[xy[0]][xy[1]].isClicked())
					if(board.self()[xy[0]][xy[1]].isMined()){
						if(c==eSource)
							c.setIcon(new ImageIcon("src/pside/tileexplode.png"));
						else
						{
							ImageIcon i= new ImageIcon("src/pside/tilemine.png");
							c.setIcon(i);
						}
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
			mines.setText("Remaining mines:\n"+(board.getNumMines()-board.flaggedLocs().size()));
		}
		else if(won==0)
		{
			timer.stop();
			for(CoordButton c:tiles)
			{
				int[] xy=c.getXY();
				Location L=board.self()[xy[0]][xy[1]];
				if(L.isFlagged()&&!L.isMined())
					c.setIcon(new ImageIcon("src/pside/tilebadflag.png"));
				if(L.isMined())
					c.setIcon(new ImageIcon("src/pside/tilemine.png"));
				if(c==eSource)
					c.setIcon(new ImageIcon("src/pside/tileexplode.png"));
				c.setDisabledIcon(c.getIcon());
				c.setEnabled(false);
			}		
			JOptionPane.showMessageDialog(frame, "You have lost.", "", JOptionPane.WARNING_MESSAGE);	
		}
		else
		{
			timer.stop();
			for(CoordButton c:tiles)
			{
				int[] xy=c.getXY();
				Location L=board.self()[xy[0]][xy[1]];
				if(L.isFlagged())
					c.setIcon(new ImageIcon("src/pside/tileflag.png"));
				c.setDisabledIcon(c.getIcon());
				c.setEnabled(false);
			}
			JOptionPane.showMessageDialog(frame, "You have won!","",JOptionPane.INFORMATION_MESSAGE);
		}
	}
}