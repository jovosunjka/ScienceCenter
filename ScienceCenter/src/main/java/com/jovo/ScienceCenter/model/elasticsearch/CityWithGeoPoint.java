package com.jovo.ScienceCenter.model.elasticsearch;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = CityWithGeoPoint.INDEX_NAME, type = CityWithGeoPoint.TYPE_NAME, shards = 1, replicas = 0)
//@Setting(settingPath = "/elasticsearch/mappings.json")
public class CityWithGeoPoint {
    public static final String INDEX_NAME = "index_for_city";
    public static final String TYPE_NAME = "city";

    @Id
    @Field(type = FieldType.Text)
    private String name;

    //@Field(type = FieldType.Object)
    @GeoPointField
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private GeoPoint location;


    public CityWithGeoPoint() {

    }

    public CityWithGeoPoint(String name, double latitude, double longitude) {
        this.name = name;
        this.location = new GeoPoint(latitude, longitude);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }
}
