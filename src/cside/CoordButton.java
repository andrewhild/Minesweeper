package cside;

import javax.swing.Icon;
import javax.swing.JButton;

public class CoordButton extends JButton {
	
	private int[] coords;
	
	public CoordButton(String s, int[] xy) {
		super(s);
		coords=xy;
	}
	
	public CoordButton(Icon i, int[] xy) {
		super(i);
		coords=xy;
	}
	
	public CoordButton(String s,Icon i, int[] xy) {
		super(s,i);
		coords=xy;
	}
	
	public int[] getXY() {
		return coords;
	}
	
	public int[] setXY(int[] xy) {
		coords=xy;
		return coords;
	}

}
