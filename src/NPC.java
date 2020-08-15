import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class NPC  extends Gracz
{
	NPC(String name)
	{
		super(name);

		this.setBounds(800, 0, 800, 900);
		
		this.bestie.setBounds(365, 0, 100, 100);
		this.krysztaly.setBounds(220, 0, 100, 100);
		this.kamienie.setBounds(80, 0, 100, 100);

		zamek = new Zamek(200, 60, 600, 600, 2);
		zamek.setWieza(Ustawienia.getPoczatkowaWieza());
		zamek.setMur(Ustawienia.getPoczatkowyMur());

		add(zamek);

		talia.setVisible(false);
	}
	
	public Karta rzucKarte()
	{
		Random rand = new Random();
		
		List<Karta> lista = new ArrayList<Karta>();
		
		for (Karta k : talia.getKarty())
		{
			if (k.czyMoznaRzucic(this))
			{
				lista.add(k);
			}
		}

		if (lista.size() == 0)
		{
			//JEŻELI KOMPUTER NIE MA KARTY KTÓRĄ MÓGŁBY RZUCIĆ TO MUSI COŚ ZROBIĆ I TRZEB TO TU NAPISAĆ
			return null;
		}

		Karta tmp = lista.get(rand.nextInt(lista.size()));
		talia.wez(tmp);
		tmp.uzyj(this, this.przeciwnik);

		return tmp; 
	}
}

