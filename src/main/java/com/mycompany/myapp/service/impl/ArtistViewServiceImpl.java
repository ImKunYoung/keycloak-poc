package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ArtistView;
import com.mycompany.myapp.repository.ArtistViewRepository;
import com.mycompany.myapp.service.ArtistViewService;
import com.mycompany.myapp.service.dto.ArtistViewDTO;
import com.mycompany.myapp.service.mapper.ArtistViewMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ArtistView}.
 */
@Service
@Transactional
public class ArtistViewServiceImpl implements ArtistViewService {

    private final Logger log = LoggerFactory.getLogger(ArtistViewServiceImpl.class);

    private final ArtistViewRepository artistViewRepository;

    private final ArtistViewMapper artistViewMapper;

    public ArtistViewServiceImpl(ArtistViewRepository artistViewRepository, ArtistViewMapper artistViewMapper) {
        this.artistViewRepository = artistViewRepository;
        this.artistViewMapper = artistViewMapper;
    }

    @Override
    public ArtistViewDTO save(ArtistViewDTO artistViewDTO) {
        log.debug("Request to save ArtistView : {}", artistViewDTO);
        ArtistView artistView = artistViewMapper.toEntity(artistViewDTO);
        artistView = artistViewRepository.save(artistView);
        return artistViewMapper.toDto(artistView);
    }

    @Override
    public ArtistViewDTO update(ArtistViewDTO artistViewDTO) {
        log.debug("Request to update ArtistView : {}", artistViewDTO);
        ArtistView artistView = artistViewMapper.toEntity(artistViewDTO);
        artistView = artistViewRepository.save(artistView);
        return artistViewMapper.toDto(artistView);
    }

    @Override
    public Optional<ArtistViewDTO> partialUpdate(ArtistViewDTO artistViewDTO) {
        log.debug("Request to partially update ArtistView : {}", artistViewDTO);

        return artistViewRepository
            .findById(artistViewDTO.getId())
            .map(existingArtistView -> {
                artistViewMapper.partialUpdate(existingArtistView, artistViewDTO);

                return existingArtistView;
            })
            .map(artistViewRepository::save)
            .map(artistViewMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistViewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArtistViews");
        return artistViewRepository.findAll(pageable).map(artistViewMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtistViewDTO> findOne(Long id) {
        log.debug("Request to get ArtistView : {}", id);
        return artistViewRepository.findById(id).map(artistViewMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtistView : {}", id);
        artistViewRepository.deleteById(id);
    }
}
