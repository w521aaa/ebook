/**
 * @author weim
 * @date 18-7-27
 */
public class Zhengze {

    public static void main(String[] args) {

//        String href = "/0_276/211302.html";
//        System.out.println(href.substring(0,href.lastIndexOf("/")+1));

        String imageUrl = "http://www.biquge.com.tw/files/article/image/18/18949/18949s.jpg";
        System.out.println(imageUrl.substring(imageUrl.lastIndexOf("/")));
        System.out.println(imageUrl);

//        String str = "<body><div id=\"wrapper\"> \n" +
//                " <script>login();</script> \n" +
//                " <div class=\"header\"> \n" +
//                "  <div class=\"header_logo\"> \n" +
//                "   <a href=\"http://www.biquge.com.tw\">笔趣阁</a> \n" +
//                "  </div> \n" +
//                "  <script>bqg_panel();</script> \n" +
//                " </div> ";
//
//        System.out.println(getString(str));

    }

    private static String getString(String str) {

        return str.replaceAll("<script>.*</script>", "");
    }


}
