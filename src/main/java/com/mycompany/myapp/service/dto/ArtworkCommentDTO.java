package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.ArtworkComment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkCommentDTO implements Serializable {

    private Long id;

    private Long member;

    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        if (!(o instanceof ArtworkCommentDTO)) {
            return false;
        }

        ArtworkCommentDTO artworkCommentDTO = (ArtworkCommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, artworkCommentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkCommentDTO{" +
            "id=" + getId() +
            ", member=" + getMember() +
            ", content='" + getContent() + "'" +
            ", artwork=" + getArtwork() +
            "}";
    }
}
