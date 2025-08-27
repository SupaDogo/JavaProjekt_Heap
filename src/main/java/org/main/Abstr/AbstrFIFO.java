package org.main.Abstr;

import org.main.Interface.IAbstrFIFO;

import java.util.Iterator;

public class AbstrFIFO<T> implements IAbstrFIFO<T> {
    private AbstrDoubleList<T> list;

    public AbstrFIFO() {
        this.list = new AbstrDoubleList<>();
    }
    @Override
    public void zrus() {
    list.zrus();
    }

    @Override
    public boolean jePrazdny() {
        return list.jePrazdny();
    }

    @Override
    public void vloz(T data) {
    list.vlozPrvni(data);
    }

    @Override
    public T odeber() {
        return list.odeberPosledni();
    }

    @Override
    public Iterator vytvorIterator() {
        return list.iterator();
    }
}
