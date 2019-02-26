/*
 * CLASSE FILA - IMPLEMENTA O TIPO ABSTRATO DE DADOS FILA EST�TICA CIRCULAR
 * OPERA��ES: *
 *	vazia() -> retorna true se a fila est� vazia.
 *	cheia() -> returna true se a fila  est� cheia.
 *	enfileirar(x) -> coloca o elemento x no fim da fila.
 *	desenfileirar() -> retorna o elemento situado no inicio da fila.
 */

public class Fila {
	private int vet[];	
	private int inicio;
	private int fim;
	
	public Fila(int tam) {
		vet = new int[tam];
		inicio = fim = 0;
	}

	public void enfileirar(int i) {
		vet[fim] = i;
		fim = (fim + 1) % vet.length;
	}

	public int desenfileirar() {
		int item;
		item = vet[inicio];
		inicio = (inicio + 1) % vet.length;
		return item;
	}

	public boolean vazia() {
		return inicio == fim;
	}

	public boolean cheia() {
		return ((fim + 1) % vet.length) == inicio;
	}
}