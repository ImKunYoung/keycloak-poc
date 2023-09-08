package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ArtistComment;
import com.mycompany.myapp.repository.ArtistCommentRepository;
import com.mycompany.myapp.service.ArtistCommentService;
import com.mycompany.myapp.service.dto.ArtistCommentDTO;
import com.mycompany.myapp.service.mapper.ArtistCommentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ArtistComment}.
 */
@Service
@Transactional
public class ArtistCommentServiceImpl implements ArtistCommentService {

    private final Logger log = LoggerFactory.getLogger(ArtistCommentServiceImpl.class);

    private final ArtistCommentRepository artistCommentRepository;

    private final ArtistCommentMapper artistCommentMapper;

    public ArtistCommentServiceImpl(ArtistCommentRepository artistCommentRepository, ArtistCommentMapper artistCommentMapper) {
        this.artistCommentRepository = artistCommentRepository;
        this.artistCommentMapper = artistCommentMapper;
    }

    @Override
    public ArtistCommentDTO save(ArtistCommentDTO artistCommentDTO) {
        log.debug("Request to save ArtistComment : {}", artistCommentDTO);
        ArtistComment artistComment = artistCommentMapper.toEntity(artistCommentDTO);
        artistComment = artistCommentRepository.save(artistComment);
        return artistCommentMapper.toDto(artistComment);
    }

    @Override
    public ArtistCommentDTO update(ArtistCommentDTO artistCommentDTO) {
        log.debug("Request to update ArtistComment : {}", artistCommentDTO);
        ArtistComment artistComment = artistCommentMapper.toEntity(artistCommentDTO);
        artistComment = artistCommentRepository.save(artistComment);
        return artistCommentMapper.toDto(artistComment);
    }

    @Override
    public Optional<ArtistCommentDTO> partialUpdate(ArtistCommentDTO artistCommentDTO) {
        log.debug("Request to partially update ArtistComment : {}", artistCommentDTO);

        return artistCommentRepository
            .findById(artistCommentDTO.getId())
            .map(existingArtistComment -> {
                artistCommentMapper.partialUpdate(existingArtistComment, artistCommentDTO);

                return existingArtistComment;
            })
            .map(artistCommentRepository::save)
            .map(artistCommentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArtistComments");
        return artistCommentRepository.findAll(pageable).map(artistCommentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtistCommentDTO> findOne(Long id) {
        log.debug("Request to get ArtistComment : {}", id);
        return artistCommentRepository.findById(id).map(artistCommentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtistComment : {}", id);
        artistCommentRepository.deleteById(id);
    }
}
