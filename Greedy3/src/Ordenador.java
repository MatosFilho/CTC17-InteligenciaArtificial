import java.util.Comparator;

public class Ordenador implements Comparator<Cidade> {

	@Override
	public int compare(Cidade o1, Cidade o2) {
		if (o1.getDistEstimada() > o2.getDistEstimada())
			return 1;
		else if (o1.getDistEstimada() < o2.getDistEstimada())
			return -1;
		return 0;
	}

}
