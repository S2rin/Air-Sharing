package com.airsharing.company.airsharing.model;

import java.io.Serializable;

/**
 * Created by hyunju on 2017-11-06.
 */

public class UploadContent extends Content{
    public UploadContent() {
    }

    public UploadContent(String uuid, String dust, String content) {
        this.uuid = uuid;
        this.dust = dust;
        this.content = content;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDust() {
        return dust;
    }

    public void setDust(String dust) {
        this.dust = dust;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDetailedRegion() {
        return detailedRegion;
    }

    public void setDetailedRegion(String detailedRegion) {
        this.detailedRegion = detailedRegion;
    }

}
