import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Przycisk extends JButton
{
	private ImageIcon icon1;
	private ImageIcon icon2;
	private ImageIcon icon3;
	
	public Przycisk(String path1, String path2) {
		super();
		icon1 = new ImageIcon(path1);
		this.setIcon(icon1);

		icon2 = new ImageIcon(path2);
		icon3 = null;
		
		this.addMouseListener(new EventPrzycisk(this, icon1, icon2));
		this.addMouseListener(new EventKursor(Gra.getInstance(), TypKursora.klikanie));
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setVisible(true);
	}
	
	
	public void skaluj()
	{
		int x = Ustawienia.skaluj(this.getX());
		int y = Ustawienia.skaluj(this.getY());
		int width = Ustawienia.skaluj(this.getWidth());
		int height = Ustawienia.skaluj(this.getHeight());

		icon1.setImage(icon1.getImage().getScaledInstance(width, height, 0));
		icon2.setImage(icon2.getImage().getScaledInstance(width, height, 0));
		
		this.setBounds(x, y, width, height);
	
	}
	
	public void wlacz()
	{
		this.setEnabled(true);
		this.setVisible(true);
	}
	
	public void wylacz()
	{
		this.setEnabled(false);
		this.setVisible(false);
	}
	
	public void setOffIcon(String path)
	{
		icon3 = new ImageIcon(path);
	}
}
