import java.util.Random;

public class Tabuleiro {

	private int[] estadoAtual;
	private int tamanho;
	
	public Tabuleiro(int tamanho) {
		this.estadoAtual = new int[tamanho];
		this.tamanho = tamanho;
	}

	public int[] getEstadoAtual() { return estadoAtual; }
	public void setEstado(int[] novoEstado) { this.estadoAtual = novoEstado; }
	
	public int[] novoEstado(int[] tabuleiro) {
		int n, m; 
		
		n = new Random().nextInt(tamanho);
		
		do {
			m = new Random().nextInt(tamanho);
		} while (n == m);
		
		int aux;
		aux = tabuleiro[n];
		tabuleiro[n] = tabuleiro[m];
		tabuleiro[m] = aux;
		
		return tabuleiro;
	}
	
	public int getCusto (int []tabuleiro) {
		int custo = 0;
		
		for (int i = 0; i < tamanho; i++) 
			for (int j = 0; j < tamanho; j++) 
				if (i != j) {
					if (( (i-tabuleiro[i]) == (j-tabuleiro[j]) ) || ( (i+tabuleiro[i]) == (tabuleiro[j]+j) ))
						custo++;
				}
			
		return custo/2;
	}

	public void setEstadoInicial() {
		for (int i = 0; i < tamanho; i++)
			this.estadoAtual[i] = i;
	}

}
