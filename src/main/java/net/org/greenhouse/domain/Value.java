package net.org.greenhouse.domain;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created by Selector on 05.05.2015.
 */
@Document(collection = "T_VALUES")
public class Value implements Serializable {

    @Id
    private String type;

    @Field("value")
    private String value;

    @Field("max")
    private String max;

    @Field("last_modified_date")
    private DateTime lastModifiedDate;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(DateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Value value1 = (Value) o;

        if (type != null ? !type.equals(value1.type) : value1.type != null) return false;
        if (value != null ? !value.equals(value1.value) : value1.value != null) return false;
        if (max != null ? !max.equals(value1.max) : value1.max != null) return false;
        return !(lastModifiedDate != null ? !lastModifiedDate.equals(value1.lastModifiedDate) : value1.lastModifiedDate != null);

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (max != null ? max.hashCode() : 0);
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0);
        return result;
    }
}
