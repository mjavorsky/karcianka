import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;


public abstract class Ekran extends JPanel implements ActionListener
{
	Gra parent;
	
	public Ekran(Gra parent)
	{
		this.parent = parent;
		setLayout(null);
		
		this.setLocation(0, 0);
		this.setSize(parent.getWidth(), parent.getHeight());
	}

	public abstract void skaluj();
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
