package game;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
    /**
	 * 
	 */
	
	private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final String NAME = "Juego";

    private static JFrame window;

    private static Thread thread;
    
    private Game()
    {
        setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));

        window = new JFrame(NAME);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(new BorderLayout());
        window.add(this, BorderLayout.CENTER);
        window.pack();
        window.setLocationRelativeTo(null);
        
        window.setVisible(true);
    }
    
    public static void main(String []args) {
    	Game game = new Game();
    	game.start();
    }
    
    private void start() 
    {
    	thread = new Thread(this, "Gráficos");
    	thread.start(); 
    }

    private void stop() 
    {
    	
    }
    
	@Override
	public void run() {
		System.out.println("ejecutando");
	}
}
