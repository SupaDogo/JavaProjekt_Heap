package org.main.Abstr;

import org.main.Interface.IAbstrHeap;
import org.main.enums.ePriorita;
import org.main.enums.eTypProhl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class AbstrHeap<E> implements IAbstrHeap<E> {

    private ArrayList<E> arrayList = new ArrayList<>();

    private Comparator<E> comparator;


    @Override
    public E zpristupniMax() {
        return arrayList.get(1);
    }

    @Override
    public E odeberMax() {
        if(jePrazdny()){
            return null;
        }
        E temp = arrayList.get(1);
        if(arrayList.size()-1==1 ){
            zrus();
        }else{
            arrayList.set(1, arrayList.remove(arrayList.size()-1));
            heapDown(1);
        }
        return temp;
    }

    private void heapDown(int i) {
        int velikost = arrayList.size()-1;

        while(i*2<=velikost){
            int levy = i*2;
            int pravy = i*2+1;
            int nejvetsi = i;

            if(levy<=velikost && comparator.compare(arrayList.get(levy), arrayList.get(nejvetsi)) > 0){
                nejvetsi = levy;
            }

            if(pravy<=velikost && comparator.compare(arrayList.get(pravy), arrayList.get(nejvetsi)) > 0){
                nejvetsi = pravy;
            }

            if(nejvetsi!=i){

                E temp = arrayList.get(i);
                arrayList.set(i, arrayList.get(nejvetsi));
                arrayList.set(nejvetsi, temp);

                i=nejvetsi;
            }else {
                break;
            }

        }

    }

    @Override
    public void vloz(E obec) {
        if (jePrazdny()) {
            arrayList.add(0,null);
            arrayList.add(1, obec);
            return;
        }
        arrayList.add(obec);
        int i = arrayList.size() - 1;

        while (i > 1) {
            int parentI = i / 2;
            E parent = arrayList.get(parentI);
            E novy = arrayList.get(i);

            if (comparator.compare(novy, parent) < 0) {
                break;
            } else{
                arrayList.set(i, parent);
                arrayList.set(parentI, novy);

                i = parentI;
            }
        }


    }

    @Override
    public boolean jePrazdny() {
        return arrayList.isEmpty();
    }

    @Override
    public void zrus() {
        arrayList.clear();
    }

    @Override
    public void vybuduj() {
        int i = (arrayList.size()-1)/2;
        for (;i>=1;i--){
            heapDown(i);
        }
    }


    @Override
    public void reorganizuj(Comparator<E> comparator) {
        this.comparator = comparator;
        vybuduj();
    }

    public Iterator<E> vytvorIterator(eTypProhl typ) {
        return new Iterator<E>() {
            AbstrFIFO<E> fronta = new AbstrFIFO<>();
            AbstrLIFO<E> zasobnik = new AbstrLIFO<>();
            private E root;
            {
                if(!jePrazdny()){
                    root = arrayList.get(1);
                }
                if(root!=null) {
                    if (typ == eTypProhl.SIRKY) {
                        fronta.vloz(root);
                    } else if (typ == eTypProhl.HLOUBKY) {
                        zasobnik.vloz(root);
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return (!fronta.jePrazdny()||!zasobnik.jePrazdny());
            }

            @Override
            public E next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }

                E aktualni = null;

                if(typ==eTypProhl.HLOUBKY){
                    aktualni = zasobnik.odeber();

                    int i = arrayList.indexOf(aktualni);

                    int pravy = i*2+1;
                    if(pravy<arrayList.size()&&arrayList.get(pravy)!=null){
                        zasobnik.vloz(arrayList.get(pravy));
                    }

                    int levy = i*2;
                    if(levy<arrayList.size()&&arrayList.get(levy)!=null){
                        zasobnik.vloz(arrayList.get(levy));
                    }


                }else if (typ==eTypProhl.SIRKY) {
                    aktualni = fronta.odeber();

                    int i = arrayList.indexOf(aktualni);

                    int levy = i*2;
                    if(levy<arrayList.size()&&arrayList.get(levy)!=null){
                        fronta.vloz(arrayList.get(levy));
                    }

                    int pravy = i*2+1;
                    if(pravy<arrayList.size()&&arrayList.get(pravy)!=null){
                        fronta.vloz(arrayList.get(pravy));
                    }
                }
                return aktualni;
            }




        };




    }





}
