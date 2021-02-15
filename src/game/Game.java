package game;

import javax.swing.JFrame;

import control.Keyboard;

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
    
    private static int aps = 0;
    private static int fps = 0;
    

    private static JFrame window;
    private static Thread thread;
    private static Keyboard keyboard;
    
    private static volatile boolean running= false;
    
    private Game()
    {
        setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));

        keyboard = new Keyboard();
        addKeyListener(keyboard);
        
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
    
    private synchronized void start() 
    {
    	running = true;
    	
    	thread = new Thread(this, "Gráficos");
    	thread.start(); 
    }

    private synchronized void stop() 
    {
    	running = false;
    	try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    private void refresh() 
    {
    	keyboard.refresh();
    	
    	if(keyboard.up) 
    	{
    		System.out.println("::UP::");
    	}
    	if(keyboard.down) 
    	{
    		System.out.println("::DOWN::");
    	}
    	if(keyboard.right) 
    	{
    		System.out.println("::RIGHT::");
    	}
    	if(keyboard.left) 
    	{
    		System.out.println("::LEFT::");
    	}
    	
    	aps++;
    }
    
    private void showGraphics() {
    	fps++;
    }
    
    
    
	@Override
	public void run() {
		System.out.println("ejecutando...");
		
		final int NS_POR_SEGUNDO = 1000000000;
		final byte APS_OBJETIVO = 60; 
		final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO;
		
		long referenciaActualizacion = System.nanoTime();
		
		long referenciaContador = System.nanoTime();
		
		double tiempoTranscurrido;
		double delta = 0;
		
		requestFocus();
		
		while(running) 
		{
			final long inicioBucle = System.nanoTime();
			
			tiempoTranscurrido = inicioBucle - referenciaActualizacion;
			referenciaActualizacion = inicioBucle;
			
			delta += tiempoTranscurrido / NS_POR_ACTUALIZACION;
			
			while (delta >= 1) {
				this.refresh();
				delta--;
			}
			
			
			this.showGraphics(); 
			
			if(System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) 
			{
				window.setTitle(NAME + " || APS: " + aps + " || FPS: " + fps);
				aps = 0;
				fps = 0;
				referenciaContador = System.nanoTime();
			}
		}
	}
}
