package org.nalby.yobatis.core.util;

import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.nalby.yobatis.core.exception.ProjectException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class XmlUtil {

    private XmlUtil() {}

    private static String removeBlankLines(String content) throws IOException {
        StringReader stringReader = new StringReader(content);
        BufferedReader reader = new BufferedReader(stringReader);
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().length() != 0) {
                builder.append(line);
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public static String toXmlString(Document document) {
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setTrimText(false);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos, true, "utf-8");
            XMLWriter writer = new XMLWriter(ps, format);
            writer.write(document);
            String content = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            ps.close();
            return removeBlankLines(content);
        } catch (IOException e) {
            throw new ProjectException(e);
        }
    }


    private static Document buildDoc(String text) {
        SAXReader saxReader = new SAXReader();
        saxReader.setValidation(false);
        saxReader.setStripWhitespaceText(false);
        try  {
            return saxReader.read(new ByteArrayInputStream(("<rootDoc>" + text + "</rootDoc>").getBytes()));
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Element> convertToElements(String text) {
        List<Element> commentedElements = null;
        Document doc = buildDoc(text);
        if (doc != null) {
            commentedElements = doc.getRootElement().elements();
        }
        if (commentedElements == null) {
            commentedElements = Collections.EMPTY_LIST;
        }
        return commentedElements;
    }

    private static boolean isCommentedElement(String text) {
        return buildDoc(text) != null;
    }

    /**
     * Uncomment commented elements contained by parent.
     * @param parent
     * @return elements after uncommenting, or an empty list.
     */
    public static List<Element> loadCommentedElements(Element parent)  {
        Expect.notNull(parent, "parent must not be emtpy.");
        String text = null;
        for (Iterator<Node> iterator = parent.nodeIterator(); iterator.hasNext(); ) {
            Node node = iterator.next();
            if (node.getNodeType() != Node.COMMENT_NODE) {
                continue;
            }
            Comment comment = (Comment) node;
            String tmp = comment.asXML().replaceAll("\\s+", " ");
            tmp = tmp.replaceAll("<!--", "<");
            tmp = tmp.replaceAll("-->", ">");
            if (isCommentedElement(tmp)) {
                text = text == null ? tmp : text + tmp;
            }
        }
        return convertToElements(text);
    }

    public static Comment commentElement(Element e) {
        Expect.notNull(e, "e must not be null.");
        String str = e.asXML();
        str = str.replaceFirst("^<", "");
        str = str.replaceFirst(">$", "");
        return DocumentFactory.getInstance().createComment(str);
    }
}
