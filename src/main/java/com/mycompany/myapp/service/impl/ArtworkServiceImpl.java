package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Artwork;
import com.mycompany.myapp.repository.ArtworkRepository;
import com.mycompany.myapp.service.ArtworkService;
import com.mycompany.myapp.service.dto.ArtworkDTO;
import com.mycompany.myapp.service.mapper.ArtworkMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Artwork}.
 */
@Service
@Transactional
public class ArtworkServiceImpl implements ArtworkService {

    private final Logger log = LoggerFactory.getLogger(ArtworkServiceImpl.class);

    private final ArtworkRepository artworkRepository;

    private final ArtworkMapper artworkMapper;

    public ArtworkServiceImpl(ArtworkRepository artworkRepository, ArtworkMapper artworkMapper) {
        this.artworkRepository = artworkRepository;
        this.artworkMapper = artworkMapper;
    }

    @Override
    public ArtworkDTO save(ArtworkDTO artworkDTO) {
        log.debug("Request to save Artwork : {}", artworkDTO);
        Artwork artwork = artworkMapper.toEntity(artworkDTO);
        artwork = artworkRepository.save(artwork);
        return artworkMapper.toDto(artwork);
    }

    @Override
    public ArtworkDTO update(ArtworkDTO artworkDTO) {
        log.debug("Request to update Artwork : {}", artworkDTO);
        Artwork artwork = artworkMapper.toEntity(artworkDTO);
        artwork = artworkRepository.save(artwork);
        return artworkMapper.toDto(artwork);
    }

    @Override
    public Optional<ArtworkDTO> partialUpdate(ArtworkDTO artworkDTO) {
        log.debug("Request to partially update Artwork : {}", artworkDTO);

        return artworkRepository
            .findById(artworkDTO.getId())
            .map(existingArtwork -> {
                artworkMapper.partialUpdate(existingArtwork, artworkDTO);

                return existingArtwork;
            })
            .map(artworkRepository::save)
            .map(artworkMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtworkDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Artworks");
        return artworkRepository.findAll(pageable).map(artworkMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkDTO> findOne(Long id) {
        log.debug("Request to get Artwork : {}", id);
        return artworkRepository.findById(id).map(artworkMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Artwork : {}", id);
        artworkRepository.deleteById(id);
    }
}
