package org.main.Interface;

import java.util.Iterator;

public interface IAbstrFIFO<T> {
    void zrus(); //zrušení celé fronty/zásobníku
    boolean jePrazdny();// test prázdnosti
    void vloz(T data);// vloží prvek do zásobníku/fronty
    T odeber();// odebere prvek ze zásobníku/fronty
    Iterator vytvorIterator();// vrací iterátor zásobníku/fronty
}
