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