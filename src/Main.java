import java.util.concurrent.ExecutionException;

public class Main {

    //Переменная "а" символизирует тумблер и счётчик открытия коробки.
    //Когда "а" увеличивается, т.е. пользователь открывает коробку,
    //переменная "closingCounter" сравнивает себя с переменной "а" и
    //если переменная "а" увеличилась (коробка открылась), то
    //переменная "closingCounter" тоже увеличивается (коробка закрылась)

    volatile public static int a = 0;
    public static final int BOX_CLOSURE = 1000;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new UserThread().start();
        new BoxThread().start();
    }

    static class UserThread extends Thread {
        @Override
        public void run() {
            while (a < 5) {
                {
                    System.out.printf("Пользователь открывает коробку коробку %s раз \n", ++a);
                    try {
                        Thread.sleep(BOX_CLOSURE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class BoxThread extends Thread {
        int closingCounter = 0;
        @Override
        public void run() {
            while (closingCounter < 5) {
                if (closingCounter != a) {
                    System.out.printf("Коробка закрывается %s раз \n", a);
                    closingCounter = a;
                }
            }
        }
    }
}