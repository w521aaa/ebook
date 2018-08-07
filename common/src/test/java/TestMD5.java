import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author weim
 * @date 18-8-1
 */
public class TestMD5 {

    public String encode(String str) throws Exception {

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            messageDigest.update(str.getBytes());

            return new BigInteger(1, messageDigest.digest()).toString(16);

        } catch (Exception e) {
            throw new Exception("MD5加密出现错误，"+e.toString());
        }
    }

    public String encode2(String str) throws Exception {

        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            md.update(str.getBytes());

            byte[] hash = md.digest();

            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误，"+e.toString());
        }

        return hexString.toString();
    }


    public static void main(String[] args) throws Exception {


        System.out.println(new String("大道朝天".getBytes("utf-8")));
        System.out.println(new String("大道朝天".getBytes("utf-8")));
        System.out.println(new String("大道朝天".getBytes("utf-8")));
        System.out.println(new String("大道朝天".getBytes("utf-8")));


        TestMD5 testMD5 = new TestMD5();
        System.out.println(testMD5.encode("大道朝天"));
        System.out.println(testMD5.encode2("大道朝天"));
        System.out.println(testMD5.encode("大道朝天"));
        System.out.println(testMD5.encode2("大道朝天"));
        System.out.println(testMD5.encode("第一场大道朝太"));
        System.out.println(testMD5.encode2("第一场大道朝太"));
        System.out.println(testMD5.encode("第一场大道朝太"));
        System.out.println(testMD5.encode2("第一场大道朝太"));
        System.out.println(testMD5.encode("第一场大道朝太"));
        System.out.println(testMD5.encode2("第一场大道朝太"));
        System.out.println(testMD5.encode("第一场 大道朝 太"));
        System.out.println(testMD5.encode2("第一场 大道朝 太"));

    }
}
