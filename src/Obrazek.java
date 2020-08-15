import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Obrazek
{
	private JLabel wyglad;
	
	private String path;
	private static HashMap<String, BufferedImage> mapa;
	
	public Obrazek(String grafika, int x, int y, int szerokosc, int wysokosc) throws IOException	
	{		
		if (mapa == null)
		{
			mapa = new HashMap<String, BufferedImage>(); 
		}
		
		BufferedImage image = mapa.get(grafika);
		path = grafika;
		
		if (image == null)
		{
			image = ImageIO.read(new File(System.getProperty("user.dir") + path));
			mapa.put(path, image);
		}	
		
		wyglad = new JLabel(new ImageIcon(resize(image, szerokosc, wysokosc)));
		wyglad.setBounds(x, y, szerokosc, wysokosc);
	}
	
	public JLabel getWyglad()
	{
		return wyglad;
	}
	
	public static  BufferedImage resize(BufferedImage image, int width, int height) 
	{
		//Jakieś dziwne bzdety do skalowania karty
       int type=0;
       type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
       BufferedImage resizedImage = new BufferedImage(width, height, type);
       Graphics2D g = resizedImage.createGraphics();
       g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
       g.drawImage(image, 0, 0, width, height, null);
       g.dispose();
       return resizedImage;
    }
	
	public static  Image resize(Image image, int width, int height) 
	{
		//Jakieś dziwne bzdety do skalowania karty
	    BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(image, 0, 0, width, height, null);
	    g2.dispose();

	    return resizedImg;
    }

    public void skaluj()
    {
		int x = Ustawienia.skaluj(wyglad.getX());
		int y = Ustawienia.skaluj(wyglad.getY());
		int width = Ustawienia.skaluj(wyglad.getWidth());
		int height = Ustawienia.skaluj(wyglad.getHeight());
		
		//image = resize(image, width, height);
		
		BufferedImage image = mapa.get(path);
		
		wyglad.setIcon(new ImageIcon(resize(image, width, height)));
		wyglad.setBounds(x, y, width, height);
    }
}
