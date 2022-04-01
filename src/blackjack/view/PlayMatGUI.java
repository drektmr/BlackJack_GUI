package blackjack.view;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import blackjack.model.*;

import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class PlayMatGUI extends JFrame {

	private JFrame game;
	private JMenuBar menuBar;
	private JPanel cpuZone;
	private JPanel playersZone;
	private JPanel playerZone1;
	private JPanel playerZone2;
	private JPanel playerZone3;
	private JPanel playerZone4;
	private JPanel playerZone5;

	private Player cpu;
	private ArrayList<Player> players;
	private int numPlayers;
	private Deck deck;
	private CartaGUI cardGUI;
	private boolean result;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public PlayMatGUI() throws Exception {
		this.players = new ArrayList<Player>();
		this.cardGUI = new CartaGUI(100);
		cardGUI.setBounds(-5, 5, 101, 75);
		initialize();
	}

	private static boolean isNumber(String n) {
		try {
			Integer.parseInt(n);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}
	
	private void generateMenuBar() {
		menuBar = new JMenuBar();
		menuBar.setBorder(new EmptyBorder(0, 0, 0, 0));
		menuBar.setBackground(new Color(105, 105, 105));
		menuBar.setForeground(new Color(255, 255, 255));
		game.setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setForeground(new Color(255, 255, 255));
		menuBar.add(mnArchivo);
		
		JMenuItem mntmAboutUs = new JMenuItem("About us...");
		mntmAboutUs.setBorder(new LineBorder(new Color(0, 0, 0)));
		mntmAboutUs.setBackground(new Color(105, 105, 105));
		mntmAboutUs.setForeground(new Color(255, 255, 255));
		mntmAboutUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"Denis Duran Ibañez","About us...", JOptionPane.PLAIN_MESSAGE);
			}
		});
		mnArchivo.add(mntmAboutUs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setBorder(new LineBorder(new Color(0, 0, 0)));
		mntmExit.setBackground(new Color(105, 105, 105));
		mntmExit.setForeground(new Color(255, 255, 255));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnArchivo.add(mntmExit);
	}
	
	private void generatePlayers() throws MyException {
		String num = JOptionPane.showInputDialog("How many players will play?");

		while (!isNumber(num)) {
			num = JOptionPane.showInputDialog("Invalid characters. Please insert a number");
		}

		int numPlayers = Integer.parseInt(num);

		if (numPlayers < 2) {
			throw new MyException("No pueden ser menos de 2 jugadores.","E002");
		} else if (numPlayers > 5) {
			throw new MyException("No pueden ser más de 5 jugadores.","E003");
		} else {
			this.numPlayers = numPlayers;
			for (int i = 1; i <= numPlayers; i++) {
				// Preguntamos nombre del jugador
				String name = JOptionPane.showInputDialog("What's the name of the player" + i + "?");

				// Creamos jugador
				Player player = new Player(name, this.deck);
				this.players.add(player);

				// Posicion inicial de las cartas
				int x = 40;
				int y = 40;

				// Creamos el nombre del jugador y le aplicamos estilos
				JLabel playerName = new JLabel(player.getName());
				playerName.setForeground(Color.WHITE);
				playerName.setBounds(80, 0, 225, 20);
				JPanel playerZone = (JPanel) playersZone.getComponent(i - 1);

				playerZone.add(playerName);
			}
		}
	}

	private void getMoreCards() {
		// Generamos la ventana emergente que nos preguntará si el jugador actual quiere
		// más cartas o pasará turno

		JDialog questionBox = new JDialog(this.game);
		questionBox.setModal(true);
		questionBox.setSize(250, 100);
		questionBox.getContentPane().setLayout(new GridLayout(3, 0, 10, 10));

		// Creamos el texto y la añadimos al ventana
		JLabel question = new JLabel("Do you want a new card?");
		questionBox.add(question);
		this.result = true;

		questionBox.setLocation(100, 50);

		// Creamos el botón "Yes" para que el resultado sea true
		JButton buttonYes = new JButton("Yes");
		buttonYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				result=true;
				questionBox.dispose();
			}
		});

		JButton buttonNo = new JButton("No");
		buttonNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				result=false;
				questionBox.dispose();
			}
		});
		questionBox.getContentPane().add(buttonYes);
		questionBox.getContentPane().add(buttonNo);

		// Robamos una carta
		for (int i = 0; i<numPlayers;i++) {
			this.players.get(i).flipCards();
			this.refresh();
			while (this.result) {
				questionBox.setVisible(true);
				if (this.result && this.deck.size()>0) {
					Card toAdd = this.deck.getCard();
					toAdd.flipCard();
					this.players.get(i).addCard(toAdd);
					this.refresh();
				}else if(!this.result && this.deck.size()<=0){
					this.result=false;
					JOptionPane.showMessageDialog(null, "Deck is empty.", "Atention",JOptionPane.INFORMATION_MESSAGE);
				}else {
					this.result=false;
				}
			}
			this.result=true;
		}
	}

	private void printCards() {
		int x = 40;
		int y = 40;
		int posPlayer = 1;
		JLabel crupier = (JLabel) this.cpuZone.getComponent(2);
		// Generamos las imagenes de las cartas del crupier
		// Creamos la zona donde se mostrara el nombre del crupier, sus cartas y
		// asignamos estilos.
		JLabel crupierName = new JLabel("CPU");
		crupierName.setForeground(Color.WHITE);
		crupierName.setBounds(80, 0, 225, 20);
		crupier.add(crupierName);

		for (Card crupierCard : cpu.getCards()) {
			Image cardImage = this.cardGUI.getImatge(crupierCard);
			ImageIcon icon = new ImageIcon(cardImage);

			JLabel crupierZone = new JLabel("");
			crupierZone.setSize(225, 315);
			crupierZone.setBounds(x, y, 225, 315);
			crupierZone.setIcon(icon);

			crupier.add(crupierZone);
			x += 30;
			y += 30;
		}

		// Generamos las cartas de los jugadores
		for (Player player : this.players) {
			x = 40;
			y = 40;
			// Creamos y mostramos las cartas de los jugadores
			for (Card item : player.getCards()) {
				// Generamos cada imagen y la asignamos a un icono
				Image cardImage = this.cardGUI.getImatge(item);
				ImageIcon icon = new ImageIcon(cardImage);

				// Creamos la posicion donde ire la iamgen de la carta y la colocamos en su
				// lugar
				JLabel playerCard = new JLabel("");
				playerCard.setSize(225, 315);
				playerCard.setBounds(x, y, 225, 315);
				playerCard.setIcon(icon);

				switch (posPlayer) {
				case 1:
					this.playerZone1.add(playerCard);
					break;
				case 2:
					this.playerZone2.add(playerCard);
					break;
				case 3:
					this.playerZone3.add(playerCard);
					break;
				case 4:
					this.playerZone4.add(playerCard);
					break;
				case 5:
					this.playerZone5.add(playerCard);
					break;
				}

				// Incrementamos las posiciones para ir colocando las cartas solapadas
				x += 30;
				y += 30;
			}
			// Reiniciamos las variables para las siguientes 2 cartas
			x = 40;
			y = 40;
			posPlayer += 1;
			// player.getCards().stream().forEach(item->this.cardGUI.getImatge(item));
		}
		posPlayer = 0;
	}

	private void generatePlayGame() {
		this.game = new JFrame();
		this.game.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.game.setSize(1920, 1080);
		this.game.getContentPane().setBackground(new Color(0, 51, 0));
		this.game.setBounds(100, 100, 450, 300);
		this.game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.game.getContentPane().setLayout(new GridLayout(3, 0, 0, 0));
		
		this.generateMenuBar();
		// Sección para la parte superior del programa donde la máquina ira dando las
		// cartas

		this.cpuZone = new JPanel();
		cpuZone.setBorder(null);
		cpuZone.setBackground(UIManager.getColor("OptionPane.questionDialog.titlePane.foreground"));
		this.game.getContentPane().add(cpuZone);
		cpuZone.setLayout(new GridLayout(0, 5, 0, 0));

		JLabel emptys1 = new JLabel("");
		cpuZone.add(emptys1);

		JLabel emptys2 = new JLabel("");
		cpuZone.add(emptys2);

		JLabel crupier = new JLabel("");
		cpuZone.add(crupier);

		JLabel emptys3 = new JLabel("");
		cpuZone.add(emptys3);

		JLabel emptys4 = new JLabel("");
		cpuZone.add(emptys4);

		// Zona central donde pondremos un logo para la aplicación
		JPanel empty = new JPanel();
		empty.setBorder(null);
		empty.setBackground(UIManager.getColor("OptionPane.questionDialog.titlePane.foreground"));
		this.game.getContentPane().add(empty);

		// Zona para los jugadores que crearemos dependiendo de la respuesta del panel
		// inicial
		this.playersZone = new JPanel();
		this.playersZone.setBackground(UIManager.getColor("OptionPane.questionDialog.titlePane.foreground"));
		this.game.getContentPane().add(this.playersZone);
		this.playersZone.setLayout(new GridLayout(0, 5, 0, 0));

		// Creamos zonas para los jugadores y sus cartas
		this.playerZone1 = new JPanel();
		playerZone1.setBackground(UIManager.getColor("OptionPane.questionDialog.titlePane.foreground"));
		playerZone1.setBorder(null);
		playersZone.add(playerZone1);
		playerZone1.setLayout(null);

		this.playerZone2 = new JPanel();
		playerZone2.setBackground(UIManager.getColor("OptionPane.questionDialog.titlePane.foreground"));
		playerZone2.setBorder(null);
		playersZone.add(playerZone2);
		playerZone2.setLayout(null);

		this.playerZone3 = new JPanel();
		playerZone3.setBackground(UIManager.getColor("OptionPane.questionDialog.titlePane.foreground"));
		playerZone3.setBorder(null);
		playersZone.add(playerZone3);
		playerZone3.setLayout(null);

		this.playerZone4 = new JPanel();
		playerZone4.setBackground(UIManager.getColor("OptionPane.questionDialog.titlePane.foreground"));
		playerZone4.setBorder(null);
		playersZone.add(playerZone4);
		playerZone4.setLayout(null);

		this.playerZone5 = new JPanel();
		playerZone5.setBackground(UIManager.getColor("OptionPane.questionDialog.titlePane.foreground"));
		playerZone5.setBorder(null);
		playersZone.add(playerZone5);
		playerZone5.setLayout(null);

	}

	private void refresh() {
		// Creamos zonas para los jugadores y sus cartas
		this.playerZone1.removeAll();
		this.playerZone2.removeAll();
		this.playerZone3.removeAll();
		this.playerZone4.removeAll();
		this.playerZone5.removeAll();
		this.cpuZone.removeAll();

		JLabel emptys1 = new JLabel("");
		cpuZone.add(emptys1);

		JLabel emptys2 = new JLabel("");
		cpuZone.add(emptys2);

		JLabel crupier = new JLabel("");
		cpuZone.add(crupier);

		JLabel emptys3 = new JLabel("");
		cpuZone.add(emptys3);

		JLabel emptys4 = new JLabel("");
		cpuZone.add(emptys4);
		this.printCards();
		this.game.setVisible(true);
		this.game.repaint();
	}

	private void initialize() throws MyException {
		// Creamos la baraja
		this.deck = new Deck();
		// Definimos el crupier con sus cartas
		this.cpu = new Player("CPU", deck);
		// Generamos el tablero
		this.generatePlayGame();
		// Generamos los jugadores
		this.generatePlayers();
		// Mostramos las cartas con el reverso
		this.printCards();
		this.game.setVisible(true);
		this.getMoreCards();
		this.cpu.flipCards();
		this.refresh();
		// this.game.setVisible(true);

	}

}
