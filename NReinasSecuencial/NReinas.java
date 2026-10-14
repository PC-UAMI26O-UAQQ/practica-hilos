public class NReinas {

    public static boolean esComida(int[][] tablero, int r, int c) {
        int auxR;
        int auxC;

        // Misma columna
        auxR = r - 1;
        auxC = c;
        while (auxR >= 0) {
            if (tablero[auxR][auxC] == 1) {
                return true;
            }
            auxR--;
        }

        // Diagonal superior derecha
        auxR = r - 1;
        auxC = c + 1;
        while (auxR >= 0 && auxC < tablero.length) {
            if (tablero[auxR][auxC] == 1) {
                return true;
            }
            auxR--;
            auxC++;
        }

        // Diagonal superior izquierda
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

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Uso: java NReinas N");
            System.exit(0);
        }

        int n = Integer.parseInt(args[0]);
        int[][] tablero = new int[n][n];

        long soluciones = backtrackReinas(tablero, 0);

        System.out.println("N = " + n);
        System.out.println("Total de soluciones: " + soluciones);
    }
}
