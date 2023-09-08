package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Exhibition;
import com.mycompany.myapp.repository.ExhibitionRepository;
import com.mycompany.myapp.service.ExhibitionService;
import com.mycompany.myapp.service.dto.ExhibitionDTO;
import com.mycompany.myapp.service.mapper.ExhibitionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Exhibition}.
 */
@Service
@Transactional
public class ExhibitionServiceImpl implements ExhibitionService {

    private final Logger log = LoggerFactory.getLogger(ExhibitionServiceImpl.class);

    private final ExhibitionRepository exhibitionRepository;

    private final ExhibitionMapper exhibitionMapper;

    public ExhibitionServiceImpl(ExhibitionRepository exhibitionRepository, ExhibitionMapper exhibitionMapper) {
        this.exhibitionRepository = exhibitionRepository;
        this.exhibitionMapper = exhibitionMapper;
    }

    @Override
    public ExhibitionDTO save(ExhibitionDTO exhibitionDTO) {
        log.debug("Request to save Exhibition : {}", exhibitionDTO);
        Exhibition exhibition = exhibitionMapper.toEntity(exhibitionDTO);
        exhibition = exhibitionRepository.save(exhibition);
        return exhibitionMapper.toDto(exhibition);
    }

    @Override
    public ExhibitionDTO update(ExhibitionDTO exhibitionDTO) {
        log.debug("Request to update Exhibition : {}", exhibitionDTO);
        Exhibition exhibition = exhibitionMapper.toEntity(exhibitionDTO);
        exhibition = exhibitionRepository.save(exhibition);
        return exhibitionMapper.toDto(exhibition);
    }

    @Override
    public Optional<ExhibitionDTO> partialUpdate(ExhibitionDTO exhibitionDTO) {
        log.debug("Request to partially update Exhibition : {}", exhibitionDTO);

        return exhibitionRepository
            .findById(exhibitionDTO.getId())
            .map(existingExhibition -> {
                exhibitionMapper.partialUpdate(existingExhibition, exhibitionDTO);

                return existingExhibition;
            })
            .map(exhibitionRepository::save)
            .map(exhibitionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExhibitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Exhibitions");
        return exhibitionRepository.findAll(pageable).map(exhibitionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExhibitionDTO> findOne(Long id) {
        log.debug("Request to get Exhibition : {}", id);
        return exhibitionRepository.findById(id).map(exhibitionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Exhibition : {}", id);
        exhibitionRepository.deleteById(id);
    }
}
