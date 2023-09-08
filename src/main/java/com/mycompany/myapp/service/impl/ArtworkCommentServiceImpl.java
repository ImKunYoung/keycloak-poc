package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ArtworkComment;
import com.mycompany.myapp.repository.ArtworkCommentRepository;
import com.mycompany.myapp.service.ArtworkCommentService;
import com.mycompany.myapp.service.dto.ArtworkCommentDTO;
import com.mycompany.myapp.service.mapper.ArtworkCommentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ArtworkComment}.
 */
@Service
@Transactional
public class ArtworkCommentServiceImpl implements ArtworkCommentService {

    private final Logger log = LoggerFactory.getLogger(ArtworkCommentServiceImpl.class);

    private final ArtworkCommentRepository artworkCommentRepository;

    private final ArtworkCommentMapper artworkCommentMapper;

    public ArtworkCommentServiceImpl(ArtworkCommentRepository artworkCommentRepository, ArtworkCommentMapper artworkCommentMapper) {
        this.artworkCommentRepository = artworkCommentRepository;
        this.artworkCommentMapper = artworkCommentMapper;
    }

    @Override
    public ArtworkCommentDTO save(ArtworkCommentDTO artworkCommentDTO) {
        log.debug("Request to save ArtworkComment : {}", artworkCommentDTO);
        ArtworkComment artworkComment = artworkCommentMapper.toEntity(artworkCommentDTO);
        artworkComment = artworkCommentRepository.save(artworkComment);
        return artworkCommentMapper.toDto(artworkComment);
    }

    @Override
    public ArtworkCommentDTO update(ArtworkCommentDTO artworkCommentDTO) {
        log.debug("Request to update ArtworkComment : {}", artworkCommentDTO);
        ArtworkComment artworkComment = artworkCommentMapper.toEntity(artworkCommentDTO);
        artworkComment = artworkCommentRepository.save(artworkComment);
        return artworkCommentMapper.toDto(artworkComment);
    }

    @Override
    public Optional<ArtworkCommentDTO> partialUpdate(ArtworkCommentDTO artworkCommentDTO) {
        log.debug("Request to partially update ArtworkComment : {}", artworkCommentDTO);

        return artworkCommentRepository
            .findById(artworkCommentDTO.getId())
            .map(existingArtworkComment -> {
                artworkCommentMapper.partialUpdate(existingArtworkComment, artworkCommentDTO);

                return existingArtworkComment;
            })
            .map(artworkCommentRepository::save)
            .map(artworkCommentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtworkCommentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ArtworkComments");
        return artworkCommentRepository.findAll(pageable).map(artworkCommentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtworkCommentDTO> findOne(Long id) {
        log.debug("Request to get ArtworkComment : {}", id);
        return artworkCommentRepository.findById(id).map(artworkCommentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ArtworkComment : {}", id);
        artworkCommentRepository.deleteById(id);
    }
}
