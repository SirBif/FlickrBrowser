package com.flickrbrowser.rest;

import com.flickrbrowser.util.FlickrBrowserConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/27/14
 * Time: 11:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class FlickrXmlParser {
    private DocumentBuilder documentBuilder;

    public FlickrXmlParser() throws ParserConfigurationException {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public List<PhotoResult> extractPhotoList(String xmlResponse) {
        List<PhotoResult> photoList = new ArrayList<PhotoResult>();

        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlResponse));

        try {
            Document doc = documentBuilder.parse(is);
            NodeList nodes = doc.getElementsByTagName(FlickrBrowserConstants.XmlAttributes.PHOTO_ATTRIBUTE_NAME);
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                photoList.add(new PhotoResult(element));
            }
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return photoList;
    }
}
