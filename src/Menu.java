import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;


public class Menu extends Ekran implements ActionListener
{
	private Przycisk game;
	private Przycisk exit;
	private Przycisk settings;
	private Przycisk load;
	private Obrazek tlo;

	public Menu(Gra parent)
	{
		super(parent);
		
		this.setVisible(true);
		
		try
		{
			makeGUI();
		}
		catch (IOException ex)
		{
			System.out.println(ex.getMessage());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String action = e.getActionCommand();
		
		if (action.equals("exit"))
		{
			System.exit(0);
			//parent.koniecProgramu();
		}

		if (action.equals("newgame"))
		{
			parent.pushEkran(new Granie(parent, true));
		}
		
		if (action.equals("settings"))
		{
			parent.pushEkran(new EkranUstawien(parent));
		}
		
		if (action.equals("load"))
		{
			StanGry sg = new StanGry();
			try {
				Granie g = sg.wczytaj();
				parent.pushEkran(g);
			} catch (ClassNotFoundException | IOException e1) {
				OknoInformacyjne blad = new OknoInformacyjne("Nie udało się wczytać gry.", OknoInformacyjne.Rodzaj.OK, parent);
				e1.printStackTrace();
				System.out.println(e1.getMessage());
			}
		}
	}

	void makeGUI() throws IOException 
	{
		game = new Przycisk("Przycisk/new_game.png", "Przycisk/new_game2.png");
		game.addActionListener(this);
		game.setActionCommand("newgame");
		game.setBounds(200, 500, 128, 146);
		add(game);

		settings = new Przycisk("Przycisk/ustawienia.png", "Przycisk/ustawienia2.png");
		settings.addActionListener(this);
		settings.setActionCommand("settings");
		settings.setBounds(800, 500, 128, 146);
		add(settings);
		
		exit = new Przycisk("Przycisk/exit.png", "Przycisk/exit2.png");
		exit.addActionListener(this);
		exit.setActionCommand("exit");
		exit.setBounds(1100, 500, 128, 146);
		add(exit);
		
		load = new Przycisk("Przycisk/load.png", "Przycisk/load2.png");
		load.addActionListener(this);
		load.setActionCommand("load");
		load.setBounds(500, 500, 128, 146);
		add(load);
		
		tlo = new Obrazek("/Grafika/tlo_menu.png", 0, 0, 1600, 900);
		add(tlo.getWyglad());
	}
	
	@Override
	public void skaluj() 	
	{
		game.skaluj();
		exit.skaluj();
		settings.skaluj();
		load.skaluj();
		tlo.skaluj();
	}
}
