package Exceptions;

public class ReculDeNcases extends Exception{
    int n;
    public ReculDeNcases(int N){
        n=N;
    }

    public int getN() {
        return n;
    }
}
