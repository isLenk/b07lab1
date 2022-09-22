public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        coefficients = new double[]{0};
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial other) {
        // Determine which polynomial constains more coefficients.
        Polynomial larger_poly = (other.coefficients.length > this.coefficients.length) ? other : this;
        Polynomial smaller_poly = (this != larger_poly) ? this : other;

        double[] new_coefficients = new double[larger_poly.coefficients.length];

        // Add the larger polynomials coefficients to our new double array, and add the smaller coefficient if it exists.
        for (int i=0; i < larger_poly.coefficients.length; i++) {
            new_coefficients[i] = larger_poly.coefficients[i];
            if (smaller_poly.coefficients.length > i) {
                new_coefficients[i] += smaller_poly.coefficients[i];
            }
        }
        
        return new Polynomial(new_coefficients);
    }

    public double evaluate(double x) {
        double value=0;
        for (int i=-1;++i<this.coefficients.length;)
            value+=this.coefficients[i]*Math.pow(x,i);
        return value;
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }

}