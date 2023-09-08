package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ArtistLike;
import com.mycompany.myapp.repository.ArtistLikeRepository;
import com.mycompany.myapp.service.ArtistLikeService;
import com.mycompany.myapp.service.dto.ArtistLikeDTO;
import com.mycompany.myapp.service.mapper.ArtistLikeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ArtistLike}.
 */
@Service
@Transactional
public class ArtistLikeServiceImpl implements ArtistLikeService {

    private final Logger log = LoggerFactory.getLogger(ArtistLikeServiceImpl.class);

    private final ArtistLikeRepository artistLikeRepository;

    private final ArtistLikeMapper artistLikeMapper;

    public ArtistLikeServiceImpl(ArtistLikeRepository artistLikeRepository, ArtistLikeMapper artistLikeMapper) {
        this.artistLikeRepository = artistLikeRepository;
        this.artistLikeMapper = artistLikeMapper;
    }

    @Override
    public ArtistLikeDTO save(ArtistLikeDTO artistLikeDTO) {
        log.debug("Request to save ArtistLike : {}", artistLikeDTO);
        ArtistLike artistLike = artistLikeMapper.toEntity(artistLikeDTO);
        artistLike = artistLikeRepository.save(artistLike);
        return artistLikeMapper.toDto(artistLike);
    }

    @Override
    public ArtistLikeDTO update(ArtistLikeDTO artistLikeDTO) {
        log.debug("Request to update ArtistLike : {}", artistLikeDTO);
        ArtistLike artistLike = artistLikeMapper.toEntity(artistLikeDTO);
        artistLike = artistLikeRepository.save(artistLike);
        return artistLikeMapper.toDto(artistLike);
    }

    @Override
    public Optional<ArtistLikeDTO> partialUpdate(ArtistLikeDTO artistLikeDTO) {
        log.debug("Request to partially update ArtistLike : {}", artistLikeDTO);

        return artistLikeRepository
            .findById(artistLikeDTO.getId())
            .map(existingArtistLike -> {
                artistLikeMapper.partialUpdate(existingArtistLike, artistLikeDTO);

                return existingArtistLike;
            })
            .map(artistLikeRepository::save)
            .map(artistLikeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistLikeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArtistLikes");
        return artistLikeRepository.findAll(pageable).map(artistLikeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtistLikeDTO> findOne(Long id) {
        log.debug("Request to get ArtistLike : {}", id);
        return artistLikeRepository.findById(id).map(artistLikeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtistLike : {}", id);
        artistLikeRepository.deleteById(id);
    }
}
