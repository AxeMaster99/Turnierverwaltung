package backend.turnier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Group {

	private ArrayList<Mannschaft> mannschaften = new ArrayList<Mannschaft>();

	public Group(Mannschaft m1, Mannschaft m2, Mannschaft m3, Mannschaft m4) {
		Collections.addAll(this.mannschaften, m1, m2, m3, m4);
	}

	public Mannschaft getMannschaft(int i) {
		return this.mannschaften.get((i - 1));
	}

	public ArrayList<Mannschaft> getMannschaften() {
		return this.mannschaften;
	}

	public ArrayList<String> getGruppenSieger() {
		ArrayList<String> sieger = new ArrayList<String>();
		this.mannschaften
		.stream()
		.sorted((m1,m2) -> Integer.compare(m2.getPunkte()*100+m2.getTordifferenz(),m1.getPunkte()*100+m1.getTordifferenz()))
		.forEach(team -> {
					sieger.add(team.getName());
				});

		/*Collections.sort(this.mannschaften, new Comparator<Mannschaft>() {
			@Override
			public int compare(Mannschaft m1, Mannschaft m2) {

				int m1pnkt = m1.getPunkte() * 100 + m1.getTordifferenz();
				int m2pnkt = m2.getPunkte() * 100 + m2.getTordifferenz();

				if (m1pnkt > m2pnkt) {
					return -1;
				} else {
					return 1;
				}

			}
		});*/

		return sieger;
	}

}
