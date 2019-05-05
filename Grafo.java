import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/* FACULDADE COTEMIG
 * TRABALHO PRATICO - ALGORITMOS E ESTRUTURAS DE DADOS II
 * EDITOR DE GRAFOS
 * REVIS�O: 2019.1
 * AUTORES: Arthur Mendonça Ribeiro e prof. VIRGILIO BORGES DE OLIVEIRA
 * DATA DA ULTIMA ALTERACAO: 26/02/2019
 **/

public class Grafo extends GrafoBase {
    public boolean visitado[];
    public int numCromatico;
    public int custoTotal;

    public void AGM(int v) {
        //cria lista de vertices visitados
        ArrayList<Integer> listaVisitados = new ArrayList<>();
        //adiciona o vertice selecionado como primeiro visitado
        listaVisitados.add(getVertice(v).getNum());
        Vertice vertice = getVertice(v);
        vertice.setCor(Color.RED);

        this.setExibirPesos(true);

        //enquanto todos os vertices nao forem visitados
        while(listaVisitados.size() < getN()){
            Aresta arestaMaisLeve = null;
            int proximoVertice = -1;
            int menorPeso = Integer.MAX_VALUE;

            //itera por cada vertice visitado
            for(int i = 0; i < listaVisitados.size(); i++){
                ArrayList<Vertice> vizinhos = getAdjacentes(i);
                //pega vizinhos do vertice na iteração atual
                for(int j = 0; j < vizinhos.size(); j++){
                    //nao pegar peso de aresta no proprio vertice nem vertice que ja foi visitado
                    if(!listaVisitados.contains(vizinhos.get(j).getNum())){
                        Aresta arestaAtual = getAresta(i, vizinhos.get(j).getNum());
                        //pega aresta com menor peso
                        if(arestaAtual != null && (arestaAtual.getPeso() < menorPeso)){
                            menorPeso = arestaAtual.getPeso();
                            custoTotal += arestaAtual.getPeso();

                            arestaMaisLeve = arestaAtual;
                            proximoVertice = vizinhos.get(j).getNum();
                        }
                    }
                }
            }

            if(proximoVertice != -1){
                //colore aresta e vertice visitados
                arestaMaisLeve.setCor(Color.RED);
                Vertice proximoVerticeObj = getVertice(proximoVertice);
                proximoVerticeObj.setCor(Color.RED);
                //adiciona vertice visitado à lista
                listaVisitados.add(proximoVertice);
            }
        }

    }

    public void caminhoMinimo(int i, int j) {
        //começamos do vertice i, e vamos analisando seus vizinhos um a um ate chegar no vertice j
        Vertice vertice = getVertice(i);
        Vertice verticeFinal = getVertice(j);

        while (!verticeFinal.isTerminado()){

        }
    }

    public boolean isArvore(){
        visitado = new boolean[getN()];

        largura(0);

        //se algum vertice nao tiver sido visitado, entao grafo não é arvore
        for(int i = 0; i < getN(); i++){
            if(!visitado[i]){
                return false;
            }
        }

        return true;
    }

    public boolean isEuleriano() {
        int i;

        for (i = 0; i < this.getN(); i++) {
            if (this.grau(i) % 2 != 0)
                return false;
        }

        return true;
    }

    public boolean isUnicursal() {
        int cont = 2;

        for (int i = 0; i < this.getN(); i++) {
            if (grau(i) % 2 != 0) {
                cont--;
            }
        }

        if (this.getN() < 1) {
            return false;
        }

        return cont == 0;
    }

    public String paresOrdenados() {
        StringBuilder data = new StringBuilder("E = {");

        for (int i = 0; i < getN(); i++) {
            Vertice verticeAtual = getVertice(i);

            for (int j = 0; j < getAdjacentes(i).size(); j++) {
                Vertice verticeAdjecente = getVertice(getAdjacentes(i).get(j).getNum());

                if (verticeAtual.getNum() != verticeAdjecente.getNum()) {
                    data.append("(");
                    data.append(verticeAtual.getRotulo());
                    data.append(",");
                    data.append(verticeAdjecente.getRotulo());
                    data.append(")");

                    if ((i - 1) != getN() && (j - 1) != getN()) {
                        data.append(",");
                    }
                }
            }
        }

        return data.append("}").toString();
    }

    public void completarGrafo() {
        for (int i = 0; i < getN(); i++) {
            Vertice atual = getVertice(i);

            for (int j = 0; j < getN(); j++) {
                Vertice vizinho = getVertice(j);

                if (getAresta(atual.getNum(), vizinho.getNum()) == null) {
                    setAresta(atual.getNum(), vizinho.getNum(), 1);
                }

                if (getAresta(atual.getNum(), vizinho.getNum()).getPeso() == 0) {
                    if (atual.getNum() != vizinho.getNum()) {
                        getAresta(atual.getNum(), vizinho.getNum()).setPeso(1);
                    }
                }
            }
        }

        repaint();

    }

    public void largura(int v) {
        Fila f = new Fila(getN());
        f.enfileirar(v);

        while(!f.vazia()){
            v = f.desenfileirar();
            Vertice verticeV = getVertice(v);
            verticeV.setCor(Color.PINK);

            for(int i = 0; i < getN(); i++){
                Aresta aresta = getAresta(v, i);
                if(aresta != null && !visitado[i]){
                    aresta.setCor(Color.PINK);

                    f.enfileirar(i);
                    visitado[i] = true;
                }
            }
        }
    }

    public void numeroCromatico(int v) {
        visitado[v] = true;

        Vertice vertice = getVertice(v);
        ArrayList<Vertice> vizinhos = getAdjacentes(v);

        //pega cor 1 (vermelho)
        int selecionarCor = 1;
        //necessario para tratar caso de erro na logica inicial
        boolean vizinhoCor1 = false;

        //verifica se os vizinhos ja tem alguma cor marcada, para definir a cor do vertice atual de acordo
        //se tem vizinho de cor 1, 2 por exemplo: o vertice atual terá cor 3
        for(int i = 0; i < vizinhos.size(); i++){
            Vertice verticeVizinho = vizinhos.get(i);

            if(verticeVizinho.getCor() == Color.red){
                selecionarCor = 2;
                vizinhoCor1 = true;
            }
            else if(verticeVizinho.getCor() == Color.green){
                selecionarCor = 3;
            }
            else if(verticeVizinho.getCor() == Color.blue){
                selecionarCor = 4;
            }

            //o numero cromatico será definido na classe grafo, para poder ser exibido na chamada do alert no grafoGUI
            if(this.numCromatico < selecionarCor){
                this.numCromatico = selecionarCor;
            }
        }

        //seta cor do vertice
        if(selecionarCor == 1){
            vertice.setCor(Color.RED);
        }
        else if(selecionarCor == 2){
            vertice.setCor(Color.GREEN);
        }
        else if(selecionarCor == 3){
            vertice.setCor(Color.BLUE);
        }
        else if(selecionarCor == 4){
            vertice.setCor(Color.CYAN);
            //pode acontecer de ter vizinho de cor verde, azul e ciano. nesse caso, iria falhar e colocar ciano.
            //corrige falha e seta de cor vermelha como deveria ser
            if(vizinhoCor1 == false){
                vertice.setCor(Color.RED);
            }
        }

        //tudo certo com o vertice, prosseggue atraves da profundidade para verificar os outros
        for(int i = 0; i < getN(); i++){
            Aresta aresta = getAresta(v, i);

            if(aresta != null && !visitado[i]){
                numeroCromatico(i);
            }
        }
    }

    public void profundidade(int v) {
        visitado[v] = true;

        Vertice verticeV = getVertice(v);
        verticeV.setCor(Color.CYAN);

        for(int i = 0; i < getN(); i++){
            Aresta aresta = getAresta(v, i);

            if(aresta != null && !visitado[i]){
                aresta.setCor(Color.CYAN);
                profundidade(i);
            }
        }
    }
}
