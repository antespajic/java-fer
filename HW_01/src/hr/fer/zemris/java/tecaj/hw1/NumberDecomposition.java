package hr.fer.zemris.java.tecaj.hw1;

/**
 * Program računa i ispisuje rastav broja na proste faktore.
 * 
 * @author Ante Spajic
 *
 */
public class NumberDecomposition {

	/**
	 * Metoda koja se poziva prilikom pokretanja programa.
	 * 
	 * @param args
	 *            argument komandne linije je broj koji treba faktorizirati
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Invalid number of arguments");
			return;
		}
		int n = Integer.parseInt(args[0]);
		if (n <= 2) {
			System.err.println("You must enter number greater than 2");
			return;
		}
		System.out.println("You requested decomposition of number " + n
				+ " into prime factors. Here they are: ");
		int cnt = 0;
		for (int i = 2; i <= n; i++) {
			while (n % i == 0) {
				++cnt;
				n /= i;
				System.out.println(cnt + ". " + i);
			}
		}
	}
}
