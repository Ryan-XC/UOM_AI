package creation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class BulletFactory {
private static final HashMap<String, BulletType> bulletTypeMap = new HashMap<String, BulletType>();
	
private static final String assets = "../assets/";

public BulletType getBulletTypeFromFactory(String objectType){
	BulletType bullet = (BulletType) bulletTypeMap.get(objectType);
	
	if(!(bullet != null)) {
		
		InputStream url = this.getClass().getResourceAsStream(assets + objectType+".png");
		BufferedImage image;
		try {
			image = ImageIO.read(url);
			bullet = new BulletType(objectType, image);
			
			bulletTypeMap.put(objectType, bullet);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	return bullet;
}

}
