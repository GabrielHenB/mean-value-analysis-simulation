package com.mvaghb;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainWindow {
	private JFrame window;
	private JPanel panel;
	private JLabel topLabel;
	private JPanel mainContainer;
	private JPanel optionsContainer;
	private JPanel resultsContainer;
	private JScrollPane scrollPane;
	private JPanel scenariosContainer;
	
	
	// Data
	private JTextField inputClientes;
	private JTextField inputRecursos;
	private JTextField inputChegadas;
	private JTextField inputServicos;
	private JTextField inputVisitas;
	private JTextArea output;
	
	// Construtor
	public MainWindow() {
		window = new JFrame();
		window.setTitle("Simulador de Analise de Valor Medio");
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		window.setSize(1024,800);
		window.setResizable(false);
		window.setLocationRelativeTo(null); //centraliza
		
		panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
		panel.setBackground(Color.GRAY);
		
		window.add(panel, BorderLayout.NORTH); // painel container no topo
		
		topLabel = new JLabel("Análise de Valor Médio");
		topLabel.setForeground(Color.WHITE);
		topLabel.setFont(new Font("Sans-serif", Font.BOLD, 26));
		panel.add(topLabel); // quando nao especificado adiciona ao center
		
		mainContainer = new JPanel();
		mainContainer.setLayout(new GridLayout(1,2,10,5)); // rows cols
		mainContainer.setBackground(new Color(20,20,20));
		
		window.add(mainContainer, BorderLayout.CENTER);
		
		resultsContainer = new JPanel();
		
		optionsContainer = new JPanel();
		optionsContainer.setLayout(new GridLayout(0, 1));
		optionsContainer.setBackground(new Color(20,20,20));
		resultsContainer.setBackground(new Color(30,20,20));
		
		JLabel intro = new JLabel();
		intro.setText("<html>Insira os valores<br> para iniciar a simulação!</html>");
		intro.setForeground(Color.white);
		intro.setBackground(new Color(20,20,20));
		intro.setFont(new Font("Arial", Font.PLAIN, 14));
		optionsContainer.add(intro);
		
		JLabel tutorial = new JLabel();
		tutorial.setText("<html>Digite no formato exemplo: 2.4 , 3.42 <br>Separe por ',' e decimais com '.'.<br>"
				+ "Se os valores forem por minuto o resultado sera por minuto e assim em diante<br>"
				+ "O calculo é sobre o valor e nao considera unidade!</html>");
		tutorial.setForeground(Color.white);
		intro.setBackground(new Color(20,20,20));
		intro.setFont(new Font("Arial", Font.PLAIN, 12));
		
		inputClientes = createInput(2);
		inputRecursos = createInput(2);
		inputChegadas = createInput(2);
		inputServicos = createInput(2);
		inputVisitas = createInput(2);
		
		JPanel i1 = new JPanel(new GridLayout(0,2));
		JPanel i2 = new JPanel(new GridLayout(0,2));
		JPanel i3 = new JPanel(new GridLayout(0,2));
		JPanel i4 = new JPanel(new GridLayout(0,2));
		JPanel i5 = new JPanel(new GridLayout(0,2));
		
		JLabel l1 = new JLabel("Numero de Clientes:");
		l1.setForeground(Color.white); i1.setBackground(new Color(20,20,20));
		i1.add(l1); i1.add(inputClientes);
		
		JLabel l2 = new JLabel("Recursos (Filas):");
		l2.setForeground(Color.white); i2.setBackground(new Color(20,20,20));
		i2.add(l2); i2.add(inputRecursos);
		
		JLabel l3 = new JLabel("Taxa de Chegada:");
		l3.setForeground(Color.white); i3.setBackground(new Color(20,20,20));
		i3.add(l3); i3.add(inputChegadas);
		
		JLabel l4 = new JLabel("Taxa de Serviço por Dispositivo");
		l4.setForeground(Color.white); i4.setBackground(new Color(20,20,20));
		i4.add(l4); i4.add(inputServicos);
		
		JLabel l5 = new JLabel("Taxa de Visitas por Dispositivo");
		l5.setForeground(Color.white); i5.setBackground(new Color(20,20,20));
		i5.add(l5); i5.add(inputVisitas);
		
		JPanel tutorialContainer = new JPanel();
		tutorialContainer.setBackground(new Color(20,20,20));
		tutorialContainer.add(tutorial);
		
		optionsContainer.add(i1);
		optionsContainer.add(i2);
		optionsContainer.add(i3);
		optionsContainer.add(tutorialContainer);
		optionsContainer.add(i4);
		optionsContainer.add(i5);
		
		JButton buttonRun = createBtn("Start", "Iniciar", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				runMVA(inputClientes.getText(),inputRecursos.getText(),inputChegadas.getText(),inputServicos.getText(),inputVisitas.getText());
			}
		});
		optionsContainer.add(buttonRun);
		
		
		scenariosContainer = new JPanel();
		scenariosContainer.setBackground(new Color(20,20,20));
		setScenario("4","3","0","5,4,8","2,2,2","1","minutos");
		setScenario("4","3","0","0.400,0.700,0.650","2,3,2","2","segundos");
		
		optionsContainer.add(scenariosContainer);
		
		mainContainer.add(optionsContainer);
		
		output = new JTextArea(4,50);
		output.setForeground(Color.WHITE);
		output.setBackground(Color.BLACK);
		output.setLineWrap(true);
		output.setEditable(false);
		output.setFont(new Font("sans-serif", Font.PLAIN, 16));
		scrollPane = new JScrollPane(output);
		resultsContainer.setLayout(new BorderLayout());
		resultsContainer.add(scrollPane, BorderLayout.CENTER);
		mainContainer.add(resultsContainer);
		
	}
	
	private JTextField createInput(int size) {
		JTextField textfd = new JTextField(size);
		// Espacamento interno
		textfd.setMargin(new Insets(2, 4, 2, 4));
		
		return textfd;
	}
	
	private JButton createBtn(String text, String hoverText, ActionListener listener) {
		JButton button = new JButton(text);
		
		// Texto que aparece quando hover no btn
		button.setToolTipText(hoverText);
		
		button.setFont(new Font("Arial", Font.PLAIN, 24));
		
		button.addActionListener(listener);
		
		return button;
	}
	
	public void runMVA(String clientesTexto, String recursosTexto, String taxaTexto, String servicosTexto, String visitasTexto) {
		// Grab needed values and run MVA
		
		// Valida se existem campos vazios
		if(clientesTexto.length() <= 0 || recursosTexto.length() <= 0 || taxaTexto.length() <= 0 || servicosTexto.length() <= 0 || visitasTexto.length() <= 0) {
			render("Erro!\n Existem campos em branco!");
			return;
		}
		
		int clientes = Integer.parseInt(clientesTexto);
		int recursos = Integer.parseInt(recursosTexto);
		float taxa = Float.parseFloat(taxaTexto);
		
		String[] taxasDeServico = servicosTexto.split(",");
		float[] servicos = new float[taxasDeServico.length];
		
		for(int i = 0; i < taxasDeServico.length; i++) {
			servicos[i] = Float.parseFloat(taxasDeServico[i].trim());
		}
		System.out.println("debug: TaxasDeVisita: ");
		String[] taxasDeVisita = visitasTexto.split(",");
		float[] visitas = new float[taxasDeVisita.length];
		for(int i = 0; i < taxasDeVisita.length; i++) {
			visitas[i] = Float.parseFloat(taxasDeVisita[i].trim());
			System.out.print(" " + visitas[i]);
		}
		
		if(!validate(clientes, recursos, taxa, servicos, visitas)) {
			render("Erro!\n Os dados fornecidos estão inválidos!\n Certifique-se de que"
					+ "o n de clientes e recursos sao maiores que zero\n"
					+ "e que o array de taxas de serviço e visitas por dispositivo\n"
					+ "tem o mesmo tamanho que o n de recursos e estão\n"
					+ "no formato correto: (float),(float)");
		}
		else {
			MVA algoritmo = new MVA(clientes, recursos, taxa, servicos, visitas);
			String result = algoritmo.runAllAndReturn();
			render(result);
		}
		
	}
	
	public void setScenario(String clientes, String recursos, String taxa, String servicos, String visitas, String index, String unidade) {
		JButton buttonScenario = createBtn("Cenário " + index, "Carrega os dados do cenário de teste", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inputClientes.setText(clientes);
				inputRecursos.setText(recursos);
				inputChegadas.setText(taxa);
				inputServicos.setText(servicos);
				inputVisitas.setText(visitas);
				render("Cenário de Teste Carregado ... Use Start para simular!");
				renderKeep("Descrição do Cenário: " + "\nN de Clientes  =  " + clientes + "\nN de Recursos (Filas) = " + recursos);
				renderKeep("Taxas de Serviço por Recurso = " + servicos + " " + unidade + "/v" + "\nTaxas de Visitas por Recursos = " + visitas + " visitas/" + unidade);
			}
		});
		
		this.scenariosContainer.add(buttonScenario);
	}
	
	/**
	 * Gera o texto no display de resultados
	 * @param text
	 * @return
	 */
	public JTextField render(String text) {
		JTextField content = new JTextField(30);
		/*content.setEditable(false);
		content.setText(text);
		content.setBackground(new Color(40,40,40));
		content.setForeground(Color.white);
		resultsContainer.add(content);*/
		System.out.println("TEXTO RECEBIDO = " + text);
		output.setText(text);
		return content;
	}
	
	/**
	 * Gera o texto no display de resultados, concatenando com o que ja estava la.
	 * @param text
	 */
	public void renderKeep(String text) {
		output.setText(output.getText() + "\n\n" + text);
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
	
	public void show() {
		window.setVisible(true);
	}
}
