package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet 
{
	//Array que contendra todo el valor de los colores (en RGB) de la hoja de sprite que se le pase
	public final int[] pixels;
	
	//Dimensiones de la spritesheet
	private final int width;
	private final int height;
	
	
	public SpriteSheet(final String path, final int width, final int height) {
		this.width = width;
		this.height = height;
		
		//Se multiplica el ancho y alto de la spritesheet, para inicializar el vector pixeles con el número de pixeles que tenga la imagen
		pixels = new int[width * height]; 
		
		//Se crea una imagen
		BufferedImage img;
		
		try {
			//Se lee la imagen con la ruta
			img = ImageIO.read(SpriteSheet.class.getResource(path));
			/*
			 * Se rellena el vector de pixeles con el valor de los pixeles en RGB que tiene la imagen, automaticamente con el metodo getRGB de la clase BufferedImage.
			 * El primer parametro corresponde a el valor inicial X y el segundo parametro al valor inicial en Y.
			 * El tercer parametro corresponde al ancho de la imagen y el cuarto al alto de la imagen.
			 * El quinto corresponde al vector en donde guardaremos el valor de los pixeles. 
			 * El sexto parametro corresponde a el offset, generalmente se pone 0.
			 * y el septimo es el ancho de la imagen, o el tamaño del escaneo, generalmente se pone el ancho de la imagen.
			 * 
			*/
			img.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	public int getHeight() {
		return this.height;
	}
	
}
