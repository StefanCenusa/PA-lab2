import java.util.Arrays;
import java.util.Map;

/**
 * Created by Stefan & andrei on 26.02.2015.
 */
public class Matrix {
    private final int n, m;
    private VectorMap[] rows = new VectorMap[100];

    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        for (int index=0; index<n; index++){
            rows[index] = new VectorMap(m);
        }
    }

    public void put(int row, int col, double value){
        if (row < 0 || row >= n) {
            throw new RuntimeException("Index randuri gresit!");
        }
        if (col < 0 || col >= m) {
            throw new RuntimeException("Index coloane gresit!");
        }
        rows[row].put(col,value);
    }

    public double get(int row, int col){
        if (row < 0 || row >= n) {
            throw new RuntimeException("Index randuri gresit!");
        }
        if (col < 0 || col >= m) {
            throw new RuntimeException("Index coloane gresit!");
        }
        return rows[row].get(col);
    }

    public int numberOfNonZeroEntries(){
        int count=0;
        for (int i=0; i < n; i++){
            count+=rows[i].numberOfNonZeroEntries();
        }
        return count;
    }

    public Matrix product(Matrix x){
        if (this.getM() != x.getN()) {
            throw new RuntimeException("Inmultimrea matricelor nu poate fi realizata!");
        }

        Matrix result = new Matrix(this.n,x.m);
        Matrix xTransposed = x.transpose();

        for (int i=0; i<this.getN(); i++){
            for (int j=0; j<xTransposed.getN();j++) {
                double sum = this.getRows()[i].product(xTransposed.getRows()[j]);
                if (sum != 0) {
                    result.put(i, j, sum);
                }
            }
        }
        return result;
    }

    public Matrix product (double number){
        Matrix b = new Matrix(this.getN(),this.getM());
        for (int i=0; i<b.getN(); i++){
            b.getRows()[i]=this.getRows()[i].product(number);
        }
        return b;
    }

    public Matrix sum(Matrix b){
        if ((this.getN() != b.getN()) || (this.getM() != b.getM())){
            throw new RuntimeException("Matrici de dimensiuni diferite!");
        }
        Matrix result = new Matrix(this.getN(),this.getM());
        for (int i=0; i<this.getN(); i++){
            result.getRows()[i]=this.getRows()[i].sum(b.getRows()[i]);
        }
        return result;
    }

    public Matrix transpose(){
        Matrix transposedMatrix= new Matrix(this.getM(),this.getN());
        for (int i=0; i<this.getN(); i++){
            for (Map.Entry<Integer, Double> entry : this.getRows()[i].getVectorMap().entrySet()){
                transposedMatrix.put(entry.getKey(),i,entry.getValue());
            }
        }
        return transposedMatrix;
    }

    public VectorMap[] getRows() {
        return rows;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    @Override
    public String toString() {
        String s="";
        for(int i=0; i<this.getN(); i++){
            s+= i + ": " + this.getRows()[i] + '\n';
        }
        return s;
    }

    public static void main(String[] args) {
        Matrix A = new Matrix(2,3);
        A.put(0, 0, 1.0);
        A.put(0, 1, 2.0);
        A.put(0, 2, 3.0);
        A.put(1, 0, 4.0);
        A.put(1, 1, 5.0);
        A.put(1, 2, 6.0);

        Matrix B = new Matrix(3,2);
        B.put(0,0,7);
        B.put(0,1,8);
        B.put(1,0,9);
        B.put(1,1,10);
        B.put(2,0,11);
        B.put(2,1,12);

        System.out.println("A:\n" + A);
        System.out.println("At:\n" + A.transpose());
        System.out.println("A + A:\n: " + A.sum(A));
        System.out.println("AxB:\n" + A.product(B));
    }
}
