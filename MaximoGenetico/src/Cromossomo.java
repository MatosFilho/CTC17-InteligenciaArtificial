import java.util.Random;

public class Cromossomo {

	private String cromoX;
	private String cromoY;
	private double f;
	private double fix;
	
	public Cromossomo() {
		gerarCromossomo ();
	}
	
	private void gerarCromossomo() {
		this.cromoX = Integer.toBinaryString(new Random().nextInt(32768));
		this.cromoY = Integer.toBinaryString(new Random().nextInt(32768));
		if (cromoX.length() < 15)
			while (cromoX.length() < 15)
				cromoX = "0" + cromoX;
		if (cromoY.length() < 15)
			while (cromoY.length() < 15)
				cromoY = "0" + cromoY;
	}
	
	public String getCromoX() { return cromoX; }
	public void setCromoX(String cromoX) { this.cromoX = cromoX; }
	public String getCromoY() { return cromoY; }
	public void setCromoY(String cromoY) { this.cromoY = cromoY; }
	public double getF() { return f; }
	public void setF(double f) { this.f = f;	}
	public double getFix() { return fix; }
	public void setFix(double fix) { this.fix = fix; }

	public double getValorX() {
		int intX = Integer.parseInt(cromoX, 2);
		return ( -10 + 20 * ((double) intX/32767));
	}
	
	public double getValorY() {
		int intY = Integer.parseInt(cromoY, 2);
		return ( -10 + 20 * ((double) intY/32767));
	}
}
