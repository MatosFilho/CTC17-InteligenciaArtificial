public class Computador extends Jogador{
    
    public Computador(int jogador){
    	super(jogador);
        this.jogador = jogador;
        System.out.println("Jogador 'Computador' criado!");
    }
    
    @Override
    public void jogar(Tabuleiro tabuleiro){
    	Tentativa(tabuleiro);
        tabuleiro.setPosicao(tentativa, jogador);
    }
    
    @Override
    public void Tentativa(Tabuleiro tabuleiro){
        int v = -10000000;
    	for (int i = 0; i < 3; i++)
        	for (int j = 0; j < 3; j++) {
        		int[] tenta = new int[2];
        		tenta[0] = i;
        		tenta[1] = j;
        		if (tabuleiro.getPosicao(tenta) == 0) {
        			int[][] tabaux = tabuleiro.getTabuleiro();
        			int[][] tab = new int[3][3];
        			copiaMatriz(tab, tabaux);
        			tab[i][j] = 1;
        			int v2 = melhorJogada(jogador+1,tab);
        			if (v2 > v) {
        				tentativa = tenta;	
        				v = v2;
        			} 
        		}
        	}
    }
    
    public int melhorJogada(int jog, int[][] tab) {
    	//checa se jogo acabou
    	if(checaColunas(tab)==1 || checaDiagonais(tab)==1 || checaLinhas(tab)==1)
    		return score(tab);
    	if(checaColunas(tab)==-1 || checaDiagonais(tab)==-1 || checaLinhas(tab)==-1)
    		return score(tab);
    	if(fimDeJogo(tab))
    		return 0;
    	
    	
    	jog = jog%2;
    	
    	//checa se é max
    	if (jog%2 == 0) {
    		int valor = -1000000;
    		for (int i = 0; i < 3; i++) {
    			for (int j = 0; j < 3; j++) {
    				if (tab[i][j] == 0) {
    					int[][] tab2 = new int[3][3];
    					copiaMatriz(tab2,tab);
    					tab2[i][j] = 1;
    					int v2 = melhorJogada(jog+1,tab2);
    					if (v2 > valor)
    						valor = v2;
    				}
    			}
    		}
    		return valor;
    	}
    	
    	//checa se é min
    	if (jog%2 == 1) {
    		int valor = 1000000;
    		for (int i = 0; i < 3; i ++) {
    			for (int j = 0; j < 3; j++) {
    				if (tab[i][j] == 0) {
    					int[][] tab2 = new int[3][3];
    					copiaMatriz(tab2, tab);
    					tab2[i][j] = -1;
    					int v2 = melhorJogada(jog+1, tab2);
    					if(v2 < valor)
    						valor = v2;
    				}
    			}
    		}
    		return valor;
    	}
    	return 0;
    }
    
    private boolean fimDeJogo(int[][] tab) {
		boolean terminou = true;
    	for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (tab[i][j] == 0)
					terminou = false;
		return terminou;
	}

	public void copiaMatriz(int[][] m1, int[][] m2) {
    	for (int i = 0; i < m2.length; i++)
    		for (int j = 0; j < m2.length; j++)
    			m1[i][j] = m2[i][j];
    }
    
    public int checaLinhas(int[][] tab){
        for(int linha=0 ; linha<3 ; linha++) {
            if( (tab[linha][0] + tab[linha][1] + tab[linha][2]) == -3)
                return -1;
            if( (tab[linha][0] + tab[linha][1] + tab[linha][2]) == 3)
                return 1;
        }
        return 0;
    }
    
    public int checaColunas(int[][] tab){
        for(int coluna=0 ; coluna<3 ; coluna++){
            if( (tab[0][coluna] + tab[1][coluna] + tab[2][coluna]) == -3)
                return -1;
            if( (tab[0][coluna] + tab[1][coluna] + tab[2][coluna]) == 3)
                return 1;
        }
        return 0;
    }
    
    public int checaDiagonais(int[][] tab){
        if( (tab[0][0] + tab[1][1] + tab[2][2]) == -3)
            return -1;
        if( (tab[0][0] + tab[1][1] + tab[2][2]) == 3)
            return 1;
        if( (tab[0][2] + tab[1][1] + tab[2][0]) == -3)
            return -1;
        if( (tab[0][2] + tab[1][1] + tab[2][0]) == 3)
            return 1;
        return 0;
    }
    
   
    public int score (int[][] tab) {
    	int score = 0;
    	for(int linha=0 ; linha<3 ; linha++) {
            if( (tab[linha][0] + tab[linha][1] + tab[linha][2]) == -3)
                score += -100;
            if( (tab[linha][0] + tab[linha][1] + tab[linha][2]) == 3)
                score += 100;
            if( (tab[linha][0] + tab[linha][1] + tab[linha][2]) == -2)
                score += -10;
            if( (tab[linha][0] + tab[linha][1] + tab[linha][2]) == 2)
                score += 10;
            if( ((tab[linha][0] + tab[linha][1] + tab[linha][2]) == -1) && tab[linha][0]!=1 && tab[linha][1]!=1 && tab[linha][2]!=1)
                score += -1;
            if( ((tab[linha][0] + tab[linha][1] + tab[linha][2]) == 1) && tab[linha][0]!=-1 && tab[linha][1]!=-1 && tab[linha][2]!=-1)
                score += 1;
        }
    	
    	for(int coluna=0 ; coluna<3 ; coluna++){
            if( (tab[0][coluna] + tab[1][coluna] + tab[2][coluna]) == -3)
                score += -100;
            if( (tab[0][coluna] + tab[1][coluna] + tab[2][coluna]) == 3)
                score += 100;
            if( (tab[0][coluna] + tab[1][coluna] + tab[2][coluna]) == -2)
                score += -10;
            if( (tab[0][coluna] + tab[1][coluna] + tab[2][coluna]) == 2)
                score += 10;
            if( ((tab[0][coluna] + tab[1][coluna] + tab[2][coluna]) == -1) && tab[0][coluna]!=1 && tab[1][coluna]!=1 && tab[2][coluna]!=1)
                score += -1;
            if( ((tab[0][coluna] + tab[1][coluna] + tab[2][coluna]) == 1)&& tab[0][coluna]!=-1 && tab[1][coluna]!=-1 && tab[2][coluna]!=-1)
                score += 1;
        }
    	
    	if( (tab[0][0] + tab[1][1] + tab[2][2]) == -3)
            score += -100;
        if( (tab[0][0] + tab[1][1] + tab[2][2]) == 3)
            score += 100;
        if( (tab[0][2] + tab[1][1] + tab[2][0]) == -3)
            score += -100;
        if( (tab[0][2] + tab[1][1] + tab[2][0]) == 3)
            score += 100;
        if( (tab[0][0] + tab[1][1] + tab[2][2]) == -2)
            score += -10;
        if( (tab[0][0] + tab[1][1] + tab[2][2]) == 2)
            score += 10;
        if( (tab[0][2] + tab[1][1] + tab[2][0]) == -2)
            score += -10;
        if( (tab[0][2] + tab[1][1] + tab[2][0]) == 2)
            score += 10;
        if( ((tab[0][0] + tab[1][1] + tab[2][2]) == -1) && tab[0][0]!=1 && tab[1][1]!=1 && tab[2][2]!=1)
            score += -1;
        if( ((tab[0][0] + tab[1][1] + tab[2][2]) == 1)&& tab[0][0]!=-1 && tab[1][1]!=-1 && tab[2][2]!=-1)
            score += 1;
        if( ((tab[0][2] + tab[1][1] + tab[2][0]) == -1) && tab[0][2]!=1 && tab[1][1]!=1 && tab[2][0]!=1)
            score += -1;
        if( ((tab[0][2] + tab[1][1] + tab[2][0]) == 1)&& tab[0][2]!=-1 && tab[1][1]!=-1 && tab[2][0]!=-1)
            score += 1;
        return score;
    }
}