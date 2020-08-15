import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Window;

public class EventKursor extends MouseAdapter {

	Window parent;
	
	TypKursora typ;
	
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image cursor1 = toolkit.getImage("Grafika/kursor1.png");
	Image cursor2 = toolkit.getImage("Grafika/kursor2.png");
	Image cursor3 = toolkit.getImage("Grafika/kursor3.png");
	Cursor c;
	
	Point p = new Point(0, 0);
	
	public EventKursor(Window parent, TypKursora typ) {
		this.parent = parent;
		this.typ = typ;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		switch(typ) {
			case klikanie:
				c = toolkit.createCustomCursor(cursor2, p, "");
				break;
			case przesuwanie:
				if(!e.isMetaDown()) {
					c = toolkit.createCustomCursor(cursor3, p, "");
				}
				else {
					c = toolkit.createCustomCursor(cursor2, p, "");
				}
				break;
		}
		parent.setCursor(c);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
		c = toolkit.createCustomCursor(cursor1, p, "");
		parent.setCursor(c);
	}
}
