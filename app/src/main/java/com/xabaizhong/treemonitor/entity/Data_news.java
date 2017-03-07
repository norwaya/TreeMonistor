package com.xabaizhong.treemonitor.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by admin on 2017/2/24.
 */

@Entity(indexes = {
        @Index(value = "id DESC", unique = true)
})
public class Data_news {
    /**
     * uniquekey : cd8416fd6f62e3c812c40878492debda
     * title : 风雨飘摇的三星面临资本被秃鹫分食的危险
     * date : 2017-03-07 09:01
     * category : 国际
     * author_name : 观察者网国际频道
     * url : http://mini.eastday.com/mobile/170307090124407.html
     * thumbnail_pic_s : http://08.imgmini.eastday.com/mobile/20170307/20170307090124_31ff4e08a424c9490890dbf790237906_1_mwpm_03200403.jpeg
     * thumbnail_pic_s02 : http://08.imgmini.eastday.com/mobile/20170307/20170307090124_31ff4e08a424c9490890dbf790237906_2_mwpm_03200403.jpeg
     * thumbnail_pic_s03 : http://08.imgmini.eastday.com/mobile/20170307/20170307090124_31ff4e08a424c9490890dbf790237906_3_mwpm_03200403.jpeg
     */
    @Id
    private Long id;
    @Unique
    private String uniquekey;
    private String title;
    private String date;
    private String category;
    private String author_name;
    private String url;
    private String thumbnail_pic_s;
    private String thumbnail_pic_s02;
    private String thumbnail_pic_s03;

    @Generated(hash = 877029569)
    public Data_news(Long id, String uniquekey, String title, String date, String category, String author_name, String url,
            String thumbnail_pic_s, String thumbnail_pic_s02, String thumbnail_pic_s03) {
        this.id = id;
        this.uniquekey = uniquekey;
        this.title = title;
        this.date = date;
        this.category = category;
        this.author_name = author_name;
        this.url = url;
        this.thumbnail_pic_s = thumbnail_pic_s;
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }

    @Generated(hash = 473996451)
    public Data_news() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUniquekey() {
        return this.uniquekey;
    }

    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor_name() {
        return this.author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail_pic_s() {
        return this.thumbnail_pic_s;
    }

    public void setThumbnail_pic_s(String thumbnail_pic_s) {
        this.thumbnail_pic_s = thumbnail_pic_s;
    }

    public String getThumbnail_pic_s02() {
        return this.thumbnail_pic_s02;
    }

    public void setThumbnail_pic_s02(String thumbnail_pic_s02) {
        this.thumbnail_pic_s02 = thumbnail_pic_s02;
    }

    public String getThumbnail_pic_s03() {
        return this.thumbnail_pic_s03;
    }

    public void setThumbnail_pic_s03(String thumbnail_pic_s03) {
        this.thumbnail_pic_s03 = thumbnail_pic_s03;
    }

    public void setId(Long id) {
        this.id = id;
    }


}