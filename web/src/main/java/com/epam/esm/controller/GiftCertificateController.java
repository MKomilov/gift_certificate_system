package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.BadRequestException;
import com.epam.esm.exception.DaoException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * This class is an endpoint of the API which allows to perform CRUD operations on {@link GiftCertificate}.
 * @author Murod Komilov
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gift_certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;

    /**
     * Returns all {@link GiftCertificate} items in db
     * @return {@link List} of {@link GiftCertificate} models.
     * @throws DaoException if something went wrong while retrieving data from db
     */
    @GetMapping
    public List<GiftCertificate> allCertificates() throws DaoException {
        return giftCertificateService.getAll();
    }

    /**
     * Creates a new {@link GiftCertificateDto} model in db.
     * @param giftDto a new {@link GiftCertificateDto} dto for saving.
     * @return CREATED HttpStatus.
     * @throws DaoException if error occurred while saving a model.
     * @throws BadRequestException if the {@link GiftCertificateDto} dto contains incorrect param.
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody GiftCertificateDto giftDto) throws DaoException, BadRequestException {
        GiftCertificate giftCertificate = giftCertificateService.create(giftDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(giftCertificate + " is successfully created!!!");
    }

    /**
     * Returns a {@link GiftCertificate} by its ID from db.
     * @param id a {@link GiftCertificate} ID.
     * @return a {@link GiftCertificate} model.
     * @throws DaoException if {@link GiftCertificate} specified ID not found.
     * @throws BadRequestException if specified ID is not valid.
     */
    @GetMapping("/{id}")
    public GiftCertificate getById(@PathVariable Long id) throws DaoException, BadRequestException {
        return giftCertificateService.getById(id);
    }

    /**
     * Updates a {@link GiftCertificate} in db by specified ID.
     * @param id a {@link GiftCertificate} ID.
     * @param certificateDto a {@link GiftCertificateDto} that contains information for updating.
     * @return OK HttpStatus
     * @throws DaoException if the {@link GiftCertificate} model with specified id not found.
     * @throws BadRequestException if the {@link GiftCertificateDto} contains incorrect information.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCertificate(@PathVariable Long id,
                                               @RequestBody GiftCertificateDto certificateDto) throws DaoException, BadRequestException {
        giftCertificateService.update(id, certificateDto);
        return ResponseEntity.status(HttpStatus.OK).body("Success");
    }

    /**
     * Removes from db a {@link GiftCertificate} by specified ID.
     * @param id a {@link GiftCertificate} ID.
     * @return NO_CONTENT HttpStatus
     * @throws DaoException if the {@link GiftCertificate} model with specified id not found.
     * @throws BadRequestException if specified ID is not valid.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) throws DaoException, BadRequestException {
        GiftCertificate delete = giftCertificateService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(delete.toString() + " is successfully deleted");
    }

    /**
     * Returns a {@link List} of {@link GiftCertificate} from db by special filter.
     * @param map request parameters which include the information needed for the search.
     * @return a {@link List} of found {@link GiftCertificate} entities.
     * @throws DaoException if {@link GiftCertificate} entities not found.
     * @throws BadRequestException if request parameters contains incorrect parameter values.
     */
    @GetMapping("/filter")
    public List<GiftCertificate> getFilteredCertificates(@RequestParam Map<String, String> map) throws DaoException, BadRequestException {
        return giftCertificateService.filter(map);
    }
}
