import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JPanel;


public class taliaKart
{
	private List<Karta> talia;
	private Karta naWierzchu;
	private Point location;
	private List<taliaKart> friends; 
	private boolean odkryte;
	private List<EventTalia> listeners = new ArrayList<EventTalia>();
	private Gracz gracz;
	private int WYSOKOSC_TALII = 200;
	private int SZEROKOSC_TALII = 138;
	private int ODSTEPY = 7;
	private int maksymalnaIloscKart;
	private boolean visible;
	private boolean enable = true;
	private int layout;							//FIXME zrobić z tego kiedyś enum
	
	taliaKart(int x, int y, boolean odkryte)	//Pierwsze dwa argumenty to miejsce gdzie mają leżeć karty, 3 to czy mają być domyślnie zasłaniane czy odsłaniane
	{
		friends = new ArrayList<taliaKart>();
		
		location = new Point(x, y);
		naWierzchu = null;
		talia = new ArrayList<Karta>();
		this.odkryte = odkryte;
		layout = 0;
		maksymalnaIloscKart = -1;
		visible = true;
	}
	
	public void setGracz(Gracz g)
	{
		gracz = g;
	}
	
	public void setTaliatLayout(int i)
	{
		layout = i;
		rozlozKarty();
	}
	
	void dodaj(Karta karta)
	{
		dodaj(karta, odkryte);
	}
	
	void dodaj(Karta karta, boolean odkryj)
	{
		if (karta == null) return ;
		
		if (czyJeszczeMozeBracKarty())
		{
			talia.add(0, karta);
			naWierzchu = karta;
			
			karta.setLocation(location);
			this.rozlozKarty();	//Ukłądamy karty według naszego layoutu
			karta.setTalia(this);
			karta.setVisible(visible);
			
			karta.setEnable(this.enable);
			
			if (odkryj)
			{
				karta.setStan(stanKarty.odkryta);
			}
			else
			{
				karta.setStan(stanKarty.zakryta);
			}
		}
	}
	
	Karta wez()
	{
		return this.wez(0);
	}
	
	Karta wez(int index)
	{
		if (naWierzchu == null) return null;
	
		if (talia.size()  > index && index >= 0)
		{
			Karta r = null;
			
			if (talia.size() > 1)	//Jeżeli w talii są jeszcze jakieś karty ustawiamy pierwszą kartę jako karta na wierzchu
			{	
				if (talia.get(index) == naWierzchu)
				{
					r = naWierzchu;
					naWierzchu = talia.get(1);
				}
				else
				{
					r = talia.get(index);
				}
			}
			else
			{
				r = naWierzchu;
				naWierzchu = null;
			}

			talia.remove(index);	//Usuwanie karty z talii
			//rozlozKarty();		//Żeby nie było pustych miejsc
			
			return r;				//Zwraca kartę która ostatnio była na wierzchu
		}

		return null;
	}
	
	Karta wez(Karta k)
	{
		return wez(talia.indexOf(k));
	}

	void tasuj()
	{	
		for (Karta i : talia)
		{
			//remove(i);
		}
		
		Collections.shuffle(talia);
		//zakryjWszystkie(); //FIXME to chyba nie potrzebne, na razie zakomentowałem
		rozlozKarty();
		
		for (Karta i : talia)
		{
			//add(i);
		}
		
		naWierzchu = talia.get(talia.size() - 1);
	}
	
	void setVisible(boolean v)
	{
		visible = v;
	}
	
	void ukryjWszystkie()
	{
		for (Karta i: talia)
		{
			i.setVisible(false);
		}
	}
	
	void zakryjWszystkie()
	{
		for (Karta i: talia)
		{
			i.setStan(stanKarty.zakryta);
		}
	}

	void odkryjWszystkie()
	{
		for (Karta i: talia)
		{
			i.setStan(stanKarty.odkryta);
		}
	}

	void skalujKarty()
	{
		for (Karta i: talia)
		{
			i.skaluj();
		}
	}

	void skaluj()
	{
		SZEROKOSC_TALII = Ustawienia.skaluj(SZEROKOSC_TALII);
		WYSOKOSC_TALII = Ustawienia.skaluj(WYSOKOSC_TALII); 
		ODSTEPY = Ustawienia.skaluj(ODSTEPY); 
		location = new Point(Ustawienia.skaluj(location.x), Ustawienia.skaluj(location.y));

		rozlozKarty();
	}
	
	void odkryjPierwsza()
	{
		naWierzchu.setStan(stanKarty.odkryta);
	}

	void zakryjPierwsza()
	{
		naWierzchu.setStan(stanKarty.zakryta);
	}

	List<Karta> getKarty()
	{
		return talia;
	}

	boolean kolizja(Karta k)
	{			 
		return kolizja(k.getLocation());
	}
	
	boolean kolizja(Point p)
	{
		if ((p.x > location.x && p.x < location.x + SZEROKOSC_TALII && p.y > location.y && p.y< location.y + WYSOKOSC_TALII) 	 //Lewy gróny
		 || (p.x > location.x && p.x < location.x + SZEROKOSC_TALII && p.y + WYSOKOSC_TALII > location.y && p.y  + WYSOKOSC_TALII < location.y + WYSOKOSC_TALII)  //Lewy dolny
	     || (p.x + SZEROKOSC_TALII > location.x && p.x + SZEROKOSC_TALII < location.x + SZEROKOSC_TALII && p.y > location.y && p.y< location.y + WYSOKOSC_TALII) //prawy górny
	     || (p.x + SZEROKOSC_TALII > location.x && p.x + SZEROKOSC_TALII < location.x + SZEROKOSC_TALII && p.y  + WYSOKOSC_TALII > location.y && p.y + WYSOKOSC_TALII < location.y + WYSOKOSC_TALII)) //prawy dolny
		{
			return true;
		}
		else
		{	
			return false;
		}
	}
	
	Point getLocation()
	{
		return location;
	}

	Point getLocation(Karta k)
	{
		Point tmp = new Point(location);
		

		int miejsce;
		
		if (layout == 0)
		{
			//PBLOK PUSTY
		}
		else if (layout == 1)
		{
			miejsce = talia.indexOf(k);
			tmp.setLocation(tmp.x + miejsce * (SZEROKOSC_TALII + ODSTEPY), tmp.y); //FIXME Zamienieć 145 na zmienną szerokość karty + odstęp(7)
		}
		else if (layout == 2)
		{ 
			miejsce = talia.indexOf(k);
			tmp.setLocation(tmp.x, tmp.y + miejsce * (WYSOKOSC_TALII + ODSTEPY) ); //FIXME Zamienieć 200 na zmienną wysokosc karty + odstęp(7)
		}
		
		return tmp;
	}

	private void rozlozKarty()
	{
		if (layout == 0) return;

		Point p = new Point(location);	//Pozycja początkowa
		
		
		for (Karta i : talia)
		{
			if (layout == 0)
			{
				i.setLocation(p);
			}
			else if (layout == 1)
			{
				i.setLocation(p);        
				p.setLocation(i.getLocation().x + (SZEROKOSC_TALII + ODSTEPY), i.getLocation().y); //Przesunięcie następnej karty
			}
			else if (layout == 2)
			{
				i.setLocation(p);           
				p.setLocation(i.getLocation().x, i.getLocation().y + WYSOKOSC_TALII + ODSTEPY ); //Przesunięcie następnej karty
			}
			
		}
	}
	
	void registerFriend(taliaKart talia)
	{
		friends.add(talia);
	}
	
	void unregisterFriend(taliaKart talia)
	{
		friends.remove(talia);
	}
	
	boolean toFriends(Karta k)
	{
		if (gracz != null)
		{
			for (taliaKart i : friends)
			{
				if (i.kolizja(k))
				{
					//System.out.println("T");
					
					if (k.uzyj(gracz, gracz.getPrzeciwnik()))
					{
						i.dodaj(this.wez(k));
						if (k.playAgain() == false) 
						{
							emitRzuconaKarta();	//Wywołujemy zdarzenie rzucenia karty (końca tury) jeżeli karta nie ma PlayAgain
						}
						else
						{
							rozlozKarty();
						}
						
						for (Karta karta: gracz.getTalia().getKarty())
						{
							karta.setZablokowana( !(karta.czyMoznaRzucic(gracz)) );
						}
						
						return true;
					}
					else
					{
						return false;
					}
				}
			}
		}

		//System.out.println("F");
		return false;
	}

	void emitRzuconaKarta()
	{
		for (EventTalia e : listeners)
		{
            e.rzuconaKarta();
		}
	}

	void emitPodniesionaKarta(Karta k)
	{
		for (EventTalia e : listeners)
		{
            e.podniesionaKarta(k);
		}
	}
	
	void addActionListener(EventTalia e) 
	{
		listeners.add(e);
	}
	
	void setEnable(boolean b) 
	{
		enable = b;
		
		for (Karta i : talia)
		{
			i.setEnable(enable);
		}
		
	}

	void setMaksymalnaIloscKart(int i) 
	{
		maksymalnaIloscKart = i;
	}

	boolean czyJeszczeMozeBracKarty() 
	{
		if  (maksymalnaIloscKart == -1 || talia.size() < maksymalnaIloscKart)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static void wczytajZPliku(String nazwa, taliaKart talia) throws IOException 
	{
        BufferedReader plik = null;
        int kolumnDoWczytania = 23;	//Liczba kolumn w pliku
        
        try 
        {            
            plik = new BufferedReader(new FileReader(nazwa));
            String l = plik.readLine();		//Wczytanie pierwszej linii...
            
            l = plik.readLine();			//...i od razu kolejnej żeby pominąć linię z nazwami kolumn
            
            while (l != null) 
            {
            	String[] parts = l.split(Pattern.quote(";")); //Rozdzielenie wyrazów w linii, elementy tablicy to wyrazy rozdzielone średniakmi
            	
            	Karta tmp;
        	
        		//trim usuwa białe znaki z początku i końca stringu
            	if (Integer.parseInt(parts[1].trim()) == 1)	//w zależnosi od typu tworzymy różne karty
        		{
        			tmp = new KartaKopalnii(parts[2].trim(), Integer.parseInt(parts[3].trim()), talia, Integer.parseInt(parts[0].trim()));
        		}
            	else if (Integer.parseInt(parts[1].trim()) == 2)	//w zależnosi od typu tworzymy różne karty
        		{
        			tmp = new KartaMagii(parts[2].trim(), Integer.parseInt(parts[3].trim()), talia, Integer.parseInt(parts[0].trim()));
        		}
            	else if (Integer.parseInt(parts[1].trim()) == 3)	//w zależnosi od typu tworzymy różne karty
        		{
        			tmp = new KartaPodziemii(parts[2].trim(), Integer.parseInt(parts[3].trim()), talia, Integer.parseInt(parts[0].trim()));
        		}
        		else 
        		{
        			System.out.println("Błąd podano zły typ karty.");
        			break;
        		}
        	
        		int [] um = new int[kolumnDoWczytania - 4]; //odejmujemy 4 pierwsze kolumny: id, typ, nazwa i koszt żeby zostały same kolumny z umiejętnościami
        		
        		for (int i = 0; i < um.length; ++i)
        		{
        			um[i] = Integer.parseInt(parts[i + 4].trim());	//Przepisujemy przesunięte o 4 pierwsze kolumny
        		}

        		tmp.wczytajUmiejetnosci(um);
    			tmp.setOpis(parts[kolumnDoWczytania].trim());
    			talia.dodaj(tmp);		//Dodanie wczytanej karty do talii
        	
                l = plik.readLine();
            }
        } 
        finally 
        {
            if (plik != null) 
            {
                plik.close();
            }
        }
    }
	
	
	
}
