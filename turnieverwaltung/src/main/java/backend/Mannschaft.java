package backend;

public class Mannschaft {
		private String name;
		private Spieler spieler;
		
		public Mannschaft(String name){
			this.name = name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
}
