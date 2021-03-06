/* FACULDADE COTEMIG
 * TRABALHO PRATICO - ALGORITMOS E ESTRUTURAS DE DADOS II
 * EDITOR DE GRAFOS
 * REVISÃO: 2019.1
 * AUTOR: prof. VIRGILIO BORGES DE OLIVEIRA
 * DATA DA ULTIMA ALTERACAO: 26/02/2019
 **/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GrafoGUI extends JFrame implements ActionListener {
	private JMenuBar menuBar;
	private JMenu menu;
	private JCheckBoxMenuItem mExibPesos, mPesosAleatorios;
	
	/* Complete a declaração abaixo com as demais opções do menu: */
	private JMenuItem 	mNovo, 
						mAbrir, 
						mSalvar, 
						mSair, 
						mSobre, 
						mPares,
						mArvore,
						mEuleriano,
						mUnicursal,
						mProfundidade,
						mLargura,
						mAgm,
						mNumCromatico,
						mCaminhoMinimo,
                        mCompletarGrafo;
	
	Grafo g = new Grafo();

	public GrafoGUI(String titulo) {
		super(titulo); // define o título da janela
		getContentPane().add(g); //  adiciona o grafo na janela

		/* MENUS */

		menuBar = new JMenuBar(); // cria a barra de menus

		menu = new JMenu("Arquivo"); // primeiro menu
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);

		mNovo = new JMenuItem("Novo", KeyEvent.VK_N);
		mNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				ActionEvent.CTRL_MASK));
		mNovo.addActionListener(this);
		menu.add(mNovo);

		mAbrir = new JMenuItem("Abrir", KeyEvent.VK_A);
		mAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));
		mAbrir.addActionListener(this);
		menu.add(mAbrir);

		mSalvar = new JMenuItem("Salvar", KeyEvent.VK_S);
		mSalvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		mSalvar.addActionListener(this);
		menu.add(mSalvar);

		menu.addSeparator(); // separador

		mSair = new JMenuItem("Sair", KeyEvent.VK_R);
		mSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.ALT_MASK));
		mSair.addActionListener(this);
		menu.add(mSair);

		menu = new JMenu("Algoritmos"); // segundo menu
		menu.setMnemonic(KeyEvent.VK_G);
		menuBar.add(menu);

		mPares = new JMenuItem("Pares Ordenados", KeyEvent.VK_O);
		mPares.addActionListener(this);
		menu.add(mPares);

		mEuleriano = new JMenuItem("Grafo Euleriano", KeyEvent.VK_E);
		mEuleriano.addActionListener(this);
		menu.add(mEuleriano);

		mUnicursal = new JMenuItem("Grafo Unicursal", KeyEvent.VK_U);
		mUnicursal.addActionListener(this);
		menu.add(mUnicursal);

		mArvore = new JMenuItem("Grafo Arvore", KeyEvent.VK_Z);
		mArvore.addActionListener(this);
		menu.add(mArvore);
		
		mProfundidade = new JMenuItem("Busca em Profundidadee", KeyEvent.VK_P);
		mProfundidade.addActionListener(this);
		menu.add(mProfundidade);

		mCompletarGrafo = new JMenuItem("Completar Grafo", KeyEvent.VK_C);
        mCompletarGrafo.addActionListener(this);
		menu.add(mCompletarGrafo);

		mAgm = new JMenuItem("Arvore Geradora Minima", KeyEvent.VK_M);
		mAgm.addActionListener(this);
		menu.add(mAgm);

		mNumCromatico = new JMenuItem("Numero Cromatico");
		mNumCromatico.addActionListener(this);
		menu.add(mNumCromatico);

		mCaminhoMinimo = new JMenuItem("Caminho Mínimo");
		mCaminhoMinimo.addActionListener(this);
		menu.add(mCaminhoMinimo);

		mLargura = new JMenuItem("Largura", KeyEvent.VK_L);
		mLargura.addActionListener(this);
		menu.add(mLargura);
		
		/*		 
		 * Complete com as demais opções do menu 
		 */
		
		menu = new JMenu("Ferramentas"); // terceiro menu
		menu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(menu);

		mExibPesos = new JCheckBoxMenuItem("Exibir pesos");
		mExibPesos.addActionListener(this);
		menu.add(mExibPesos);

		mPesosAleatorios = new JCheckBoxMenuItem("Gerar pesos aleatórios");
		mPesosAleatorios.addActionListener(this);
		menu.add(mPesosAleatorios);

		menu = new JMenu("Ajuda"); // quarto menu
		menu.setMnemonic(KeyEvent.VK_J);
		menuBar.add(menu);

		mSobre = new JMenuItem("Sobre...", KeyEvent.VK_S);
		mSobre.addActionListener(this);
		menu.add(mSobre);

		setJMenuBar(menuBar); // adiciona a barra de menus ao frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // terminar o programa
														// ao fechar a janela
		setResizable(true); // permite modificar o tamanho da janela
		setSize(610, 500); // define o tamanho da janela (colunas, linhas)

		setVisible(true); // torna o frame visível
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mNovo) {
			g.limpar();
		}
		
		if (e.getSource() == mAbrir) {
		    JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Grafo", "grf");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
				g.abrirArquivo(chooser.getSelectedFile().getAbsoluteFile());
		    }
		}

		if(e.getSource() == mAgm){
			g.visitado= new boolean[g.getN()];

			if(g.getVerticeMarcado() == null){
				JOptionPane.showMessageDialog(this, "Eh necessário marcar o vértice inicial!");
				return;
			}

			JOptionPane.showMessageDialog(this, "Custo Total: " + g.AGM(g.getVerticeMarcado().getNum()));
		}

		if(e.getSource() == mCaminhoMinimo){
			if(g.getVerticeMarcado() == null){
				JOptionPane.showMessageDialog(this, "Eh necessário marcar o vértice inicial!");
				return;
			}

			String numString = JOptionPane.showInputDialog(
					this,
					"Qual sera o numero do vertice final?",
					0
			);

			int num = Integer.parseInt(numString);

			JOptionPane.showMessageDialog(this, "Custo Minimo: " + g.caminhoMinimo(g.getVerticeMarcado().getNum(), num));
			g.setVerticeMarcado(null);
		}

		if(e.getSource() == mNumCromatico){
			g.visitado= new boolean[g.getN()];

			if(g.getVerticeMarcado() == null){
				JOptionPane.showMessageDialog(this, "Eh necessário marcar o vértice inicial!");
				return;
			}

			JOptionPane.showMessageDialog(this, "Numero Cromatico: " + g.numeroCromatico(g.getVerticeMarcado().getNum()));
		}
		
		if (e.getSource() == mSalvar) {
		    JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Grafo", "grf");
		    chooser.setFileFilter(filter);
		    int returnVal = chooser.showSaveDialog(this);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	g.salvarArquivo(chooser.getSelectedFile().getAbsoluteFile());
		    }
		}
		
		if (e.getSource() == mSair) {
			System.exit(0);
		}
		
		if (e.getSource() == mExibPesos) {
			g.setExibirPesos(mExibPesos.getState());
		}

		if (e.getSource() == mArvore) {
			if(!g.isArvore()){
				JOptionPane.showMessageDialog(this, "Nao e Arvore");
				return;
			}

			JOptionPane.showMessageDialog(this, "E Arvore");
		}
		
		if (e.getSource() == mPesosAleatorios) {
			g.setPesosAleatorios(mPesosAleatorios.getState());
		}

		if (e.getSource() == mCompletarGrafo) {
			g.completarGrafo();
		}
		
		if (e.getSource() == mSobre) {
			JOptionPane.showMessageDialog(this,
				"EDITOR DE GRAFOS\nversão 2019.1\n" +
				"Interface desenvolvida por: Arthur Mendonça Ribeiro e Virgílio Borges de Oliveira.\n" +
				"FACULDADE COTEMIG (somente para fins didáticos)");
		}
		
		if (e.getSource() == mPares) {
            JOptionPane.showMessageDialog(this, g.paresOrdenados());
		}

		if (e.getSource() == mEuleriano) {
			
			if(g.isEuleriano()) {
				JOptionPane.showMessageDialog(this, "Eh Euleriano");
			}
			else{
				JOptionPane.showMessageDialog(this, "Não eh Euleriano");
			}
			
		}

		if(e.getSource() == mProfundidade){

			if(g.getVerticeMarcado() == null){
				JOptionPane.showMessageDialog(this, "Eh necessário marcar o vertice inicial!");
				return;
			}

			g.visitado= new boolean[g.getN()];

			g.profundidade(g.getVerticeMarcado().getNum());

			repaint();
		}

		if(e.getSource() == mLargura){

			if(g.getVerticeMarcado() == null){
				JOptionPane.showMessageDialog(this, "Eh necessário marcar o vértice inicial!");
				return;
			}

			g.visitado= new boolean[g.getN()];

			g.largura(g.getVerticeMarcado().getNum());

			repaint();
		}

		if (e.getSource() == mUnicursal) {
			if(g.isUnicursal()){
                JOptionPane.showMessageDialog(this, "Eh Unicursal");
            }
			else{
                JOptionPane.showMessageDialog(this, "Não eh Unicursal");
            }
		}

/* Complete com as demais opções do menu */
		
	}

	public static void main(String args[]) {
		new GrafoGUI("Editor de Grafos - 2019.1"); 
	}
}