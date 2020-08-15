import java.awt.Color;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.Timer;


public class Granie extends Ekran implements ActionListener, EventTalia
{
	taliaKart stosZakryty ;
	taliaKart stosOdkryty ;
	Timer timer;
	int Speed = 1000; //jak zrobić z tego stałą?
	int loopslot;
	Obrazek tlo;

	Gracz player1;
	Gracz player2;
	Przycisk menu;
	Przycisk pominTure;
	Przycisk wyrzucKarte;
	Przycisk koniecGry;
	Obrazek przegrana;
	Obrazek wygrana;
	Przycisk czyWygrana;
	private Przycisk save;
	
	private int tura = 0;
	private boolean wyrzucanieKarty = false;
	
	public Granie(Gra parent, boolean czyNowaGra)
	{
		super(parent);
		timer = new Timer(Speed, this);			//FIXME Przenieść timer do klasy: Ekran
		timer.setInitialDelay(1000);		
		timer.start(); 
		timer.setActionCommand("Zegar");
		
		try
		{
			makeGUI();
		}
		catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
		
		
		if(czyNowaGra)
			pierwszaTura();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String action = e.getActionCommand();
		
		if (action.equals("menu"))
		{
			
			OknoInformacyjne zapis = new OknoInformacyjne("Na pewno zakończyć grę?", OknoInformacyjne.Rodzaj.YESNO, parent);
		
			if (zapis.getOdpowiedz().equals("YES")) 	//Może kiedyś zrobić enum? :P
			{
				parent.popEkran();
			}
		}
		
		if (action.equals("tura"))
		{
			tura();
		}

		if (action.equals("koniecGry"))
		{
			parent.popEkran();
		}
		
		if (action.equals("save"))
		{
			StanGry sg = new StanGry(this);
			try {
				sg.zapisz();
				OknoInformacyjne zapis = new OknoInformacyjne("Pomyślnie zapisano grę.", OknoInformacyjne.Rodzaj.OK, parent);
			} catch (IOException e1) {
				
				e1.printStackTrace();
				System.out.println(e1.getMessage());
			}
		}
		
		if (action.equals("wyrzuc"))
		{
			List<Karta> listaDoWyrzucenia = new ArrayList<Karta>();
			
			for (Karta k: player1.getTalia().getKarty())
			{
				if (k.getStan() == stanKarty.wyrzucana)
				{
					listaDoWyrzucenia.add(k);
				}
			}
			
			if (listaDoWyrzucenia.size() > 0)
			{
				for (Karta k: listaDoWyrzucenia)
				{
					player1.getTalia().wez(k);
					stosOdkryty.dodaj(k);
				}
			
				tura();
			}
		}

		if (action.equals("Zegar"))
		{
			for (Move item : stosZakryty.getKarty()) 
			{
				//item.moveTo(20, 20); //Poruszanie się kart, np do rozdawania czy coś
			}
			
		}
		
		this.repaint();
		parent.pack();
	}

	void makeGUI() throws IOException 
	{
		tlo = new Obrazek("/Grafika/tlo.png", 0, 0, 1600, 900);
		
		czyWygrana = new Przycisk("Przycisk/Wygrana.png", "Przycisk/Wygrana.png");
		czyWygrana.addActionListener(this);
		czyWygrana.setBounds(1551, 200, 50, 100);
		add(czyWygrana);
		czyWygrana.addMouseListener(new MouseAdapter() 
		{ 
				public void mouseEntered(MouseEvent e) 
				{ 
					player1.pokazWarunkiWygranej();
				}
				public void mouseExited(MouseEvent e) 
				{ 
					player1.ukryjWarunkiWygranej();
				}
		}
		);
		
		menu = new Przycisk("Przycisk/menu.png", "Przycisk/menu2.png");
		menu.addActionListener(this);
		menu.setActionCommand("menu");
		menu.setBounds(1380, 7, 200, 122);
		add(menu);
		
		save = new Przycisk("Przycisk/save.png", "Przycisk/save2.png");
		save.addActionListener(this);
		save.setActionCommand("save");
		save.setBounds(150, 7, 140, 163);
		add(save);

		wyrzucKarte = new Przycisk("Przycisk/wyrzuc.png", "Przycisk/wyrzuc2.png");
		wyrzucKarte.addActionListener(this);
		wyrzucKarte.setActionCommand("wyrzuc");
		wyrzucKarte.setBounds(1410, 810, 64, 64);
		add(wyrzucKarte);
		
		pominTure = new Przycisk("Przycisk/tura.png", "Przycisk/tura2.png");
		pominTure.addActionListener(this);
		pominTure.setActionCommand("tura");
		pominTure.setBounds(1430, 740, 120, 120);
		add(pominTure);
		
		Obrazek miejsceNaKarty= new Obrazek("/Grafika/miejsceNaKarty.png", 700, 250, 189, 250);
		miejsceNaKarty.skaluj();
		add(miejsceNaKarty.getWyglad());


		player1 = new Czlowiek("Człowiek");
		player1.getTalia().addActionListener(this); 			//FIXME Nie wiem czy to ładnie, ale na razie zostawiam
		player2 = new NPC("Komputer");
		player1.setPrzeciwnik(player2);
		player2.setPrzeciwnik(player1);
		
		stosZakryty = new taliaKart(0, 0, false);
		stosZakryty.setTaliatLayout(0);
		stosZakryty.setEnable(false);
		
		taliaKart.wczytajZPliku("karty.txt", stosZakryty);
		stosZakryty.tasuj();
		dodajKarty(stosZakryty);

		stosOdkryty = new taliaKart(725, 275, true);
		stosOdkryty.addActionListener(this);
		stosOdkryty.setEnable(false);
		
		player1.getTalia().registerFriend(stosOdkryty);
		
		przegrana = new Obrazek("/Grafika/przegrana.png", 0, 0, 1600, 900);
		wygrana = new Obrazek("/Grafika/wygrana.png", 0, 0, 1600, 900);
		koniecGry = new Przycisk("Grafika/koniecGry.png", "Grafika/koniecGry.png");
		koniecGry.setBounds(0, 0, 1600, 900);
		koniecGry.addActionListener(this);
		koniecGry.setActionCommand("koniecGry");
		
		add(player1);
		add(player2);
		add(tlo.getWyglad());
		
		repaint();
	}
	
	void dodajKarty(taliaKart karty)
	{
		for (Karta i : karty.getKarty())
		{
			add(i);
			
//			for (MouseListener j : i.getMouseListeners())
//			{
//				addMouseListener(j);
//			}
		}
	}
	
	void usunKarty(taliaKart karty)
	{
		for (Karta i : karty.getKarty())
		{
			remove(i);
		}
	}
	
	void usunKarte(Karta k)
	{
			remove(k);
	}
	
	void dodajKarte(Karta k)
	{
			add(k, 0);
	}
	
	void rozdaj()
	{
		//Trzeba uważać żeby nie zapętlić się w nieskończonosć:
		while (player1.getTalia().czyJeszczeMozeBracKarty())		//Dopóki gracz pierwszy może brać karty to mu je dajemy
		{
			player1.getTalia().dodaj(stosZakryty.wez());
		}

		while (player2.getTalia().czyJeszczeMozeBracKarty())		//Dopóki gracz drugi (komputer) może brać karty to mu je dajemy
		{
			player2.getTalia().dodaj(stosZakryty.wez());
		}
		
		//FIXME dodać animację
		
		for (int i = 0; i < stosOdkryty.getKarty().size(); ++i)
		{
			stosZakryty.dodaj(stosOdkryty.wez());
		}
		
		stosZakryty.tasuj();
	}
	
	void tura()
	{
		rozdaj();

		if (czyKoniecGry() == false)
		{
			ruchNPC();
		}
		
		czyKoniecGry();
		
		player1.poczatekTury();
		player2.poczatekTury();
	}
	
	void pierwszaTura()
	{
		rozdaj();
		
		Random r = new Random();
		
		double d = r.nextDouble();
		
		if (d < 0.5 && czyKoniecGry() == false)
			ruchNPC();
		
		czyKoniecGry();
		
		player1.poczatekGry();
		player2.poczatekGry();
	}
	
	boolean czyKoniecGry()
	{

		if (player1.czyWygrana() || player2.czyPrzegrana())
		{
			add(wygrana.getWyglad(), 0);
			add(koniecGry, 0);
			
			return true;
		}
		
		if (player2.czyWygrana() || player1.czyPrzegrana())
		{
			add(przegrana.getWyglad(), 0);
			add(koniecGry, 0);
			
			return true;
		}
		
		return false;
	}
	
	void ruchNPC()
	{
		Karta rzuconaPrzezKomputer;
		
		do
		{
			//Komputer:
			rzuconaPrzezKomputer = player2.rzucKarte();
			if (rzuconaPrzezKomputer == null) break;
			usunKarte(rzuconaPrzezKomputer);	//Usuniecie z ekranu
			dodajKarte(rzuconaPrzezKomputer);	//Dodanie na wierzchu
			stosOdkryty.dodaj(rzuconaPrzezKomputer, true);
			
			if (czyKoniecGry())   //KONIEC GRY
			{
				break;
			}
			
		} while (rzuconaPrzezKomputer.playAgain());	//Jeżeli rzuci kartę z umiejętnościa PlayAgain to rzuca kolejną.
		
	}
	
	@Override
	public void skaluj() 	
	{
		stosZakryty.skalujKarty();
		stosZakryty.skaluj();
		stosOdkryty.skalujKarty();
		stosOdkryty.skaluj();
		player1.getTalia().skalujKarty();
		player1.skaluj();
		player2.getTalia().skalujKarty();
		player2.skaluj();
		tlo.skaluj();
		menu.skaluj();
		pominTure.skaluj();
		wyrzucKarte.skaluj();
		koniecGry.skaluj();
		przegrana.skaluj();
		wygrana.skaluj();
		save.skaluj();
		czyWygrana.skaluj();
		czyWygrana.setLocation(czyWygrana.getX(), czyWygrana.getY());
	}
	
	@Override
	public void rzuconaKarta() 	
	{
		tura();
	}

	@Override
	public void podniesionaKarta(Karta k) 	
	{
		usunKarte(k);
		dodajKarte(k);
//		System.out.println();
	}

	public taliaKart getStosOdkryty() {
		return stosOdkryty;
	}
	
	public taliaKart getStosZakryty() {
		return stosZakryty;
	}
	
	public Gracz getPlayer1() {
		return player1;
	}
	
	public Gracz getPlayer2() {
		return player2;
	}
	
	public void przygotujDoWczytania() {
		
		czyKoniecGry();
		
		player1.poczatekGry();
		player2.poczatekGry();
	}
	
	public void setOdkryte(taliaKart talia) {
		this.stosOdkryty = talia;
	}
	
	public void setZakryte(taliaKart talia) {
		this.stosZakryty = talia;
	}
}