package core.threads;
/*
       Slowko kluczowe `volatile` sluzy do modyfikowania widzialnosci zmiennej wspoldzielonej przez inne watki.
       W zwyczajnej sytuacji zmienna wspodzielona przez kilka watkow jest przez procesor optymalizowana - tj. moze
       znajdowac sie w pamieci cashe tego procesora. Fakt ten spraiwa, ze watki moga miec nieaktualna wartosc tej zmiennej,
       gdyz watki nie maja dostepu do pamieci cashe a do pamieci glownej (np. RAM).
        */

public class T4_Volatile {

    public void start() {
        for (int i = 0; i < 10000; i++)
            new ValatileChecker().start();

    }


    static class ValatileChecker {
        private static int number;
        private static boolean ready;

        private static class Reader extends Thread {

            @Override
            public void run() {
                while (!ready) {
                    Thread.yield();
                }
                System.out.println(number);
            }
        }


        public void start() {
            new Reader().start();
            number = 42;
            ready = true;
        }
    }

}
