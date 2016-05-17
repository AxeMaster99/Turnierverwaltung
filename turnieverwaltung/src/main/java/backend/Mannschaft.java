package backend;

public class Mannschaft {
		private String name = "...";
		private Spieler spieler;
		
		public Mannschaft() {
			this.name = "...";
		}
		
		public Mannschaft(String name){
			this.name = name;
		}
		
		@Override
		public String toString() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
}
