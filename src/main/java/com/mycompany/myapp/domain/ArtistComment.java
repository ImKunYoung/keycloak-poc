package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ArtistComment.
 */
@Entity
@Table(name = "artist_comment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtistComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "vo_member")
    private Long voMember;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JsonIgnoreProperties(value = { "artistcomments", "artistviews", "artistlikes" }, allowSetters = true)
    private Artist artist;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArtistComment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVoMember() {
        return this.voMember;
    }

    public ArtistComment voMember(Long voMember) {
        this.setVoMember(voMember);
        return this;
    }

    public void setVoMember(Long voMember) {
        this.voMember = voMember;
    }

    public String getContent() {
        return this.content;
    }

    public ArtistComment content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Artist getArtist() {
        return this.artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public ArtistComment artist(Artist artist) {
        this.setArtist(artist);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtistComment)) {
            return false;
        }
        return id != null && id.equals(((ArtistComment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtistComment{" +
            "id=" + getId() +
            ", voMember=" + getVoMember() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
