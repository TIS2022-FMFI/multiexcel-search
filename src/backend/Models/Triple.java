package backend.Models;

public class Triple<T, U, V> {

    public T first;
    public U second;
    public V third;

    public Triple(T first, U second, V third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }
}
