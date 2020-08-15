import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class KartaMagii extends Karta
{
	KartaMagii(String name, int koszt, taliaKart talia, int ID) 
	{
		super(name, koszt, talia, ID);
		
        try
        {
        	przod = new Obrazek("/Karty/niebieska.png", 0, 0, width, height);	
        	tyl = new Obrazek("/Karty/rewers.png", 0, 0, width, height);
        }
        catch (IOException e)
        {
        	System.out.println(e.getMessage());
        }
		
		add(tyl.getWyglad());
		tyl.getWyglad().setVisible(false);
		
		add(lkoszt);
		add(lnazwa);
		add(lopis);
		add(przod.getWyglad());
	}

	@Override
	public boolean czyMoznaRzucic(Gracz player)
	{
		if (player.zmianaKrysztaly(0) >= koszt)	//Można zrobić funkcję getKrysztaly() kiedys
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void odejmijZasoby(Gracz player)
	{
		player.zmianaKrysztaly(-koszt);
	}
}
