public class NReinas {

    public static boolean esComida(int[][] tablero, int r, int c) {
        int auxR;
        int auxC;

        auxR = r - 1;
        auxC = c;
        while (auxR >= 0) {
            if (tablero[auxR][auxC] == 1) {
                return true;
            }
            auxR--;
        }

        auxR = r - 1;
        auxC = c + 1;
        while (auxR >= 0 && auxC < tablero.length) {
            if (tablero[auxR][auxC] == 1) {
                return true;
            }
            auxR--;
            auxC++;
        }

        auxR = r - 1;
        auxC = c - 1;
        while (auxR >= 0 && auxC >= 0) {
            if (tablero[auxR][auxC] == 1) {
                return true;
            }
            auxR--;
            auxC--;
        }

        return false;
    }

    public static long backtrackReinas(int[][] tablero, int r) {
        if (r == tablero.length) {
            return 1;
        }

        long soluciones = 0;

        for (int c = 0; c < tablero.length; c++) {
            if (!esComida(tablero, r, c)) {
                tablero[r][c] = 1;
                soluciones += backtrackReinas(tablero, r + 1);
                tablero[r][c] = 0;
            }
        }

        return soluciones;
    }

    static class NReinasHilo extends Thread {
        private int id;
        private int hilos;
        private int N;
        private long[] resultados;

        NReinasHilo(int id, int hilos, int N, long[] resultados) {
            this.id = id;
            this.hilos = hilos;
            this.N = N;
            this.resultados = resultados;
        }

        @Override
        public void run() {
            int tam = N / hilos;
            int resto = N % hilos;
            int inicio = id * tam + Math.min(id, resto);
            int cantidad = tam + (id < resto ? 1 : 0);
            int fin = inicio + cantidad;

            long solucionesLocales = 0;

            for (int c = inicio; c < fin; c++) {
                int[][] tablero = new int[N][N];
                tablero[0][c] = 1;
                solucionesLocales += backtrackReinas(tablero, 1);
            }

            System.out.println(
                "Hilo " + id + " columnas [" + inicio + ", " + fin + ")"
                + " soluciones: " + solucionesLocales);

            resultados[id] = solucionesLocales;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.out.println("Uso: java NReinas N hilos");
            System.exit(0);
        }

        int N = Integer.parseInt(args[0]);
        int hilos = Integer.parseInt(args[1]);

        Thread[] trabajadores = new Thread[hilos];
        long[] resultados = new long[hilos];

        for (int i = 0; i < hilos; i++) {
            trabajadores[i] = new NReinasHilo(i, hilos, N, resultados);
            trabajadores[i].start();
        }

        long total = 0;
        for (int i = 0; i < hilos; i++) {
            trabajadores[i].join();
            total += resultados[i];
        }

        System.out.println("N = " + N + ", hilos = " + hilos);
        System.out.println("Total de soluciones: " + total);
    }
}
