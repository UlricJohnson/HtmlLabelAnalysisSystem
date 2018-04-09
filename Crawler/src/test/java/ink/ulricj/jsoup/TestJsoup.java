package ink.ulricj.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

/**
 * created by Ulric on 2018/3/22
 */

public class TestJsoup {

    @Test
    public void testSohu() throws IOException{
        Document document = Jsoup.connect("http://www.sohu.com/").get();
        System.out.println(document);
    }

}
