package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.View} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ViewDTO implements Serializable {

    private Long id;

    private Long voMember;

    private ExhibitionDTO artwork;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVoMember() {
        return voMember;
    }

    public void setVoMember(Long voMember) {
        this.voMember = voMember;
    }

    public ExhibitionDTO getArtwork() {
        return artwork;
    }

    public void setArtwork(ExhibitionDTO artwork) {
        this.artwork = artwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewDTO)) {
            return false;
        }

        ViewDTO viewDTO = (ViewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, viewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ViewDTO{" +
            "id=" + getId() +
            ", voMember=" + getVoMember() +
            ", artwork=" + getArtwork() +
            "}";
    }
}
