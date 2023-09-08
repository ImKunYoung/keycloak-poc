package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ArtistLike.
 */
@Entity
@Table(name = "artist_like")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ArtistLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "vo_member")
    private Long voMember;

    @ManyToOne
    @JsonIgnoreProperties(value = { "artistcomments", "artistviews", "artistlikes" }, allowSetters = true)
    private Artist artist;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ArtistLike id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVoMember() {
        return this.voMember;
    }

    public ArtistLike voMember(Long voMember) {
        this.setVoMember(voMember);
        return this;
    }

    public void setVoMember(Long voMember) {
        this.voMember = voMember;
    }

    public Artist getArtist() {
        return this.artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public ArtistLike artist(Artist artist) {
        this.setArtist(artist);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ArtistLike)) {
            return false;
        }
        return id != null && id.equals(((ArtistLike) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ArtistLike{" +
            "id=" + getId() +
            ", voMember=" + getVoMember() +
            "}";
    }
}
