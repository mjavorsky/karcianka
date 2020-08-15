import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Zamek extends JPanel
{
	private Obrazek zamek;
	private Obrazek wieza;
	private Obrazek mur;
	private JLabel wysokoscMuruNapis;
	private JLabel wysokoscMuruNapis2;
	private JLabel wysokoscWiezyNapis;
	private int wysokoscMuru;
	private int wysokoscWiezy;
	private int numer;
	
	String path;
	
	Zamek(int x, int y, int width, int height, int numer)	
	{
		this.numer = numer;
		
		setBounds(x, y, width, height);	
		setLayout(null);
		
        wysokoscMuru = 0;
        wysokoscWiezy = 0;
        
        try
        {
        	zamek = new Obrazek("/Grafika/zamek" + numer + ".png", 0, 0, this.getWidth(), this.getHeight());	
        	wieza = new Obrazek("/Grafika/wieza" + numer + ".png", 0, 0, this.getWidth(), this.getHeight());	
        	mur = new Obrazek("/Grafika/mur" + numer + ".png", 0, 0, this.getWidth(), this.getHeight());	
        }
        catch (IOException e)
        {
        	//Coś jakby nie było grafik
        }

		zamek.getWyglad().setBounds(0, 0, width, height);
		wieza.getWyglad().setBounds(0, (150 - wysokoscWiezy) * 2, width, height);
		mur.getWyglad().setBounds(0, (150 - wysokoscMuru) * 2, width, height);
		

		wysokoscMuruNapis = new JLabel(Integer.toString(wysokoscMuru));
		wysokoscMuruNapis.setToolTipText("Mur");
		wysokoscMuruNapis2 = new JLabel(Integer.toString(wysokoscMuru));
		wysokoscMuruNapis2.setToolTipText("Mur");
		wysokoscWiezyNapis = new JLabel(Integer.toString(wysokoscWiezy));
		wysokoscWiezyNapis.setToolTipText("Wieża");

		add(wysokoscMuruNapis);
		add(wysokoscMuruNapis2);
		add(wysokoscWiezyNapis);
		add(zamek.getWyglad());
		add(wieza.getWyglad());
		add(mur.getWyglad());
		
		this.setVisible(true);
		this.setOpaque(false);	//Przeźroczystość
		
		repaint();
	}
	
    public int zmianaWieza(int wartosc) 
	{
    	setWieza(wysokoscWiezy + wartosc > 0 ? wysokoscWiezy + wartosc : 0);
		return getWieza();
    }
	
    public int zmianaMur(int wartosc) 
	{
    	setMur(wysokoscMuru + wartosc > 0 ? wysokoscMuru + wartosc : 0);
		return getMur();
    }
    
    public void setWieza(int wartosc)
    {
		wieza.getWyglad().setLocation(wieza.getWyglad().getX(), wieza.getWyglad().getY() - ((wartosc - wysokoscWiezy) * 2));
		wysokoscWiezy = wartosc;
		wysokoscWiezyNapis.setText(Integer.toString(wysokoscWiezy));
    }
    
    public void setMur(int wartosc)
    {
		mur.getWyglad().setLocation(mur.getWyglad().getX(), mur.getWyglad().getY() - ((wartosc - wysokoscMuru) * 2));
		wysokoscMuru = wartosc;
		wysokoscMuruNapis.setText(Integer.toString(wysokoscMuru));
		wysokoscMuruNapis2.setText(Integer.toString(wysokoscMuru));
    }
    
    public int getWieza()
    {
    	return wysokoscWiezy;
    }
    
    public int getMur()
    {
    	return wysokoscMuru;
    }
    
    public void skaluj()
    {
		zamek.skaluj();
		wieza.skaluj();
		mur.skaluj();
		
		int x = Ustawienia.skaluj(135);
		int y = Ustawienia.skaluj(320);
		int width = Ustawienia.skaluj(30);
		int height = Ustawienia.skaluj(50);

		Font font = new Font("Verdana", Font.BOLD, Ustawienia.skaluj(12));
		
		wysokoscMuruNapis.setFont(font);
		wysokoscMuruNapis.setBounds(x, y, width, height);

		x = Ustawienia.skaluj(455);
		wysokoscMuruNapis2.setFont(font);
		wysokoscMuruNapis2.setBounds(x, y, width, height);
		
		 x = Ustawienia.skaluj(290);
		 y = Ustawienia.skaluj(385);
		 width = Ustawienia.skaluj(30);
		 height = Ustawienia.skaluj(50);

		wysokoscWiezyNapis.setFont(font);
		wysokoscWiezyNapis.setBounds(x, y, width, height);
    	
		 x = Ustawienia.skaluj(this.getX());
		 y = Ustawienia.skaluj(this.getY());
		 width = Ustawienia.skaluj(this.getWidth());
		 height = Ustawienia.skaluj(this.getHeight());

    	this.setBounds(x, y, width, height);
    }    
    
    public void pokazWygrana()
    {
    	wysokoscWiezyNapis.setForeground(Color.RED);
    	wysokoscWiezyNapis.setText(Integer.toString(wysokoscWiezy - Ustawienia.getWygranaWieza()));
    }
    
    public void ukryjWygrana()
    {
    	wysokoscWiezyNapis.setForeground(Color.BLACK);
    	wysokoscWiezyNapis.setText(Integer.toString(wysokoscWiezy));
    }
    
}
