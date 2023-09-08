package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ArtworkView;
import com.mycompany.myapp.repository.ArtworkViewRepository;
import com.mycompany.myapp.service.ArtworkViewService;
import com.mycompany.myapp.service.dto.ArtworkViewDTO;
import com.mycompany.myapp.service.mapper.ArtworkViewMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ArtworkView}.
 */
@Service
@Transactional
public class ArtworkViewServiceImpl implements ArtworkViewService {

    private final Logger log = LoggerFactory.getLogger(ArtworkViewServiceImpl.class);

    private final ArtworkViewRepository artworkViewRepository;

    private final ArtworkViewMapper artworkViewMapper;

    public ArtworkViewServiceImpl(ArtworkViewRepository artworkViewRepository, ArtworkViewMapper artworkViewMapper) {
        this.artworkViewRepository = artworkViewRepository;
        this.artworkViewMapper = artworkViewMapper;
    }

    @Override
    public ArtworkViewDTO save(ArtworkViewDTO artworkViewDTO) {
        log.debug("Request to save ArtworkView : {}", artworkViewDTO);
        ArtworkView artworkView = artworkViewMapper.toEntity(artworkViewDTO);
        artworkView = artworkViewRepository.save(artworkView);
        return artworkViewMapper.toDto(artworkView);
    }

    @Override
    public ArtworkViewDTO update(ArtworkViewDTO artworkViewDTO) {
        log.debug("Request to update ArtworkView : {}", artworkViewDTO);
        ArtworkView artworkView = artworkViewMapper.toEntity(artworkViewDTO);
        artworkView = artworkViewRepository.save(artworkView);
        return artworkViewMapper.toDto(artworkView);
    }

    @Override
    public Optional<ArtworkViewDTO> partialUpdate(ArtworkViewDTO artworkViewDTO) {
        log.debug("Request to partially update ArtworkView : {}", artworkViewDTO);

        return artworkViewRepository
            .findById(artworkViewDTO.getId())
            .map(existingArtworkView -> {
                artworkViewMapper.partialUpdate(existingArtworkView, artworkViewDTO);

                return existingArtworkView;
            })
            .map(artworkViewRepository::save)
            .map(artworkViewMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtworkViewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArtworkViews");
        return artworkViewRepository.findAll(pageable).map(artworkViewMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkViewDTO> findOne(Long id) {
        log.debug("Request to get ArtworkView : {}", id);
        return artworkViewRepository.findById(id).map(artworkViewMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtworkView : {}", id);
        artworkViewRepository.deleteById(id);
    }
}
