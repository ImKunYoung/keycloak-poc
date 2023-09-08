package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Artwork.
 */
@Entity
@Table(name = "artwork")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Artwork implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "short_description")
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "artistname")
    private String artistname;

    @Column(name = "makingday")
    private String makingday;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @OneToMany(mappedBy = "artwork")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "artwork" }, allowSetters = true)
    private Set<ArtworkComment> artworkcomments = new HashSet<>();

    @OneToMany(mappedBy = "artwork")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "artwork" }, allowSetters = true)
    private Set<ArtworkView> artworkviews = new HashSet<>();

    @OneToMany(mappedBy = "artwork")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "artwork" }, allowSetters = true)
    private Set<ArtworkLike> artworklikes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Artwork id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Artwork title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return this.shortDescription;
    }

    public Artwork shortDescription(String shortDescription) {
        this.setShortDescription(shortDescription);
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return this.longDescription;
    }

    public Artwork longDescription(String longDescription) {
        this.setLongDescription(longDescription);
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Artwork imageUrl(String imageUrl) {
        this.setImageUrl(imageUrl);
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getArtistname() {
        return this.artistname;
    }

    public Artwork artistname(String artistname) {
        this.setArtistname(artistname);
        return this;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public String getMakingday() {
        return this.makingday;
    }

    public Artwork makingday(String makingday) {
        this.setMakingday(makingday);
        return this;
    }

    public void setMakingday(String makingday) {
        this.makingday = makingday;
    }

    public Status getStatus() {
        return this.status;
    }

    public Artwork status(Status status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<ArtworkComment> getArtworkcomments() {
        return this.artworkcomments;
    }

    public void setArtworkcomments(Set<ArtworkComment> artworkComments) {
        if (this.artworkcomments != null) {
            this.artworkcomments.forEach(i -> i.setArtwork(null));
        }
        if (artworkComments != null) {
            artworkComments.forEach(i -> i.setArtwork(this));
        }
        this.artworkcomments = artworkComments;
    }

    public Artwork artworkcomments(Set<ArtworkComment> artworkComments) {
        this.setArtworkcomments(artworkComments);
        return this;
    }

    public Artwork addArtworkcomments(ArtworkComment artworkComment) {
        this.artworkcomments.add(artworkComment);
        artworkComment.setArtwork(this);
        return this;
    }

    public Artwork removeArtworkcomments(ArtworkComment artworkComment) {
        this.artworkcomments.remove(artworkComment);
        artworkComment.setArtwork(null);
        return this;
    }

    public Set<ArtworkView> getArtworkviews() {
        return this.artworkviews;
    }

    public void setArtworkviews(Set<ArtworkView> artworkViews) {
        if (this.artworkviews != null) {
            this.artworkviews.forEach(i -> i.setArtwork(null));
        }
        if (artworkViews != null) {
            artworkViews.forEach(i -> i.setArtwork(this));
        }
        this.artworkviews = artworkViews;
    }

    public Artwork artworkviews(Set<ArtworkView> artworkViews) {
        this.setArtworkviews(artworkViews);
        return this;
    }

    public Artwork addArtworkviews(ArtworkView artworkView) {
        this.artworkviews.add(artworkView);
        artworkView.setArtwork(this);
        return this;
    }

    public Artwork removeArtworkviews(ArtworkView artworkView) {
        this.artworkviews.remove(artworkView);
        artworkView.setArtwork(null);
        return this;
    }

    public Set<ArtworkLike> getArtworklikes() {
        return this.artworklikes;
    }

    public void setArtworklikes(Set<ArtworkLike> artworkLikes) {
        if (this.artworklikes != null) {
            this.artworklikes.forEach(i -> i.setArtwork(null));
        }
        if (artworkLikes != null) {
            artworkLikes.forEach(i -> i.setArtwork(this));
        }
        this.artworklikes = artworkLikes;
    }

    public Artwork artworklikes(Set<ArtworkLike> artworkLikes) {
        this.setArtworklikes(artworkLikes);
        return this;
    }

    public Artwork addArtworklikes(ArtworkLike artworkLike) {
        this.artworklikes.add(artworkLike);
        artworkLike.setArtwork(this);
        return this;
    }

    public Artwork removeArtworklikes(ArtworkLike artworkLike) {
        this.artworklikes.remove(artworkLike);
        artworkLike.setArtwork(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Artwork)) {
            return false;
        }
        return id != null && id.equals(((Artwork) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Artwork{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", shortDescription='" + getShortDescription() + "'" +
            ", longDescription='" + getLongDescription() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            ", artistname='" + getArtistname() + "'" +
            ", makingday='" + getMakingday() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
