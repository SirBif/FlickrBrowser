package com.flickrbrowser;

import com.flickrbrowser.activity.Search;
import com.flickrbrowser.activity.TestableSearch;
import com.flickrbrowser.parcelable.SimpleLocation;

/**
 * Created with IntelliJ IDEA.
 * User: Bif
 * Date: 2/28/14
 * Time: 7:48 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class TestUtil {


    public static final String okHttpResponse = "{\"photos\":{\"page\":1,\"pages\":4591,\"perpage\":2,\"total\":\"9181\",\"photo\":[{\"id\":\"6926764145\",\"owner\":\"15998539@N06\",\"secret\":\"43c1e2cbc7\",\"server\":\"7045\",\"farm\":8,\"title\":\"serbatoio dell' acqua - tank of water\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0,\"description\":{\"_content\":\"serbatoio\"}}]},\"stat\":\"ok\"}";

    public static final String okHttpResponseNoDescription = "{\"photos\":{\"page\":1,\"pages\":4591,\"perpage\":2,\"total\":\"9181\",\"photo\":[{\"id\":\"6926764145\",\"owner\":\"15998539@N06\",\"secret\":\"43c1e2cbc7\",\"server\":\"7045\",\"farm\":8,\"title\":\"serbatoio dell' acqua - tank of water\",\"ispublic\":1,\"isfriend\":0,\"isfamily\":0,\"description\":{\"_content\":\"\"}}]},\"stat\":\"ok\"}";

    public static final String koHttpResponse = "{\"stat\":\"fail\", \"code\":112, \"message\":\"Method \\\"flickvr.photos.search\\\" not found\"}";

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
