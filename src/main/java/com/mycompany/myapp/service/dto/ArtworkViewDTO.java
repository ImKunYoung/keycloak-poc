package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ArtworkView} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkViewDTO implements Serializable {

    private Long id;

    private Long member;

    private ArtworkDTO artwork;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMember() {
        return member;
    }

    public void setMember(Long member) {
        this.member = member;
    }

    public ArtworkDTO getArtwork() {
        return artwork;
    }

    public void setArtwork(ArtworkDTO artwork) {
        this.artwork = artwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtworkViewDTO)) {
            return false;
        }

        ArtworkViewDTO artworkViewDTO = (ArtworkViewDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artworkViewDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkViewDTO{" +
            "id=" + getId() +
            ", member=" + getMember() +
            ", artwork=" + getArtwork() +
            "}";
    }
}
