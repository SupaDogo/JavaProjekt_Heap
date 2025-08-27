package org.main.Interface;

import org.main.Obec;
import org.main.enums.eTypProhl;

import java.util.Iterator;

public interface IAgendaKraj {
    Obec najdi(String key);
    void vloz(String key, Obec obec);
    Obec odeber(String key);
    void vybuduj();
    Iterator VytvorIterator(eTypProhl typ);
    int importData(String soubor);
    void exportData(String soubor, eTypProhl typ);
    void zrus();
}
