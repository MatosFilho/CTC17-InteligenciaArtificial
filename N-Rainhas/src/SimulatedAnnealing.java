
public class SimulatedAnnealing {
	//private static long soma = 0;
	private Tabuleiro tabuleiro;
	private int tamanho;
	
	public SimulatedAnnealing(int tamanho) {
		this.tabuleiro = new Tabuleiro(tamanho);
		this.tamanho = tamanho;
		buscarSolucao();
	}

	private void buscarSolucao() {
		
		int[] atual = new int[tamanho];
		int[] proximo = new int[tamanho];
		int[] aux = new int[tamanho];
		
		tabuleiro.setEstadoInicial();
		atribuiEstado(atual, tabuleiro.getEstadoAtual());
		
		long inicio = System.currentTimeMillis();
		
		for (int t = 1; t < 100000; t++) {
			int T = tabuleiro.getCusto(atual);
			if (T == 0) {
				solucao(atual);
				break;
			}
			atribuiEstado(aux, atual);
			atribuiEstado(proximo, tabuleiro.novoEstado(aux));
			int deltaEnergia = tabuleiro.getCusto(atual) - tabuleiro.getCusto(proximo);
			if (deltaEnergia > 0)
				atribuiEstado(atual, proximo);
			else if (Math.random() <= Math.exp( (float) deltaEnergia/T))
				atribuiEstado(atual, proximo);
		}
		
		long fim = System.currentTimeMillis();
		long delta = fim - inicio;
		//soma += delta;
		System.out.println(delta);
		
	}
	
	private void solucao(int[] atual) {
		
		for (int i = 0; i < tamanho; i++)
			System.out.printf("%3d", atual[i]);
		System.out.println();
		
		for (int i = 0; i < tamanho; i++) {
			for (int j = 0; j < tamanho; j++) {
				if ( i == atual[j] )
					System.out.printf("  R");
				else
					System.out.printf("  -");
			}
			System.out.println();
		}
				
	}

	public void atribuiEstado(int[] x, int[] y) {
		for (int i = 0; i < tamanho; i++)
			x[i] = y[i];
	}
	
}
