package org.main.Abstr;

import org.main.Interface.IAbstrLIFO;

import java.util.Iterator;

public class AbstrLIFO<T> implements IAbstrLIFO<T> {
    private AbstrDoubleList<T> list;

    public AbstrLIFO() {
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
        return list.odeberPrvni();
    }

    @Override
    public Iterator vytvorIterator() {
        return list.iterator();
    }
}
