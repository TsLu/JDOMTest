package demo;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Document;


import javax.imageio.IIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用JDOM方法解析xml文档
 * Created by luts on 2015/12/21.
 */
public class JDOMTest {
    private static ArrayList<Books> booksList = new ArrayList<Books>();

    public static void main(String[]args){
        //创建一个SAXBuilder的对象
        SAXBuilder saxBuilder = new SAXBuilder();
        InputStream inputStream;
        try {
            //创建一个输入流，将xml文件加载到输入流中
            inputStream = new FileInputStream("books.xml");
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            //通过saxBuilder的build方法，将输入流加载到saxBuilder中
            Document document = saxBuilder.build(isr);
            //通过document对象获取xml文件的根节点
            Element rootElement = document.getRootElement();
            //获取节点下的的子节点的List结合
            List<Element> bookList = rootElement.getChildren();
            //继续解析
            for (Element book : bookList){
                Books bookEntry = new Books();
                System.out.println("-------------开始解析第" + (bookList.indexOf(book) + 1) + "书------------");
                //解析book的属性集合
                List<Attribute> attrList = book.getAttributes();
                //知道节点下的属性名称时，获取节点值
                //book.getAttribute("category");
                for (Attribute attr : attrList){

                    //获取属性名
                    String attrName = attr.getName();
                    //获取属性值
                    String attrValue = attr.getValue();
                    System.out.println("属性名： " + attrName + "----属性值：" + attrValue);
                    if (attrName.equals("category")) {
                        bookEntry.setCategory(attrValue);
                    }
                }

                //对book节点的子节点名以及节点值的遍历
                List<Element> bookChilds = book.getChildren();
                for (Element child : bookChilds){
                    System.out.println("节点名： " + child.getName() + "----属性值： "+ child.getValue());
                    if (child.getName().equals("title")){

                        bookEntry.setTitle(child.getValue());
                        //知道节点下的属性名称时，获取节点值
                        String lang = child.getAttributeValue("lang");
                        bookEntry.setLanguage(lang);
                        System.out.println("title   包含的属性 lang 的属性值为： " + child.getAttributeValue("lang"));
                        /* List<Attribute> childAttrList = childNode.getAttributes();
                        for (Attribute childAttr : childAttrList){

                            //获取属性名
                            String childAttrName = childAttr.getName();
                            //获取属性值
                            String childAttrValue = childAttr.getValue();
                            System.out.println("属性名： " + childAttrName + "----属性值：" + childAttrValue);
                            if (childAttrName.equals("lang")) {
                                bookEntry.setLanguage(childAttrName);
                            }
                        }*/
                    }
                    else if (child.getName().equals("author")){
                        bookEntry.setAuthor(child.getValue());
                    }
                    else if (child.getName().equals("year")){
                        bookEntry.setYear(child.getValue());
                    }
                    else if (child.getName().equals("price")){
                        bookEntry.setPrice(child.getValue());
                    }
                }
                System.out.println("------结束解析第" + (bookList.indexOf(book) + 1) + "本书-------");
                booksList.add(bookEntry);
                bookEntry = null;
                System.out.println(booksList.size());
                System.out.println(booksList.get(0).getCategory());
                System.out.println(booksList.get(0).getTitle());
                System.out.println(booksList.get(0).getAuthor());
                System.out.println(booksList.get(0).getLanguage());
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (JDOMException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}

