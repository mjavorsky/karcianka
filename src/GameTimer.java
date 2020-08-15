import javax.swing.Timer;
import java.awt.event.*;

public class GameTimer extends Timer {
    
    private static int Speed = 1;
    private static int obroty = 0;
    
    private static GameTimer instance = null;
	
	   private GameTimer(ActionListener al) {super(Speed, al);}
	   
	   public static GameTimer getInstance() 
	   {
	      if(instance == null) {
	         instance = new GameTimer( new ActionListener() {

                     @Override
                     public void actionPerformed(ActionEvent e) 
                     {
                         ++obroty;
                    }
                 
                 });
	      }
	      return instance;
	   }
           
       public int getObroty() {
           return obroty;
       }
}
