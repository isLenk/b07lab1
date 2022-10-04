import java.io.File;
import java.io.FileNotFoundException;

public class Driver { 
    public static void main(String [] args) { 
             //Polynomial nullPoly = new Polynomial();
             File fila = new File("./nullPoly.txt");
             //try {nullPoly = new Polynomial(fila);} catch (FileNotFoundException f) {}
     
             System.out.println("---------------------------");
             System.out.println("> Multiplication ");
             Polynomial p1 = new Polynomial(new double[]{8, 3, 1}, new int[]{4, 2, 1});
             Polynomial p2 = new Polynomial(new double[]{4, 12, 3, 1}, new int[]{3, 8, 2, 1});
             //p1 = nullPoly;
             Polynomial.printPoly(p1);
             Polynomial.printPoly(p2);
             
             System.out.println("MULTIPLYING");
             Polynomial r = p1.multiply(p2);
     
             Polynomial.printPoly(r);
             p1 = new Polynomial(new double[]{5, 15, 4, 3, 1,50}, new int[]{8, 6, 3, 2, 10, 0});
             p2 = new Polynomial(new double[]{10, 2, 5, -3, 1}, new int[]{10, 8, 9, 6, 1});
             
             //p1 = nullPoly;
             System.out.println("--------------------------");
             System.out.println("> Addition ");
     
             Polynomial.printPoly(p1);
             Polynomial.printPoly(p2);
             System.out.println("ADDING");
     
             Polynomial r1 = p1.add(p2);
     
             Polynomial.printPoly(r1);
             System.out.println("--------------------------");
             System.out.println("> File Handling");
     
             File fil = new File("./line.txt");
             try {Polynomial.printPoly(new Polynomial(fil));} catch (FileNotFoundException f) {}
             
             System.out.println("--------------------------");
             System.out.println("> Zero Polynomial Testing");
     
                 Polynomial zero = new Polynomial();
     
                 System.out.println("Testing Polynomials: ");
                 Polynomial.printPoly(zero);
                 Polynomial.printPoly(p2);
     
                 System.out.println(">>  Multiplication ");
                 r = zero.multiply(p2);
                 Polynomial.printPoly(r);
                 
                 System.out.println();
                 System.out.println(">>  Addition ");
     
                 Polynomial d=new Polynomial(r1.coefficients.clone(), r1.exponents.clone());
                 r1 = zero.add(p2);
                 Polynomial.printPoly(r1);
                 
             System.out.println("--------------------------");
             System.out.println("> Poly.toString() Result:");
     
             System.out.println(r1.toString());
             System.out.println();
             
             System.out.println("--------------------------");
             System.out.println("> Attempting to save to file (\"./Papi.txt\"):");
             d.saveToFile("./Papi.txt");        
     
   } 
}