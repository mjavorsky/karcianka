import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public abstract class Karta extends JPanel implements Move
{
	protected Obrazek przod;
	protected Obrazek tyl;
	protected Obrazek obrazek;
	protected Obrazek obrazekZablokowana;
	protected String path;
	protected String name;
	protected stanKarty stan;
	protected int width;
	protected int height;
	protected EventKarta event;
	protected taliaKart talia;
	protected JLabel lnazwa;
	protected JLabel lkoszt;
	protected int koszt;
	protected String opis;
	protected JTextPane lopis;
	protected int [] tablicaUmiejetnosci;
	protected Obrazek iks;
	protected boolean enable = true;
	protected boolean zablokowana = false;
	protected int ID;
	
	private final int LICZBA_UMIEJETNOSCI = 19;
	
	protected Karta(String name, int koszt, taliaKart talia, int ID) 
	{
		this.setLayout(null);
		this.name = name;
		this.koszt = koszt;
		this.talia = talia;
		this.tablicaUmiejetnosci = new int[LICZBA_UMIEJETNOSCI];
		this.ID = ID;
		
		lnazwa = new JLabel(name, SwingConstants.CENTER);						//2 argument to wyśrodkowanie napisu
		lnazwa.setFont(new Font("Verdana", Font.BOLD, 16));
		lnazwa.setForeground(Color.WHITE);
		lnazwa.setBounds(4, 5, 134, 25);
		
		lkoszt = new JLabel(Integer.toString(koszt), SwingConstants.CENTER);	
		lkoszt.setFont(new Font("Verdana", Font.BOLD, 20));
		lkoszt.setForeground(Color.WHITE);
		lkoszt.setBounds(102, 165, 30, 30);										//Współrzędne dla kołka na karcie
		
		lopis = new JTextPane();
		lopis.setBounds(5, 120, 128, 50);
		lopis.setEditable(false);
		//lopis.setLineWrap(true);
		lopis.setOpaque(false);
		lopis.setBorder(BorderFactory.createEmptyBorder());
		lopis.setEnabled(false);
		
		width = 138;
		height = 200;


		try
		{
			iks = new Obrazek("/Karty/x.png", 0, 0, width, height);
			iks.skaluj();
			iks.getWyglad().setVisible(false);
			add(iks.getWyglad());
			obrazekZablokowana = new Obrazek("/Karty/zablokowana.png", 0, 0, width, height);
			obrazekZablokowana.skaluj();
			obrazekZablokowana.getWyglad().setVisible(false);
			add(obrazekZablokowana.getWyglad());
		}
		catch (IOException e)
		{
			System.out.println(e.getMessage());
		}

		try
		{
			obrazek = new Obrazek("/Karty/" + name + ".png", 1, 30, 136, 85);
		}
		catch (IOException e)
		{
			obrazek = null;
			System.out.println("Brak Obrazka: " + name);
		}
		finally
		{
			if (obrazek != null)
			{
				obrazek.skaluj();
				add(obrazek.getWyglad());
			}
		}
		
		setBounds(0, 0, width, height);	
		
		stan = stanKarty.odkryta;
		this.setVisible(true);
		this.setOpaque(false);
		repaint();
		
		//Nasłuchiwanie myszki
		event = new EventKarta(this);
		this.addMouseListener(event);
		this.addMouseMotionListener(event);
		lopis.addMouseListener(event);
		lopis.addMouseMotionListener(event);
		
		EventKursor ek = new EventKursor(Gra.getInstance(), TypKursora.przesuwanie);
		this.addMouseListener(ek);
		lopis.addMouseListener(ek);
	}

	public abstract boolean czyMoznaRzucic(Gracz player);
	public abstract void odejmijZasoby(Gracz player);
	
	void setStan(stanKarty nowy)
	{
		stan = nowy;
		
		if (stan == stanKarty.zakryta)
		{
			tyl.getWyglad().setVisible(true);
			iks.getWyglad().setVisible(false);
		}

		if (stan == stanKarty.odkryta)
		{
			tyl.getWyglad().setVisible(false);
			iks.getWyglad().setVisible(false);
		}

		if (stan == stanKarty.wyrzucana)
		{
			tyl.getWyglad().setVisible(false);
			iks.getWyglad().setVisible(true);
		}
		
		repaint();
	}
	
	stanKarty getStan()
	{
		return stan;
	}
	
	int getKoszt()
	{
		return koszt;
	}
	
	void setEnable(boolean b) 
	{
		if (enable == false && b == true)
		{
			this.addMouseListener(event);
			this.addMouseMotionListener(event);
			setZablokowana(false);
		}
		else if (enable == true && b == false)
		{
			this.removeMouseListener(event);
			this.removeMouseMotionListener(event);
			setZablokowana(true);
		}

		enable = b;
		
		
	}
	
	boolean czyWyrzucac()
	{
		if (stan == stanKarty.wyrzucana)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	void odwroc()
	{
		if (stan == stanKarty.zakryta)
		{
			setStan(stanKarty.odkryta);
		}
		else if (stan == stanKarty.odkryta)
		{
			setStan(stanKarty.zakryta);
		}
	}
	
	void setHeight(int h)
	{
		this.height = h;
	}
	
	String getNazwa()
	{
		return name;
	}
	
	void setWidth(int w)
	{
		this.width = w;
	}
	
	void setTalia(taliaKart p)
	{
		talia = p;
	}
	
	taliaKart getTalia()
	{
		return talia;
	}
	
	void setOpis(String opis)
	{
		this.opis = opis;
		lopis.setText(this.opis);
	}
	
	void wczytajUmiejetnosci(int [] tab)
	{
		for (int i = 0; i < LICZBA_UMIEJETNOSCI; ++i)
		{
			this.tablicaUmiejetnosci[i] = tab[i];
		}
	}
	
	boolean uzyj(Gracz player1, Gracz player2)		//Moze kiedyś wymyśli sie coś innego :P
	{
		//System.out.println(tablicaUmiejetnosci[1]);

		if (czyMoznaRzucic(player1))	
		{
			this.odejmijZasoby(player1);
			
			//Dla gracza:
			player1.zmianaKopalnie(tablicaUmiejetnosci[0]);	//Po prostu zwiększamy lub zmniejszamy wartości danych zmiennych o: tablicaUmiejetnosci[i]
			player1.zmianaKamienie(tablicaUmiejetnosci[1]);
			player1.zmianaMagia(tablicaUmiejetnosci[2]);
			player1.zmianaKrysztaly(tablicaUmiejetnosci[3]);
			player1.zmianaPodziemia(tablicaUmiejetnosci[4]);
			player1.zmianaBestie(tablicaUmiejetnosci[5]);
			player1.zmianaWieza(tablicaUmiejetnosci[6]);
			player1.zmianaMur(tablicaUmiejetnosci[7]);
			player1.zmianaZycie(-tablicaUmiejetnosci[8]);

			//Dla NPC:
			player2.zmianaKopalnie(tablicaUmiejetnosci[9]);
			player2.zmianaKamienie(tablicaUmiejetnosci[10]);
			player2.zmianaMagia(tablicaUmiejetnosci[11]);
			player2.zmianaKrysztaly(tablicaUmiejetnosci[12]);
			player2.zmianaPodziemia(tablicaUmiejetnosci[13]);
			player2.zmianaBestie(tablicaUmiejetnosci[14]);
			player2.zmianaWieza(tablicaUmiejetnosci[15]);
			player2.zmianaMur(tablicaUmiejetnosci[16]);
			player2.zmianaZycie(-tablicaUmiejetnosci[17]);	//Obrażenia są podawane jako dodatnie więc żeby je odjąć trzeba użyć operatora -
			
			return true;
		}
		
		return false;
	}
	
	boolean playAgain()
	{
		if (tablicaUmiejetnosci[18] == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public void moveTo(int x, int y) //Funckja do przesuwania kart na jakieś miejsce oczywiscie do poprawy żeby ruch był płynny
	{
		//ystem.out.println("DUPA");
		int X = getX();
		int Y = getY();
		this.setLocation(X + x, Y + y);
		
		/* w c++ mam to napisane tak:
		  
		      	if (!aktywna) return ;

    			float d = (static_cast<float>(aktualny_czas) - time_s) / (time_e - time_s);

		 		*x = p_x + d * dx;  //dx to jest deltax czyli: dx = destinationx - startx;
		 		*y = p_y + d * dy;

    			if (*x == destinationx && *y == destinationy)
    			{
        			dezaktywuj();
    			}
		 
		 */
		
	}
	
	@Override
	public void moveToFrom(int sx, int sy, int dx, int dy) 
	{

	}
	
    public void skaluj()
    {
		int x = Ustawienia.skaluj(4);
		int y = Ustawienia.skaluj(5);
		int width = Ustawienia.skaluj(134);
		int height = Ustawienia.skaluj(25);

		lnazwa.setFont(new Font("Verdana", Font.BOLD, Ustawienia.skaluj(16)));
		lnazwa.setBounds(x, y, width, height);
		
		 x = Ustawienia.skaluj(102);
		 y = Ustawienia.skaluj(165);
		 width = Ustawienia.skaluj(30);
		 height = Ustawienia.skaluj(30);

		 lkoszt.setFont(new Font("Verdana", Font.BOLD, Ustawienia.skaluj(20)));
		 lkoszt.setBounds(x, y, width, height);

		 x = Ustawienia.skaluj(5);
		 y = Ustawienia.skaluj(120);
		 width = Ustawienia.skaluj(128);
		 height = Ustawienia.skaluj(50);

		 lopis.setFont(new Font("Verdana", Font.PLAIN, Ustawienia.skaluj(12)));
		 lopis.setBounds(x, y, width, height);

		 
		 przod.skaluj();
		 tyl.skaluj();
		 
	     this.setBounds(Ustawienia.skaluj(this.getX()), Ustawienia.skaluj(this.getY()), Ustawienia.skaluj(this.width), Ustawienia.skaluj(this.height));
    }
	
	public void setZablokowana(boolean z)
	{
		obrazekZablokowana.getWyglad().setVisible(z);
		this.zablokowana = z;
	}
	
	public boolean getZablokowana()
	{
		return zablokowana;
	}
	
	public int getID() {
		return this.ID;
	}
}
