package pr2;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.*;


public class ArrayCalculator {
    private static int ARRAY_SIZE = 40;
    private static final int PART_SIZE = 10;

    private static int MIN_RANGE = 0;
    private static int MAX_RANGE = 1000;

    public static void main(String[] args) {
        menu();

        int[] numbers = generateRandomArray();
        Set<Double> averages = new CopyOnWriteArraySet<>();
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<Double>> futures = new ArrayList<>();

        for (int i = 0; i < numbers.length; i += PART_SIZE) {
            int start = i;
            int end = Math.min(i + PART_SIZE, numbers.length);

            Future<Double> future = executor.submit(new ArrayProcessor(numbers, start, end));
            futures.add(future);
        }

        for (Future<Double> future : futures) {
            try {
                if (!future.isCancelled()) {

                    while (!future.isDone()) {
                        System.out.println("Check out the calculation results...");
                        Thread.sleep(200);
                    }

                    averages.add(future.get());
                }
                else
                    System.err.println("Canceling a calculation");

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        double avgResult = 0;
        for (Double avg : averages) {
            avgResult += avg;
        }
        avgResult /= ARRAY_SIZE;

        long endTime = System.currentTimeMillis();

        System.out.println("\nArray processing results:");
        System.out.println("\tProgram running time - " + (endTime - startTime) + "ms");
        System.out.printf("\tThe average value of the array is - %.2f\n", avgResult);
    }

    private static int[] generateRandomArray() {
        int[] array = new int[ARRAY_SIZE];
        Random random = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = random.nextInt(MAX_RANGE - MIN_RANGE + 1) + MIN_RANGE;
        }
        return array;
    }

    public static void menu() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter the minimum range: ");
            MIN_RANGE = scanner.nextInt();
            MIN_RANGE = Math.max(MIN_RANGE, 0);

            System.out.print("Enter the maximum range: ");
            MAX_RANGE = scanner.nextInt();
            MAX_RANGE = (MAX_RANGE > MIN_RANGE) ? MAX_RANGE : MIN_RANGE+300;
            MAX_RANGE = Math.min(MAX_RANGE, 1000);

            System.out.print("Enter the number of items: ");
            ARRAY_SIZE = scanner.nextInt();
            ARRAY_SIZE = Math.max(ARRAY_SIZE, 40);
            ARRAY_SIZE = Math.min(ARRAY_SIZE, 60);

            System.out.println("\nStart processing an array with a radius of " + MIN_RANGE + " - " + MAX_RANGE + " and a length of " + ARRAY_SIZE + ":");
        } catch (Exception e) {
            System.out.println("Invalid value!");
            menu();
        }
    }
}
