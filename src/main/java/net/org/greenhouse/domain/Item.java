package net.org.greenhouse.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.org.greenhouse.domain.util.CustomDateTimeDeserializer;
import net.org.greenhouse.domain.util.CustomDateTimeSerializer;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Item.
 */
@Document(collection = "T_ITEM")
public class Item implements Serializable {

    @Id
    private String id;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("title")
    private DateTime title;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("createDate")
    private DateTime createDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Field("modifiedDate")
    private DateTime modifiedDate;

    @Field("collectionId")
    private Long collectionId;

    @Field("values")
    private Inner values;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DateTime getTitle() {
        return title;
    }

    public void setTitle(DateTime title) {
        this.title = title;
    }

    public DateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(DateTime createDate) {
        this.createDate = createDate;
    }

    public DateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(DateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

    public Inner getValues() {
        return values;
    }

    public void setValues(Inner values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Item item = (Item) o;

        if (!Objects.equals(id, item.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", createDate='" + createDate + "'" +
            ", modifiedDate='" + modifiedDate + "'" +
            ", collectionId='" + collectionId + "'" +
            ", values='" + values + "'" +
            '}';
    }

    public static class Inner {
        @JsonProperty("inner_temp")
        private Long innerTemp;
        @JsonProperty("inner_humidity")
        private Long innerHumidity;

        public Long getInnerTemp() {
            return innerTemp;
        }

        public void setInnerTemp(Long innerTemp) {
            this.innerTemp = innerTemp;
        }

        public Long getInnerHumidity() {
            return innerHumidity;
        }

        public void setInnerHumidity(Long innerHumidity) {
            this.innerHumidity = innerHumidity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Inner inner = (Inner) o;

            if (innerHumidity != null ? !innerHumidity.equals(inner.innerHumidity) : inner.innerHumidity != null)
                return false;
            if (innerTemp != null ? !innerTemp.equals(inner.innerTemp) : inner.innerTemp != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = innerTemp != null ? innerTemp.hashCode() : 0;
            result = 31 * result + (innerHumidity != null ? innerHumidity.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Inner{" +
                "innerTemp=" + innerTemp +
                ", innerHumidity=" + innerHumidity +
                '}';
        }
    }
}
