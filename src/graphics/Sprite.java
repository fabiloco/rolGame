package graphics;

public final class Sprite 
{
	private final int side;
	
	private int x;
	private int y;
	
	public int[] pixels;
	private SpriteSheet sheet;
	
	public Sprite(final int side, final int column, final int row, final SpriteSheet sheet)
	{
		this.side=side;
		
		pixels = new int[this.side * this.side];
		
		this.x = column * this.side;
		this.y = row * this.side;
		this.sheet = sheet;
		
		for(int y = 0; y < side; y++) {
			for(int x = 0; x < side; x++) {
				pixels[(x + y) * side] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.getHeight()]; 
			}
		}
	}
}
