/* FACULDADE COTEMIG
 * TRABALHO PRATICO - ALGORITMOS E ESTRUTURAS DE DADOS II
 * EDITOR DE GRAFOS
 * REVISÃO: 2019.1
 * AUTOR: prof. VIRGILIO BORGES DE OLIVEIRA
 * DATA DA ULTIMA ALTERACAO: 26/02/2019
 **/

import java.awt.Color;

import javax.swing.JPanel;

public class Aresta {
	private int peso;
	private Color cor; 
	private JPanel p;

	public Aresta(int peso, Color cor, JPanel p) {
		this.peso = peso;
		this.cor = cor;
		this.p = p;
		p.repaint();
	}

	/* DEFINE O PESO */
	public void setPeso(int peso) {
		this.peso = peso;
		p.repaint();
	}

	/* RETORNA O PESO */
	public int getPeso() {
		return this.peso;
	}

	/* DEFINE A COR DA ARESTA */
	public void setCor(Color cor) {
		this.cor = cor;
		p.repaint();
	}

	/* RETORNA A COR DA ARESTA */
	public Color getCor() {
		return this.cor;
	}
}