package com.mvaghb;

import java.util.Arrays;

public class MVA {
	private int n;
	private int recursos;
	private float taxaChegada;
	private float[] taxasServico;
	private float[] taxasVisitas;
	private float[][] temposResposta;
	
	// Inferidos
	private float[][] Xi;
	private float[][] Ui;
	private float[][] Ni;
	private float[][] Wi;
	private float Ro; //tempo reposta do sistema
	private float Xo; //throughput do sistema
	
	
	public MVA(int clientes, int recursos, float taxa, float[] servico, float[] visitas) {
		n = clientes;
		this.recursos = recursos;
		taxaChegada = taxa;
		taxasServico = servico;
		
		//taxasVisitas = new float[this.recursos];
		taxasVisitas = visitas;
		temposResposta = new float[this.recursos][n+1];
		Xi = new float[this.recursos][n+1];
		Ui = new float[this.recursos][n+1];
		Ni = new float[this.recursos][n+1];
		Wi = new float[this.recursos][n+1];
		// Isso eh feito para cada fila ou recurso do sistema
		for(int i = 0; i < this.recursos; i++) {
			//Ri = Si x [1 + N(0)]
			temposResposta[i][0] = taxasServico[i] * (1+0);
		}
	}
	
	public float recursivePerDevice(int index, int ni, float si) {
		if(ni == 0) {
			// Caso base
			// Ri = Si x [1 + N(0)]
			System.out.println("Caso base atingido!\n");
			return 0;
		}
		else {
			// Ri = Si x [1 + N(n-1)]
			//temposResposta[index] = si * (1 + recursivePerDevice(index, ni-1, si));
			for(int i = 0; i < recursos; i++) {
				float temp = 1 + recursivePerDevice(i, ni-1, taxasServico[i]);
				System.out.println("R i" + i + " = " + taxasServico[i] + " x " + temp);
				temposResposta[i][ni] = taxasServico[i] * temp;
				Wi[i][ni] = temposResposta[i][ni] - taxasServico[i];
				//temposResposta[i] = taxasServico[i] * (1 + recursivePerDevice(i, ni-1, taxasServico[i]));
			}
			// Obter tempo de resposta do sistema para n clientes
			Ro = 0;
			for (int i = 0; i < taxasVisitas.length; i++) {
				System.out.println("Ro = " + taxasVisitas[i] + " x " + temposResposta[i][ni]);
				Ro = Ro + taxasVisitas[i] * temposResposta[i][ni];
			}
			// Obter throughput do sistema
			Xo = ni / Ro;
			
			// Obter throughput por dispositivo
			//Xi[index] = taxasVisitas[index] * Xo;
			for(int i = 0; i < recursos; i++) {
				Xi[i][ni] = taxasVisitas[i] * Xo;
			}
			
			// Obter Ui = Si * Xi por dispositivo
			//Ui[index] = si * Xi[index];
			for(int i = 0; i < recursos; i++) {
				Ui[i][ni] = taxasServico[i] * Xi[i][ni];
			}
			System.out.println("Rodado dispositivo = "+ index +" e seguintes valores: Ro=" + Ro + " Xo=" + Xo + " Xi=" + Xi[index][ni] + " Ui=" + Ui[index][ni] + "\n");
			// Finalmente obter o numero medio de clientes do dispositivo
			// Ni = Ri * Xi
			for(int i = 0; i < recursos; i++) {
				Ni[i][ni] = temposResposta[i][ni] * Xi[i][ni];
			}
			System.out.println("D = " + index + " N = " + (temposResposta[index][ni]*Xi[index][ni]));
			return temposResposta[index][ni] * Xi[index][ni];
		}
	}
	
	public void runAll() {
		
		for(int a = 0; a < recursos; a++) {
			temposResposta[a][n] = taxasServico[a] * (1 + recursivePerDevice(a, n-1, taxasServico[a]));
			Wi[a][n] = temposResposta[a][n] - taxasServico[a];
		}
		Ro = 0;
		for (int b = 0; b < recursos; b++) Ro = Ro + (taxasVisitas[b]*temposResposta[b][n]);
		Xo = n / Ro;
		for (int b = 0; b < recursos; b++) Xi[b][n] = taxasVisitas[b] * Xo;
		for (int b = 0; b < recursos; b++) Ui[b][n] = taxasServico[b] * Xi[b][n];
		for (int b = 0; b < recursos; b++) Ni[b][n] = temposResposta[b][n] * Xi[b][n];
		for(int a = 0; a < recursos; a++) {
			System.out.println("Para o recurso " + a);
			System.out.println("R = " + temposResposta[a][n] + "| W = " + Wi[a][n] + "| Utilizacao = " + Ui[a][n] + "| N = " + Ni[a][n]);
		}
	}
	
	public String runAllAndReturn() {
		// [R, W, Ui, N]
		runAll();
		String result = "";
		for(int recurso = 0; recurso < recursos; recurso++) {
			result += "\nRecurso de número [" + recurso + "]\n| Tempo de Resposta Médio = " + temposResposta[recurso][n] +
					"\n| Tempo de Espera W = " + Wi[recurso][n] + "\n| Utilização = " + Ui[recurso][n] + "\n| N = " + Ni[recurso][n];
			result += " ";
		}
		result += "\n";
		return result;
	}
	
	/**
	 * Funcao rapida para validar os dados de entrada do formulario
	 * @param clientes
	 * @param recursos
	 * @param taxa
	 * @param servico
	 * @param visitas
	 * @return
	 */
	public boolean validate(int clientes, int recursos, float taxa, float[] servico, float[] visitas) {
		boolean ok = true;
		
		if(clientes <= 0 || recursos <= 0 || taxa < 0) {
			ok = false;
		}
		
		if(servico.length < recursos || visitas.length < recursos) {
			ok = false;
		}
		
		return ok;
	}
}
