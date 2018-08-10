import java.io.FileWriter;
import java.io.IOException;

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


        String html = "<br> &nbsp;&nbsp;&nbsp;&nbsp;风总；乌托邦广告公司的资料都在这里；您看一下，风舒苑的秘书説着\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;嗯，并没有回头\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;他们业绩如何？\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;我看了乌托邦广告公司业绩表，非常出色，，看他们的发展趋势很有可能成为风城广告巨头，仅仅三年多有这么出色的业绩。自然离不开他们的三个合伙人。\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;叫什么名字？\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;公司管理运营是关锡，外销部戚薇，广告设计楚墨寒。\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;当她听到那三个字的时候，身体颤抖了一下，太熟悉了，抖了一下并没有回头，\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;乌托邦广告公司市场价位多少？\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;风总这，，，；秘书惊讶的表情！\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;，多少？\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;差不多一千万。\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;这个计划，需要他们广告公司，你去跟他们谈吧！\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;是，风总\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;嗯，去吧。\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;乌托邦广告公司会客室里，关总；这是我们公司的计划书你看一下。\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;关锡————你们的计划我们看了一下，我们公司会给你们一个满意的答复，\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;好的，谢谢合作，\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;希望合作愉快\n" +
                "<br> \n" +
                "<br> &nbsp;&nbsp;&nbsp;&nbsp;墨寒——他们的要求很高，世界水平线了！";


        System.out.println(html.replaceAll("<br>.*nbsp;", "").replaceAll("<br>", ""));

        try {
            FileWriter fileWriter = new FileWriter("/tmp/aaa.txt",true);
            fileWriter.write("bbbbb\n");
            fileWriter.write(html.replaceAll("<br>.*nbsp;", "").replaceAll("<br>", ""));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String getString(String str) {

        return str.replaceAll("<script>.*</script>", "");
    }


}
