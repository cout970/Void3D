package com.cout970.gl.util;

/**
 * Created by cout970 on 05/05/2016.
 */
public class Pair<T, E> {

    private T first;
    private E second;

    public Pair(T first, E second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public E getSecond() {
        return second;
    }

    public void setSecond(E second) {
        this.second = second;
    }
}
