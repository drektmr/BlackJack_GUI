package blackjack.controller;

import java.awt.EventQueue;

import javax.swing.JOptionPane;

import blackjack.model.MyException;
import blackjack.view.PlayMatGUI;

public class App {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayMatGUI blackjack = new PlayMatGUI();
					
				} catch (MyException e) {
					JOptionPane.showMessageDialog(null, e.toString(), "MyException detected", JOptionPane.WARNING_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e, "Exception detected", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
}
