package blackjack.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.border.BevelBorder;

import blackjack.model.Card;
import blackjack.model.Clover;
import blackjack.model.Diamond;
import blackjack.model.Heart;
import blackjack.model.Spade;


public class CartaGUI extends JFrame {
	private int x;			//tamany de la carta
	private int y;			
	private int posX;		//posició de la carta
	private int posY;
	BufferedImage baralla;
	BufferedImage radera;

	
	
	
	public CartaGUI(int x) throws IOException {
		this.x = x;
		this.y = (int)(this.x * 1.4);
		
        File f = new File("./src/blackjack/view/baraja-poker.jpg");
        baralla = ImageIO.read(f);
        f = new File("./src/blackjack/view/radera.jpg");
        radera = ImageIO.read(f);
	}

	public BufferedImage resize(BufferedImage bufferedImage) {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        BufferedImage bufim = new BufferedImage(this.x, this.y, bufferedImage.getType());
        Graphics2D g = bufim.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(bufferedImage, 0, 0, this.x, this.y, 0, 0, w, h, null);
        g.dispose();
        return bufim;
    }
	
	public Image getImatge(Card oCarta) {
		BufferedImage recorte;
		int xInicial = 0;
		int yInicial = 0;
		
		if (oCarta.getView()){ 
			//Calculem la posició de la imatge que hem d'obtenir.
			if (oCarta instanceof Heart) {
				xInicial = (((Heart) oCarta).getNumber()-1) * 225;
				yInicial = 0;
			} else if (oCarta instanceof Spade) {
				xInicial = (((Spade) oCarta).getNumber()-1) * 225;
				yInicial = 315;
			} else if (oCarta instanceof Diamond) {
				xInicial = (((Diamond) oCarta).getNumber()-1) * 225;
				yInicial = 630;
			} else {
				xInicial = (((Clover) oCarta).getNumber()-1) * 225;
				yInicial = 945;
			}
			
			recorte = ((BufferedImage) baralla).getSubimage(xInicial, yInicial, 225, 315);
		} else {
			recorte = this.radera;
		}
		recorte = resize(recorte);

		return recorte;
	}
}