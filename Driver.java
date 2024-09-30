import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        try {
            // testing constructor w/ out args
            Polynomial p = new Polynomial();
            System.out.println(p.evaluate(3));  // expected output: 0.0

            // testing constructor with args
            double[] c1 = {6, 0, 0, 5};
            int[] e1 = {0, 1, 2, 3};
            Polynomial p1 = new Polynomial(c1, e1);

            double[] c2 = {0, -2, 0, 0, -9};
            int[] e2 = {0, 1, 2, 3, 4};
            Polynomial p2 = new Polynomial(c2, e2);

            // testing addition
            Polynomial sum = p1.add(p2);
            System.out.println("s(0.1) = " + sum.evaluate(0.1));  // expected output: 5.8041
            if (sum.hasRoot(1)) {
                System.out.println("1 is a root of s");
            } else {
                System.out.println("1 is not a root of s");
            }

            // testing multiplication
            Polynomial product = p1.multiply(p2);
            System.out.println("Product evaluated at x = 2: " + product.evaluate(2));  

            // testing file constructor
            Polynomial pFromFile = new Polynomial(new File("polynomial.txt"));
            System.out.println("pFromFile evaluated at x = 2: " + pFromFile.evaluate(2));

            // testing saving to file
            p1.saveToFile("output_polynomial.txt");

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
