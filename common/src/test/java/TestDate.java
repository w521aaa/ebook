import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author weim
 * @date 18-8-10
 */
public class TestDate {

    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.MONTH, 8);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(calendar.getTime()));

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-3);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);


        System.out.println(sdf.format(calendar.getTime()));

        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+3);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 5);



        System.out.println(sdf.format(calendar.getTime()));


    }

}
