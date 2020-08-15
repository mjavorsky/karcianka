import javax.swing.JPanel;

public abstract class Gracz extends JPanel
{
	protected taliaKart talia;
	protected int zycie;
	protected Zasoby kamienie;
	protected Zasoby krysztaly;
	protected Zasoby bestie;
	protected Zamek zamek;
	protected Gracz przeciwnik;
	protected boolean przegrana = false;
	protected boolean wygrana = false;
	protected String nazwa;
	private final int MAX_KART = 5;
	
	protected Gracz(String name)
	{	
		this.setLayout(null);
		this.setVisible(true);
		this.setOpaque(false);	//Przeźroczystość
		
		this.nazwa = name;
		
		this.kamienie = new Zasoby(Ustawienia.getPoczatkoweKopalnie(), Ustawienia.getPoczatkoweZasoby());
		this.krysztaly = new Zasoby(Ustawienia.getPoczatkoweKopalnie(), Ustawienia.getPoczatkoweZasoby());
		this.bestie = new Zasoby(Ustawienia.getPoczatkoweKopalnie(), Ustawienia.getPoczatkoweZasoby());
		
		this.kamienie.setToolTips("Kopalnie", "Kamienie");
		this.krysztaly.setToolTips("Magia", "Kryształy");
		this.bestie.setToolTips("Podziemia", "Bestie");
		
		add(this.kamienie);
		add(this.krysztaly);
		add(this.bestie);
		
		talia = new taliaKart(430, 665, true);
		talia.setGracz(this);
		talia.setMaksymalnaIloscKart(5);
		talia.setTaliatLayout(1);
	}
	
	public int zmianaZycie(int wartosc)
	{
		if (zamek.getMur() >= -wartosc)	//Wartość jest ujemna
		{
			zamek.zmianaMur(wartosc);
			return zamek.getMur() + zamek.getWieza();
		}
		else
		{
			wartosc += zamek.getMur();
			zmianaMur(-zamek.getMur());			//Można od razu: zamek.setMur(0); nie wiem jak bedzie czytelniej 
												//ale jeżeli będziemy chcieli wstawiać jakieś animacje to chyba tak będzie lepiej
			return zmianaWieza(wartosc);
		}
	}
	
	public int zmianaWieza(int wartosc)
	{		
		return zamek.zmianaWieza(wartosc);
	}
	
	public int zmianaMur(int wartosc)
	{
		return zamek.zmianaMur(wartosc);
	}
	
	public int zmianaKopalnie(int wartosc)
	{
		return kamienie.zmianaProdukcja(wartosc);
	}
	
	public int zmianaKamienie(int wartosc)
	{
		return kamienie.zmianaIlosc(wartosc);
	}
	
	public int zmianaMagia(int wartosc)
	{
		return krysztaly.zmianaProdukcja(wartosc);
	}
	
	public int zmianaKrysztaly(int wartosc)
	{
		return krysztaly.zmianaIlosc(wartosc);
	}
	
	public int zmianaPodziemia(int wartosc)
	{
		return bestie.zmianaProdukcja(wartosc);
	}
	
	public int zmianaBestie(int wartosc)
	{
		return bestie.zmianaIlosc(wartosc);
	}
	
	public void dodajZasoby()
	{
		this.zmianaKamienie(this.zmianaKopalnie(0));
		this.zmianaKrysztaly(this.zmianaMagia(0));
		this.zmianaBestie(this.zmianaPodziemia(0));
	}
	
	public void setPrzeciwnik(Gracz nowy)
	{
		if (this.przeciwnik == nowy) return;
		
		this.przeciwnik = nowy;
		this.przeciwnik.setPrzeciwnik(this);
	}

	public Gracz getPrzeciwnik()
	{
		return this.przeciwnik;
	}

	public taliaKart getTalia()
	{
		return this.talia;
	}

	public void skaluj()
	{
		
		zamek.skaluj();
		kamienie.skaluj();
		krysztaly.skaluj();
		bestie.skaluj();
		talia.skaluj();
		
		int x = Ustawienia.skaluj(this.getX());
		int y = Ustawienia.skaluj(this.getY());
		int width = Ustawienia.skaluj(this.getWidth());
		int height = Ustawienia.skaluj(this.getHeight());

    	this.setBounds(x, y, width, height);
	}
	
	public boolean czyPrzegrana()
	{
		if (zamek.getWieza() <= 0)
		{
			setPrzegrana(true);
			przeciwnik.setWygrana(true);
		}
		
		return przegrana;
	}

	public void setPrzegrana(boolean b)
	{
		przegrana = b;
		wygrana = false;
	}

	public void setWygrana(boolean b)
	{
		wygrana = b;
		przegrana = false;
	}
	
	public boolean czyWygrana()
	{
		if (kamienie.getIlosc() >= Ustawienia.getWygranaZasoby() 
				&& krysztaly.getIlosc() >= Ustawienia.getWygranaZasoby()
				&& bestie.getIlosc() >= Ustawienia.getWygranaZasoby())
		{
			this.setWygrana(true);
			przeciwnik.setPrzegrana(true);
		}
		
		if (zamek.getWieza() >= Ustawienia.getWygranaWieza())
		{
			this.setWygrana(true);
			przeciwnik.setPrzegrana(true);
		}
		
	
		return wygrana;
	}
	
	public void pokazWarunkiWygranej()
	{
		kamienie.pokazWygrana();
		krysztaly.pokazWygrana();
		bestie.pokazWygrana();
		zamek.pokazWygrana();
	}
	
	public void ukryjWarunkiWygranej()
	{
		kamienie.ukryjWygrana();
		krysztaly.ukryjWygrana();
		bestie.ukryjWygrana();
		zamek.ukryjWygrana();
	}
	
	public abstract Karta rzucKarte();
	
	public void poczatekTury()
	{
		this.dodajZasoby();
		
		for (Karta karta: talia.getKarty())
		{
			karta.setZablokowana( !(karta.czyMoznaRzucic(this)) );
		}
	}
	
	public void poczatekGry()
	{
		for (Karta i: talia.getKarty())
			i.setZablokowana( !(i.czyMoznaRzucic(this)) );
	}
	
	public Zasoby getKamienie() {
		return kamienie;
	}
	
	public void setKamienie(Zasoby kamienie) {
		this.kamienie = kamienie;
	}
	
	public Zasoby getKrysztaly() {
		return krysztaly;
	}
	
	public void setKrysztaly(Zasoby krysztaly) {
		this.krysztaly = krysztaly;
	}
	
	public Zasoby getBestie() {
		return bestie;
	}
	
	public void setBestie(Zasoby bestie) {
		this.bestie = bestie;
	}
	
	public String getNazwa() {
		return nazwa;
	}
	
	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}
	
	public Zamek getZamek() {
		return zamek;
	}
}
