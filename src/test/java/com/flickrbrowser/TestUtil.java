package com.flickrbrowser;

import android.location.Location;
import android.location.LocationManager;
import com.flickrbrowser.activity.Search;
import com.flickrbrowser.activity.TestableSearch;
import com.flickrbrowser.util.SimpleLocation;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/28/14
 * Time: 7:48 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class TestUtil {


    public static final String okHttpResponse = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
            "<rsp stat=\"ok\">\n" +
            "<photos page=\"1\" pages=\"1\" perpage=\"250\" total=\"1\">\n" +
            "    <photo id=\"1535647353\" owner=\"98545448@N00\" secret=\"e5b7537af5\" server=\"2371\" farm=\"3\" title=\"Airone rosso\" ispublic=\"1\" isfriend=\"0\" isfamily=\"0\">\n" +
            "<description>airone</description>\n"+
            "</photo>\n"+
            "</photos>\n" +
            "</rsp>";

    public static final String okHttpResponseNoDesc = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
            "<rsp stat=\"ok\">\n" +
            "<photos page=\"1\" pages=\"1\" perpage=\"250\" total=\"1\">\n" +
            "    <photo id=\"1535647353\" owner=\"98545448@N00\" secret=\"e5b7537af5\" server=\"2371\" farm=\"3\" title=\"Airone rosso\" ispublic=\"1\" isfriend=\"0\" isfamily=\"0\">\n" +
            "</photo>\n"+
            "</photos>\n" +
            "</rsp>";

    public static final String koHttpResponse = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
            "<rsp stat=\"fail\">\n" +
            "\t<err code=\"112\" msg=\"Method &quot;fliclllkr.photos.search&quot; not found\" />\n" +
            "</rsp>";


    public static Class<? extends Search> getTestActivityClass() {
        return TestableSearch.class;
    }

    public static SimpleLocation getTestLocation() {
        SimpleLocation loc = new SimpleLocation();
        loc.setLatitude(44.813019);
        loc.setLongitude(11.757689);
        return loc;
    }
}
