package cloud.fmunozse.demojpaentitywrapperpayload.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import javax.persistence.*;
import java.io.IOException;

@Data
@Entity
public class Payment {

    private class WrapperPayloadTyped {
        protected Object objectPayload;
        protected Class clazz;
    }

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String payload;

    @Transient
    private WrapperPayloadTyped wrapperPayloadTyped;


    public void setPayload(String payload) {
        this.wrapperPayloadTyped = null;
        this.payload = payload;
    }

    public String getPayload() {
        if (this.wrapperPayloadTyped != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                this.payload = mapper.writeValueAsString(this.wrapperPayloadTyped.objectPayload);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return this.payload ;
    }


    public <T> T getPayloadTyped (Class<T> clazz) {
        if (wrapperPayloadTyped != null) {
            return (T) wrapperPayloadTyped.objectPayload;
        }

        if (payload == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        WrapperPayloadTyped wrapper = new WrapperPayloadTyped();
        wrapper.clazz = clazz;
        try {
            wrapper.objectPayload = mapper.readValue(payload, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.wrapperPayloadTyped = wrapper;
        return (T) wrapper.objectPayload;
    }

    public void setPayloadTyped(Class clazz, Object payload) {
        WrapperPayloadTyped wrapper = new WrapperPayloadTyped();
        wrapper.clazz = clazz;
        wrapper.objectPayload = payload;
        this.wrapperPayloadTyped = wrapper;
    }


    @PrePersist
    private void prePersist() {
        if (this.wrapperPayloadTyped == null) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            this.payload = mapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
