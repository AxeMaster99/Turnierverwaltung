package backend;

public class Group {
	private Mannschaft m1;
	private Mannschaft m2;
	private Mannschaft m3;
	private Mannschaft m4;

	public Group(Mannschaft m1,Mannschaft m2,Mannschaft m3,Mannschaft m4){
		this.m1=m1;
		this.m2=m2;
		this.m3=m3;
		this.m4=m4;
	}

	public Mannschaft getM1() {
		return m1;
	}

	public Mannschaft getM2() {
		return m2;
	}

	public Mannschaft getM3() {
		return m3;
	}

	public Mannschaft getM4() {
		return m4;
	}
}
