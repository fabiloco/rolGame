package graphics;

public class Display 
{
	private final int width;
	private final int height;
	
	public final int[] pixels;

	//Inicio temporal
	
	private final static int SIDE_SPRITE = 32;
	private final static int MASK_SPRITE = SIDE_SPRITE - 1;
	
	//Fin temporal
	
	public Display(final int width, final int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
	//Sirve para limpiar la pantalla, pintar todos los pixeles de negro
	public void clean() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void show(final int compensacionX, final int compensacionY) {
		for(int y = 0; y < height; y++){
			int posicionY = y + compensacionY;
			
			//Marca los limites de la pantalla, sirve para que no se este mostrando algún pixel en la pantalla, lo cual causaria un error
			if(posicionY < 0 || posicionY >= height) {
				continue; 
			}
			
			
			for(int x = 0; x < width; x++) {
				int posicionX = x + compensacionX;
				
				//Marca los limites de la pantalla, sirve para que no se este mostrando algún pixel en la pantalla, lo cual causaria un error
				if(posicionX < 0 || posicionX >= width) {
					continue;
				}
				
				//Temporal
				pixels[posicionX + posicionY * width] = Sprite.grass.pixels[(x & MASK_SPRITE) + (y & MASK_SPRITE) * SIDE_SPRITE];
				
			}
		}
	}
}
