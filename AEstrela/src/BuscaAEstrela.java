import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class BuscaAEstrela {
	
	private static int ORIGEM;
	private static int DESTINO;	
	private static Cidade cidadeAux;
	private static ArrayList<Cidade> cidades;
	
	public static void criaMapaCidades(int origem, int destino) {
		DESTINO = destino;
		ORIGEM = origem;
		cidades = new ArrayList<Cidade>();
		preencheCidades();
		preencheVizinhos();
	}
	
	public static void preencheCidades() {
		try{
	         BufferedReader br = new BufferedReader(new FileReader("c:/Uruguay.csv"));
	         while(br.ready()){
	            String linha = br.readLine();
	            linha = linha.replace(",", ".");
	            String[] aux = linha.split(";");
	            cidadeAux = new Cidade(Integer.parseInt(aux[0]), Float.parseFloat(aux[1]), Float.parseFloat(aux[2]));
	            preencheIndiceVizinhos(aux);
	            cidades.add(cidadeAux);
	         }
	         distanciasEstimadas();
	         br.close();
	      }catch(IOException ioe){
	         ioe.printStackTrace();
	      }
	}
	
	public static void preencheIndiceVizinhos(String[] v) {
		for(int i = 3; i < (v.length-1); i++)
			cidadeAux.indicieVizinhos.add(Integer.parseInt(v[i]));
	}
	
	public static void preencheVizinhos() {
		for (Cidade aux : cidades) {
			for(Integer viz : aux.indicieVizinhos)
				aux.vizinhos.add(cidades.get(viz-1));
			Collections.sort(aux.vizinhos, new Ordenador());
			cidades.set(aux.getCidade()-1, aux);
		}
	}
	
	public static void distanciasEstimadas() {
		for (Cidade aux : cidades) {
			aux.setDistEstimada(calculaDistanciaDestino(aux.getCidade()));
			cidades.set(aux.getCidade()-1, aux);
		}
	}
	
	public static float calculaDistanciaDestino(int n) {
		float distX = (cidades.get(n-1).getPosX() - cidades.get(DESTINO-1).getPosX())*(cidades.get(n-1).getPosX() - cidades.get(DESTINO-1).getPosX());
		float distY = (cidades.get(n-1).getPosY() - cidades.get(DESTINO-1).getPosY())*(cidades.get(n-1).getPosY() - cidades.get(DESTINO-1).getPosY());
		return (float) Math.sqrt(distX + distY);
	}
	
	public static float calculaDistancia(int n, int m) {
		float distX = (cidades.get(n-1).getPosX() - cidades.get(m-1).getPosX())*(cidades.get(n-1).getPosX() - cidades.get(m-1).getPosX());
		float distY = (cidades.get(n-1).getPosY() - cidades.get(m-1).getPosY())*(cidades.get(n-1).getPosY() - cidades.get(m-1).getPosY());
		return (float) Math.sqrt(distX + distY);
	}

	private static double custoViagem() {
		ArrayList<Integer> caminhoCidades = new ArrayList<Integer>();
		caminhoCidades.add(600);
		int num = 600;
		while (num != 203) {
			num = cidades.get(num-1).getCidadeAnterior();
			caminhoCidades.add(num);
		}
		
		System.out.println("O número de cidades visitadas no percurso foi: " + caminhoCidades.size());
		
		double custo = 0;
		int anterior = 600;
		for (int atual : caminhoCidades) {
			custo += calculaDistancia(anterior, atual);
			anterior = atual;
		}
		return custo;
	}

	private static void busca() {
		ArrayList<Cidade> caminho = new ArrayList<Cidade>();
		Cidade aux = cidades.get(ORIGEM-1);
		aux.setCidadeAnterior(-1);
		aux.setCustoParcial(0);
		aux.setCustoEstimado(aux.getDistEstimada());
		aux.setVisitado(true);
		while(aux.getCidade() != DESTINO) {
			for(Cidade c : aux.vizinhos) {
				if (!c.isVisitado()) {
					c.setCidadeAnterior(aux.getCidade());
					c.setCustoParcial(aux.getCustoParcial()+calculaDistancia(c.getCidade(), aux.getCidade()));
					c.setVisitado(true); 
					if(aux.getCustoEstimado() >= c.getCustoParcial()+c.getDistEstimada())
						c.setCustoEstimado(aux.getCustoEstimado());
					else
						c.setCustoEstimado(c.getCustoParcial()+c.getDistEstimada());
					caminho.add(c);
				}
				if (c.getCustoEstimado()>aux.getCustoParcial()+calculaDistancia(c.getCidade(), aux.getCidade())+c.getDistEstimada()) {
					c.setCidadeAnterior(aux.getCidade());
					c.setCustoParcial(aux.getCustoParcial()+calculaDistancia(c.getCidade(), aux.getCidade()));
					if(aux.getCustoEstimado() >= c.getCustoParcial()+c.getDistEstimada())
						c.setCustoEstimado(aux.getCustoEstimado());
					else
						c.setCustoEstimado(c.getCustoParcial()+c.getDistEstimada());
				}
			}
			Collections.sort(caminho, new Ordenador());
			aux = caminho.get(0);
			caminho.remove(0);
		}
	}
	
	public static void main(String[] args) {
		
		criaMapaCidades(203, 600);
		busca();
		double custo = custoViagem();
		System.out.println("O custo da viagem é: "+ custo);
	}
	
}
