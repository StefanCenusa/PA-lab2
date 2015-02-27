import java.util.Map;

/**
 * @author Stefan&Andrei
 */
public class Matrix {
    private final int n, m;
    private VectorMap[] rows = new VectorMap[100];

    /**
     * @param n nr de linii
     * @param m nr de coloane
     */
    public Matrix(int n, int m) {
        this.n = n;
        this.m = m;
        for (int index=0; index<n; index++){
            rows[index] = new VectorMap(m);
        }
    }

    /**
     * @param n nr linii
     * @param m nr coloane
     * @param matrice elementele matricii intr-un tablou bidimensional
     */
    public Matrix(int n, int m, double[][] matrice){
        this.n=n;
        this.m=m;
        for (int index=0; index<n; index++){
            rows[index] = new VectorMap(m);
            for (int col=0; col<m; col++){
                if (matrice[index][col] !=0) {
                    rows[index].put(col, matrice[index][col]);
                }
            }
        }
    }

    /**
     * @param n nr linii
     * @param m nr coloane
     * @param matrice elementele matricii intr-un tablou unidimensional
     */
    public Matrix(int n, int m, double[] matrice){
        this.n=n;
        this.m=m;
        for (int index=0; index<n; index++){
            rows[index] = new VectorMap(m);
            for (int col=0; col<m; col++){
                if (matrice[index*col+col] !=0) {
                    rows[index].put(col, matrice[index*col+col]);
                }
            }
        }
    }

    /**
     * @param row rand
     * @param col coloana
     * @param value valoare
     */
    public void put(int row, int col, double value){
        if (row < 0 || row >= n) {
            throw new RuntimeException("Index randuri gresit!");
        }
        if (col < 0 || col >= m) {
            throw new RuntimeException("Index coloane gresit!");
        }
        rows[row].put(col,value);
    }

    /**
     * @param row rand
     * @param col coloana
     * @return m[row][col]
     */
    public double get(int row, int col){
        if (row < 0 || row >= n) {
            throw new RuntimeException("Index randuri gresit!");
        }
        if (col < 0 || col >= m) {
            throw new RuntimeException("Index coloane gresit!");
        }
        return rows[row].get(col);
    }

    /**
     * @return numarul de elemente non-zero
     */
    public int numberOfNonZeroEntries(){
        int count=0;
        for (int i=0; i < n; i++){
            count+=rows[i].numberOfNonZeroEntries();
        }
        return count;
    }

    /**
     * @param x matricea deinmultit
     * @return produsul dintre doua matrici
     */
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

    /**
     * @param number scalarul
     * @return produsul dintre o matrice si un scalar
     */
    public Matrix product (double number){
        Matrix b = new Matrix(this.getN(),this.getM());
        for (int i=0; i<b.getN(); i++){
            b.getRows()[i]=this.getRows()[i].product(number);
        }
        return b;
    }

    /**
     * @param b matricea de adunat
     * @return suma dintre 2 matrici
     */
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

    /**
     * @return transpusa unei matrici
     */
    public Matrix transpose(){
        Matrix transposedMatrix= new Matrix(this.getM(),this.getN());
        for (int i=0; i<this.getN(); i++){
            for (Map.Entry<Integer, Double> entry : this.getRows()[i].getVectorMap().entrySet()){
                transposedMatrix.put(entry.getKey(),i,entry.getValue());
            }
        }
        return transposedMatrix;
    }

    /**
     * @return vectorul de randuri
     */
    public VectorMap[] getRows() {
        return rows;
    }

    /**
     * @return nr de randuri
     */
    public int getN() {
        return n;
    }

    /**
     * @return nr de coloane
     */
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
