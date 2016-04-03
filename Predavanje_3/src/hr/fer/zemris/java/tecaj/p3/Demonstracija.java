package hr.fer.zemris.java.tecaj.p3;

import hr.fer.zemris.java.tecaj_3.prikaz.PrikaznikSlike;
import hr.fer.zemris.java.tecaj_3.prikaz.Slika;

public class Demonstracija {

	public static void main(String[] args) {

		GeometrijskiLik[] likovi = new GeometrijskiLik[] {
				new Pravokutnik(0, 0, 50, 50), 
				new Pravokutnik(50, 50, 25, 25) 
				};
		Slika slika = new Slika(500, 500);

		for (GeometrijskiLik geometrijskiLik : likovi) {
			geometrijskiLik.popuniLik(slika);
		}

		// slika.nacrtajSliku(System.out);

		PrikaznikSlike.prikaziSliku(slika);
	}
	
	public static void renderirajSadrziocTocaka(Slika slika, SadrziocTocaka t) {
		for (int y = 0, sirina = slika.getSirina(), visina = slika.getVisina(); y < visina; y++) {
			for (int x = 0; x < sirina; x++) {
				if (t.sadrziTocku(x, y)) {
					slika.upaliTocku(x, y);
				}
			}
		}
	}
}