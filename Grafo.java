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

    public void AGM(int v) {
        //cria lista de vertices visitados
        ArrayList<Integer> listaVisitados = new ArrayList<>();
        //adiciona o vertice selecionado como primeiro visitado
        listaVisitados.add(v);
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
                ArrayList<Vertice> vizinhos = getAdjacentes(v);
                //pega vizinhos do vertice na iteração atual
                for(int j = 0; j < vizinhos.size(); j++){
                    //nao pegar peso de aresta no proprio vertice nem vertice que ja foi visitado
                    if(i != j && !listaVisitados.contains(j)){
                        Aresta arestaAtual = getAresta(i, j);
                        //pega aresta com menor peso
                        if(arestaAtual.getPeso() < menorPeso){
                            menorPeso = arestaAtual.getPeso();
                            arestaMaisLeve = arestaAtual;
                            proximoVertice = j;
                        }
                    }
                }
            }
            //colore aresta e vertice visitados
            arestaMaisLeve.setCor(Color.RED);
            Vertice proximoVerticeObj = getVertice(proximoVertice);
            proximoVerticeObj.setCor(Color.RED);
            //adiciona vertice visitado à lista
            listaVisitados.add(proximoVertice);
        }

    }

    public void caminhoMinimo(int i, int j) {

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

    public void numeroCromatico() {

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
