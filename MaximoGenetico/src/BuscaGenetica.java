import java.util.ArrayList;
import java.util.Collections;

public class BuscaGenetica {
	
	private ArrayList<Cromossomo> populacao;
	private ArrayList<Cromossomo> proximaPopulacao;
	private Cromossomo elite;
	private Cromossomo elite2;
	private int tamanho;
	
	public BuscaGenetica(int t) {
		populacao = new ArrayList<Cromossomo>();
		proximaPopulacao = new ArrayList<Cromossomo>();
		elite = new Cromossomo();
		elite2 = new Cromossomo();
		tamanho = t;
		buscarSolucao();
	}
	
	public void buscarSolucao() {
		
		gerarPopulacao(tamanho);
		avaliarPopulacao();
		
		for(int geracao = 0; geracao < 10; geracao++) {
			selecionarPopulacao();
			reproduzirPopulacao();
			mutacao();
			atualizarPopulacao();
			avaliarPopulacao();
			elitismo();
		}
		
		Collections.sort(populacao, new Comparador());
		System.out.println("Melhor solucao:");
		System.out.println("X = " + populacao.get(0).getValorX() + "| Y = " + populacao.get(0).getValorY());
		System.out.println("f(x) = " + populacao.get(0).getF() + " fix(x) = " + populacao.get(0).getFix());
	}
	
	private void gerarPopulacao(int tamanho) {
		for (int k = 0; k < tamanho; k++) {
			Cromossomo novoCromossomo = new Cromossomo();
			populacao.add(novoCromossomo);
		}
		for (int l = 0; l < tamanho; l++) {
			Cromossomo novoCromossomo = new Cromossomo();
			proximaPopulacao.add(novoCromossomo);
		}
	}

	private void avaliarPopulacao() {
		double funcTotal = 0;
		
		for (Cromossomo crom : populacao) {
			double x = crom.getValorX();
			double y = crom.getValorY();
			double func = 4*Math.exp(-(x*x + y*y)) + Math.exp(-(x-5)*(x-5)-(y-5)*(y-5))
						+Math.exp(-(x+5)*(x+5)-(y-5)*(y-5)) + Math.exp(-(x-5)*(x-5)-(y+5)*(y+5)) + Math.exp(-(x+5)*(x+5)-(y+5)*(y+5));
			funcTotal += func;
			crom.setF(func);
		}
		
		for (Cromossomo crom2 : populacao) {
			crom2.setFix(crom2.getF()/funcTotal);
			//System.out.println("X = " + crom2.getValorX() + "| Y = " + crom2.getValorY());
			//System.out.println("f(x) = " + crom2.getF() + " fix(x) = " + crom2.getFix());
		}
	}
	
	private void selecionarPopulacao() {
		Collections.sort(populacao, new Comparador());
		
		//elitismo
		elite.setCromoX(populacao.get(0).getCromoX());
		elite.setCromoY(populacao.get(0).getCromoY());
		elite.setF(populacao.get(0).getF());
		elite.setFix(populacao.get(0).getFix());
		
		elite2.setCromoX(populacao.get(1).getCromoX());
		elite2.setCromoY(populacao.get(1).getCromoY());
		elite2.setF(populacao.get(1).getF());
		elite2.setFix(populacao.get(1).getFix());
		
		/*imprime ordenado
		System.out.printf("\n fix ordenado : \n");
		for(Cromossomo cr : populacao)
			System.out.println(cr.getFix());*/
		
		//System.out.printf("\n probab. acumulada : \n");
		//probabilidade acumulada
		double[] probAcumulada = new double[populacao.size()];
		double valAcumulado = 0;
		for (int indice = 0; indice < probAcumulada.length; indice++) {
			valAcumulado += populacao.get(indice).getFix();
			probAcumulada[indice] = valAcumulado;
		}
		
		/*for (double x : probAcumulada)
			System.out.println(x);
		System.out.printf("\nAntes selecionados : \n");
		for (Cromossomo y :proximaPopulacao)
			System.out.println("X : " + y.getCromoX() + " Y : " + y.getCromoY());*/
		
		//escolhe randomicamente
		for (int m = 0 ; m < probAcumulada.length; m++) {
			double aleatorio = Math.random();
			//System.out.println("aleatorio " + m + " : " + aleatorio);
			for (int n = 0; n < probAcumulada.length; n++) {
				if (aleatorio <= probAcumulada[n]) {
					proximaPopulacao.get(m).setCromoX(populacao.get(n).getCromoX());
					proximaPopulacao.get(m).setCromoY(populacao.get(n).getCromoY());
					//System.out.println(n);
					break;
				}
			}
		}
		
		/*System.out.printf("\n selecionados : \n");
		for (Cromossomo y :proximaPopulacao)
			System.out.println("X : " + y.getCromoX() + " Y : " + y.getCromoY());
		*/
	}
	
	private void reproduzirPopulacao() {
		ArrayList<Integer> pais = new ArrayList<Integer>();
		
		double probCrossOver = 0.6;
		for (int m = 0; m < tamanho; m++)
			if(Math.random() < probCrossOver)
				pais.add(m);
		
		/*System.out.printf("\n crossover : \n" + pais.size());
		for (int y :pais)
			System.out.println(y);*/
		
		//recombinar
		for(int n = 0; n < pais.size()/2; n++) {
			String filhoX;
			String filhoY;
			String filhaX;
			String filhaY;
			//gera filho
			filhoX = proximaPopulacao.get(pais.get(2*n)).getCromoX().substring(0, 10);
			filhoX += proximaPopulacao.get(pais.get(2*n+1)).getCromoX().substring(10);
			filhoY = proximaPopulacao.get(pais.get(2*n)).getCromoY().substring(0, 10);
			filhoY += proximaPopulacao.get(pais.get(2*n+1)).getCromoY().substring(10);
			//gera filha
			filhaX = proximaPopulacao.get(pais.get(2*n+1)).getCromoX().substring(0, 10);
			filhaX += proximaPopulacao.get(pais.get(2*n)).getCromoX().substring(10);
			filhaY = proximaPopulacao.get(pais.get(2*n+1)).getCromoY().substring(0, 10);
			filhaY += proximaPopulacao.get(pais.get(2*n)).getCromoY().substring(10);
			//substitui pais por filhos
			proximaPopulacao.get(pais.get(2*n)).setCromoX(filhoX);
			proximaPopulacao.get(pais.get(2*n)).setCromoY(filhoY);
			proximaPopulacao.get(pais.get(2*n+1)).setCromoX(filhaX);
			proximaPopulacao.get(pais.get(2*n+1)).setCromoY(filhaY);
		}
		
	}

	private void mutacao() {
		double probMutacao = 0.02;
		for(int j = 0; j < tamanho; j++) {
			String cX = proximaPopulacao.get(j).getCromoX();
			String mutX = "";
			for(int i = 0; i < cX.length(); i++) {
				double aleatorio = Math.random();
				if(aleatorio <= probMutacao) {
					if(cX.charAt(i) == '1')
						mutX += '0';
					else
						mutX += '1';
				} else
					mutX += cX.charAt(i);			
			}
			String cY = proximaPopulacao.get(j).getCromoY();
			String mutY = "";
			for(int i = 0; i < cY.length(); i++) {
				double aleatorio = Math.random();
				if(aleatorio <= probMutacao) {
					if(cY.charAt(i) == '1')
						mutY += '0';
					else
						mutY += '1';
				} else
					mutY += cY.charAt(i);			
			}
			proximaPopulacao.get(j).setCromoX(mutX);
			proximaPopulacao.get(j).setCromoY(mutY);
		}
	}
	
	private void atualizarPopulacao() {
		for(int c = 0; c < tamanho; c++) {
			populacao.get(c).setCromoX(proximaPopulacao.get(c).getCromoX());
			populacao.get(c).setCromoY(proximaPopulacao.get(c).getCromoY());
		}
	}
	
	private void elitismo() {
		Collections.sort(populacao, new Comparador());
		populacao.remove(tamanho-1);
		populacao.remove(tamanho-2);
		populacao.add(elite);
		populacao.add(elite2);
	}
	
}
