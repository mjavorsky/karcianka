import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;

public class OknoInformacyjne extends JDialog implements ActionListener//, Runnable
{
	public enum Rodzaj 
	{
        OK, YESNO;
    }
	
	private JLabel napis;
	private Przycisk ok = null;
	private Przycisk yes = null;
	private Przycisk no = null;
	private Obrazek tlo;
	private Rodzaj rodzajOkna;
	private String odpowiedz;
	private JFrame parent;
	
	public OknoInformacyjne(String napis, Rodzaj rodzaj, JFrame parent)
	{
		super(parent, "", true);
		this.setLayout(null);
		this.setModal(true);
		this.setUndecorated(true);
		
		this.addMouseListener(new EventKursor(this, TypKursora.klikanie));
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("Grafika/kursor1.png");
		Cursor c = toolkit.createCustomCursor(image, new Point(0, 0), "");
		this.setCursor(c);

		rodzajOkna = rodzaj;
		odpowiedz = "null";
		
//		JPanel a = new JPanel();
//		a.setBounds(0, 0, 400, 265);
//		this.add(a);
		
		this.napis = new JLabel(napis, SwingConstants.CENTER);
		this.napis.setFont(new Font("Verdana", Font.BOLD, 24));
		this.napis.setBounds(0, 20, 400, 105);
		
		if (rodzajOkna == Rodzaj.OK)
		{
			ok = new Przycisk("Przycisk/OknoOk.png", "Przycisk/OknoOk2.png");
			ok.addActionListener(this);
			ok.setActionCommand("ok");
			ok.setBounds(0, 125, 400, 140);

			this.add(ok);
		}
		else if (rodzajOkna == Rodzaj.YESNO)
		{
			yes = new Przycisk("Przycisk/Tak.png", "Przycisk/Tak2.png");
			yes.addActionListener(this);
			yes.setActionCommand("yes");
			yes.setBounds(70, 170, 125, 45);
			
			no = new Przycisk("Przycisk/Nie.png", "Przycisk/Nie2.png");
			no.addActionListener(this);
			no.setActionCommand("no");
			no.setBounds(205, 170, 125, 45);
			
			this.add(yes);
			this.add(no);
		}
		
		try
		{
			tlo = new Obrazek("/Grafika/okno.png", 0, 0, 400, 265);
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}
		
		this.add(this.napis);
		this.add(tlo.getWyglad());
		
		this.setPreferredSize(new Dimension(400, 265));
		this.setMinimumSize(new Dimension(400, 265));
		this.setMaximumSize(new Dimension(400, 265));
		this.setSize(400, 265);
		
		this.setLocation(((Ustawienia.getWidth() / 2) - (this.getWidth() / 2)), 
			    (Ustawienia.getHeight() / 2) - (this.getHeight() / 2));
		
		this.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String action = e.getActionCommand();
		
		if (action.equals("ok"))
		{
			this.setVisible(false);
			this.odpowiedz = "OK";
			this.dispose();
		}

		if (action.equals("yes"))
		{
			this.setVisible(false);
			this.odpowiedz = "YES";
			this.dispose();
		}

		if (action.equals("no"))
		{
			this.setVisible(false);
			this.odpowiedz = "NO";
			this.dispose();
		}
	}
	
	public String getOdpowiedz()
	{
		return odpowiedz;
	}

//	@Override
//	public void run() 
//	{
//		while (true) 
//		{
//			if (odpowiedz.equals("null")) 
//			{
//				try 
//				{
//					Thread.sleep(1000);
//				} 
//				catch (InterruptedException e1) 
//				{
//					break;
//				}
//			}
//		}
//		
//	}
}
