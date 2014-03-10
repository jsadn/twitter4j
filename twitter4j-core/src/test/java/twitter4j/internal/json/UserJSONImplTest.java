/*
 * Copyright 2007 Yusuke Yamamoto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author Naoya Hatayama - applepedlar at gmail.com
 */

package twitter4j.internal.json;

import twitter4j.TwitterException;
import twitter4j.URLEntity;
import junit.framework.TestCase;

public class UserJSONImplTest extends TestCase {
    
    public void testGetDescriptionURLEntities1() throws JSONException, TwitterException {
        String rawJson = "{\"id\":219570417,\"id_str\":\"219570417\",\"name\":\"\\u3066\\u3059\\u3068\",\"screen_name\":\"gjmp9\",\"location\":\"\\u65e5\\u672c\",\"description\":\"&lt;test&gt; url: http:\\/\\/t.co\\/UcHD19ZC url2: http:\\/\\/t.co\\/dRuJ7wCm subaccount: @gjmp10 hashtag: #test\",\"url\":\"http:\\/\\/fdghj.com\",\"entities\":{\"url\":{\"urls\":[{\"url\":\"http:\\/\\/fdghj.com\",\"expanded_url\":null,\"indices\":[0,16]}]},\"description\":{\"urls\":[{\"url\":\"http:\\/\\/t.co\\/UcHD19ZC\",\"expanded_url\":\"http:\\/\\/test.com\\/\",\"display_url\":\"test.com\",\"indices\":[18,38]},{\"url\":\"http:\\/\\/t.co\\/dRuJ7wCm\",\"expanded_url\":\"http:\\/\\/longurl.com\\/abcdefghijklmnopqrstuvwxyz\",\"display_url\":\"longurl.com\\/abcdefghijklmn\\u2026\",\"indices\":[45,65]}]}},\"protected\":false,\"followers_count\":8,\"friends_count\":11,\"listed_count\":0,\"created_at\":\"Thu Nov 25 06:47:37 +0000 2010\",\"favourites_count\":1,\"utc_offset\":-36000,\"time_zone\":\"Hawaii\",\"geo_enabled\":false,\"verified\":false,\"statuses_count\":280,\"lang\":\"ja\",\"status\":{\"created_at\":\"Wed Dec 05 01:53:33 +0000 2012\",\"id\":276142234003468288,\"id_str\":\"276142234003468288\",\"text\":\"\\u307b\\u3052\\u307b\\u3052\",\"source\":\"\\u003ca href=\\\"http:\\/\\/jigtwi.jp\\/?p=1001\\\" rel=\\\"nofollow\\\"\\u003ejigtwi for Android\\u003c\\/a\\u003e\",\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":null,\"in_reply_to_user_id_str\":null,\"in_reply_to_screen_name\":null,\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"retweet_count\":0,\"entities\":{\"hashtags\":[],\"urls\":[],\"user_mentions\":[]},\"favorited\":false,\"retweeted\":false},\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"CBC1E5\",\"profile_background_image_url\":\"http:\\/\\/a0.twimg.com\\/profile_background_images\\/722705453\\/321f2aba5dcbbc97ec0dd0bf9969cb48.jpeg\",\"profile_background_image_url_https\":\"https:\\/\\/si0.twimg.com\\/profile_background_images\\/722705453\\/321f2aba5dcbbc97ec0dd0bf9969cb48.jpeg\",\"profile_background_tile\":true,\"profile_image_url\":\"http:\\/\\/a0.twimg.com\\/profile_images\\/2607446059\\/bip392byfu5669roobg5_normal.png\",\"profile_image_url_https\":\"https:\\/\\/si0.twimg.com\\/profile_images\\/2607446059\\/bip392byfu5669roobg5_normal.png\",\"profile_link_color\":\"B40B43\",\"profile_sidebar_border_color\":\"FFFFFF\",\"profile_sidebar_fill_color\":\"E5507E\",\"profile_text_color\":\"362720\",\"profile_use_background_image\":true,\"default_profile\":false,\"default_profile_image\":false,\"following\":true,\"follow_request_sent\":false,\"notifications\":false}";
        JSONObject json = new JSONObject(rawJson);
        UserJSONImpl user = new UserJSONImpl(json);
        
        URLEntity[] descriptionUrlEntities = user.getDescriptionURLEntities();
        assertNotNull(descriptionUrlEntities);
        assertEquals(2, descriptionUrlEntities.length);
        
        assertEquals("http://test.com/", descriptionUrlEntities[0].getExpandedURL());
        assertEquals("test.com", descriptionUrlEntities[0].getDisplayURL());
        assertEquals("http://t.co/UcHD19ZC", descriptionUrlEntities[0].getURL());
        assertEquals(12, descriptionUrlEntities[0].getStart());
        assertEquals(32, descriptionUrlEntities[0].getEnd());
        assertEquals("http://t.co/UcHD19ZC", user.getDescription().substring(descriptionUrlEntities[0].getStart(), descriptionUrlEntities[0].getEnd()));
        
        assertEquals("http://longurl.com/abcdefghijklmnopqrstuvwxyz", descriptionUrlEntities[1].getExpandedURL());
        assertEquals("longurl.com/abcdefghijklmn…", descriptionUrlEntities[1].getDisplayURL());
        assertEquals("http://t.co/dRuJ7wCm", descriptionUrlEntities[1].getURL());
        assertEquals(39, descriptionUrlEntities[1].getStart());
        assertEquals(59, descriptionUrlEntities[1].getEnd());
        assertEquals("http://t.co/dRuJ7wCm", user.getDescription().substring(descriptionUrlEntities[1].getStart(), descriptionUrlEntities[1].getEnd()));
        
        assertEquals("<test> url: http://t.co/UcHD19ZC url2: http://t.co/dRuJ7wCm subaccount: @gjmp10 hashtag: #test", user.getDescription());
    }
    
    public void testGetDescriptionURLEntities2() throws JSONException, TwitterException {
        String rawJsonWithEmptyDescription = "{\"id\":219570417,\"id_str\":\"219570417\",\"name\":\"\\u3066\\u3059\\u3068\",\"screen_name\":\"gjmp9\",\"location\":\"\\u65e5\\u672c\",\"description\":\"\",\"url\":\"http:\\/\\/fdghj.com\",\"entities\":{\"url\":{\"urls\":[{\"url\":\"http:\\/\\/fdghj.com\",\"expanded_url\":null,\"indices\":[0,16]}]},\"description\":{\"urls\":[]}},\"protected\":false,\"followers_count\":8,\"friends_count\":11,\"listed_count\":0,\"created_at\":\"Thu Nov 25 06:47:37 +0000 2010\",\"favourites_count\":1,\"utc_offset\":-36000,\"time_zone\":\"Hawaii\",\"geo_enabled\":false,\"verified\":false,\"statuses_count\":280,\"lang\":\"ja\",\"status\":{\"created_at\":\"Wed Dec 05 01:53:33 +0000 2012\",\"id\":276142234003468288,\"id_str\":\"276142234003468288\",\"text\":\"\\u307b\\u3052\\u307b\\u3052\",\"source\":\"\\u003ca href=\\\"http:\\/\\/jigtwi.jp\\/?p=1001\\\" rel=\\\"nofollow\\\"\\u003ejigtwi for Android\\u003c\\/a\\u003e\",\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":null,\"in_reply_to_user_id_str\":null,\"in_reply_to_screen_name\":null,\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"retweet_count\":0,\"entities\":{\"hashtags\":[],\"urls\":[],\"user_mentions\":[]},\"favorited\":false,\"retweeted\":false},\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"CBC1E5\",\"profile_background_image_url\":\"http:\\/\\/a0.twimg.com\\/profile_background_images\\/722705453\\/321f2aba5dcbbc97ec0dd0bf9969cb48.jpeg\",\"profile_background_image_url_https\":\"https:\\/\\/si0.twimg.com\\/profile_background_images\\/722705453\\/321f2aba5dcbbc97ec0dd0bf9969cb48.jpeg\",\"profile_background_tile\":true,\"profile_image_url\":\"http:\\/\\/a0.twimg.com\\/profile_images\\/2607446059\\/bip392byfu5669roobg5_normal.png\",\"profile_image_url_https\":\"https:\\/\\/si0.twimg.com\\/profile_images\\/2607446059\\/bip392byfu5669roobg5_normal.png\",\"profile_link_color\":\"B40B43\",\"profile_sidebar_border_color\":\"FFFFFF\",\"profile_sidebar_fill_color\":\"E5507E\",\"profile_text_color\":\"362720\",\"profile_use_background_image\":true,\"default_profile\":false,\"default_profile_image\":false,\"following\":true,\"follow_request_sent\":false,\"notifications\":false}";
        JSONObject jsonWithEmptyDescription = new JSONObject(rawJsonWithEmptyDescription);
        UserJSONImpl userWithEmptyDescription = new UserJSONImpl(jsonWithEmptyDescription);
        assertEquals("", userWithEmptyDescription.getDescription());
        assertEquals(0, userWithEmptyDescription.getDescriptionURLEntities().length);
        
        String rawJsonWithoutDescription = "{\"id\":219570417,\"id_str\":\"219570417\",\"name\":\"\\u3066\\u3059\\u3068\",\"screen_name\":\"gjmp9\",\"location\":\"\\u65e5\\u672c\",\"url\":\"http:\\/\\/fdghj.com\",\"entities\":{\"url\":{\"urls\":[{\"url\":\"http:\\/\\/fdghj.com\",\"expanded_url\":null,\"indices\":[0,16]}]}},\"protected\":false,\"followers_count\":8,\"friends_count\":11,\"listed_count\":0,\"created_at\":\"Thu Nov 25 06:47:37 +0000 2010\",\"favourites_count\":1,\"utc_offset\":-36000,\"time_zone\":\"Hawaii\",\"geo_enabled\":false,\"verified\":false,\"statuses_count\":280,\"lang\":\"ja\",\"status\":{\"created_at\":\"Wed Dec 05 01:53:33 +0000 2012\",\"id\":276142234003468288,\"id_str\":\"276142234003468288\",\"text\":\"\\u307b\\u3052\\u307b\\u3052\",\"source\":\"\\u003ca href=\\\"http:\\/\\/jigtwi.jp\\/?p=1001\\\" rel=\\\"nofollow\\\"\\u003ejigtwi for Android\\u003c\\/a\\u003e\",\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":null,\"in_reply_to_user_id_str\":null,\"in_reply_to_screen_name\":null,\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"retweet_count\":0,\"entities\":{\"hashtags\":[],\"urls\":[],\"user_mentions\":[]},\"favorited\":false,\"retweeted\":false},\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"CBC1E5\",\"profile_background_image_url\":\"http:\\/\\/a0.twimg.com\\/profile_background_images\\/722705453\\/321f2aba5dcbbc97ec0dd0bf9969cb48.jpeg\",\"profile_background_image_url_https\":\"https:\\/\\/si0.twimg.com\\/profile_background_images\\/722705453\\/321f2aba5dcbbc97ec0dd0bf9969cb48.jpeg\",\"profile_background_tile\":true,\"profile_image_url\":\"http:\\/\\/a0.twimg.com\\/profile_images\\/2607446059\\/bip392byfu5669roobg5_normal.png\",\"profile_image_url_https\":\"https:\\/\\/si0.twimg.com\\/profile_images\\/2607446059\\/bip392byfu5669roobg5_normal.png\",\"profile_link_color\":\"B40B43\",\"profile_sidebar_border_color\":\"FFFFFF\",\"profile_sidebar_fill_color\":\"E5507E\",\"profile_text_color\":\"362720\",\"profile_use_background_image\":true,\"default_profile\":false,\"default_profile_image\":false,\"following\":true,\"follow_request_sent\":false,\"notifications\":false}";
        JSONObject jsonWithoutDescription = new JSONObject(rawJsonWithoutDescription);
        UserJSONImpl userWithoutDescription = new UserJSONImpl(jsonWithoutDescription);
        assertEquals(null, userWithoutDescription.getDescription());
        assertEquals(0, userWithoutDescription.getDescriptionURLEntities().length);
    }

    public void testProfileImageURL() throws JSONException, TwitterException {
        String rawJsonWithoutProfileImageExtension = "{\"id\":400609977,\"id_str\":\"400609977\",\"name\":\"Chris Bautista\",\"screen_name\":\"ayecrispy\",\"location\":\"Jacksonville, FL\",\"description\":\"I'm a gamer and will always be one. I like to keep up with the entertainment life. Where it's celebrities, technology or trying to keep up with latest trend\",\"url\":null,\"entities\":{\"description\":{\"urls\":[]}},\"protected\":false,\"followers_count\":17,\"friends_count\":177,\"listed_count\":0,\"created_at\":\"Sat Oct 29 09:23:10 +0000 2011\",\"favourites_count\":0,\"utc_offset\":-18000,\"time_zone\":\"Eastern Time (US & Canada)\",\"geo_enabled\":false,\"verified\":false,\"statuses_count\":113,\"lang\":\"en\",\"status\":{\"created_at\":\"Sun Dec 16 02:37:57 +0000 2012\",\"id\":280139673333035008,\"id_str\":\"280139673333035008\",\"text\":\"Gotta love olive Garden!\",\"source\":\"\\u003ca href=\\\"http:\\/\\/tweedleapp.com\\/\\\" rel=\\\"nofollow\\\"\\u003e Tweedle\\u003c\\/a\\u003e\",\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":null,\"in_reply_to_user_id_str\":null,\"in_reply_to_screen_name\":null,\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"retweet_count\":0,\"entities\":{\"hashtags\":[],\"urls\":[],\"user_mentions\":[]},\"favorited\":false,\"retweeted\":false},\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"1A1B1F\",\"profile_background_image_url\":\"http:\\/\\/a0.twimg.com\\/images\\/themes\\/theme9\\/bg.gif\",\"profile_background_image_url_https\":\"https:\\/\\/si0.twimg.com\\/images\\/themes\\/theme9\\/bg.gif\",\"profile_background_tile\":false,\"profile_image_url\":\"http:\\/\\/a0.twimg.com\\/profile_images\\/1835646533\\/gu44kEhi_normal\",\"profile_image_url_https\":\"https:\\/\\/si0.twimg.com\\/profile_images\\/1835646533\\/gu44kEhi_normal\",\"profile_link_color\":\"2FC2EF\",\"profile_sidebar_border_color\":\"181A1E\",\"profile_sidebar_fill_color\":\"252429\",\"profile_text_color\":\"666666\",\"profile_use_background_image\":true,\"default_profile\":false,\"default_profile_image\":false,\"following\":false,\"follow_request_sent\":false,\"notifications\":false}";
        JSONObject jsonWithoutProfileImageExtension = new JSONObject(rawJsonWithoutProfileImageExtension);
        UserJSONImpl userWithoutProfileImageExtension = new UserJSONImpl(jsonWithoutProfileImageExtension);

        assertEquals("http://a0.twimg.com/profile_images/1835646533/gu44kEhi_bigger", userWithoutProfileImageExtension.getBiggerProfileImageURL());
        assertEquals("https://si0.twimg.com/profile_images/1835646533/gu44kEhi_bigger", userWithoutProfileImageExtension.getBiggerProfileImageURLHttps());
        assertEquals("http://a0.twimg.com/profile_images/1835646533/gu44kEhi", userWithoutProfileImageExtension.getOriginalProfileImageURL());
        assertEquals("https://si0.twimg.com/profile_images/1835646533/gu44kEhi", userWithoutProfileImageExtension.getOriginalProfileImageURLHttps());

        String rawJsonWithProfileImageExtension = "{\"id\":613742117,\"id_str\":\"613742117\",\"name\":\"Tweedle\",\"screen_name\":\"tweedleapp\",\"location\":\"\",\"description\":\"Twitter application for Android, follow this account for updates.\\r\\n\\r\\nDeveloped by @HandlerExploit\",\"url\":\"http:\\/\\/tweedleapp.com\",\"entities\":{\"url\":{\"urls\":[{\"url\":\"http:\\/\\/tweedleapp.com\",\"expanded_url\":null,\"indices\":[0,21]}]},\"description\":{\"urls\":[]}},\"protected\":false,\"followers_count\":2210,\"friends_count\":1,\"listed_count\":20,\"created_at\":\"Wed Jun 20 20:42:48 +0000 2012\",\"favourites_count\":1,\"utc_offset\":null,\"time_zone\":null,\"geo_enabled\":false,\"verified\":false,\"statuses_count\":323,\"lang\":\"en\",\"status\":{\"created_at\":\"Sun Dec 16 06:47:33 +0000 2012\",\"id\":280202487317790721,\"id_str\":\"280202487317790721\",\"text\":\"@bcw_ It is better in some aspects, but in others it is not ideal. We need to add a way to notify the user better though.\",\"source\":\"web\",\"truncated\":false,\"in_reply_to_status_id\":280202314080464896,\"in_reply_to_status_id_str\":\"280202314080464896\",\"in_reply_to_user_id\":101066646,\"in_reply_to_user_id_str\":\"101066646\",\"in_reply_to_screen_name\":\"bcw_\",\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"retweet_count\":0,\"entities\":{\"hashtags\":[],\"urls\":[],\"user_mentions\":[{\"screen_name\":\"bcw_\",\"name\":\"Verified Bradley\",\"id\":101066646,\"id_str\":\"101066646\",\"indices\":[0,5]}]},\"favorited\":false,\"retweeted\":false},\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"C0DEED\",\"profile_background_image_url\":\"http:\\/\\/a0.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\"profile_background_image_url_https\":\"https:\\/\\/si0.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\"profile_background_tile\":false,\"profile_image_url\":\"http:\\/\\/a0.twimg.com\\/profile_images\\/2433552309\\/dltv4u9hajvoxsne5bly_normal.png\",\"profile_image_url_https\":\"https:\\/\\/si0.twimg.com\\/profile_images\\/2433552309\\/dltv4u9hajvoxsne5bly_normal.png\",\"profile_link_color\":\"0084B4\",\"profile_sidebar_border_color\":\"C0DEED\",\"profile_sidebar_fill_color\":\"DDEEF6\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"default_profile\":true,\"default_profile_image\":false,\"following\":true,\"follow_request_sent\":false,\"notifications\":false}";
        JSONObject jsonWithProfileImageExtension = new JSONObject(rawJsonWithProfileImageExtension);
        UserJSONImpl userWithProfileImageExtension = new UserJSONImpl(jsonWithProfileImageExtension);

        assertEquals(userWithProfileImageExtension.getBiggerProfileImageURL(), "http://a0.twimg.com/profile_images/2433552309/dltv4u9hajvoxsne5bly_bigger.png");
        assertEquals(userWithProfileImageExtension.getOriginalProfileImageURL(), "http://a0.twimg.com/profile_images/2433552309/dltv4u9hajvoxsne5bly.png");
    }
}
