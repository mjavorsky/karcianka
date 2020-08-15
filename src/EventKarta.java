import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EventKarta extends MouseAdapter
{
	Karta parent;
	int X;
	int Y;
	boolean drag;
	
	EventKarta(Karta parent)
	{
		this.parent = parent;
		drag = false;
	}

	@Override 
	public void mouseEntered(MouseEvent e)
    {
		if (parent.getZablokowana() == false)
			parent.setLocation(parent.getX(), parent.getY() - 10);
    }

	@Override 
	public void mouseExited(MouseEvent e)
    {
		parent.setLocation(parent.getX(), parent.getTalia().getLocation().y);
    }
	
	@Override 
	public void mouseDragged(MouseEvent e)
    {
		if(!e.isMetaDown()) {
			parent.setLocation(e.getXOnScreen() - X, e.getYOnScreen() - Y);
        
			drag = true;
		}
    }
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if (!drag)
		{
			X = e.getXOnScreen() - parent.getX();
			Y = e.getYOnScreen() - parent.getY();
		}
		
		parent.getTalia().emitPodniesionaKarta(parent);
	}
	
	@Override
    public void mouseClicked(MouseEvent e) 
	{
        if (e.getButton() == MouseEvent.BUTTON3)
        {
        	if (parent.getStan() == stanKarty.odkryta)
        	{
        		parent.setStan(stanKarty.wyrzucana);
        	}
        	else if (parent.getStan() == stanKarty.wyrzucana)
        	{
        		parent.setStan(stanKarty.odkryta);
        	}
        }
    }
	
	@Override
	public void mouseReleased(MouseEvent e) 
	{
		 drag = false;
		 
		 if (!parent.getTalia().toFriends(parent)) 						//Jeżeli NIE jest położona na żadnym z kolegów talii
		 {
			 //System.out.println("X: " + parent.getX());
			 //System.out.println("Y: " + parent.getY());
			 parent.setLocation(parent.getTalia().getLocation(parent));		//to wraca swoje miejsce
		 }
	}
}
