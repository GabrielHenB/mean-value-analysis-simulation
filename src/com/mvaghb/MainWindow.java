package com.mvaghb;

import javax.swing.JFrame;

public class MainWindow {
	private JFrame window;
	
	// Construtor
	public MainWindow() {
		window = new JFrame();
		window.setTitle("Simulador de Analise de Valor Medio");
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setSize(800,500);
		window.setLocationRelativeTo(null); //centraliza
	}
	
	public void show() {
		window.setVisible(true);
	}
}
