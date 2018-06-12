package org.nalby.yobatis.core.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Test;
import org.nalby.yobatis.core.xml.AbstractXmlParser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class XmlUtilTests {

    private class XmlHelper extends AbstractXmlParser {
        public XmlHelper(InputStream inputStream) throws DocumentException, IOException {
            super(inputStream, "context");
        }
        public Element getRoot() {
            return document.getRootElement();
        }
    }

    private XmlUtilTests.XmlHelper document(String xml) {
        try {
            return new XmlUtilTests.XmlHelper(new ByteArrayInputStream(xml.getBytes()));
        } catch (DocumentException | IOException e) {
            throw new IllegalAccessError("bad xml.");
        }
    }

    private Element build(String xml) {
        try {
            Element tmp = new XmlUtilTests.XmlHelper(new ByteArrayInputStream(xml.getBytes())).getRoot();
            assertTrue(tmp != null);
            return tmp;
        } catch (DocumentException | IOException e) {
            throw new IllegalAccessError("bad xml.");
        }
    }

    @Test
    public void onlyTextComments() {
        String xml = "<context><!--test--></context>";
        List<Element> elements = XmlUtil.loadCommentedElements(build(xml));
        assertTrue(elements.isEmpty());
    }

    @Test
    public void oneComment() {
        String xml = "<context><!--test/--></context>";
        List<Element> elements = XmlUtil.loadCommentedElements(build(xml));
        assertTrue(elements.size() == 1);
        assertTrue(elements.get(0).getName().equals("test"));
    }

    @Test
    public void mixed() {
        String xml = "<context><test3/><!--test/--><!--test1/><test2/--></context>";
        List<Element> elements = XmlUtil.loadCommentedElements(build(xml));
        assertTrue(elements.size() == 3);
    }

    @Test
    public void comment() {
        String xml = "<context><test3/></context>";
        Element context = build(xml);
        Element test = context.element("test3");
        assertTrue(XmlUtil.commentElement(test).asXML().equals("<!--test3/-->"));
    }

    @Test
    public void toXmlString() {
        String xmlContent =  "<context>" +
                "<e1/>" +
                "\n" +
                "\n" +
                "\n" +
                "</context>";
        XmlHelper helper = document(xmlContent);
        System.out.println(helper.toXmlString());
    }
}
