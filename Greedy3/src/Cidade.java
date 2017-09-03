import java.util.ArrayList;

public class Cidade {
	private int cidade;
	private int cidadeAnterior;
	private float posX;
	private float posY;
	private float distEstimada;
	private boolean visitado;
	ArrayList<Cidade> vizinhos;
	ArrayList<Integer> indicieVizinhos;
	
	public Cidade(int num, float x, float y) {
		this.cidade = num;
		this.posX = x;
		this.posY = y;
		this.visitado = false;
		this.vizinhos = new ArrayList<Cidade>();
		this.indicieVizinhos = new ArrayList<Integer>();
	}

	public int getCidadeAnterior() { return cidadeAnterior; }
	public void setCidadeAnterior(int cidadeAnterior) { this.cidadeAnterior = cidadeAnterior; }
	public boolean isVisitado() { return visitado; }
	public void setVisitado(boolean visitado) { this.visitado = visitado; }
	public float getDistEstimada() { return distEstimada; }
	public void setDistEstimada(float distEstimada) { this.distEstimada = distEstimada; }
	public int getCidade() { return cidade; }
	public float getPosX() { return posX; }
	public float getPosY() { return posY; }

}