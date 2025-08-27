package org.main.Interface;

import org.main.Abstr.AbstrDoubleList;
import org.main.Obec;
import org.main.enums.ePriorita;

import java.util.Comparator;

public interface IAbstrHeap<E> {
    E zpristupniMax();
    E odeberMax();
    void vloz(E obec);
    boolean jePrazdny();
    void zrus();
    void vybuduj();
    void reorganizuj(Comparator<E> comparator);

}
