package org.main;

import org.main.Obec;
import org.main.Abstr.AbstrHeap;
import org.main.enums.ePriorita;
import org.main.enums.eTypProhl;

import java.io.*;
import java.util.Iterator;

public class HeapControl {
   private AbstrHeap<Obec> abstrHeap = new AbstrHeap<>();

   public Obec zpristupniMax(){
       return abstrHeap.zpristupniMax();
   }
    public HeapControl() {
        abstrHeap.reorganizuj(Obec.compareObyvatele());
    }

    public void zrus(){
        abstrHeap.zrus();
    }

    public void reorganizuj(ePriorita p) {
        if (p == ePriorita.NAZEV) {
            abstrHeap.reorganizuj(Obec.compareNazev());
        } else {
            abstrHeap.reorganizuj(Obec.compareObyvatele());
        }
    }


    public void vloz(Obec obec) {

        abstrHeap.vloz(obec);
    }


    public Obec odeberMax() {
        return (Obec) abstrHeap.odeberMax();
    }

    public int importData(String soubor) {
        int pocetObci = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(soubor))) {
            String radek;

            while ((radek = br.readLine()) != null) {

                String[] data = radek.split(";");

                if (data.length == 5) {


                    int PSC = Integer.parseInt(data[0].trim());
                    String nazevObce = data[1].trim();
                    int pocetMuzu = Integer.parseInt(data[2].trim());
                    int pocetZen = Integer.parseInt(data[3].trim());
                    int celkem = Integer.parseInt(data[4].trim());


                    Obec obec = new Obec(PSC, nazevObce, pocetMuzu, pocetZen, celkem);


                    abstrHeap.vloz(obec);
                    pocetObci++;


                }else if(data.length == 7){
                    int i1 = Integer.parseInt(data[0].trim());
                    String s1 = data[1].trim();
                    int PSC = Integer.parseInt(data[2].trim());
                    String nazevObce = data[3].trim();
                    int pocetMuzu = Integer.parseInt(data[4].trim());
                    int pocetZen = Integer.parseInt(data[5].trim());
                    int celkem = Integer.parseInt(data[6].trim());


                    Obec obec = new Obec(PSC, nazevObce, pocetMuzu, pocetZen, celkem);


                    abstrHeap.vloz(obec);
                    pocetObci++;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Soubor nebyl nalezen: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Chyba při čtení souboru: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Chyba ve formátu dat: " + e.getMessage());
        }

        return pocetObci;
    }

    public void exportData(String soubor, eTypProhl typ) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(soubor))) {


            Iterator<Obec> iterator = abstrHeap.vytvorIterator(typ);
            while (iterator.hasNext()) {
                Obec obec = iterator.next();


                String csvRow = String.format("%d;%s;%d;%d;%d",
                        obec.getPSC(),
                        obec.getObec(),
                        obec.getPocetMuzu(),
                        obec.getPocetZen(),
                        obec.getCelkem());

                bw.write(csvRow);
                bw.newLine();
            }


        } catch (IOException e) {


        }

    }

    public Iterator<Obec> VytvorIterator(eTypProhl typ) {
        return abstrHeap.vytvorIterator(typ);
    }
}
