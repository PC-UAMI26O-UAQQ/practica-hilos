import java.util.Arrays;
import java.util.Random;

public class SumaArreglo {
    static int[] arreglo = null;

    SumaArreglo(int n) {
        arreglo = new int[n];
    }

    public int sumar() {
        int suma = 0;

        for (int i = 0; i < arreglo.length; i++) {
            suma = suma + arreglo[i];
        }

        System.out.println(
            "Thread id: " + Thread.currentThread().getId()
            + " Suma Local: " + suma);

        return suma;
    }

    void inicializarArreglo(int[] arreglo, int n) {
        Random azar = new Random();

        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] = azar.nextInt(n);
        }
    }

    void imprimir(int[] arreglo) {
        System.out.println(Arrays.toString(arreglo));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        SumaArreglo sm = new SumaArreglo(n);
        sm.inicializarArreglo(arreglo, 100);
        sm.imprimir(arreglo);

        int resultado = sm.sumar();
        System.out.println("Suma: " + resultado);
    }
}
