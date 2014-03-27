package com.flickrbrowser.rest;

import com.flickrbrowser.parcelable.PhotoResult;
import com.flickrbrowser.util.FlickrBrowserConstants;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 Helper class used to parse Flickr xml responses
 */
public class FlickrXmlParser {
    private DocumentBuilder documentBuilder;
    private static FlickrXmlParser parserInstance;

    public static FlickrXmlParser getParser() {
        if(parserInstance == null) {
            try {
                parserInstance = new FlickrXmlParser();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
        return parserInstance;
    }

    private FlickrXmlParser() throws ParserConfigurationException {
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public ParsedResponse parseResponse(String xmlResponse) {
        InputStream is = new ByteArrayInputStream(xmlResponse.getBytes());
        return parseResponse(is);
    }

    public ParsedResponse parseResponse(InputStream xmlResponse) {
        try {
            return parseInputStream(xmlResponse);
        } finally {
            IOUtils.closeQuietly(xmlResponse);
        }
    }

    private ParsedResponse parseInputStream(InputStream xmlResponse) {
        List<PhotoResult> photoList = new ArrayList<PhotoResult>();
        int numberOfPages = 0;

        try {
            Document doc = documentBuilder.parse(xmlResponse);
            NodeList nodes;

            nodes = doc.getElementsByTagName(FlickrBrowserConstants.XmlAttributes.PHOTOS_NODE);
            if(nodes.getLength() == 1) {
                Element element = (Element) nodes.item(0);
                numberOfPages = Integer.valueOf(element.getAttribute(FlickrBrowserConstants.XmlAttributes.PAGES));
            }

            nodes = doc.getElementsByTagName(FlickrBrowserConstants.XmlAttributes.PHOTO_NODE);
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                photoList.add(createPhotoResult(element));
            }
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return new ParsedResponse(photoList, numberOfPages);
    }

    private PhotoResult createPhotoResult(Element element) {
        PhotoResult photo = new PhotoResult();
        photo.setId(element.getAttribute(FlickrBrowserConstants.XmlAttributes.ID));
        photo.setOwner(element.getAttribute(FlickrBrowserConstants.XmlAttributes.OWNER));
        photo.setSecret(element.getAttribute(FlickrBrowserConstants.XmlAttributes.SECRET));
        photo.setFarm(element.getAttribute(FlickrBrowserConstants.XmlAttributes.FARM));
        photo.setServer(element.getAttribute(FlickrBrowserConstants.XmlAttributes.SERVER));
        photo.setTitle(element.getAttribute(FlickrBrowserConstants.XmlAttributes.TITLE));

        NodeList descriptions = element.getElementsByTagName(FlickrBrowserConstants.XmlAttributes.DESCRIPTION_NODE);
        if(descriptions.getLength() > 0) {
            Node desc = descriptions.item(0);
            if(desc.hasChildNodes()) {
                photo.setDescription(descriptions.item(0).getFirstChild().getNodeValue());
            }
        }
        return photo;
    }

    /**
     * Objects of this class presents the results obtained from a Flickr XML response in a structured manner.
     * Contains a list of PhotoResult and the number of result pages available
     * @see PhotoResult
     */
    public class ParsedResponse {
        private int pagesNumber;
        private List<PhotoResult> photos;

        public ParsedResponse(List<PhotoResult> photos, int pages) {
            this.pagesNumber = pages;
            this.photos = photos;
        }

        public int getPagesNumber() {
            return pagesNumber;
        }

        public List<PhotoResult> getPhotos() {
            return photos;
        }
    }
}
