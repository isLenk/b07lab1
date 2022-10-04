import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Polynomial {
    double[] coefficients;
    int[] exponents;

    public Polynomial() {
        coefficients = new double[]{0};
        exponents = new int[]{0};
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws FileNotFoundException{
        Scanner sc = new Scanner(file);

        // Empty File
        if (!sc.hasNextLine()) {sc.close(); return;}
 
        Matcher vars = Pattern.compile("(-?[0-9]+\\.?[0-9]*x?[0-9]*)").matcher(sc.nextLine());
        sc.close();

        int termCount = (int)vars.results().count(), i = 0;
        vars.reset();
        this.coefficients = new double[termCount];
        this.exponents = new int[termCount];
        
        while (vars.find()) {
            String[] nums = vars.group(1).split("x");
            this.exponents[i] = (nums.length>1)?Integer.parseInt(nums[1]):0;
            this.coefficients[i++] = Double.parseDouble(nums[0]);
        }
    }

    public Polynomial add(Polynomial other) {
        if (other.exponents == null || this.exponents == null) { return (other.exponents == null)?this:other;};
        int[] degs = new int[other.exponents.length + this.exponents.length];
        double[] coefs = new double[degs.length];
        int index = this.exponents.length;
        for (int i = 0; i < this.exponents.length;  coefs[i] = this.coefficients[i],i++) degs[i] = this.exponents[i];
        for (int i = 0; i < other.exponents.length; i++) {
            int hasVal = -1, deg = other.exponents[i];
            for(int v=0;v<degs.length&&hasVal<0;v++)if(degs[v]==deg)hasVal = v;
            if (hasVal<0) {
                degs[index] = other.exponents[i];
                coefs[index++] = other.coefficients[i];
            } else coefs[hasVal] += other.coefficients[i];
        }
        int a = degs.length-1, b=a;
        while (b>=0) a -= (coefs[b--]==0)?1:0;
        int[] shortDegs = new int[a];
        double[] shortCoefs = new double[a];
        for (int i = 0; i < shortDegs.length; shortDegs[i] = degs[i++]) shortCoefs[i] = coefs[i];
        return new Polynomial(shortCoefs, shortDegs);
    }
    public String toString() {
        if (this.exponents == null) return "";
        String content = "", operation = "";
        for (int i = 0; i < this.exponents.length; i++) {
            // If last iteration, do not append +/-, if coefficient is non-negative, '+', else do nothing as coefficient is negative
            operation = (i<1)?"":(this.coefficients[i] >= 0)? "+" : "";
            content += operation + this.coefficients[i] + ((this.exponents[i]!=0)?("x" + this.exponents[i]):"");
        }
        return content;
    }

    public void saveToFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
                FileWriter out = new FileWriter(file);
                out.write(this.toString());
                out.close();
            }
        }   
        catch (Exception e) { e.getStackTrace();}
    }

    public double evaluate(double x) {
        double value=0;
        for (int i=-1;++i<this.coefficients.length;)
            value+=this.coefficients[i]*Math.pow(x,this.exponents[i]);
        return value;
    }

    public Polynomial multiply(Polynomial other) {
        if (other.exponents == null || this.exponents == null) { return new Polynomial();};
        int[] _deg = new int[other.exponents.length * this.exponents.length];
        double[] _coef = new double[other.exponents.length * this.exponents.length];
        int index = 0;
        for (int indexP1 = 0; indexP1 < this.exponents.length; indexP1++)
            for (int indexP2 = 0; indexP2 < other.exponents.length; indexP2++) {
                int exp1 = this.exponents[indexP1], exp2 = other.exponents[indexP2];
                int degree = exp1 + exp2, hasVal = -1;
                // Check if degree exists in array
                for (int degInd = -1; ++degInd < _deg.length && hasVal<0;) if (_deg[degInd] == degree) hasVal = degInd;

                if (hasVal < 0) {
                    _deg[index] = degree;
                    _coef[index++] = this.coefficients[indexP1] * other.coefficients[indexP2];
                } else _coef[hasVal] += this.coefficients[indexP1] * other.coefficients[indexP2];
            }

        int a = _deg.length-1, b=a;
        while (b>=0) a -= (_coef[b--]==0)?1:0;
        // Special Case, the zero polynomial, return zero polynomial
        if (a < 1) return new Polynomial();
        int[] shortDegs = new int[a];
        double[] shortCoefs = new double[a];
        for (int i = 0; i < shortDegs.length; shortDegs[i] = _deg[i++]) shortCoefs[i] = _coef[i];
        return new Polynomial(shortCoefs, shortDegs);
    }

    public boolean hasRoot(double x) {
        return this.evaluate(x) == 0;
    }

    public static void printPoly(Polynomial r) {
        if (r.exponents == null) { System.out.println("NULL Polynomial"); return;};
        
        Polynomial a = new Polynomial(r.coefficients.clone(), r.exponents.clone());
        int[] d = new int[r.exponents.length];
        double[] c = new double[r.coefficients.length];
        for (int i = 0, index = 0 ; i < d.length; i++) {
            int cval = d[index], ind=0;
            for (int v = 0; v < a.exponents.length; v++) {
                if (a.exponents[v] > cval) {
                    cval = a.exponents[v];
                    ind=v;
                    d[index] = cval;
                }
            }
            c[index] = a.coefficients[ind];
            a.exponents[ind] = -1;
            //a.coefficients[ind] = 0;
            index++;
        }

        Polynomial b = new Polynomial(c, d);

        for (int ind = 0; ind < b.exponents.length; ind++) {
            System.out.print("("+b.coefficients[ind] + ")x^" +  b.exponents[ind] + ((ind+1 < b.exponents.length) ? " + " : ""));
        }
        System.out.println();
    }
}