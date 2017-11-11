import java.util.Comparator;

public class Comparador implements Comparator<Cromossomo> {

	@Override
	public int compare(Cromossomo c1, Cromossomo c2) {
		if (c1.getFix() < c2.getFix())
			return 1;
		else if (c2.getFix() < c1.getFix())
			return -1;
		else return 0;
	}

}
