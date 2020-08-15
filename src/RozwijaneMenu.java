
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class RozwijaneMenu
{
	private List<Przycisk> listaPrzyciskow = new ArrayList<Przycisk>();
	private int x;
	private int y;
	private int width;
	private int height;
	private int odstep;
	private Przycisk naWierzchu;
	private int nastepnyY;
	private boolean rozwiniete;
	
	RozwijaneMenu(int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.odstep = 5;
		
		this.nastepnyY = 0;
		rozwiniete = false;
	}
	
	public void dodajPrzycisk(Przycisk p)
	{
		if (listaPrzyciskow.size() == 0)
		{
			naWierzchu = p;
		}
			
		listaPrzyciskow.add(p);
		
		p.setVisible(false);
		p.setBounds(x, y + nastepnyY, width, height);
		nastepnyY += height + odstep;
	}
	
	public Przycisk getNaWierzchu()
	{
		return naWierzchu;
	}
	
	public void rozwin()
	{

		for (Przycisk i: listaPrzyciskow)
		{
			i.setVisible(true);
		}
		
		rozwiniete = true;
	}
	
	public void zwin(Przycisk p)
	{
		for (Przycisk i: listaPrzyciskow)
		{
			i.setVisible(false);
		}
		
		listaPrzyciskow.remove(p);
		listaPrzyciskow.add(0, p);
		
		rozmiescPrzyciski();
		
		naWierzchu = p;
		naWierzchu.setVisible(true);
		rozwiniete = false;
	}
	
	private void rozmiescPrzyciski()
	{
		nastepnyY = 0;
		
		for (Przycisk i: listaPrzyciskow)
		{
			i.setBounds(x, y + nastepnyY, width, height);
			nastepnyY += height + odstep;
		}
	}
	
	public void skaluj()
	{
		x = Ustawienia.skaluj(this.x);
		y = Ustawienia.skaluj(this.y);
		width = Ustawienia.skaluj(this.width);
		height = Ustawienia.skaluj(this.height);
		
		for (Przycisk i: listaPrzyciskow)
		{
			i.skaluj();
		}
		rozmiescPrzyciski();
	}
	
	public boolean getRozwiniete()
	{
		return rozwiniete;
	}
}
