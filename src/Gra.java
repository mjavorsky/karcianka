import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileNotFoundException;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.util.*;

import javax.swing.*;

public class Gra extends JFrame implements ActionListener
{
	private static Gra instance;

	List<Ekran> listaEkranow;
	
	int width;
	int height;
	static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	private boolean gotowe = false;

	Gra()
	{
		super("Karcianka");
		
		instance = this;
		
		this.addMouseListener(new EventKursor(Gra.getInstance(), TypKursora.klikanie));
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage("Grafika/kursor1.png");
		Cursor c = toolkit.createCustomCursor(image, new Point(0, 
				0), "");
		this.setCursor(c);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		this.width = Ustawienia.getWidth();
		this.height = Ustawienia.getHeight();

		setSize(width, height);
		setPreferredSize(getSize());
		
		try
		{
			Ustawienia.wczytajUstawienia();
			this.ekranZUstawien();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println(ex.getMessage());
			wlaczPelnyEkran();
		}
		
		listaEkranow = new ArrayList<Ekran>();
		this.pushEkran(new Menu(this));

		
		this.repaint();
		setVisible(true);
		
		gotowe = true;
	}
	
	void pushEkran(Ekran ekran)
	{
		if (listaEkranow.size() > 0) 
			{
				this.remove(listaEkranow.get(listaEkranow.size() - 1));
			}
		
		listaEkranow.add(ekran);
		this.add(ekran);
		ekran.skaluj();
		
		this.pack();
		this.repaint();
	}
	void popEkran()
	{
		this.remove(listaEkranow.get(listaEkranow.size() - 1));
		listaEkranow.remove(listaEkranow.size() - 1);
		this.add(listaEkranow.get(listaEkranow.size() - 1));

		this.repaint();
	}

	void koniecProgramu()
	{
		System.out.println("DUPA");
	}

	public void zmienRozdzielczosc(int width, int height)
	{
		Ustawienia.ustawRozdzielczosc(width, height);
		Ustawienia.ustawSkale();

		this.width = width;
		this.height = height;
		
		setPreferredSize(new Dimension(width, height));
		setSize(width, height);
		
		if (gotowe == true)
		{
			for (Ekran i : listaEkranow)
			{
				remove(i);
			}
			
			listaEkranow.clear();
			this.pushEkran(new Menu(this));
		}
	}
	
	public void wlaczPelnyEkran()
	{
		if (gotowe == true)
		{
			this.dispose();
		}
		
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		device.setFullScreenWindow(this);
		setLocationRelativeTo(null);
		this.setVisible(true);

		Ustawienia.setPelnyEkran(true);

		this.zmienRozdzielczosc(java.awt.Toolkit.getDefaultToolkit().getScreenSize().width, 
				java.awt.Toolkit.getDefaultToolkit().getScreenSize().height);
	}
	
	public void wylaczPelnyEkran()
	{
		if (gotowe == true)
		{
			this.dispose();
		}
		
		this.setUndecorated(false);
		device.setFullScreenWindow(null);
		setExtendedState(JFrame.NORMAL);
		this.setVisible(true);
		
		Ustawienia.setPelnyEkran(false);

		this.zmienRozdzielczosc(Ustawienia.getWidth(), Ustawienia.getHeight());
	}

	public void ekranZUstawien()
	{
		if (Ustawienia.getPelnyEkran())
		{
			wlaczPelnyEkran();
		}
		else
		{
			wylaczPelnyEkran();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		
	}
	
	public static Gra getInstance() {
		return instance;
	}
}
