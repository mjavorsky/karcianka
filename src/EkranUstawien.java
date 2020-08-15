import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class EkranUstawien extends Ekran implements ActionListener
{

	private Obrazek tlo;
	private Obrazek tlo_male;
	private Obrazek napisPelnyEkran;
	private Obrazek poziomTrudnosci;
	private Przycisk poziomTrudnosciPrzycisk;
	private Przycisk powrot;
	private Przycisk pelnyEkranTAK;
	private Przycisk pelnyEkranNIE;
	private Przycisk duzaRozdzielczosc;
	private Przycisk sredniaRozdzielczosc;
	private Przycisk malaRozdzielczosc;
	private RozwijaneMenu menu;
	private JLabel poziomTrudnosciNapis;
	
	public EkranUstawien(Gra parent) 
	{
		super(parent);
		
		//PelnyEkran = Ustawienia.getPelnyEkran();
		
		try
		{
			makeGUI();
		}
		catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	public void makeGUI()  throws IOException 
	{
		poziomTrudnosciNapis = new JLabel("Łatwy", SwingConstants.CENTER);
		poziomTrudnosciNapis.setBounds(655, 523, 103, 43);
		
		poziomTrudnosciPrzycisk = new Przycisk("Przycisk/ptPrzycisk.png", "Przycisk/ptPrzycisk2.png");
		poziomTrudnosciPrzycisk.addActionListener(this);
		poziomTrudnosciPrzycisk.setActionCommand("pt");
		poziomTrudnosciPrzycisk.setBounds(655, 523, 103, 43);
		add(poziomTrudnosciPrzycisk);
		add(poziomTrudnosciNapis);
		poziomTrudnosciPrzycisk.addMouseMotionListener(new MouseAdapter() 
		{ 
			@Override 	
			public void mouseDragged(MouseEvent e)
		    {
				przesunieciePoziomuTrudnosci(e.getXOnScreen());
		    }
		}
		);
		
		powrot = new Przycisk("Przycisk/powrot.png", "Przycisk/powrot2.png");
		powrot.addActionListener(this);
		powrot.setActionCommand("menu");
		powrot.setBounds(300, 500, 128, 146);
		add(powrot);
		
		pelnyEkranTAK = new Przycisk("Przycisk/notok.png", "Przycisk/notok2.png");
		pelnyEkranTAK.addActionListener(this);
		pelnyEkranTAK.setActionCommand("pelnyEkranTAK");
		pelnyEkranTAK.setBounds(885, 235, 70, 70);
		add(pelnyEkranTAK);
		
		pelnyEkranNIE = new Przycisk("Przycisk/ok.png", "Przycisk/ok2.png");
		pelnyEkranNIE.addActionListener(this);
		pelnyEkranNIE.setActionCommand("pelnyEkranNIE");
		pelnyEkranNIE.setBounds(885, 235, 70, 70);
		add(pelnyEkranNIE);

		menu = new RozwijaneMenu(667, 319, 280, 45);
		duzaRozdzielczosc = new Przycisk("Przycisk/1920on.png", "Przycisk/1920on2.png");
		duzaRozdzielczosc.setOffIcon("Przycisk/1920off.png");
		duzaRozdzielczosc.addActionListener(this);
		duzaRozdzielczosc.setActionCommand("duzaRozdzielczosc");
		duzaRozdzielczosc.setBounds(0, 0, 280, 45);
		add(duzaRozdzielczosc);
		menu.dodajPrzycisk(duzaRozdzielczosc);

		sredniaRozdzielczosc = new Przycisk("Przycisk/1600on.png", "Przycisk/1600on2.png");
		sredniaRozdzielczosc.setOffIcon("Przycisk/1600off.png");
		sredniaRozdzielczosc.addActionListener(this);
		sredniaRozdzielczosc.setActionCommand("sredniaRozdzielczosc");
		sredniaRozdzielczosc.setBounds(0, 0, 280, 45);
		add(sredniaRozdzielczosc);
		menu.dodajPrzycisk(sredniaRozdzielczosc);

		malaRozdzielczosc = new Przycisk("Przycisk/1280on.png", "Przycisk/1280on2.png");
		malaRozdzielczosc.setOffIcon("Przycisk/1280off.png");
		malaRozdzielczosc.addActionListener(this);
		malaRozdzielczosc.setActionCommand("malaRozdzielczosc");
		malaRozdzielczosc.setBounds(0, 0, 280, 45);
		add(malaRozdzielczosc);
		menu.dodajPrzycisk(malaRozdzielczosc);
		
		switch (Ustawienia.getWidth())
		{
		case 1920: menu.zwin(duzaRozdzielczosc); break;
		case 1600: menu.zwin(sredniaRozdzielczosc); break;
		default: menu.zwin(malaRozdzielczosc); break;
		}
		
		
		if (Ustawienia.getPelnyEkran())
		{
			pelnyEkranTAK.wylacz();
			menu.getNaWierzchu().setEnabled(false);
		}
		else
		{
			pelnyEkranNIE.wylacz();
			menu.getNaWierzchu().setEnabled(true);
		}
		poziomTrudnosci = new Obrazek("/Grafika/pt.png", 644, 484, 330, 93);
		add(poziomTrudnosci.getWyglad());
		
		napisPelnyEkran = new Obrazek("/Grafika/pelny_ekran.png", 667, 248, 200, 45);
		add(napisPelnyEkran.getWyglad());
		
		tlo_male = new Obrazek("/Grafika/brownPanel.png", 580, 182, 450, 450);
		add(tlo_male.getWyglad());
		
		tlo = new Obrazek("/Grafika/tlo_ustawienia.png", 0, 0, 1600, 900);
		add(tlo.getWyglad());
	}
	
	private void przesunieciePoziomuTrudnosci(int x)
	{
		if (x < Ustawienia.skaluj(759))
		{
			przesunieciePoziomuTrudnosci(PoziomTrudnosci.latwy);
		}
		else if (x < Ustawienia.skaluj(861))
		{
			przesunieciePoziomuTrudnosci(PoziomTrudnosci.sredni);
		}
		else if (x < Ustawienia.skaluj(964))
		{
			przesunieciePoziomuTrudnosci(PoziomTrudnosci.trudny);
		}
	}
	
	private void przesunieciePoziomuTrudnosci(PoziomTrudnosci pt)
	{
		if (pt == PoziomTrudnosci.latwy)
		{
			poziomTrudnosciPrzycisk.setLocation(Ustawienia.skaluj(655), poziomTrudnosciPrzycisk.getY());
			poziomTrudnosciNapis.setLocation(Ustawienia.skaluj(655), poziomTrudnosciPrzycisk.getY());
			poziomTrudnosciNapis.setText("Łatwy");
			Ustawienia.zmienPoziomTrudnosci(PoziomTrudnosci.latwy);
		}
		else if (pt == PoziomTrudnosci.sredni)
		{
			poziomTrudnosciPrzycisk.setLocation(Ustawienia.skaluj(758),  poziomTrudnosciPrzycisk.getY());
			poziomTrudnosciNapis.setLocation(Ustawienia.skaluj(758), poziomTrudnosciPrzycisk.getY());
			poziomTrudnosciNapis.setText("Średni");
			Ustawienia.zmienPoziomTrudnosci(PoziomTrudnosci.sredni);
		}
		else if (pt == PoziomTrudnosci.trudny)
		{
			poziomTrudnosciPrzycisk.setLocation(Ustawienia.skaluj(861),  poziomTrudnosciPrzycisk.getY());
			poziomTrudnosciNapis.setLocation(Ustawienia.skaluj(861), poziomTrudnosciPrzycisk.getY());
			poziomTrudnosciNapis.setText("Trudny");
			Ustawienia.zmienPoziomTrudnosci(PoziomTrudnosci.trudny);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String action = e.getActionCommand();
		
		if (action.equals("menu"))
		{
			try
			{
				Ustawienia.zapiszUstawienia();
			}
			catch (FileNotFoundException ex)
			{
				System.out.println(ex.getMessage());
			}
			
			parent.popEkran();
		}
		
		if (action.equals("pelnyEkranTAK"))
		{
			parent.wlaczPelnyEkran();
			parent.pushEkran(new EkranUstawien(parent));
		}
		
		if (action.equals("pelnyEkranNIE"))
		{
			parent.wylaczPelnyEkran();
			parent.pushEkran(new EkranUstawien(parent));
		}
		
		if (action.equals("duzaRozdzielczosc"))
		{
			if (menu.getRozwiniete())
			{
				parent.zmienRozdzielczosc(1920, 1080);
				parent.pushEkran(new EkranUstawien(parent));
			}
			else
			{
				menu.rozwin();
			}
		}
		
		if (action.equals("sredniaRozdzielczosc"))
		{
			if (menu.getRozwiniete())
			{
				parent.zmienRozdzielczosc(1600, 900);
				parent.pushEkran(new EkranUstawien(parent));
			}
			else
			{
				menu.rozwin();
			}
		}
		
		if (action.equals("malaRozdzielczosc"))
		{
			if (menu.getRozwiniete())
			{
				parent.zmienRozdzielczosc(1280, 720);
				parent.pushEkran(new EkranUstawien(parent));
			}
			else
			{
				menu.rozwin();
			}
		}
	}
	
	@Override
	public void skaluj() 
	{		
		int x = Ustawienia.skaluj(655);
		int y = Ustawienia.skaluj(523);
		int width = Ustawienia.skaluj(103);
		int height = Ustawienia.skaluj(43);

		poziomTrudnosciNapis.setFont(new Font("Verdana", Font.ITALIC | Font.BOLD, Ustawienia.skaluj(22)));
		poziomTrudnosciNapis.setBounds(x, y, width, height);
		poziomTrudnosciPrzycisk.skaluj();
		przesunieciePoziomuTrudnosci(Ustawienia.getPoziomTrudnosci());
	
		tlo.skaluj();
		powrot.skaluj();
		tlo_male.skaluj();
		napisPelnyEkran.skaluj();
		pelnyEkranNIE.skaluj();
		pelnyEkranTAK.skaluj();
		menu.skaluj();
		poziomTrudnosci.skaluj();
	}

}
