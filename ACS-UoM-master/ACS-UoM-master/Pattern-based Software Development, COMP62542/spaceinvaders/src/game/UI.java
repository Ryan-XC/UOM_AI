package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class UI extends Canvas implements Iui {

	private BufferStrategy strategy;
	private Game game;
	private BufferedImage background1;
	private BufferedImage background2;
	private final String assets = "../assets/";
	
	private int start;
	
	public UI(IMainGame game) {
		this.game = (Game) game;
		this.start = -3840;
		createWindow();
		try {
			InputStream url = this.getClass().getResourceAsStream(assets + "background1.png");
		    background1 = ImageIO.read(url);
		    InputStream url2 = this.getClass().getResourceAsStream(assets + "background2.png");
		    background2 = ImageIO.read(url2);
		    
		    drawUI();
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		

	}

	public void createWindow() {
		JFrame container = new JFrame("Pattern Invaders");
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(1024, 768));
		panel.setLayout(null);
		setBounds(0, 0, 1024, 768);
		panel.add(this);

		setIgnoreRepaint(true);
		container.pack();
		container.setResizable(false);

		container.setLocationRelativeTo(null);
		container.setVisible(true);

		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					game.setMoveLeft(true);				
					
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					game.setMoveRight(true);
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					game.setFire(true);
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					game.setPause(true);
				}
				if (e.getKeyCode() == KeyEvent.VK_Q){
					game.setQuit(true);
				}
				if (e.getKeyCode() == KeyEvent.VK_R){
					game.setRestart(true);
				}
				game.setAnyKeyPressed(true);
				game.getState().changeState();
				//game.getState().draw(getGraphic());
				drawUI();
				//System.out.println("click");
				// game.getState().play();
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					game.setMoveLeft(false);
				}
				if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					game.setMoveRight(false);
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					game.setFire(false);
				}
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					game.setPause(false);
				}
				if (e.getKeyCode() == KeyEvent.VK_Q){
					game.setQuit(false);
				}
				if (e.getKeyCode() == KeyEvent.VK_R){
					game.setRestart(false);
				}
				game.setAnyKeyPressed(false);

			}
		});

		requestFocus();

		createBufferStrategy(2);
		strategy = getBufferStrategy();

	}

	@Override
	public void drawUI() {
		Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
		g.setColor(Color.black);
		// g.fillRect(0, 0, 1024, 768);
		g.drawImage(background1, 0, 0, null);
		g.drawImage(background2, 0, start/5, null);
		g.drawImage(background2, 0, 768+(start/5), null);

		start += 1;
		if (start == 0) start = -3840;
		
		this.game.getState().draw(g);

		g.dispose();
		strategy.show();
	}

}
