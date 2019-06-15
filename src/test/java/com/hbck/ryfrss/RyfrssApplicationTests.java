package com.hbck.ryfrss;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RyfrssApplicationTests {

    private int i = 1;

    private String homeUrl = "https://wangdoc.com/javascript/";

    @Test
    public void contextLoads() throws IOException {
        Element right = getElement(homeUrl);
        hasNext(right);
    }

    private void hasNext(Element right) throws IOException {
        if (right != null) {
            String href = right.attr("href");
            String rightUrl = homeUrl + href.replace("../", "").replace("./", "");
            System.out.println(rightUrl);
            Element element = getElement(rightUrl);
            if (i < 4) {
                hasNext(element);
            }
        }
    }

    private Element getElement(String homeUrl) throws IOException {
        Document home = Jsoup.connect(homeUrl).get();
        Element article = home.selectFirst("article");
        article.selectFirst(".page-meta").html("");
        String title = "#" + article.selectFirst(".title").text();
        String fileName = i + title + ".md";
        File file = new File(fileName);
        String newLine = System.getProperty("line.separator");
        FileOutputStream out = new FileOutputStream(file);
        BufferedOutputStream buff = new BufferedOutputStream(out);
        System.out.println(article);
        Element nav = home.selectFirst("nav.level");
        Element right = nav.selectFirst(".level-right a");
        for (Element c : article.children()) {
            if (c.is("h1")) { // 标题
                buff.write(("# " + c.text()).getBytes());
                buff.write(newLine.getBytes());
                buff.flush();
            } else if (c.is("h2")) { // 标题
                buff.write(("## " + c.text()).getBytes());
                buff.write(newLine.getBytes());
                buff.flush();
            } else if (c.is("h3")) { // 标题
                buff.write(("### " + c.text()).getBytes());
                buff.write(newLine.getBytes());
                buff.flush();
            } else if (c.is("h4")) { // 标题
                buff.write(("#### " + c.text()).getBytes());
                buff.write(newLine.getBytes());
                buff.flush();
            } else if (c.is("h5")) { // 标题
                buff.write(("##### " + c.text()).getBytes());
                buff.write(newLine.getBytes());
                buff.flush();
            } else if (c.is("h6")) { // 标题
                buff.write(("###### " + c.text()).getBytes());
                buff.write(newLine.getBytes());
                buff.flush();
            } else if (c.is("p")) { // 段落
                buff.write(newLine.getBytes());
                buff.write(c.text().getBytes());
                buff.write(newLine.getBytes());
                buff.flush();
            } else if (c.is("ul")) { // 无序列表
                for (Element l : c.children()) {
                    buff.write(("- " + l.text()).getBytes());
                    buff.write(newLine.getBytes());
                }
            } else if (c.is("pre")) { // 代码块
                buff.write(("```javascript".getBytes()));
                buff.write(newLine.getBytes());
                for (Element l : c.child(0).children()) {
                    buff.write(l.text().getBytes());
                    buff.write(newLine.getBytes());
                }
                buff.write(("```".getBytes()));
                buff.write(newLine.getBytes());
            } else if (c.is("blockquote")) { // 引用
                buff.write(("> " + c.text()).getBytes());
                buff.write(newLine.getBytes());
            }
        }
        buff.close();
        out.close();
        i++;
        return right;
    }

}
