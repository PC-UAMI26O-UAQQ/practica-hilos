import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Arrays;
import java.util.Random;

public class SumaArreglo implements Callable<Integer> {
    static int hilos = 1;
    static int[] arreglo = null;
    private int id = -1;

    SumaArreglo(int id) {
        this.id = id;
    }

    @Override
    public Integer call() throws Exception {
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

        return suma;
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
        int sumaFinal = 0;
        int n = 0;

        try {
            n = Integer.parseInt(args[0]);
            hilos = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Error: No es posible convertir a entero");
            System.exit(0);
        }

        arreglo = new int[n];
        inicializarArreglo(arreglo, 100);
        imprimir(arreglo);

        LinkedList<Future<Integer>> valores = new LinkedList<>();
        ExecutorService pool = Executors.newFixedThreadPool(hilos);

        for (int i = 0; i < hilos; i++) {
            Callable<Integer> tarea = new SumaArreglo(i);
            Future<Integer> future = pool.submit(tarea);
            valores.add(future);
        }

        for (Future<Integer> future : valores) {
            try {
                sumaFinal += future.get();
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error: al obtener el dato del hilo");
            }
        }

        System.out.println("La suma Callable es: " + sumaFinal);
        pool.shutdown();
    }
}
