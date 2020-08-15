import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;


public class EventPrzycisk extends MouseAdapter {

	Przycisk parent;
	ImageIcon icon1;
	ImageIcon icon2;
	
	public EventPrzycisk(Przycisk parent, ImageIcon icon1, ImageIcon icon2) {
		this.parent = parent;
		this.icon1 = icon1;
		this.icon2 = icon2;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		//ImageIcon ic = new ImageIcon("Przycisk/tabliczka2.png");
		//parent.setIcon(ic);
		super.mouseClicked(e);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		parent.setIcon(icon1);
		//super.mouseReleased(e);
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		parent.setIcon(icon2);
		//super.mouseEntered(e);
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		parent.setIcon(icon1);
		//super.mouseExited(e);
	}
}
