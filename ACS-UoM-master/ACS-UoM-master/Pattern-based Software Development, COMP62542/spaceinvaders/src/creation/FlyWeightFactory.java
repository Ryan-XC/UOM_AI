package creation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class FlyWeightFactory {
	private static final HashMap<String, SpaceShip> SpaceshipMap = new HashMap<String, SpaceShip>();

	private static final String assets = "../assets/";
	
	public SpaceShip getPlaneFromFactory(String objectType){
		SpaceShip spaceship = (SpaceShip) SpaceshipMap.get(objectType);
		
		if(!(spaceship != null)) {
			
			InputStream url = this.getClass().getResourceAsStream(assets + objectType+".png");
			BufferedImage image;
			try {
				image = ImageIO.read(url);
				spaceship = new SpaceShip(objectType, image);
				
				SpaceshipMap.put(objectType, spaceship);
			} catch (IOException e) {
				
				System.out.println(e.getMessage() + "\n" + objectType);
			}
			
		}
		
		return spaceship;
	}
	
	

}
