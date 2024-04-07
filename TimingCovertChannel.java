public class TimingCovertChannel {

    private static final Object lock = new Object();
    private static long sendTime = 0;
    private static boolean bitSent = false;

    // the intervals are longer comparing to python code so the 0 and 1 bits are bettetr distinguisheable
    private static final long shortInterval = 500; // 0.5 seconds for '0'
    private static final long longInterval = 1000; // 1 second for '1'
    private static final long tolerance = (shortInterval + longInterval) / 2;

    static class Sender implements Runnable {
        private final String message;

        Sender(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            for (char bit : message.toCharArray()) {
                synchronized (lock) {
                    sendTime = System.currentTimeMillis();
                }
                try {
                    Thread.sleep(bit == '0' ? shortInterval : longInterval);
                    synchronized (lock) {
                        bitSent = true;
                        lock.notify();
                    }
                    System.out.println("Sender: Sent bit " + bit);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class Receiver implements Runnable {
        private final int messageLength;

        Receiver(int messageLength) {
            this.messageLength = messageLength;
        }

        @Override
        public void run() {
            StringBuilder receivedMessage = new StringBuilder();
            for (int i = 0; i < messageLength; i++) {
                synchronized (lock) {
                    try {
                        while (!bitSent) {
                            lock.wait();
                        }
                        bitSent = false;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                // Use the current time minus the last send time to decide the bit
                long elapsedTime = System.currentTimeMillis() - sendTime;
                char bit = elapsedTime < tolerance ? '0' : '1';
                receivedMessage.append(bit);
                System.out.println("Receiver: Received bit " + bit);
            }
            System.out.println("Received message: " + receivedMessage.toString());
        }
    }

    public static void main(String[] args) {
        String binaryMessage = "101";
        Thread senderThread = new Thread(new Sender(binaryMessage));
        Thread receiverThread = new Thread(new Receiver(binaryMessage.length()));

        receiverThread.start();
        senderThread.start();
    }
}
