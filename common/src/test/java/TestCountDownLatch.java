import java.util.concurrent.*;

/**
 * @author weim
 * @date 18-7-31
 */
public class TestCountDownLatch {

    public static void main(String[] args) {

        ExecutorService service = Executors.newCachedThreadPool();

        CountDownLatch countDownLatch = new CountDownLatch(20);

        System.out.println("start");

        for(int i=0; i<20; i++) {
            //Mine mine = new Mine(i, countDownLatch);
            //service.execute(mine);

            //线程里面return 是否会停止线程
            final int index = i;
            service.execute(() -> {

                if(index % 3 == 0 ) {
                    System.out.println(index);
                    return;
                }

                System.out.println("aaaaaaaaaaaa==>" + index);
            });
        }

        System.out.println("middle");

//        try {
//            countDownLatch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        System.out.println("end");
    }

    static class Mine implements Runnable {

        private Integer index;

        private CountDownLatch countDownLatch;

        public Mine(Integer integer, CountDownLatch countDownLatch) {
            this.index = integer;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("   " + this.index + "  ");

            countDownLatch.countDown();
        }
    }

}
