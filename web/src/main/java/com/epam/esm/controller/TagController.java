package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is an endpoint of the API which allows to perform CRD operations on {@link Tag}.
 * @author Murod Komilov
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/tags")
public class TagController {
    private final TagService tagService;

    /**
     * Returns all {@link Tag} items in db
     * @return {@link List} of {@link Tag} models.
     * @throws DaoException if something went wrong while retrieving data from db
     */
    @GetMapping
    public List<Tag> getAllTags() throws DaoException {
        return tagService.getAll();
    }

    /**
     * Returns a {@link Tag} by its ID from db.
     * @param id a {@link Tag} ID.
     * @return a {@link Tag} model.
     * @throws DaoException if {@link Tag} specified ID not found.
     * @throws BadRequestException if specified ID is not valid.
     */
    @GetMapping("/{id}")
    public Tag tagById(@PathVariable Long id) throws DaoException, BadRequestException {
        return tagService.getById(id);
    }

    /**
     * Creates a new {@link TagDto} model in db.
     * @param tagDto a new {@link TagDto} dto for saving.
     * @return CREATED HttpStatus.
     * @throws DaoException if error occurred while saving a model.
     * @throws BadRequestException if the {@link TagDto} dto contains incorrect param.
     */
    @PostMapping
    public ResponseEntity<?> createTag(@RequestBody TagDto tagDto) throws DaoException, BadRequestException {
        Tag tag = tagService.create(tagDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tag + " is successfully created!!!");
    }

    /**
     * Removes from db a {@link Tag} by specified ID.
     * @param id a {@link Tag} ID.
     * @return NO_CONTENT HttpStatus
     * @throws DaoException if the {@link Tag} model with specified id not found.
     * @throws BadRequestException if specified ID is not valid.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws DaoException, BadRequestException {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
