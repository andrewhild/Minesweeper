package pside;

import cside.Board;
import cside.CoordButton;
import cside.Location;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.*;

@SuppressWarnings("serial")
public class GameObject extends JFrame implements ActionListener, MouseListener{
	//General variables
	private final int tileside=20;
	
	//Graphics objects
	private JPanel data,field;
	private JLabel mines, time;
	private int seconds=0;
	private GridLayout minefield;
	private ArrayList<CoordButton> tiles;
	
	//Menu objects
	private JMenuBar menu;
	private JMenu game,help;
	private JMenuItem newgame,hiscore,exit,about,howto;
	private JRadioButtonMenuItem beginner, intermediate, advanced;
	
	//Game objects
	private Board board;
	private Timer timer;
	private Object eSource;
	private Scanner scan;
	private ArrayList<Integer> list;
	
	public GameObject(Board dmz) {
		board=dmz;
		//initialize the frame
		setTitle("Minesweeper by Andrew Hild");
		ImageIcon i = new ImageIcon("src/pside/tileexplode.png");
		setIconImage(i.getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		//initialize the mine field
		minefield=new GridLayout(board.getNumRows(),board.getNumCols());
		field=new JPanel();
		field.setLayout(minefield);
		configure();
		
		//Initialize game state informing objects
		data=new JPanel();
		data.setLayout(new BorderLayout());
		mines=new JLabel("Mines:\n"+board.getNumMines());
		time=new JLabel("Time:\n"+seconds);
		data.add(time, BorderLayout.WEST);
		data.add(mines, BorderLayout.EAST);
		//data.add(new JLabel("Time elapsed and mines remaining data to go here"));
		timer=new Timer(1000,this);
		try {
		scan=new Scanner(new File("src/cside/scores.txt")).useDelimiter("\\s");
		list=new ArrayList<Integer>();
		while(scan.hasNextInt())
			list.add(scan.nextInt());
		}
		catch (FileNotFoundException fnfe) {
			list=new ArrayList<Integer>();
			while(list.size()<3)
				list.add(new Integer(-1));
			JOptionPane.showMessageDialog(this, "Error: scores file not found.","System:",JOptionPane.ERROR_MESSAGE);
		}
		//Pack the frame
		this.getContentPane().add(data, BorderLayout.CENTER);
		this.getContentPane().add(field, BorderLayout.PAGE_END);
		this.setJMenuBar(getMenu());
		this.pack();
	}

	//Processes output for field buttons, menu buttons, and the timer
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
			time.setText("Time:\n"+seconds);
		}
		else if(source instanceof JMenuItem)
		{
			if(source==newgame)
			{
				timer.stop();
				seconds=0;
				time.setText("Time:\n" + seconds);
				board = new Board(board.getNumRows(),board.getNumCols(),board.getNumMines());
				mines.setText("Mines:\n"+board.getNumMines());
				configure();
				validate();
			}
			else if(source==beginner)
			{
				if(!timer.isRunning())
				{
				intermediate.setSelected(false);
				advanced.setSelected(false);
				board=new Board(9,9,10);
				minefield=new GridLayout(board.getNumRows(),board.getNumCols());
				field.setLayout(minefield);
				actionPerformed(new ActionEvent(newgame,ActionEvent.ACTION_PERFORMED,null));
				pack();
				}
			}
			else if(source==intermediate)
			{
				if(!timer.isRunning())
				{
				beginner.setSelected(false);
				advanced.setSelected(false);
				board=new Board(16,16,40);
				minefield=new GridLayout(board.getNumRows(),board.getNumCols());
				field.setLayout(minefield);
				actionPerformed(new ActionEvent(newgame,ActionEvent.ACTION_PERFORMED,null));
				pack();
				}
			}
			else if(source==advanced)
			{
				if(!timer.isRunning())
				{
				beginner.setSelected(false);
				intermediate.setSelected(false);
				board=new Board(16,30,99);
				minefield=new GridLayout(board.getNumRows(),board.getNumCols());
				field.setLayout(minefield);
				actionPerformed(new ActionEvent(newgame,ActionEvent.ACTION_PERFORMED,null));
				pack();
				}
			}
			/* To be included in a future release
			else if(source==custom)
			{
				if(!timer.isRunning())
				{
				JOptionPane.showInputDialog(this);
				minefield=new GridLayout(board.getNumRows(),board.getNumCols());
				//field.setPreferredSize(new Dimension(tileside*board.getNumRows(), tileside*board.getNumCols()));
				field.setLayout(minefield);
				actionPerformed(new ActionEvent(newgame,ActionEvent.ACTION_PERFORMED,null));
				pack();
				}
			
			}
			*/
			else if(source==hiscore)
			{
				String hiscores="High Scores:\nBeginner: ";
				hiscores+=list.get(0).toString()+"\n\n";
				hiscores+="Intermediate: "+list.get(1).toString()+"\n\n";
				hiscores+="Advanced: "+list.get(2).toString();
				JOptionPane.showMessageDialog(this, hiscores, "High scores", JOptionPane.PLAIN_MESSAGE);
			}
			else if(source==exit)
				this.dispose();
			else if(source==howto)
			{
			String helptext = "How to play Minesweeper:\n";
			helptext+="1. Click a square. It will reveal the number of mines adjacent to that square\n";
			helptext+="2. Don't click a mine! If you click one, you lose instantly.\n";
			helptext+="3. If you determine a square is a mine, right click to flag it.\n";
			helptext+="4. You win once all the mined squares are correctly flagged.\n";
			helptext+="5. The clock is running! Try to beat your best time at each of the three difficulties.\n";
			helptext+="6. To access options, use the \"Game\" menu. Good luck!";
			JOptionPane.showMessageDialog(this, helptext, "How to play:",JOptionPane.PLAIN_MESSAGE);
			}
			else if(source==about)
			{
				String aboutgame="Minesweeper v1.0\n";
				aboutgame+="by Andrew Hild, 06/05/2014";
				JOptionPane.showMessageDialog(this,aboutgame,"About",JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	private void update() {
		int won=board.isWon();
		if(won==2)
		{
			for(CoordButton c:tiles)
			{
				int[] xy=c.getXY();
				if(board.self()[xy[0]][xy[1]].isClicked())
				{
					if(board.self()[xy[0]][xy[1]].getMines()>0){
						ImageIcon i = new ImageIcon("src/pside/tile"+board.self()[xy[0]][xy[1]].getMines()+".png");
						c.setIcon(i);
					}
					else 
						c.setIcon(new ImageIcon("src/pside/tileclicked.png"));
				}
				if(board.self()[xy[0]][xy[1]].isFlagged())
					c.setIcon(new ImageIcon("src/pside/tileflag.png"));	
			}
			mines.setText("Mines:\n"+(board.getNumMines()-board.flaggedLocs().size()));
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
				if(board.self()[xy[0]][xy[1]].isFlagged())
					c.setIcon(new ImageIcon("src/pside/tileflag.png"));
				if(c==eSource)
					c.setIcon(new ImageIcon("src/pside/tileexplode.png"));
				c.setDisabledIcon(c.getIcon());
				c.setEnabled(false);
			}		
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
			JOptionPane.showMessageDialog(this, "You have won!","",JOptionPane.INFORMATION_MESSAGE);
			int index = 0;
			if(board.getNumMines()==40)
				index=1;
			else if(board.getNumMines()==99)
				index=2;
			if(seconds<list.get(index))
			{
				JOptionPane.showMessageDialog(this, "You set a new high score for this level!","",JOptionPane.INFORMATION_MESSAGE);
				File scores=new File("src/cside/scores.txt");
				list.set(index, new Integer(seconds));
				try{
				PrintWriter print = new PrintWriter(scores);
				for(Integer i:list)
					print.write(""+i.toString()+" ");
				print.close();
				}
				catch(FileNotFoundException fnfe) {
					
				}
				finally {
					//print.close();
				}
			}
		}
	}
	
	private void configure() {
		//Initialize the mine buttons themselves
				int r=0;
				int c=0;
				tiles = new ArrayList<CoordButton>();
				field.removeAll();
				for(int q=0; q<(board.getNumRows()*board.getNumCols());q++)
				{
					if((q%(board.getNumCols())==0)&&q!=0) {
						r++;
						c=0;
					}
					CoordButton b = new CoordButton(new ImageIcon("src/pside/tile.png"),new int[] {r,c});
					b.addMouseListener(this);
					b.addActionListener(this);
					b.setPreferredSize(new Dimension(tileside+1,tileside+1));
					tiles.add(b);
					c++;
				}
				for(CoordButton button:tiles) {
					field.add(button);
				}
	}
	
	//Constructs the menu bar to keep all of this out of the constructor
	private JMenuBar getMenu() {
		//initialize the menu and add it to the frame
				menu=new JMenuBar();
				//set up the game menu
				game=new JMenu("Game");
				newgame=new JMenuItem("New game");
				newgame.addActionListener(this);
				//custom=new JMenuItem("Custom...");
				//custom.addActionListener(this);
				beginner=new JRadioButtonMenuItem("Beginner");
				beginner.setSelected(true);
				beginner.addActionListener(this);
				intermediate=new JRadioButtonMenuItem("Intermediate");
				intermediate.setSelected(false);
				intermediate.addActionListener(this);
				advanced=new JRadioButtonMenuItem("Advanced");
				advanced.setSelected(false);
				advanced.addActionListener(this);
				hiscore=new JMenuItem("High scores");
				hiscore.addActionListener(this);
				exit=new JMenuItem("Exit");
				exit.addActionListener(this);
				game.add(newgame);
				game.addSeparator();
				game.add(beginner);
				game.add(intermediate);
				game.add(advanced);
				//game.add(custom);
				game.addSeparator();
				game.add(hiscore);
				game.addSeparator();
				game.add(exit);
				//set up the help menu
				help=new JMenu("Help");
				howto=new JMenuItem("How to play");
				howto.addActionListener(this);
				about=new JMenuItem("About Minesweeper");
				about.addActionListener(this);
				help.add(howto);
				help.add(about);
				//set up menu
				menu.add(game);
				menu.add(help);
				return menu;
	}

	@Override
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
					if(!board.self()[xy[0]][xy[1]].isFlagged())
						((JButton) source).setIcon(new ImageIcon("src/pside/tile.png"));
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

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}