package com.mvaghb;

import javax.swing.SwingUtilities;

public class Launcher {

	public static void main(String[] args) {
		// Swing needs to be run on a single thread bc its not thread safe
		/*SwingUtilities.invokeLater(new Runnable () {
			@Override
			public void run() {
				// Instancia a janela principal
				MainWindow main = new MainWindow();
				main.show();
			}
		});*/
		
		// Mean Value Analysis
		int clients = 4;
		int resources = 3;
		float taxaChegadaSistema = 0; // do sistema
		float taxaServico = 0; // de cada recurso
		float[] taxasServico = {5, 8, 4};
		float[] visitas = {2, 2, 2};
		MVA algoritmo = new MVA(4,3,0,taxasServico,visitas);
		algoritmo.runAll();
	}

}
