package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Exhibition} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExhibitionDTO implements Serializable {

    private Long id;

    private String title;

    private String location;

    private String fee;

    private String contact;

    private String imgUrl;

    private String voPeriod;

    private String voArtist;

    private String voMember;

    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getVoPeriod() {
        return voPeriod;
    }

    public void setVoPeriod(String voPeriod) {
        this.voPeriod = voPeriod;
    }

    public String getVoArtist() {
        return voArtist;
    }

    public void setVoArtist(String voArtist) {
        this.voArtist = voArtist;
    }

    public String getVoMember() {
        return voMember;
    }

    public void setVoMember(String voMember) {
        this.voMember = voMember;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExhibitionDTO)) {
            return false;
        }

        ExhibitionDTO exhibitionDTO = (ExhibitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, exhibitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ExhibitionDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", location='" + getLocation() + "'" +
            ", fee='" + getFee() + "'" +
            ", contact='" + getContact() + "'" +
            ", imgUrl='" + getImgUrl() + "'" +
            ", voPeriod='" + getVoPeriod() + "'" +
            ", voArtist='" + getVoArtist() + "'" +
            ", voMember='" + getVoMember() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
