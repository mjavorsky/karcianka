import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Zasoby extends JPanel
{
	private int produkcja;
	private int ilosc;
	private JLabel lprodukcja;
	private JLabel lilosc;
	
	public Zasoby(int produkcja, int ilosc)
	{
		//setBounds(300, 10, 100, 100);
		this.setLayout(null);
		this.setOpaque(false);
		
		this.produkcja = produkcja;
		this.ilosc = ilosc;		

		lprodukcja = new JLabel(Integer.toString(produkcja));
		lilosc = new JLabel(Integer.toString(ilosc));
		
		add(lprodukcja);
		add(lilosc);
	}
	
	public int zmianaProdukcja(int wartosc)
	{
		setProdukcja( produkcja + wartosc > 0 ? produkcja + wartosc : 0);
		return getProdukcja();
	}
	
	public int zmianaIlosc(int wartosc)
	{
		setIlosc( ilosc + wartosc > 0 ? ilosc + wartosc : 0);
		return getIlosc();
	}
	
	public void setProdukcja(int wartosc)
	{
		produkcja = wartosc;
		lprodukcja.setText(Integer.toString(produkcja));
	}
	
	public int getProdukcja()
	{
		return produkcja;
	}
	
	public void setIlosc(int wartosc)
	{
		ilosc = wartosc;
		lilosc.setText(Integer.toString(ilosc));
	}
	
	public int getIlosc()
	{
		return ilosc;
	}
	
	public void setToolTips(String produkcja, String ilosc)
	{
		lprodukcja.setToolTipText(produkcja);
		lilosc.setToolTipText(ilosc);
	}
	
    public void skaluj()
    {
		lprodukcja.setFont(new Font("Verdana", Font.BOLD, Ustawienia.skaluj(22)));
		lprodukcja.setForeground(Color.YELLOW);
		lilosc.setFont(new Font("Verdana", Font.BOLD, Ustawienia.skaluj(14)));
		lilosc.setForeground(Color.WHITE);
		
		int x = Ustawienia.skaluj(40);
		int y = Ustawienia.skaluj(35);
		int width = Ustawienia.skaluj(40);
		int height = Ustawienia.skaluj(40);
		lprodukcja.setBounds(x, y, width, height);
		
		 x = Ustawienia.skaluj(12);
		 y = Ustawienia.skaluj(50);
		 width = Ustawienia.skaluj(60);
		 height = Ustawienia.skaluj(40);
		 lilosc.setBounds(x, y, width, height);
	    	
		 x = Ustawienia.skaluj(this.getX());
		 y = Ustawienia.skaluj(this.getY());
		 width = Ustawienia.skaluj(this.getWidth());
		 height = Ustawienia.skaluj(this.getHeight());

	    this.setBounds(x, y, width, height);
    }
    
    public void pokazWygrana()
    {
    	if (ilosc >= Ustawienia.getWygranaZasoby() ) 
    	{
    		lilosc.setForeground(Color.GREEN);
        	lilosc.setText(Integer.toString(ilosc - Ustawienia.getWygranaZasoby()));
    	}
    	else
    	{
    		lilosc.setForeground(Color.RED);
    		lilosc.setText(Integer.toString(ilosc - Ustawienia.getWygranaZasoby()));
    	}
    }
    
    public void ukryjWygrana()
    {
    	lilosc.setForeground(Color.WHITE);
    	lilosc.setText(Integer.toString(ilosc));
    }
}
