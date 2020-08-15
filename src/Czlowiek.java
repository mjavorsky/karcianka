public class Czlowiek extends Gracz
{
	Czlowiek(String name)
	{
		super(name);

		this.setBounds(0, 0, 800, 900);
		
		this.bestie.setBounds(685, 0, 100, 100);
		this.krysztaly.setBounds(540, 0, 100, 100);
		this.kamienie.setBounds(400, 0, 100, 100);

		zamek = new Zamek(0, 60, 600, 600, 1);
		zamek.setWieza(Ustawienia.getPoczatkowaWieza());
		zamek.setMur(Ustawienia.getPoczatkowyMur());

		add(zamek);	
	}

	public Karta rzucKarte()
	{
		return null;
	}
}
