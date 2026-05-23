# Java_repulok
```
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.TreeSet;

public class Main {
    private class Repulo {
        public String tipus;
        public float hossz;  //méterben
        public int suly;  //kg-ban
        public int ferohely;
        public int uzemanyag;  //literben

        public Repulo(String sor) {
            String[] s = sor.split(";");
            tipus = s[0];
            hossz = Float.parseFloat(s[1]);
            suly = Integer.parseInt(s[2]);
            ferohely = Integer.parseInt(s[3]);
            uzemanyag = Integer.parseInt(s[4]);
        }
    }

    private ArrayList<Repulo> repulok = new ArrayList<>();

    public Main() {
        // --- 0. feladat ---
        betolt("repulok.csv");
        System.out.printf("0) Összesen %d repülő adata beolvasva.\n", repulok.size());

        Random rnd = new Random();
        int randomIndex = rnd.nextInt(repulok.size());
        System.out.printf("   Közülük egy véletlen kiválasztott: %s\n", repulok.get(randomIndex).tipus);

        // --- 1. feladat ---
        Repulo leg = repulok.getFirst();
        for (Repulo r : repulok) {
            if (r.ferohely > leg.ferohely) {
                leg = r;
            }
        }
        Repulo leg2 = null;
        int fer2 = 0;
        for (Repulo r : repulok) {
            if (r.ferohely > fer2 && r.ferohely < leg.ferohely) {
                leg2 = r;
                fer2 = r.ferohely;
            }
        }
        System.out.printf("1) Legtöbb férőhellyel rendelkezik: %s (%d hely)\n", leg.tipus, leg.ferohely);
        System.out.printf("   A második legtöbb férőhely:  %s (%d hely)\n", leg2.tipus, leg2.ferohely);

        // --- 2. feladat ---
        int osszSuly = 0;
        int db = 0;
        for (Repulo r : repulok) {
            if (r.suly < 100000) {
                osszSuly += r.suly;
                db++;
            }
        }
        if (db > 0) {
            double atlag = (double) osszSuly / db;
            System.out.printf("2) A 100000kg súlynál kisebb gépek (%d darab) átlagsúlya: %.2fkg\n", db, atlag);
        } else {
            System.out.println("2) Nincs 100000kg-nál kisebb gép a repulők között.");
        }

        // --- 3. feladat ---
        System.out.print("3) Típusok, amelyikben nincs szám: ");
        ArrayList<String> szamNelkuli = new ArrayList<>();
        for (Repulo r : repulok) {
            if (!r.tipus.matches(".*\\d.*")) {  //matches(".*\\d.*") --> megkeresi hogy van-e benne szám?
                szamNelkuli.add(r.tipus);
            }
        }
        System.out.println(String.join(", ", szamNelkuli));

        // --- 4. feladat ---
        TreeSet<String> gyartok = new TreeSet<>();
        for (Repulo r : repulok) {
            String gyarto = r.tipus.split(" ")[0];
            gyartok.add(gyarto);
        }

        System.out.println("4) Gyártók: " + String.join(", ", gyartok));

        // Mivel a TreeSet nem indexelhető, listává alakítjuk a választáshoz
        ArrayList<String> gyartoLista = new ArrayList<>(gyartok);
        String randomGyarto = gyartoLista.get(rnd.nextInt(gyartoLista.size()));

        System.out.printf("   Közülük egy véletlen kiválasztott: %s\n", randomGyarto);
        System.out.println("   Termékeik:");

        for (Repulo r : repulok) {
            if (r.tipus.startsWith(randomGyarto)) {
                System.out.printf("   - %s\n", r.tipus);
            }
        }

        // --- 5. feladat ---
        PrintWriter ki = null;
        try {
            ki = new PrintWriter(new File("sokutas.txt"), "utf-8");
            for (Repulo repulo : repulok) {
                if (repulo.ferohely > 300) ki.printf("%s / %d hely\r\n", repulo.tipus, repulo.ferohely);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ki != null) ki.close();
        }
        System.out.printf("5) A 300 főnél több férőhelyű gépek adatai a sokutas.txt fájlba mentve.\n");
    }

    private void betolt(String fajlnev) {
        Scanner be = null;
        try {
            be = new Scanner(new File(fajlnev), "utf-8");
            be.nextLine();
            while (be.hasNextLine()) {
                repulok.add(new Repulo(be.nextLine()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (be != null) {
                be.close();
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
```
### repulok.csv
```
Típus;Hossz (m);Súly (kg);Férőhelyek száma;Üzemanyagtank (liter)
Boeing 747;70.6;396890;416;238840
Airbus A380;72.7;560000;555;320000
Boeing 737;39.5;79015;189;26000
Airbus A320;37.6;78000;180;23500
...
```
## Feladat
```
 A repulok.csv fájl repülőgépek adatait tartalmazza, pontosvesszővel elválasztva,
 utf-8 kódolással. VIGYÁZAT, az első sor fejléc!
 Hozzunk létre egy Repulok nevű projektet és oldjuk meg a következő feladatokat!

 0) Olvassuk be a fájl adatait egy megfelelő adatszerkezetbe,
    és jelenítsük meg a beolvasott adatok számát!.....................(2p)
    Írjuk ki egy véletlenszerűen kiválasztott repülő típusát!.........(1p)
 1) Keressük meg és írjuk ki melyik a legtöbb férőhelyes repülő!......(1p)
    Írjuk ki melyik a második a férőhelyek alapján!...................(1p)
 2) Számoljuk ki a 100000kg-nál kisebb gépek átlagsúlyát (2 tized)!...(1p)
 3) Írjuk ki azokat a típusokat, melyekben nincs számjegy!............(2p)
 4) A típus első szava a gyártó. Írjuk ki a gyártók neveit!...........(2p)
    A nevek vesszővel legyenek elválasztva (de az utolsó után NE)!....(1p)
    Véletlenszerűen válasszunk ki egy gyártót,........................(1p)
    és írjuk ki az általuk készített repülők típusait!................(1p)
 5) Írjuk ki a sokutas.txt fájlba azokat a géptípusokat
    és férőhelyüket, ahol 300 főnél többen utazhatnak egyszerre!......(2p)

 Minta:
 0) Összesen 19 repülő adata beolvasva.
    Közülük egy véletlen kiválasztott: Lockheed Martin C-130 Hercules
 1) Legtöbb férőhellyel rendelkezik: Airbus A380 (555 hely)
    A második legtöbb férőhely: Boeing 747 (416 hely)
 2) A 100000kg súlynál kisebb gépek (9 darab) átlagsúlya: 49546,11kg
 3) Típusok, amelyikben nincs szám: Concorde
 4) Gyártók: Airbus, Antonov, Boeing, Bombardier, Concorde, Embraer, Lockheed, McDonnell, Sukhoi
    Közülük egy véletlen kiválasztott: Boeing
    Termékeik:
    - Boeing 747
    - Boeing 737
    - Boeing 787
    - Boeing 777
    - Boeing 757
    - Boeing 767
 5) A 300 főnél több férőhelyű gépek adatai a sokutas.txt fájlba mentve.

 sokutas.txt:
 Boeing 747 / 416 hely
 Airbus A380 / 555 hely
 Boeing 777 / 368 hely
 Airbus A350 / 315 hely
```
