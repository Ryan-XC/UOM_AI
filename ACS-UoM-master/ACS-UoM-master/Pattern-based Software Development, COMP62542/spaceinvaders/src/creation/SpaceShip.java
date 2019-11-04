package creation;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Created by Bing on 21/04/2016.
 */
public class SpaceShip {
	private String type;
	private BufferedImage image;

    public SpaceShip(String type, BufferedImage image) {
        this.type = type;
        this.image = image;
        System.out.println("create " + type);
    }
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

    
}