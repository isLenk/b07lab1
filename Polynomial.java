public class Polynomial {
    double[] coefficients;

    public Polynomial() {
        coefficients = new double[]{0};
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial other) {
        Polynomial lp=(other.coefficients.length>this.coefficients.length)?other:this, bp=(this!=lp)?this:other;
        double[] nc=new double[lp.coefficients.length];
        for (int i=-1;++i<lp.coefficients.length;) nc[i] = lp.coefficients[i]+((bp.coefficients.length>i)?bp.coefficients[i]:0);
        return new Polynomial(nc);
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