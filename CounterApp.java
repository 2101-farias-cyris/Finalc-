public class CounterApp {

    private static final Object lock = new Object();
    private static volatile boolean isCountingUpFinished = false;

    public static void main(String[] args) {
        Thread counterUp = new Thread(new CounterUp());
        Thread counterDown = new Thread(new CounterDown());

        counterUp.start();
        try {
            counterUp.join(); // Wait for counterUp to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        counterDown.start();
    }

    static class CounterUp implements Runnable {
        @Override
        public void run() {
            for (int i = 1; i <= 20; i++) {
                System.out.println("Counting Up: " + i);
                try {
                    Thread.sleep(500); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            isCountingUpFinished = true;
        }
    }

    static class CounterDown implements Runnable {
        @Override
        public void run() {
            // Wait until counting up is finished
            while (!isCountingUpFinished) {
                try {
                    Thread.sleep(100); // Polling interval
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            for (int i = 20; i >= 0; i--) {
                System.out.println("Counting Down: " + i);
                try {
                    Thread.sleep(500); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
