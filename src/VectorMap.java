import java.util.Map;
import java.util.TreeMap;

public class VectorMap {
    private final int n; // lungimea vectorului
    private TreeMap<Integer, Double> vectorMap;

    public VectorMap(int n) {
        this.n = n;
        this.vectorMap = new TreeMap<Integer, Double>();
    }

    public void put(int index, double value){
        if (index<0 || index>=n) {
            throw new RuntimeException("Index gresit!");
        }
        if (value == 0.0) {
            vectorMap.remove(index);
        }
        else {
            vectorMap.put(index, value);
        }
    }

    public double get(int index){
        if (index<0 || index>=n) {
            throw new RuntimeException("Index gresit!");
        }
        return vectorMap.containsKey(index) ? vectorMap.get(index) : 0.0;
    }

    public int numberOfNonZeroEntries(){
        return vectorMap.size();
    }

    public int size(){
        return n;
    }

    public double product(VectorMap b){
        VectorMap a = this;
        if (a.n != b.n) {
            throw new RuntimeException("Lungimea vectorilor difera!");
        }
        double sum = 0.0;

        if (a.vectorMap.size() <= b.vectorMap.size())
            for (Map.Entry<Integer, Double> entry : a.vectorMap.entrySet()){
                if (b.vectorMap.containsKey(entry.getKey())){
                    sum += entry.getValue()*b.get(entry.getKey());
                }
            }
        else{
            for (Map.Entry<Integer, Double> entry : b.vectorMap.entrySet()){
                if (a.vectorMap.containsKey(entry.getKey())){
                    sum += entry.getValue()*a.get(entry.getKey());
                }
            }
        }
        return sum;
    }

    public VectorMap product(double number){
        VectorMap a = this;
        VectorMap b = new VectorMap(n);
        for (Map.Entry<Integer, Double> entry : a.vectorMap.entrySet()){
            b.put(entry.getKey(), entry.getValue() * number);
        }
        return b;
    }

    public VectorMap sum(VectorMap b){
        VectorMap a = this;
        VectorMap c = new VectorMap(n);
        if (a.n != b.n) {
            throw new RuntimeException("Lungimea vectorilor difera!");
        }
        for (Map.Entry<Integer, Double> entry : a.vectorMap.entrySet()){
            c.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Integer, Double> entry : b.vectorMap.entrySet()){
            c.put(entry.getKey(), entry.getValue() + c.get(entry.getKey()));
        }
        return c;
    }

    @Override
    public String toString(){
        String s="";
        for (Map.Entry<Integer, Double> entry : this.vectorMap.entrySet()){
            s+="("+entry.getKey()+", "+entry.getValue()+") ";
        }
        return s;
    }

    public TreeMap<Integer, Double> getVectorMap() {
        return this.vectorMap;
    }
}
