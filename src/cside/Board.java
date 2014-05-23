package cside;

public class Board {
private Location[][] self;
private int numRow, numCol;
	public Board(int x, int y, int numMines) {
		numRow=x;
		numCol=y;
		self=new Location[numRow][numCol];
		//Populate the board with locations
		for(int i=0;i<numRow;i++)
		{
			for(int j=0;j<numCol;j++)
			{
				self[i][j]=new Location(i,j);
			}
		}
		//Mine random squares with numMines mines
		for(int q=0;q<numMines;q++)
		{
			int randnumRow=(int)(numRow*Math.random());
			int randnumCol=(int)(numCol*Math.random());
			while(self[randnumRow][randnumCol].isMined())
			{
				randnumRow=(int)(numRow*Math.random());
				randnumCol=(int)(numCol*Math.random());
			}
			self[randnumRow][randnumCol].setMine();
		}
		//Count number of mines adjacent to each location for easier manipulation later on
		for(x=0;x<numRow;x++)
		{
			for(y=0;y<numCol;y++)
			{			
				//define search locations
				Location L=self[x][y];
				int adjmines=0;
				int i=x-1;
				int i1=x+1;
				int j=y-1;
				int j1=y+1;
				
				if(i<0)
					i++;
				if(i1>=numRow)
					i1--;
				if(j<0)
					j++;
				if(j1>=numCol)
					j1--;
				//search for mines		
				for(int q=i;q<=i1;q++)
				{
					for(int r=j;r<=j1;r++)
					{
						if(self[q][r].isMined()&&self[q][r]!=L)
						{
							adjmines+=1;
						}
					}
				}
				L.setAdjacent(adjmines);
				
			}
		}
	}
	
	public Location[][] Self() {
		return self;
	}
	
	public int getNumRows() {
		return numRow;
	}
	
	public int getNumCols() {
		return numCol;
	}
	
	
}