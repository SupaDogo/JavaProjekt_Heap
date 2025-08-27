package org.main.Abstr;

import org.main.Interface.IAbstrTable;
import org.main.enums.eTypProhl;

import java.util.Iterator;

public class AbstrTable <K extends Comparable<K>, V> implements IAbstrTable<K,V>{
    Prvek<K,V> root;
    
    @Override
    public void zrus() {
        root = null;
    }

    @Override
    public boolean jePrazdny() {
        return root == null;
    }

    @Override
    public V najdi(K key) {
        Prvek<K, V> aktualni = root;
        while (aktualni != null) {
            int porovnany = key.compareTo(aktualni.getKey());
            if (porovnany == 0){
                return aktualni.getData();
            }
            else if (porovnany < 0) {
                aktualni = aktualni.getPrvekVlevo();
            }else{
             aktualni = aktualni.getPrvekVpravo();
            }
        }
        return null;
    }

    @Override
    public void vloz(K key, V value) {
        Prvek<K, V> novy = new Prvek<>(key, value);
        if (jePrazdny()) {
        root = novy;
        return;
        }
        Prvek<K, V> aktualni = root;
        while(aktualni != null) {
            int porovnany = key.compareTo(aktualni.getKey());
            if (porovnany == 0) {
                System.out.println("Takovy klič už existuje");
                return;
            }
            if (porovnany<0){
                if (aktualni.getPrvekVlevo() == null){
                    aktualni.setPrvekVlevo(novy);
                    return;
                }
                aktualni = aktualni.getPrvekVlevo();
            }else{
                if (aktualni.getPrvekVpravo() == null){
                    aktualni.setPrvekVpravo(novy);
                    return;
                }
                aktualni = aktualni.getPrvekVpravo();
            }
        }
    }

    @Override
    public V odeber(K key) {
        if (jePrazdny()) {
            return null;
        }
        Prvek<K, V> removed = null;
        if((root.getPrvekVlevo() == null && root.getPrvekVpravo() == null) && key.compareTo(root.getKey()) == 0){
            removed = root;
            zrus();
            return removed.getData();
        }
        Prvek<K, V> aktualni = root;
        Prvek<K, V> parent = null;

        while (aktualni != null) {
            int porovnany = key.compareTo(aktualni.getKey());
            if (porovnany == 0){

                removed = new Prvek<>(aktualni.getKey(), aktualni.getData());
                System.out.println(removed.getKey());
                break;
            }
            parent = aktualni;
            if (porovnany < 0) {
                aktualni = aktualni.getPrvekVlevo();
            }else{
                aktualni = aktualni.getPrvekVpravo();
            }

        }



        if(aktualni.getPrvekVlevo() != null && aktualni.getPrvekVpravo() != null){
            Prvek<K, V> succParent = aktualni;
            Prvek<K, V> succChild = aktualni.getPrvekVpravo();

            while(succChild.getPrvekVlevo() != null ){
                succParent = succChild;
                succChild = succParent.getPrvekVlevo();
            }

            aktualni.setKey(succChild.getKey());
            aktualni.setData(succChild.getData());

            aktualni = succChild;
            parent = succParent;

        }

        Prvek<K, V> child;
        if(aktualni.getPrvekVlevo() != null){
            child = aktualni.getPrvekVlevo();
        }else {
            child = aktualni.getPrvekVpravo();
        }

        if (parent == null) {
            root = child;
        }
        if (parent.getPrvekVlevo()== aktualni){
            parent.setPrvekVlevo(child);
        }else {
            parent.setPrvekVpravo(child);
        }


        return removed.getData();
    }

    @Override
    public Iterator vytvorIterator(eTypProhl typ) {
        if (typ == eTypProhl.SIRKY) {
            return iteratorDoSirky();
        } else if (typ == eTypProhl.HLOUBKY) {
            return iteratorDoHloubky();
        } else {
            return null;
        }
    }

    private Iterator<V> iteratorDoSirky() {
        AbstrFIFO<Prvek<K, V>> fronta = new AbstrFIFO<>();
        AbstrDoubleList<V> listVysledku = new AbstrDoubleList<>();

        if (root != null) {
            fronta.vloz(root);
        }

        while (!fronta.jePrazdny()) {
            Prvek<K, V> aktualni = fronta.odeber();
            listVysledku.vlozPosledni(aktualni.getData());

            if (aktualni.getPrvekVlevo() != null) {
                fronta.vloz(aktualni.getPrvekVlevo());
            }
            if (aktualni.getPrvekVpravo() != null) {
                fronta.vloz(aktualni.getPrvekVpravo());
            }
        }

        return listVysledku.iterator();
    }


    private Iterator<V> iteratorDoHloubky() {
        AbstrLIFO<Prvek<K, V>> zasobnik = new AbstrLIFO<>();
        AbstrDoubleList<V> resultList = new AbstrDoubleList<>();
        Prvek<K, V> aktualni = root;


        while (aktualni != null || !zasobnik.jePrazdny()) {

            while (aktualni != null) {
                zasobnik.vloz(aktualni);
                aktualni = aktualni.getPrvekVlevo();
            }

            aktualni = zasobnik.odeber();
            resultList.vlozPosledni(aktualni.getData());

            aktualni = aktualni.getPrvekVpravo();
        }

        return resultList.iterator();
    }


    private class Prvek<K, V>{
        private K key;
        private V data;
        private Prvek<K, V> prvekVlevo = null;
        private Prvek<K,V> prvekVpravo = null;

        public Prvek(K key, V data) {
            this.key = key;
            this.data = data;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getData() {
            return data;
        }

        public void setData(V data) {
            this.data = data;
        }

        public Prvek<K, V> getPrvekVlevo() {
            return prvekVlevo;
        }

        public void setPrvekVlevo(Prvek<K, V> prvekVlevo) {
            this.prvekVlevo = prvekVlevo;
        }

        public Prvek<K, V> getPrvekVpravo() {
            return prvekVpravo;
        }

        public void setPrvekVpravo(Prvek<K, V> prvekVpravo) {
            this.prvekVpravo = prvekVpravo;
        }
    }
}
