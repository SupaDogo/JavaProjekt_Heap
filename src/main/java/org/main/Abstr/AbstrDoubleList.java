package org.main.Abstr;

import org.main.Interface.IAbstrDoubleList;

import java.util.Iterator;

public class AbstrDoubleList<T> implements IAbstrDoubleList<T> {


    private Prvek<T> prvni;
    private Prvek<T> aktualni;
    private int velikost;

    public AbstrDoubleList() {
        prvni = null;
        aktualni = null;
        velikost = 0;
    }

    public int getVelikost() {
        return velikost;
    }

    @Override
    public void zrus() {

        velikost = 0;
        prvni = null;
        aktualni = null;


    }

    @Override
    public boolean jePrazdny() {
        return this.velikost == 0;
    }

    @Override
    public void vlozPrvni(T data) {
            if (data == null) {
                throw new IllegalArgumentException();
            }
            Prvek prvekNovy = new Prvek(data);
            if (jePrazdny()) {
                prvekNovy.setPredchozi(prvekNovy);
                prvekNovy.setNasledujici(prvekNovy);
                prvni = prvekNovy;
                aktualni = prvekNovy;
                velikost++;
            } else {

                prvekNovy.setNasledujici(prvni);
                prvekNovy.setPredchozi(prvni.getPredchozi());
                prvni.setPredchozi(prvekNovy);
                prvekNovy.getPredchozi().setNasledujici(prvekNovy); //posledni prvek vidi prvni prvek
                prvni = prvekNovy;
                velikost++;

            }









    }

    @Override
    public void vlozPosledni(T data) {
        if (data != null) {
            Prvek prvekNovy = new Prvek(data);
            if (jePrazdny()) {
                vlozPrvni(data);
            } else {
                prvekNovy.setPredchozi(prvni.getPredchozi()); //set predchozi noveho prvku(posledni)
                prvni.getPredchozi().setNasledujici(prvekNovy); //set novy jako nasledujici stareho posledniho
                prvni.setPredchozi(prvekNovy);
                prvekNovy.setNasledujici(prvni); //set nasledujici prvniho prvku
                velikost++;

            }
        }
    }

    @Override
    public void vlozNaslednika(T data) {
        if (data != null) {
            Prvek prvekNovy = new Prvek(data);

            if (aktualni != null) {
                prvekNovy.setPredchozi(aktualni);
                prvekNovy.setNasledujici(aktualni.getNasledujici());
                aktualni.getNasledujici().setPredchozi(prvekNovy);
                aktualni.setNasledujici(prvekNovy);
                velikost++;
            }
        }


    }

    @Override
    public void vlozPredchudce(T data) {
        if (data != null) {
            Prvek prvekNovy = new Prvek(data);

            if (aktualni != null) {
                prvekNovy.setPredchozi(aktualni.getPredchozi());
                prvekNovy.setNasledujici(aktualni);
                aktualni.getPredchozi().setNasledujici(prvekNovy);
                aktualni.setPredchozi(prvekNovy);
                velikost++;
            }
        }
    }

    @Override
    public T zpristupniAktualni() {
        if (aktualni == null) {
            return null;
        }
        return aktualni.getData();
    }

    @Override
    public T zpristupniPrvni() {

        if (jePrazdny()) {
            return null;
        }

        aktualni = prvni;
        return aktualni.getData();
    }

    @Override
    public T zpristupniPosledni() {
        if (jePrazdny()) {
            return null;
        }


        aktualni = prvni.getPredchozi();
        return aktualni.getData();
    }

    @Override
    public T zpristupniNaslednika() {
        if (aktualni == null) {
            return null;

        }

        aktualni = aktualni.getNasledujici();
        return aktualni.getData();
    }

    @Override
    public T zpristupniPredchudce() {
        if (aktualni == null) {
            return null;
        }
        aktualni = aktualni.getPredchozi();
        return aktualni.getData();

    }

    @Override
    public T odeberAktualni() {

        if (aktualni == null) {
            return null;
        }


        Prvek <T> tempPrvek = aktualni;
        if (velikost == 1) {
            zrus();
            return tempPrvek.getData();
        }
        aktualni.getPredchozi().setNasledujici(aktualni.getNasledujici());
        aktualni.getNasledujici().setPredchozi(aktualni.getPredchozi());
        if (aktualni == prvni) {
            prvni = aktualni.getNasledujici();
        }
        aktualni = aktualni.getNasledujici();
        velikost--;
        if (velikost == 1) {
            aktualni = prvni;
        }
        return tempPrvek.getData();

    }

    @Override
    public T odeberPrvni() {

        if (jePrazdny()) {
            return null;
        }
        Prvek <T> tempPrvek = prvni;
        if (velikost == 1) {
            zrus();
            return tempPrvek.getData();
        }
        prvni.getPredchozi().setNasledujici(prvni.getNasledujici());//nastavi naslednujici prvek predchoziho prvku
        prvni.getNasledujici().setPredchozi(prvni.getPredchozi()); // nastavi predchozi prvek nasledujicimu prvku
        if (aktualni == prvni) {
            aktualni = tempPrvek.getNasledujici();
        }
        prvni = tempPrvek.getNasledujici();
        velikost--;

        return tempPrvek.getData();


    }

    @Override
    public T odeberPosledni() {

        if (jePrazdny()) {
            return null;
        }

        Prvek <T> tempPrvek = prvni.getPredchozi();
        if (velikost == 1) {
            zrus();
            return tempPrvek.getData();
        }

        if (aktualni == tempPrvek) {
            aktualni = tempPrvek.getNasledujici();
        }
        tempPrvek.getPredchozi().setNasledujici(tempPrvek.getNasledujici());//nastavi prvni jako nasledujici prvek predchoziho prvku
        tempPrvek.getNasledujici().setPredchozi(tempPrvek.getPredchozi());//nastavi predchozi prvek jako nasledujici prvniho prvku

        velikost--;
        if (velikost == 1) {
            aktualni = prvni;
        }
        return tempPrvek.getData();
    }

    @Override
    public T odeberNaslednika() {

        if (jePrazdny()|| aktualni == null) {
            return null;
        }
        Prvek <T> tempPrvek = aktualni.getNasledujici();
        if (velikost == 1) {
            zrus();
            return tempPrvek.getData();
        }
        tempPrvek.getNasledujici().setPredchozi(tempPrvek.getPredchozi());
        tempPrvek.getPredchozi().setNasledujici(tempPrvek.getNasledujici());
        if (prvni == tempPrvek) {
            prvni = tempPrvek.getNasledujici();
        }
        velikost--;
        if (velikost == 1) {
            aktualni = prvni;
        }
        return tempPrvek.getData();
    }

    @Override
    public T odeberPredchudce() {

        if (jePrazdny()|| aktualni == null) {
            return null;
        }
        Prvek <T> tempPrvek = aktualni.getPredchozi();
        if (velikost == 1) {
            zrus();
            return tempPrvek.getData();
        }
        tempPrvek.getPredchozi().setNasledujici(aktualni);
        aktualni.setPredchozi(tempPrvek.getPredchozi());
        if (prvni == tempPrvek) {
            prvni = aktualni;
        }
        velikost--;
        if (velikost == 1) {
            aktualni = prvni;
        }

        return tempPrvek.getData();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Prvek<T> aktualni = prvni;
            private boolean firstIterated = false;

            @Override
            public boolean hasNext() {

                if (aktualni == null) {
                    return false;
                }

                if (!firstIterated) {
                    return true;
                }

                return aktualni != prvni;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                T data = aktualni.getData();
                aktualni = aktualni.getNasledujici();
                firstIterated = true;
                return data;
            }

        };
    }

    private class Prvek<T> {
        private T data;
        private Prvek predchozi;
        private Prvek nasledujici;

        public Prvek(T data) {
            this.data = data;
        }

        private T getData() {
            return data;
        }

        public Prvek getPredchozi() {
            return predchozi;
        }

        public void setPredchozi(Prvek predchozi) {
            this.predchozi = predchozi;
        }

        public Prvek getNasledujici() {
            return nasledujici;
        }

        public void setNasledujici(Prvek nasledujici) {
            this.nasledujici = nasledujici;
        }
    }
}

