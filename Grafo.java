import java.awt.Color;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

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
    public int custoMinimo;

    public int AGM(int v) {
        //cria lista de vertices visitados
        ArrayList<Vertice> listaVisitados = new ArrayList<>();
        //adiciona o vertice selecionado como primeiro visitado
        listaVisitados.add(getVertice(v));
        Vertice vertice = getVertice(v);
        vertice.setCor(Color.RED);

        this.setExibirPesos(true);

        //enquanto todos os vertices nao forem visitados
        while(listaVisitados.size() < getN()){
            Aresta arestaMaisLeve = null;
            Vertice proximoVertice = null;
            Vertice veioDoVertice = null;
            int menorPeso = Integer.MAX_VALUE;

            //itera por cada vertice visitado
            for(int i = 0; i < listaVisitados.size(); i++){
                Vertice verticeAtual = listaVisitados.get(i);
                ArrayList<Vertice> vizinhos = getAdjacentes(verticeAtual.getNum());
                //pega vizinhos do vertice na iteração atual
                for(int j = 0; j < vizinhos.size(); j++){
                    //nao pegar peso de aresta do proprio vertice nem de vertice que ja foi visitado
                    if(!listaVisitados.contains(vizinhos.get(j))){
                        Aresta arestaAtual = getAresta(verticeAtual.getNum(), vizinhos.get(j).getNum());
                        //pega aresta com menor peso
                        if(arestaAtual != null && (arestaAtual.getPeso() < menorPeso)){
                            menorPeso = arestaAtual.getPeso();

                            arestaMaisLeve = arestaAtual;
                            proximoVertice = vizinhos.get(j);
                            veioDoVertice = verticeAtual;
                        }
                    }
                }
            }

            if(proximoVertice != null){
                //colore aresta e vertice visitados
                arestaMaisLeve.setCor(Color.RED);
                proximoVertice.setCor(Color.RED);
                //adiciona vertice visitado à lista
                listaVisitados.add(proximoVertice);
                custoTotal += arestaMaisLeve.getPeso();
            }
        }


        return this.custoTotal;
    }

    public int caminhoMinimo(int i, int j) {
        Vertice v1 = getVertice(i);
        Vertice v2 = getVertice(j);

        //lista de vertices ainda nao visitados
        ArrayList<Vertice> naoVisitados = new ArrayList<>();
        ArrayList<Vertice> caminhoMinimo = new ArrayList<>();

        //ja adiciona vertice inicial como item da lista de vertices do caminho minimo
        caminhoMinimo.add(v1);

        //coloca as distancias iniciais
        //o vertice inicial tem estimativa 0, os outros por enquanto infinito
        for(int index = 0; index < getN(); index++){
            if(getVertice(index).getNum() == v1.getNum()){
                v1.setEstimativa(0);
            }
            else{
                getVertice(index).setEstimativa(Integer.MAX_VALUE);
            }
            naoVisitados.add(getVertice(index));
        }

        //implementei o Comparable na classe Vertice para poder assim fazer um sort na lista
        //onde será comparado a estimativa, colocando as menores estimativas primeiro
        Collections.sort(naoVisitados);

        //itera enquanto o vertice final nao for visitado
        while(!naoVisitados.isEmpty()){
            //pega sempre vertice com menor estimativa
            Vertice atual = naoVisitados.get(0);
            //busca vizinhos do vertice atual
            ArrayList<Vertice> vizinhosAtual = getAdjacentes(atual.getNum());

            for(int index = 0; index < vizinhosAtual.size(); index++){
                Vertice vizinhoAtual = vizinhosAtual.get(index);

                //se nao foi visitado, busca menos estimativa
                if(!vizinhoAtual.isTerminado()){
                    Aresta arestaAtual = getAresta(atual.getNum(), vizinhoAtual.getNum());
                    //compara estimativa do vizinho com a estimativa do vertice atual ate ele
                    if(vizinhoAtual.getEstimativa() > (atual.getEstimativa() + arestaAtual.getPeso())){
                        //se for menor, a estimativa do vertice atual com o peso da aresta eh a nova estimativa
                        //para o vizinho atual
                        vizinhoAtual.setEstimativa(atual.getEstimativa() + arestaAtual.getPeso());
                        vizinhoAtual.setAnterior(atual);

                        //se o vizinho eh o vertice final, altera caminho minimo
                        //pois um caminho menor foi encontrado
                        if(vizinhoAtual.getNum() == v2.getNum()){
                            caminhoMinimo.clear();
                            caminhoMinimo.add(vizinhoAtual);
                            Vertice verticeCaminho = vizinhoAtual;

                            while(verticeCaminho.getAnterior() != null){
                                caminhoMinimo.add(verticeCaminho.getAnterior());
                                verticeCaminho = verticeCaminho.getAnterior();
                            }

                            //ordena a lista de vertices do caminho para que sejam coloridos atraves da menor distancia
                            Collections.sort(caminhoMinimo);
                        }
                    }
                }
            }

            //marca como visitado o vertice atual
            atual.setTerminado(true);
            naoVisitados.remove(atual);

            //ordena novamente lista de nao visitados
            Collections.sort(naoVisitados);
        }

        //colore vertices do caminho minimo
        caminhoMinimo.forEach(vertice -> {
            vertice.setCor(Color.RED);

            if(vertice.getNum() == v2.getNum()){
                custoMinimo = vertice.getEstimativa();
            }

        });

        this.setExibirPesos(true);

        return this.custoMinimo;
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
            verticeV.setCor(Color.ORANGE);

            for(int i = 0; i < getN(); i++){
                Aresta aresta = getAresta(v, i);
                if(aresta != null && !visitado[i]){
                    aresta.setCor(Color.ORANGE);

                    f.enfileirar(i);
                    visitado[i] = true;
                }
            }
        }
    }

    public int numeroCromatico(int v) {
        visitado[v] = true;

        Vertice vertice = getVertice(v);
        ArrayList<Vertice> vizinhos = getAdjacentes(v);

        vizinhos.sort((vertice1, t1) -> {
            int corV1 = 0, corV2 = 0;

            if(vertice1.getCor() == Color.red){
                corV1 = 1;
            }
            else if(vertice1.getCor() == Color.green){
                corV1 = 2;
            }
            else if(vertice1.getCor() == Color.blue){
                corV1 = 3;
            }
            else if(vertice1.getCor() == Color.cyan){
                corV1 = 4;
            }

            if(t1.getCor() == Color.red){
                corV2 = 1;
            }
            else if(t1.getCor() == Color.green){
                corV2 = 2;
            }
            else if(t1.getCor() == Color.blue){
                corV2 = 3;
            }
            else if(t1.getCor() == Color.cyan){
                corV2 = 4;
            }

            if(corV2 > corV1){
                return -1;
            }
            else if(corV2 == corV1){
                return 0;
            }

            return 1;
        });

        vertice.setCor(Color.RED);

        //pega cor 1 (vermelho)
        int selecionarCor = 1;

        //verifica se os vizinhos ja tem alguma cor marcada, para definir a cor do vertice atual de acordo
        //se tem vizinho de cor 1, 2 por exemplo: o vertice atual terá cor 3
        for(int i = 0; i < vizinhos.size(); i++){
            Vertice verticeVizinho = vizinhos.get(i);

            if(verticeVizinho.getCor() == Color.red && vertice.getCor() == Color.red){
                selecionarCor = 2;
                vertice.setCor(Color.green);
            }
            if(verticeVizinho.getCor() == Color.green && vertice.getCor() == Color.green){
                selecionarCor = 3;
                vertice.setCor(Color.blue);
            }
            if(verticeVizinho.getCor() == Color.blue && vertice.getCor() == Color.blue){
                selecionarCor = 4;
                vertice.setCor(Color.cyan);
            }

            //o numero cromatico será definido na classe grafo, para poder ser exibido na chamada do alert no grafoGUI
            if(this.numCromatico < selecionarCor){
                this.numCromatico = selecionarCor;
            }
        }

        //tudo certo com o vertice, prosseggue atraves da profundidade para verificar os outros
        for(int i = 0; i < getN(); i++){
            Aresta aresta = getAresta(v, i);

            if(aresta != null && !visitado[i]){
                aresta.setCor(Color.ORANGE);
                numeroCromatico(i);
            }
        }

        return this.numCromatico;
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
