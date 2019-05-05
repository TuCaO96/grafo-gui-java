/* FACULDADE COTEMIG
 * TRABALHO PRATICO - ALGORITMOS E ESTRUTURAS DE DADOS II
 * EDITOR DE GRAFOS
 * REVIS�O: 2019.1
 * AUTOR: prof. VIRGILIO BORGES DE OLIVEIRA
 * DATA DA ULTIMA ALTERACAO: 26/02/2019
 **/

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Vertice extends JComponent {
	private String rotulo; // r�tulo (legenda) do v�rtice
	private int numero; // n� do v�rtice
	private boolean marcado; // define se o v�rtice est� marcado ou n�o
	private int estimativa;
	private Vertice anterior;
	private boolean terminado;
	private Color cor; // cor do v�rtice desmarcado
	private Color corMarcado; // cor do v�rtice marcado

	public Vertice(int num, String rot, int x, int y) {// construtor
		this.rotulo = rot;
		this.numero = num;
		this.marcado = false;
		this.cor = Color.darkGray; // define a cor padr�o 
		this.corMarcado = Color.MAGENTA; // define a cor do v�rtice marcado
		this.terminado = false;
		this.estimativa = -1;
		setVisible(true);
		setXY(x, y);
	}

	public int getEstimativa() {
		return estimativa;
	}

	public void setEstimativa(int estimativa) {
		this.estimativa = estimativa;
	}

	public Vertice getAnterior() {
		return anterior;
	}

	public void setAnterior(Vertice anterior) {
		this.anterior = anterior;
	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}

	public void setXY(int x, int y) {
		setBounds(x, y, 20, 20);	
		repaint();
	}

	public int getX() {
		return super.getX();
	}

	public int getY() {
		return super.getY();
	}

	public void marcar() {
		this.marcado = true;
		repaint();
	}

	public void desmarcar() {
		this.marcado = false;
		repaint();
	}

	public boolean getMarcado() {
		return this.marcado;
	}

	/* DEFINE O R�TULO DO V�RTICE */
	public void setRotulo(String rot) {
		this.rotulo = rot;
		repaint();
	}

	/* RETORNA O R�TULO DO V�RTICE */
	public String getRotulo() {
		return this.rotulo;
	}

	/* DEFINE O N�MERO DO V�RTICE */
	public void setNum(int num) {
		this.numero = num;
		repaint();
	}

	/* RETORNA O N�MERO DO V�RTICE */
	public int getNum() {
		return this.numero;
	}

	/* DEFINE A COR DO V�RTICE */
	public void setCor(Color cor) {
		this.cor = cor;
		repaint();
	}

	/* RETORNA A COR DO V�RTICE */
	public Color getCor() {
		return this.cor;
	}

	/* DEFINE A COR DO V�RTICE MARCADO */
	public void setCorMarcado(Color cor) {
		this.corMarcado = cor;
		repaint();
	}

	/* RETORNA A COR DO V�RTICE MARCADO */
	public Color getCorMarcado() {
		return this.corMarcado;
	}

	public String toString() {
		return this.rotulo;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Color cor = getMarcado()?getCorMarcado():getCor();
		g.setColor(cor);
		g.fillOval(0, 0, 10, 10);
		g.setColor(Color.WHITE);
		int tam = g.getFontMetrics().stringWidth(getRotulo());
		g.fillRect(0, 10, tam, 10);
		g.setColor(Color.BLACK);
		g.drawString(getRotulo(), 0, 20);
		setBounds(getBounds().x, getBounds().y, tam, 20);
	}
	
	public void addNotify() {
		super.addNotify();
		this.getRootPane().repaint();
	}
}