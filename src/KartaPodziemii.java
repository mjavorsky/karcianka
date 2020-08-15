import java.io.IOException;

public class KartaPodziemii extends Karta
{
	KartaPodziemii(String name, int koszt, taliaKart talia, int ID) 
	{
		super(name, koszt, talia, ID);
		
        try
        {
        	przod = new Obrazek("/Karty/zielona.png", 0, 0, width, height);	
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
		if (player.zmianaBestie(0) >= koszt)	//Można zrobić funkcję getKrysztaly() kiedys
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
		player.zmianaBestie(-koszt);
	}
}