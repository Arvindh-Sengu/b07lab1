import java.io.*;

public class Polynomial {
    // field
    private double[] coeffs;
    private int[] exponents;

    // no args constructor
    public Polynomial() {
        coeffs = new double[]{0};
        exponents = new int[]{0};
    }

    // args constructor
    public Polynomial(double[] coeffs, int[] exponents) {
        this.coeffs = coeffs;
        this.exponents = exponents;
    }

    // file constructor
    public Polynomial(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        reader.close();

        parsePolynomial(line);
    }

    // Helper method to parse a polynomial from string format (like "5-3x2+7x8")
    private void parsePolynomial(String polyString) {
        polyString = polyString.replaceAll(" ", "");  // remove blank spaces
        String[] terms = polyString.split("\\+|(?=-)");  // split by + or - 

        int numTerms = terms.length;
        coeffs = new double[numTerms];
        exponents = new int[numTerms];

        for (int i = 0; i < numTerms; i++) {
            String term = terms[i];
            double coeff;
            int exp;

            if (term.contains("x")) {
                String[] parts = term.split("x");
                if (parts[0].equals("") || parts[0].equals("-")) {
                    coeff = parts[0].equals("-") ? -1 : 1;
                } else {
                    coeff = Double.parseDouble(parts[0]);  // coefficients
                }

                if (parts.length == 1) {
                    exp = 1;  // x with exponent 1
                } else {
                    exp = Integer.parseInt(parts[1].replace("^", ""));
                }
            } else {
                coeff = Double.parseDouble(term);  // constants
                exp = 0;
            }

            coeffs[i] = coeff;
            exponents[i] = exp;
        }
    }

    // add two polys
    public Polynomial add(Polynomial other) {
        int maxLength = Math.max(this.exponents.length, other.exponents.length);
        double[] resultCoeffs = new double[maxLength];
        int[] resultExponents = new int[maxLength];

        int index = 0;
        for (int i = 0; i < this.coeffs.length; i++) {
            resultCoeffs[index] = this.coeffs[i];
            resultExponents[index] = this.exponents[i];
            index++;
        }

        for (int j = 0; j < other.coeffs.length; j++) {
            boolean termAdded = false;
            for (int i = 0; i < resultExponents.length; i++) {
                if (other.exponents[j] == resultExponents[i]) {
                    resultCoeffs[i] += other.coeffs[j];
                    termAdded = true;
                    break;
                }
            }
            if (!termAdded) {
                resultCoeffs[index] = other.coeffs[j];
                resultExponents[index] = other.exponents[j];
                index++;
            }
        }

        double[] finalCoeffs = new double[index];
        int[] finalExponents = new int[index];
        System.arraycopy(resultCoeffs, 0, finalCoeffs, 0, index);
        System.arraycopy(resultExponents, 0, finalExponents, 0, index);

        return new Polynomial(finalCoeffs, finalExponents);
    }

    // multiply two polys (foil)
    public Polynomial multiply(Polynomial other) {
        int maxLength = this.coeffs.length * other.coeffs.length;
        double[] resultCoeffs = new double[maxLength];
        int[] resultExponents = new int[maxLength];

        int index = 0;
        for (int i = 0; i < this.coeffs.length; i++) {
            for (int j = 0; j < other.coeffs.length; j++) {
                resultCoeffs[index] = this.coeffs[i] * other.coeffs[j];
                resultExponents[index] = this.exponents[i] + other.exponents[j];
                index++;
            }
        }

        // combine like terms 
        for (int i = 0; i < resultExponents.length - 1; i++) {
            for (int j = i + 1; j < resultExponents.length; j++) {
                if (resultExponents[i] == resultExponents[j]) {
                    resultCoeffs[i] += resultCoeffs[j];
                    resultCoeffs[j] = 0;
                }
            }
        }

        // create final arrays without zeros
        int nonZeroCount = 0;
        for (double coeff : resultCoeffs) {
            if (coeff != 0) {
                nonZeroCount++;
            }
        }

        double[] finalCoeffs = new double[nonZeroCount];
        int[] finalExponents = new int[nonZeroCount];
        int finalIndex = 0;

        for (int i = 0; i < resultCoeffs.length; i++) {
            if (resultCoeffs[i] != 0) {
                finalCoeffs[finalIndex] = resultCoeffs[i];
                finalExponents[finalIndex] = resultExponents[i];
                finalIndex++;
            }
        }

        return new Polynomial(finalCoeffs, finalExponents);
    }

    // evaluate at x
    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coeffs.length; i++) {
            result += coeffs[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    // check if root
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    // save poly to a file 
    public void saveToFile(String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        String polyString = "";

        for (int i = 0; i < coeffs.length; i++) {
            if (i > 0 && coeffs[i] >= 0) {
                polyString += "+";
            }
            polyString += coeffs[i];
            if (exponents[i] != 0) {
                polyString += "x^" + exponents[i];
            }
        }

        writer.write(polyString);
        writer.close();
    }
}
