package pr2;

import java.util.concurrent.Callable;

class ArrayProcessor implements Callable<Double> {
    private final int[] numbers;
    private final int start;
    private final int end;

    public ArrayProcessor(int[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    public Double call() throws InterruptedException {
        double sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }

        Thread.sleep(1000);

        return sum;
    }
}
