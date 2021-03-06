package hr.fer.zemris.java.tecaj.hw2;

/**
 * Class ComplexNumber which represents an unmodifiable complex number. Each
 * method which performs some kind of modification returns a new instance which
 * represents modified number.
 * 
 * @author Ante Spajic
 *
 */
public class ComplexNumber {

	private double real;
	private double imaginary;

	/**
	 * Constructs a new ComplexNumber with given real and imaginary parts.
	 * 
	 * @param realPart
	 *            Real part of the complex number.
	 * @param imaginaryPart
	 *            Imaginary part of the complex number.
	 */
	public ComplexNumber(double realPart, double imaginaryPart) {
		this.real = realPart;
		this.imaginary = imaginaryPart;
	}

	/**
	 * Returns a new ComplexNumber from given Real part of the complex number.
	 * This complex number's imaginary part is 0.
	 * 
	 * @param real
	 *            Real part of the complex number.
	 * @return ComplexNumber with specified real part
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0.0);
	}

	/**
	 * Returns a new ComplexNumber from given Imaginary part of the complex
	 * number. This complex number's real part is 0.
	 * 
	 * @param imaginary
	 *            Imaginary part of the complex number.
	 * @return ComplexNumber with given imaginary part.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0.0, imaginary);
	}

	/**
	 * Returns a new ComplexNumber from given magnitude and angle.
	 * 
	 * @param magnitude
	 *            Magnitude of complex number.
	 * @param angle
	 *            Angle of the complex number.
	 * @return New ComplexNumber with specified magnitude and angle.
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude,
			double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude
				* Math.sin(angle));
	}

	/**
	 * Parses the <code>String</code> as a <code>ComplexNumber</code> of type
	 * x+yi.
	 * 
	 * @param s
	 *            the input complex number as string
	 * @return a <code>ComplexNumber</code> which is represented by the string.
	 */
	public static ComplexNumber parse(String s) {
		s = s.replaceAll(" ", "");
		ComplexNumber parsed = null;
		if (s.contains(String.valueOf("+"))
				|| (s.contains(String.valueOf("-")) && s.lastIndexOf('-') > 0)) {
			String re = "";
			String im = "";
			s = s.replaceAll("i", "");
			if (s.indexOf('+') > 0) {
				re = s.substring(0, s.indexOf('+'));
				im = s.substring(s.indexOf('+') + 1, s.length());
				parsed = new ComplexNumber(Double.parseDouble(re),
						Double.parseDouble(im));
			} else if (s.lastIndexOf('-') > 0) {
				re = s.substring(0, s.lastIndexOf('-'));
				im = s.substring(s.lastIndexOf('-') + 1, s.length());
				parsed = new ComplexNumber(Double.parseDouble(re),
						-Double.parseDouble(im));
			}
		} else {
			if (s.endsWith("i")) {
				s = s.replaceAll("i", "");
				s = s.trim().length() == 0 ? "1" : s;
				parsed = fromImaginary(Double.parseDouble(s));
			} else {
				parsed = fromReal(Double.parseDouble(s));
			}
		}
		return parsed;
	}

	/**
	 * Getter for real part of the complex number.
	 * 
	 * @return Real part of this complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Getter for imaginary part of the complex number.
	 * 
	 * @return Imaginary part of this complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Returns the magnitude of this complex number. Magnitude is used in
	 * trigonometric form of complex number.
	 * 
	 * @return Magnitude(z) of this complex number.
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Returns the angle of this complex number. Angle is used in trigonometric
	 * form of complex number. Angle is returned in radians from range [0,2*PI>
	 * 
	 * @return Angle(phi) of this complex number from range [0, 2*PI>.
	 */
	public double getAngle() {
		return Math.atan2(imaginary, real) < 0 ? Math.atan2(imaginary, real)
				+ 2 * Math.PI : Math.atan2(imaginary, real);
	}

	/**
	 * Adds this <code>ComplexNumber</code> to another and returns a new
	 * <code>ComplexNumber</code> which represents sum of two.
	 * 
	 * @param c
	 *            The complex number to be added to the current complex number
	 * @return The sum of two ComplexNumbers.
	 */
	public ComplexNumber add(ComplexNumber c) {
		double real = this.real + c.real;
		double imag = this.imaginary + c.imaginary;
		return new ComplexNumber(real, imag);
	}

	/**
	 * Subtracts given <code>ComplexNumber</code> from given other ComplexNumber
	 * and returns a new <code>ComplexNumber</code> which represents the result
	 * of operation.
	 * 
	 * @param c
	 *            The complex number to be subratcted from the current complex
	 *            number.
	 * @return The result of subraction.
	 */
	public ComplexNumber sub(ComplexNumber c) {
		double real = this.real - c.real;
		double imag = this.imaginary - c.imaginary;
		return new ComplexNumber(real, imag);
	}

	/**
	 * Multiplies this <code>ComplexNumber</code> with another ComplexNumber and
	 * returns the result as a new object.
	 * 
	 * @param c
	 *            ComplexNumber that multiplies this object.
	 * @return New object representing the multiplication of given two.
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException("Argument cannot be null");
		}
		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imag = this.real * c.imaginary + this.imaginary * c.real;
		return new ComplexNumber(real, imag);
	}

	/**
	 * Divides this <code>ComplexNumber</code> with given divisor and returns
	 * the result of divison as a new object.
	 * 
	 * @param c
	 *            ComplexNumber divisor
	 * @return A new object as a result of division of this and a given one.
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (c == null) {
			throw new NullPointerException("Argument cannot be null");
		}
		double a = c.getReal();
		double b = c.getImaginary();
		if (a == 0.0 && b == 0.0) {
			throw new IllegalArgumentException("Cannot divide by 0");
		}
		if (Math.abs(a) < Math.abs(b)) {
			double q = a / b;
			double denominator = a * q + b;
			return new ComplexNumber((real * q + imaginary) / denominator,
					(imaginary * q - real) / denominator);
		} else {
			double q = b / a;
			double denominator = b * q + a;
			return new ComplexNumber((imaginary * q + real) / denominator,
					(imaginary - real * q) / denominator);
		}
	}

	/**
	 * Calculates the <code>ComplexNumber</code> to the passed integer power.
	 * 
	 * @param n
	 *            The power we wish to calculate.
	 * @return a <code>ComplexNumber</code> which is (z)^power
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException(
					"Power must be greater or equal to 0");
		}
		double magnitude = Math.pow(getMagnitude(), n);
		double angle = getAngle() * n;
		return fromMagnitudeAndAngle(magnitude, angle);
	}

	/**
	 * Computes the n-th roots of this complex number. The nth roots are defined
	 * by the formula:
	 * 
	 * <pre>
	 *  <code>
	 *   z<sub>k</sub> = abs<sup>1/n</sup> (cos(phi + 2&pi;k/n) + i (sin(phi + 2&pi;k/n))
	 *  </code>
	 * </pre>
	 * 
	 * for <i>{@code k=0, 1, ..., n-1}</i>, where {@code abs} and {@code phi}
	 * are respectively the {@link #abs() modulus} and {@link #getArgument()
	 * argument} of this complex number.
	 * <p>
	 *
	 * @param n
	 *            Degree of root.
	 * @return a List of all {@code n}-th roots of {@code this}.
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException(
					"You requested a negative or 0 root. Root must be positive");
		}
		double rootAngle = getAngle() / n;
		double rootMagnitude = Math.pow(getMagnitude(), 1. / n);
		ComplexNumber[] roots = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			roots[i] = fromMagnitudeAndAngle(rootMagnitude, rootAngle);
			rootAngle += 2 * Math.PI / n;
		}
		return roots;
	}

	/**
	 * @return the complex number in (Re) + (Im)i format
	 */
	@Override
	public String toString() {
		if (imaginary == 0) {
			return real + "";
		}
		if (real == 0) {
			return imaginary + "i";
		}
		if (imaginary < 0) {
			return real + " - " + (-imaginary) + "i";
		}
		return real + " + " + imaginary + "i";
	}

}
