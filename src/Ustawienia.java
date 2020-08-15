import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Ustawienia //SINGLETON!!!
{
	private static final int NATYWNA_ROZDZIELCZOSC_X = 1600;
	private static final int NATYWNA_ROZDZIELCZOSC_Y = 900;
	private static int rozdzielczoscX = 1600;
	private static int rozdzielczoscY = 900;
	private static float skalaX;
	private static float skalaY;
	private static boolean pelnyEkran = true;
	private static int POCZATKOWA_WIEZA = 20;
	private static int POCZATKOWY_MUR = 5;
	private static int POCZATKOWE_KOPALNIE = 2;
	private static int POCZATKOWE_ZASOBY = 5;
	private static int WYGRANA_WIEZA = 50;
	private static int WYGRANA_ZASOBY = 150;
	private static PoziomTrudnosci poziomTrudnosci;
	
	private static Ustawienia instance = null;
	
	   private Ustawienia() {}
	   
	   public static Ustawienia getInstance() 
	   {
	      if(instance == null) {
	         instance = new Ustawienia();
	      }
	      return instance;
	   }

	   public static void ustawRozdzielczosc(int width, int height)
	   {
		   rozdzielczoscX = width;
		   rozdzielczoscY = height;
	   }
	   
	   public static void ustawSkale()
	   {
		   skalaX = (float)rozdzielczoscX / NATYWNA_ROZDZIELCZOSC_X;
		   skalaY = (float)rozdzielczoscY / NATYWNA_ROZDZIELCZOSC_Y;
	   }

	   public static void ustawSkale(int width, int height)
	   {
		   skalaX = (float)rozdzielczoscX / width;
		   skalaY = (float)rozdzielczoscY / height;
	   }
	   
	   public static int skaluj(int wartosc) //Na razie będą tylko rozdzielczosci w formacie 16/9
	   {
		   return (int)((float)wartosc * skalaX);
	   }
	   
	   public static int getWidth()
	   {
		   return rozdzielczoscX;
	   }
	   
	   public static int getHeight()
	   {
		   return rozdzielczoscY;
	   }

	   public static void setPelnyEkran(boolean b)
	   {
		   pelnyEkran = b;
	   }

	   public static boolean getPelnyEkran()
	   {
		   return pelnyEkran;
	   }
	   
	   public static int getWygranaZasoby()
	   {
		   return WYGRANA_ZASOBY;
	   }
	   
	   public static int getWygranaWieza()
	   {
		   return WYGRANA_WIEZA;
	   }
	   
	   public static int getPoczatkowaWieza()
	   {
		   return POCZATKOWA_WIEZA;
	   }
	   
	   public static int getPoczatkowyMur()
	   {
		   return POCZATKOWY_MUR;
	   }
	   
	   public static int getPoczatkoweZasoby()
	   {
		   return POCZATKOWE_ZASOBY;
	   }
	   
	   public static int getPoczatkoweKopalnie()
	   {
		   return POCZATKOWE_KOPALNIE;
	   }
	   

	   public static PoziomTrudnosci getPoziomTrudnosci()
	   {
		   return poziomTrudnosci;
	   }
	   
	   public static void zmienPoziomTrudnosci(PoziomTrudnosci pt)
	   {
		   poziomTrudnosci = pt;
		   
		   if (poziomTrudnosci == PoziomTrudnosci.latwy)
		   {
			   WYGRANA_ZASOBY = 150;
			   WYGRANA_WIEZA = 50;
			   POCZATKOWA_WIEZA = 20;
			   POCZATKOWY_MUR = 5;
			   POCZATKOWE_KOPALNIE = 2;
			   POCZATKOWE_ZASOBY = 5;
		   }
		   else if (poziomTrudnosci == PoziomTrudnosci.sredni)
		   {
			   WYGRANA_ZASOBY = 300;
			   WYGRANA_WIEZA = 100;
			   POCZATKOWA_WIEZA = 30;
			   POCZATKOWY_MUR = 15;
			   POCZATKOWE_KOPALNIE = 4;
			   POCZATKOWE_ZASOBY = 10;
		   }
		   else if (poziomTrudnosci == PoziomTrudnosci.trudny)
		   {
			   WYGRANA_ZASOBY = 400;
			   WYGRANA_WIEZA = 150;
			   POCZATKOWA_WIEZA = 20;
			   POCZATKOWY_MUR = 10;
			   POCZATKOWE_KOPALNIE = 5;
			   POCZATKOWE_ZASOBY = 25;
		   }
	   }
	   
	   public static void zapiszUstawienia() throws FileNotFoundException
	   {
		   PrintWriter plik = new PrintWriter("ustawienia.txt");      
		   plik.println(pelnyEkran);
		   plik.println(rozdzielczoscX);
		   plik.println(rozdzielczoscY);
		   plik.println(poziomTrudnosci);
		   plik.close();
	   }
	   
	   public static void wczytajUstawienia() throws FileNotFoundException
	   {
		   File plik = new File("ustawienia.txt");
		   Scanner in = new Scanner(plik);
		   
		   Ustawienia.setPelnyEkran(in.nextBoolean());
		   Ustawienia.ustawRozdzielczosc(in.nextInt(), in.nextInt());
		   
		   String trudnosc = in.next();
		   
		   if (trudnosc.equals("latwy"))
		   {
			   Ustawienia.zmienPoziomTrudnosci(PoziomTrudnosci.latwy);
		   } 
		   else if (trudnosc.equals("sredni"))
		   {
			   Ustawienia.zmienPoziomTrudnosci(PoziomTrudnosci.sredni);
		   }
		   else
		   {
			   Ustawienia.zmienPoziomTrudnosci(PoziomTrudnosci.trudny);
		   }
	   }
}
