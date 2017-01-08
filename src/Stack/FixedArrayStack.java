package Stack;

/**
 * Created by code on 2016.12.20..
 */
public class FixedArrayStack {
    private int N = 0;
    private String[] s;

    public FixedArrayStack(int size) {
        this.s = new String[size];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(String item) {
        s[N++] = item;
    }

    public String pop() {
        return s[--N];
    }
}
