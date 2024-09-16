public class Polynomial {
    // field
    double[] coeffs;

    // no args constructor
    public Polynomial() {
        coeffs = new double[1];
        coeffs[0] = 0;
    }

    // args constructor
    public Polynomial(double[] coeffs) {
        this.coeffs = coeffs.clone();
    }

    // add function
    public Polynomial add(Polynomial pol1) {
        int maxLength = Math.max(pol1.coeffs.length, this.coeffs.length);
        int minLength = Math.min(pol1.coeffs.length, this.coeffs.length);
        double[] newCoeffs = new double[maxLength];

        for (int i = 0; i < minLength; i++) { 
            newCoeffs[i] = pol1.coeffs[i] + this.coeffs[i];
        }

        boolean pol1Lower = pol1.coeffs.length < this.coeffs.length;
        double[] hiDegPolCoeffs;
        if (pol1Lower) {
            hiDegPolCoeffs = this.coeffs;
        }
        else {
            hiDegPolCoeffs = pol1.coeffs;
        }

        for(int i = minLength; i < maxLength; i++) {
            newCoeffs[i] = hiDegPolCoeffs[i];
        }

        Polynomial sum = new Polynomial(newCoeffs);
        return sum;
    }

    // evaluate function
    public double evaluate(double x) {
        double y = 0;
        for(int i = 0; i < coeffs.length; i++) {
            y += coeffs[i] * Math.pow(x, i);
        }
        return y;
    }

    // hasRoot function
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

}