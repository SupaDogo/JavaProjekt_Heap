package org.main;

import java.util.Comparator;

public class Obec {
    private int PSC;
    private String Obec;
    private int pocetMuzu;
    private int pocetZen;
    private int celkem;


    public Obec(int PSC, String obec, int pocetMuzu, int pocetZen, int celkem) {
        this.PSC = PSC;
        Obec = obec;
        this.pocetMuzu = pocetMuzu;
        this.pocetZen = pocetZen;
        this.celkem = celkem;
    }

    public int getPSC() {
        return PSC;
    }

    public String getObec() {
        return Obec;
    }

    public int getPocetMuzu() {
        return pocetMuzu;
    }

    public int getPocetZen() {
        return pocetZen;
    }

    public int getCelkem() {
        return celkem;
    }

    @Override
    public String toString() {
        return
                "PSC=" + PSC +
                        ", Obec='" + Obec + '\'' +
                        ", pocetMuzu=" + pocetMuzu +
                        ", pocetZen=" + pocetZen +
                        ", celkem=" + celkem
                ;
    }

    public static Comparator<Obec> compareNazev(){
        return Comparator.comparing(org.main.Obec::getObec);
    }

    public static Comparator<Obec> compareObyvatele(){
        return Comparator.comparingInt(org.main.Obec::getCelkem);
    }
}

