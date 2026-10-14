import java.util.Arrays;
import java.util.Random;

public class SumaArreglo implements Runnable {
    static int hilos = 1;
    static int[] resultado = null;
    static int[] arreglo = null;
    private int id = -1;

    SumaArreglo(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        int suma = 0;
        int tam = arreglo.length / hilos;
        int resto = arreglo.length % hilos;
        int ini = id * tam;
        int fin = ini + tam;

        for (int i = ini; i < fin; i++) {
            suma = suma + arreglo[i];
        }

        if (resto > id) {
            suma = suma + arreglo[(arreglo.length - 1) - id];
        }

        System.out.println(
            "Thread local id " + id
            + " Thread Id: " + Thread.currentThread().getId()
            + " Suma Local: " + suma);

        resultado[id] = suma;
    }

    static void inicializarArreglo(int[] arreglo, int n) {
        Random azar = new Random();

        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] = azar.nextInt(n);
        }
    }

    static void imprimir(int[] arreglo) {
        System.out.println(Arrays.toString(arreglo));
    }

    public static void main(String[] args) {
        int n = 0;
        int sumaFinal = 0;

        try {
            n = Integer.parseInt(args[0]);
            hilos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Error: No es posible convertir a entero");
            System.exit(0);
        }

        arreglo = new int[n];
        Thread[] trabajadores = new Thread[hilos];
        resultado = new int[hilos];

        inicializarArreglo(arreglo, 100);
        imprimir(arreglo);

        for (int i = 0; i < hilos; i++) {
            trabajadores[i] = new Thread(new SumaArreglo(i));
            trabajadores[i].start();
        }

        for (int i = 0; i < hilos; i++) {
            try {
                trabajadores[i].join();
                sumaFinal = sumaFinal + resultado[i];
            } catch (InterruptedException e) {
                System.out.println("Error: en la espera del hilo");
            }
        }

        System.out.println("Suma Runnable: " + sumaFinal);
    }
}
