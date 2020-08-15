import javax.swing.*;

public class Animacja extends JLabel{
   
    int startX;
    int startY;
    int endX;
    int endY;
    
    int start;
    int end;
    int czas;
    
    float x;
    float y;
    
    Obrazek o;
    
    boolean running;
    Thread t;
    
    public Animacja(int startX, int startY, int endX, int endY, Obrazek o, int czas) {
        
        this.o = o;
        Icon ic = o.getWyglad().getIcon();
        setIcon(ic);
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        x = startX;
        y = startY;
        setBounds(startX, startY, o.getWyglad().getWidth(), o.getWyglad().getHeight());
        setVisible(true);
        this.czas = czas;
        running = false;
    }
    
    public void move() {
        running = true;
        t = new Thread(){
            public void run() {
                
                start = GameTimer.getInstance().getObroty();
                end = start + czas;
                
                setVisible(true);
                while(x != endX && y != endY) {
                    float d = (GameTimer.getInstance().getObroty() - start) / (float)czas;
                    x = startX + d * (endX - startX);
                    y = startY + d * (endY - startY);
                    setLocation((int)x, (int)y);
                    
                    repaint();
                    
                }	

                setVisible(false);
                setLocation(startX, startY);
                running = false;
                t.interrupt();
            }
        };
        t.start();
        
    }
    
    public void skaluj()
    {
        o.skaluj();
        setIcon(o.getWyglad().getIcon());
        
        setSize(o.getWyglad().getWidth(), o.getWyglad().getHeight());
    }
    
    public boolean moving() {
        return running;
    }
    
    public Thread getWatek()
    {
        return t;
    }
}
