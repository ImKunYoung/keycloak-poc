package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ArtistComment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtistCommentDTO implements Serializable {

    private Long id;

    private Long voMember;

    private String content;

    private ArtistDTO artist;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArtistDTO getArtist() {
        return artist;
    }

    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtistCommentDTO)) {
            return false;
        }

        ArtistCommentDTO artistCommentDTO = (ArtistCommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artistCommentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtistCommentDTO{" +
            "id=" + getId() +
            ", voMember=" + getVoMember() +
            ", content='" + getContent() + "'" +
            ", artist=" + getArtist() +
            "}";
    }
}
