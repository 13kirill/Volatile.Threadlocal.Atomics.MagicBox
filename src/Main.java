import java.util.concurrent.ExecutionException;

public class Main {

    //Переменная "openingCounter" символизирует тумблер и счётчик открытия коробки.
    //Когда "openingCounter" увеличивается, т.е. пользователь открывает коробку,
    //переменная "closingCounter" сравнивает себя с переменной "а" и
    //если переменная "openingCounter" увеличилась (коробка открылась), то
    //переменная "closingCounter" тоже увеличивается (коробка закрылась)

    volatile public static int openingCounter = 0;
    private static final int MAX_OPENING = 5;
    private static final int BOX_CLOSURE = 1000;
    private static UserThread userThread = new UserThread();
    private static BoxThread boxThread = new BoxThread();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        userThread.start();
        boxThread.start();
    }

    static class UserThread extends Thread {
        @Override
        public void run() {
            while (openingCounter < MAX_OPENING) {
                {
                    System.out.printf("Пользователь открывает коробку коробку %s раз \n", ++openingCounter);
                    try {
                        Thread.sleep(BOX_CLOSURE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            boxThread.interrupt();
        }
    }

    static class BoxThread extends Thread {
        int closingCounter = 0;

        @Override
        public void run() {
            while (true) {
                if (closingCounter != openingCounter) {
                    System.out.printf("Коробка закрывается %s раз \n", openingCounter);
                    closingCounter = openingCounter;
                }
                if (isInterrupted()) {
                    return;
                }
            }
        }
    }
}