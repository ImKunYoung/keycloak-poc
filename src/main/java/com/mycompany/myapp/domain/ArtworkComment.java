package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ArtworkComment.
 */
@Entity
@Table(name = "artwork_comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtworkComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "member")
    private Long member;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JsonIgnoreProperties(value = { "artworkcomments", "artworkviews", "artworklikes" }, allowSetters = true)
    private Artwork artwork;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArtworkComment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMember() {
        return this.member;
    }

    public ArtworkComment member(Long member) {
        this.setMember(member);
        return this;
    }

    public void setMember(Long member) {
        this.member = member;
    }

    public String getContent() {
        return this.content;
    }

    public ArtworkComment content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Artwork getArtwork() {
        return this.artwork;
    }

    public void setArtwork(Artwork artwork) {
        this.artwork = artwork;
    }

    public ArtworkComment artwork(Artwork artwork) {
        this.setArtwork(artwork);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtworkComment)) {
            return false;
        }
        return id != null && id.equals(((ArtworkComment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtworkComment{" +
            "id=" + getId() +
            ", member=" + getMember() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
