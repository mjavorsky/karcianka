import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StanGry implements Serializable {

	private Granie game;
	
	
	private taliaKart stosOdkryty;
	private taliaKart stosZakryty;
	private List<Integer> odkryte;
	private List<Integer> zakryte;
	private PoziomTrudnosci poziom;
	
	
	private Gracz player1;
	private taliaKart talia1;
	private String nazwa1;
	private Zasoby kamienie1;
	private Zasoby krysztaly1;
	private Zasoby bestie1;
	private Zamek zamek1;
	private int mur1;
	private int wieza1;
	private int iloscKamieni1;
	private int produkcjaKamieni1;
	private int iloscKrysztalow1;
	private int produkcjaKrysztalow1;
	private int iloscBestii1;
	private int produkcjaBestii1;
	private List<Integer> listaKart1;
	
	
	
	private Gracz player2;
	private taliaKart talia2;
	private String nazwa2;
	private Zasoby kamienie2;
	private Zasoby krysztaly2;
	private Zasoby bestie2;
	private Zamek zamek2;
	private int mur2;
	private int wieza2;
	private int iloscKamieni2;
	private int produkcjaKamieni2;
	private int iloscKrysztalow2;
	private int produkcjaKrysztalow2;
	private int iloscBestii2;
	private int produkcjaBestii2;
	private List<Integer> listaKart2;
	
	public StanGry() {
		
	}
	
	public StanGry(Granie game) {
		this.game = game;
		
		stosOdkryty = this.game.getStosOdkryty();
		stosZakryty = this.game.getStosZakryty();
		
		odkryte = new ArrayList<Integer>();
		zakryte = new ArrayList<Integer>();
		
		for(Karta item : stosOdkryty.getKarty())
			odkryte.add(item.getID());
		
		for(Karta item : stosZakryty.getKarty())
			zakryte.add(item.getID());
		
		
		player1 = this.game.getPlayer1();
		talia1 = player1.getTalia();
		nazwa1 = player1.getNazwa();
		kamienie1 = player1.getKamienie();
		krysztaly1 = player1.getKrysztaly();
		bestie1 = player1.getBestie();
		zamek1 = player1.getZamek();
		mur1 = zamek1.getMur();
		wieza1 = zamek1.getWieza();
		iloscKamieni1 = kamienie1.getIlosc();
		produkcjaKamieni1 = kamienie1.getProdukcja();
		iloscKrysztalow1 = krysztaly1.getIlosc();
		produkcjaKrysztalow1 = krysztaly1.getProdukcja();
		iloscBestii1 = bestie1.getIlosc();
		produkcjaBestii1 = bestie1.getProdukcja();
		listaKart1 = new ArrayList<Integer>();
		
		for(Karta item : talia1.getKarty())
			listaKart1.add(item.getID());
		
		
		
		player2 = this.game.getPlayer2();
		talia2 = player2.getTalia();
		nazwa2 = player2.getNazwa();
		kamienie2 = player2.getKamienie();
		krysztaly2 = player2.getKrysztaly();
		bestie2 = player2.getBestie();
		zamek2 = player2.getZamek();
		mur2 = zamek2.getMur();
		wieza2 = zamek2.getWieza();
		iloscKamieni2 = kamienie2.getIlosc();
		produkcjaKamieni2 = kamienie2.getProdukcja();
		iloscKrysztalow2 = krysztaly2.getIlosc();
		produkcjaKrysztalow2 = krysztaly2.getProdukcja();
		iloscBestii2 = bestie2.getIlosc();
		produkcjaBestii2 = bestie2.getProdukcja();
		listaKart2 = new ArrayList<Integer>();
		
		for(Karta item : talia2.getKarty())
			listaKart2.add(item.getID());
		
		poziom = Ustawienia.getPoziomTrudnosci();
		
		
		
	}
	
	
	private void writeObject(ObjectOutputStream out) throws IOException {
		
		out.writeObject(odkryte);
		out.writeObject(zakryte);
		out.writeObject(listaKart1);
		out.writeObject(listaKart2);
		
		
		out.writeObject(nazwa1);
		out.writeInt(iloscKamieni1);
		out.writeInt(produkcjaKamieni1);
		out.writeInt(iloscKrysztalow1);
		out.writeInt(produkcjaKrysztalow1);
		out.writeInt(iloscBestii1);
		out.writeInt(produkcjaBestii1);
		out.writeInt(mur1);
		out.writeInt(wieza1);
		
		out.writeObject(nazwa2);
		out.writeInt(iloscKamieni2);
		out.writeInt(produkcjaKamieni2);
		out.writeInt(iloscKrysztalow2);
		out.writeInt(produkcjaKrysztalow2);
		out.writeInt(iloscBestii2);
		out.writeInt(produkcjaBestii2);
		out.writeInt(mur2);
		out.writeInt(wieza2);
		
		out.writeObject(poziom);
	}
	
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {
		
		odkryte = (List<Integer>)in.readObject();
		zakryte = (List<Integer>)in.readObject();
		listaKart1 = (List<Integer>)in.readObject();
		listaKart2 = (List<Integer>)in.readObject();
		
		
		
		nazwa1 = (String)in.readObject();
		iloscKamieni1 = in.readInt();
		produkcjaKamieni1 = in.readInt();
		iloscKrysztalow1 = in.readInt();
		produkcjaKrysztalow1 = in.readInt();
		iloscBestii1 = in.readInt();
		produkcjaBestii1 = in.readInt();
		mur1 = in.readInt();
		wieza1 = in.readInt();
		
		nazwa2 = (String)in.readObject();
		iloscKamieni2 = in.readInt();
		produkcjaKamieni2 = in.readInt();
		iloscKrysztalow2 = in.readInt();
		produkcjaKrysztalow2 = in.readInt();
		iloscBestii2 = in.readInt();
		produkcjaBestii2 = in.readInt();
		mur2 = in.readInt();
		wieza2 = in.readInt();
		
		poziom = (PoziomTrudnosci)in.readObject();
	}
	
	public void zapisz() throws IOException {
		
		FileOutputStream file = new FileOutputStream("save1.sav");
		ObjectOutputStream out = new ObjectOutputStream(file);
		this.writeObject(out);
		out.close();
		file.close();
	}
	
	public Granie wczytaj() throws IOException, ClassNotFoundException {
		FileInputStream file = new FileInputStream("save1.sav");
		ObjectInputStream in = new ObjectInputStream(file);
		this.readObject(in);
		
		in.close();
		file.close();
		
		Ustawienia.zmienPoziomTrudnosci(poziom);
		
		Granie g = new Granie(Gra.getInstance(), false);
		
		
		
		player1 = g.getPlayer1();
		player1.getKamienie().setIlosc(iloscKamieni1);
		player1.getKamienie().setProdukcja(produkcjaKamieni1);
		player1.getKrysztaly().setIlosc(iloscKrysztalow1);
		player1.getKrysztaly().setProdukcja(produkcjaKrysztalow1);
		player1.getBestie().setIlosc(iloscBestii1);
		player1.getBestie().setProdukcja(produkcjaBestii1);
		player1.getZamek().setMur(mur1);
		player1.getZamek().setWieza(wieza1);
		player1.setNazwa(nazwa1);
		
		player2 = g.getPlayer2();
		player2.getKamienie().setIlosc(iloscKamieni2);
		player2.getKamienie().setProdukcja(produkcjaKamieni2);
		player2.getKrysztaly().setIlosc(iloscKrysztalow2);
		player2.getKrysztaly().setProdukcja(produkcjaKrysztalow2);
		player2.getBestie().setIlosc(iloscBestii2);
		player2.getBestie().setProdukcja(produkcjaBestii2);
		player2.getZamek().setMur(mur2);
		player2.getZamek().setWieza(wieza2);
		player2.setNazwa(nazwa2);
		
		
		int[] tab = new int[listaKart1.size()];
		
		for(int i =0;i<tab.length;i++) {
			int j =0;
			for (Karta item : g.getStosZakryty().getKarty()) {
				if(listaKart1.get(i) == item.getID()){
					tab[i] = j;
					break;
				}
				j++;
			}
		}
		
		int[] tab2 = tab.clone();
		
		for(int i = 0; i < tab.length; i++)
			tab[i] = tab2[tab.length - 1 - i];
		
		int k = 0;
		
		for(int i = 0; i < tab.length; i++) {
			
			for(int j = 0; j < i; j++)
				if(tab[i] > tab[j])
					k++;
			player1.getTalia().dodaj(g.getStosZakryty().wez(tab[i] - k));
			k = 0;
		}
		
		
		
		tab = new int[listaKart2.size()];
		
		for(int i =0;i<tab.length;i++) {
			int j =0;
			for (Karta item : g.getStosZakryty().getKarty()) {
				if(listaKart2.get(i) == item.getID()){
					tab[i] = j;
					break;
				}
				j++;
			}
		}
		
		tab2 = tab.clone();
		
		for(int i = 0; i < tab.length; i++)
			tab[i] = tab2[tab.length - 1 - i];
		
		k = 0;
		
		for(int i = 0; i < tab.length; i++) {
			
			for(int j = 0; j < i; j++)
				if(tab[i] > tab[j])
					k++;
			player2.getTalia().dodaj(g.getStosZakryty().wez(tab[i] - k));
			k = 0;
		}
		
		
		
		tab = new int[odkryte.size()];
		
		for(int i = 0;i<tab.length;i++) {
			int j =0;
			for (Karta item : g.getStosZakryty().getKarty()) {
				if(odkryte.get(i) == item.getID()){
					tab[i] = j;
					break;
				}
				j++;
			}
		}
		
		tab2 = tab.clone();
		
		for(int i = 0; i < tab.length; i++)
			tab[i] = tab2[tab.length - 1 - i];
		
		k = 0;
		
		for(int i=0; i < tab.length; i++) {
			
			for(int j = 0; j < i; j++)
				if(tab[i] > tab[j])
					k++;
			g.getStosOdkryty().dodaj(g.getStosZakryty().wez(tab[i] - k));
			k = 0;
		}
		
		g.getStosZakryty().tasuj();
		
		g.przygotujDoWczytania();
		
		
		return g;
	}
}
