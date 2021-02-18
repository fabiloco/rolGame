package game;

import javax.swing.JFrame;

import control.Keyboard;
import graphics.Display;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
    /**
	 * 
	 */
	
	private static final int WIDTH = 1024;
    private static final int HEIGHT = 768;

    private static final String NAME = "Juego";
    
    private static int aps = 0;
    private static int fps = 0;
    

    private static int x = 0;
    private static int y = 0;
    
    
    private static JFrame window;
    private static Thread thread;
    private static Keyboard keyboard;
    private static Display display;
    
    private static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private static int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData(); 
    
    private static volatile boolean running= false;
    
    private Game()
    {
        setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));

        display = new Display(WIDTH, HEIGHT);
        
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
    	
    	thread = new Thread(this, "graficos");
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
    		//System.out.println("::UP::");
    		y++;
    	}
    	if(keyboard.down) 
    	{
    		//System.out.println("::DOWN::");
    		y--;
    	}
    	if(keyboard.right) 
    	{
    		//System.out.println("::RIGHT::");
    		x--;
    	}
    	if(keyboard.left) 
    	{
    		//System.out.println("::LEFT::");
    		x++;
    	}
    	
    	aps++;
    }
    
    private void showGraphics() 
    {
    	BufferStrategy estrategia = getBufferStrategy();
    	
    	if(estrategia == null) {
    		createBufferStrategy(3);
    		return;
    	}
    	
    	display.clean();
    	display.show(x, y);
    	
    	System.arraycopy(display.pixels, 0, this.pixels, 0, this.pixels.length);
    	
    	//Manera tosca de hacerlo
    	//for(int i = 0; i < pixels.length; i++) {
    	//	pixels[i] = display.pixels[i];
    	//}
    		
    	Graphics g = estrategia.getDrawGraphics();
    	
    	g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
    	g.dispose();
    	
    	estrategia.show();
    	
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
