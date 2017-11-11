
public class PSR {
	
	private int[][] casas;
	private int[][] campos;
	private int numPreenchidos;
	
	public PSR() {
		/* representacao das casas:
		 *     cada linha é uma casa, onde:
		 *	       -indice 0: mais a esquerda
		 *		   -indice 4: mais a direita
		 *	   cada coluna representa um campo, onde:
		 *		   -indice 0: cor
		 *		   -indece 1: nacionalidade
	     *		   -indice 2: cigarro
		 *		   -indice 3: bebiba
		 *		   -indice 4: animal 
		 **/
		casas = new int[5][5];
		/* representacao dos campos, onde:
		 *     -linha 0: cores
		 *     -linha 1: nacionalidades
		 *	   -linha 2: cigarros
		 *	   -linha 3: bebidas
		 *	   -linha 4: animais
		 */
		campos = new int[5][5];
		//indica quantas casas já foram assinaladas
		numPreenchidos = 0;
		inicializacao();
		buscarSolucao();
	}
	
	private void inicializacao() {
		/* iniciando as casas, onde:
		 *     - campo == -1: nenhum valor assinalado
		 *	   - campo != -1: um valor corespondente a um valor daquela coluna 
		 */
		for(int i = 0; i < casas.length; i++)
			for(int j = 0; j < casas[0].length; j++) 
				casas[i][j] = -1;
		
		/* iniciando campos, onde:
	     *    - campo == -1: valor não escolhido
	     *    - campo ==  1: campo já em uso
		 */ 
		for(int i = 0; i < campos.length; i++)
			for(int j = 0; j < campos[0].length; j++) 
				campos[i][j] = -1;
	}

	private void buscarSolucao() {
		int[][] solucao = backtracking();
		if(solucao == null)
			System.out.println("NULO!!!");
		else
			imprimeSolucao(solucao);
	}
	
	
	private int[][] backtracking(){
		System.out.println(numPreenchidos);
		//verifica se chegou a uma solucao
		if(numPreenchidos == 25)
			return casas;
		/*seleciona o proximo campo de uma casa a ser preenchido:
		 *	-jogada[0]: casa a ser assinalada
		 *  -jogada[1]: campo da casa a ser assinalada
		 **/
		int[] jogada = selecionar(casas);
		for (int i = 0; i < campos[jogada[0]].length; i++) {
			if ( campos[jogada[1]][i] == -1 )
				if( jogadaPermitida(jogada[0], jogada[1], i) ) 
					if (checaJogada(jogada[0], jogada[1], i)){
				
				//adiciona o campo
				casas[jogada[0]][jogada[1]] = i;
				campos[jogada[1]][i] = 1;
				numPreenchidos++;
				
				int[][] solucao = backtracking();
				if(solucao != null)
					return solucao;
				
				//remove o campos
				casas[jogada[0]][jogada[1]] = -1;
				campos[jogada[1]][i] = -1;
				numPreenchidos--;
				
			}
		}
		return null;
	}
	
	private int[] selecionar(int[][] estadoAtual) {
		int[] jogada = new int[2];
		for(int casa = 0; casa < estadoAtual.length; casa++) {
			for(int campo = 0; campo < estadoAtual[casa].length; campo++) {
				if(estadoAtual[casa][campo] == -1) {
					jogada[0] = casa;
					jogada[1] = campo;
					return jogada;
				}
			}
		}
		return null;
	}

	private void imprimeSolucao(int[][] solucao) {
		String cor = "";
		String nacionalidade = "";
		String cigarro = "";
		String bebida = "";
		String animal = "";
		
		for(int casa = 0; casa < casas.length; casa++) {
			for(int campo = 0; campo < casas[casa].length; campo++) {
				int valor = solucao[casa][campo];
				switch(campo) {
					case 0:
						switch(valor) {
							case 0: cor = "amarela"; break;
							case 1: cor = "azul"; break;
							case 2: cor = "marfim"; break;
							case 3: cor = "verde"; break;
							case 4: cor = "vermelha"; break;
							default: cor = "erro";
						}
						break;
					case 1:
						switch(valor) {
							case 0: nacionalidade = "espanhol"; break;
							case 1: nacionalidade = "ingles"; break;
							case 2: nacionalidade = "japones"; break;
							case 3: nacionalidade = "noruegues"; break;
							case 4: nacionalidade = "ucraniano"; break;
							default: nacionalidade = "erro";
						}
						break;
					case 2:
						switch(valor) {
							case 0: cigarro = "Chesterfield"; break;
							case 1: cigarro = "Kool"; break;
							case 2: cigarro = "Lucky Strike"; break;
							case 3: cigarro = "Parliament"; break;
							case 4: cigarro = "Winston"; break;
							default: cigarro = "erro";
						}
						break;
					case 3:
						switch(valor) {
							case 0: bebida = "agua"; break;
							case 1: bebida = "cafe"; break;
							case 2: bebida = "cha"; break;
							case 3: bebida = "leite"; break;
							case 4: bebida = "suco de laranja"; break;
							default: bebida = "erro";
						}
						break;
					case 4:
						switch(valor) {
							case 0: animal = "cachorro"; break;
							case 1: animal = "caramujo"; break;
							case 2: animal = "cavalo"; break;
							case 3: animal = "raposa"; break;
							case 4: animal = "zebra"; break;
							default: animal = "erro";
						}
						break;
					default:
						System.out.println("Alguma coisa errada");
				}
			}
			System.out.printf("casa "+(casa)+": ");
			System.out.printf("%9s %10s %13s %16s %9s", cor, nacionalidade, cigarro, bebida, animal );
			System.out.println();
			
			
		}
		//em que casa vive a zebra
		for(int zebra = 0; zebra < casas.length; zebra++)
			if(casas[zebra][4] == 4)
				System.out.println("A zebra vive na casa " + zebra);
		//em que casa se bebe água
		for(int agua = 0; agua < casas.length; agua++)
			if(casas[agua][3] == 0)
				System.out.println("Bebe-se agua na casa " + agua);
	}
	
	private boolean jogadaPermitida(int casa, int campo, int valor) {
		boolean permitido = true;
		
		switch(campo) {
			case 0:  //restricoes quanto a cor
				switch(valor) {
					case 0:  //se cor é amarela
						if(!(casas[casa][2] == 1 || casas[casa][2] == -1))  //condicao: amarela -> Kool
							permitido = false;
						break;
					case 1:  //se cor é azul
						if(casa == 4) {  //condicao: azul -> noruegues ao lado
							if(!(casas[3][1] == 3 || casas[3][1] == -1))
								permitido = false;
						} else if(casa == 0) {
							if(!(casas[1][1] == 3 || casas[1][1] == -1))
								permitido = false;
						} else {
							if(!(casas[casa+1][1] == 3 || casas[casa+1][1] == -1 || casas[casa-1][1] == 3 || casas[casa-1][1] == -1))
								permitido = false;
						}
						break;
					case 2:	 //se cor é marfim
						if(casa == 4) {  //condicao: marfim -> verde a direita
							permitido = false;
						} else {
							if(!(casas[casa+1][0] == 3 || casas[casa+1][0] == -1))
								permitido = false;
						}
						break;
					case 3:  //se cor é verde
						if(!(casas[casa][3] == 1 || casas[casa][3] == -1))  //condicao: verde -> café
							permitido = false;
						if(casa == 0) {  //condicao: verde -> marfim a esquerda
							permitido = false;
						} else {
							if(!(casas[casa-1][0] == 2 || casas[casa-1][0] == -1))
								permitido = false;
						}
						break;
					case 4:  //se cor é vermelha
						if(!(casas[casa][1] == 1 || casas[casa][1] == -1))  //condicao: vermelha -> ingles
							permitido = false;
						break;
					default:
						System.out.println("Valor incorreto de cor");
						break;
				}
				break;
			//restricoes quanto a nacionalidade
			case 1:
				switch(valor) {
					case 0:  //se morador é espanhol
						if(!(casas[casa][4] == 0 || casas[casa][4] == -1))  //condicao: espanhol -> cachorro
							permitido = false;
						break;
					case 1:  //se morador é inglês
						if(!(casas[casa][0] == 4 || casas[casa][0] == -1))  //condicao: ingles -> vermelha
							permitido = false;
						break;
					case 2:  //se morador é japonês
						if(!(casas[casa][2] == 3 || casas[casa][2] == -1))  //condicao: japones -> parliament
							permitido = false;
						break;
					case 3:  //se morador é norueguês
						if (casa != 0)  //condicao: noruegues -> casa 0
							permitido = false; 
						if(casa == 4) {  //condicao: noruegues -> azul ao lado
							if(!(casas[3][0] == 1 || casas[3][0] == -1))
								permitido = false;
						} else if(casa == 0) {
							if(!(casas[1][0] == 1 || casas[1][0] == -1))
								permitido = false;
						} else {
							if(!(casas[casa+1][0] == 1 || casas[casa+1][0] == -1 || casas[casa-1][0] == 1 || casas[casa-1][0] == -1))
								permitido = false;
						}
						break;
					case 4:  //se morador é ucraniano
						if(!(casas[casa][3] == 2 || casas[casa][3] == -1))  //condicao: ucraniano -> cha
							permitido = false;
						break;
					default:
						System.out.println("Valor incorreto de nacionalidade");
						break;
				}
				break;
			//restricoes quanto ao cigarro
			case 2:
				switch(valor) {
					case 0:  //se a marca de cigarro é Chesterfield
						if(casa == 4) {  //condicao: Chesterfilde -> raposa ao lado
							if(!(casas[3][4] == 3 || casas[3][4] == -1))
								permitido = false;
						} else if(casa == 0) {
							if(!(casas[1][4] == 3 || casas[1][4] == -1))
								permitido = false;
						} else {
							if(!(casas[casa+1][4] == 3 || casas[casa+1][4] == -1 || casas[casa-1][4] == 3 || casas[casa-1][4] == -1))
								permitido = false;
						}
						break;
					case 1:  //se a marca de cigarro é Kool
						if(!(casas[casa][0] == 0 || casas[casa][0] == -1))  //condicao: Kool -> amarela
							permitido = false;
						if(casa == 4) {  //condicao: Kool -> cavalo ao lado
							if(!(casas[3][4] == 2 || casas[3][4] == -1))
								permitido = false;
						} else if(casa == 0) {
							if(!(casas[1][4] == 2 || casas[1][4] == -1))
								permitido = false;
						} else {
							if(!(casas[casa+1][4] == 2 || casas[casa+1][4] == -1 || casas[casa-1][4] == 2 || casas[casa-1][4] == -1))
								permitido = false;
						}
						break;
					case 2:  //se a marca de cigarro é Lucky Strike
						if(!(casas[casa][3] == 4 || casas[casa][3] == -1))  //condicao: Lucky Strike -> suco de laranja
							permitido = false;
						break;
					case 3:  //se a marca de cigarro é Parliament
						if(!(casas[casa][1] == 2 || casas[casa][1] == -1))  //condicao: Parliament -> japones
							permitido = false;
						break;
					case 4:  //se a marca de cigarro é Winston
						if(!(casas[casa][4] == 1 || casas[casa][4] == -1))  //condicao: Winston -> caramujo
							permitido = false;
						break;
					default:
						System.out.println("Valor incorreto de cigarro");
						break;
				}
				break;
			//restricoes quanto a bebida
			case 3:
				switch(valor) {
					case 0:  //se bebida é água
						break;
					case 1:  //se bebida é café
						if(!(casas[casa][0] == 3 || casas[casa][0] == -1))  //condicao: cafe -> verde
							permitido = false;
						break;
					case 2:  //se bebida é chá
						if(!(casas[casa][1] == 4 || casas[casa][1] == -1))  //condicao: cha -> ucraniano
							permitido = false;
						break;
					case 3:  //se bebida é leite
						if(casa != 2)  //condicao: leite -> casa2
							permitido = false;
						break;
					case 4:  //se bebida é suco de laranja
						if(!(casas[casa][2] == 2 || casas[casa][2] == -1))  //condicao: suco de laranja -> Lucky Strike
							permitido = false;
						break;
					default:
						System.out.println("Valor incorreto de bebida");
						break;
				}
				break;
			//restricoes quanto ao animal
			case 4:
				switch(valor) {
					case 0:  //se animal é cachorro
						if(!(casas[casa][1] == 0 || casas[casa][1] == -1))  //condicao: cachorro -> espanhol
							permitido = false;
						break;
					case 1:  //se animal é caramujo
						if(!(casas[casa][2] == 4 || casas[casa][2] == -1))  //condicao: caramujo -> Winston
							permitido = false;
						break;
					case 2:  //se animal é cavalo
						if(casa == 4) {  //condicao: cavalo -> Kool ao lado
							if(!(casas[3][2] == 1 || casas[3][2] == -1))
								permitido = false;
						} else if(casa == 0) {
							if(!(casas[1][2] == 1 || casas[1][2] == -1))
								permitido = false;
						} else {
							if(!(casas[casa+1][2] == 1 || casas[casa+1][2] == -1 || casas[casa-1][2] == 1 || casas[casa-1][2] == -1))
								permitido = false;
						}
						break;
					case 3:  //se animal é raposa	
						if(casa == 4) {  //condicao: raposa -> Chesterfield ao lado
							if(!(casas[3][2] == 0 || casas[3][2] == -1))
								permitido = false;
						} else if(casa == 0) {
							if(!(casas[1][2] == 0 || casas[1][2] == -1))
								permitido = false;
						} else {
							if(!(casas[casa+1][2] == 0 || casas[casa+1][2] == -1 || casas[casa-1][2] == 0 || casas[casa-1][2] == -1))
								permitido = false;
						}
						break;
					case 4:  //se animal é zebra
						break;
					default:
						System.out.println("Valor incorreto de animal");
						break;
				}
				break;
			default:
				break;
		}
		return permitido;
	}
	
	private boolean checaJogada(int casa, int campo, int valor) {
		casas[casa][campo] = valor;
		for(int i = 0; i < casas.length; i++)
			for(int j = 0; j < casas[0].length; j++)
				if(!jogadaPermitida(i, j, casas[i][j])) {
					casas[casa][campo] = -1;
					return false;
				}
		return true;
	}
	
}
