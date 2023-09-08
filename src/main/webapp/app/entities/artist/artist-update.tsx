import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArtist } from 'app/shared/model/artist.model';
import { Status } from 'app/shared/model/enumerations/status.model';
import { getEntity, updateEntity, createEntity, reset } from './artist.reducer';

export const ArtistUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const artistEntity = useAppSelector(state => state.artist.entity);
  const loading = useAppSelector(state => state.artist.loading);
  const updating = useAppSelector(state => state.artist.updating);
  const updateSuccess = useAppSelector(state => state.artist.updateSuccess);
  const statusValues = Object.keys(Status);

  const handleClose = () => {
    navigate('/artist' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...artistEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          status: 'UPLOAD_PENDING',
          ...artistEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keycloakpocApp.artist.home.createOrEditLabel" data-cy="ArtistCreateUpdateHeading">
            Create or edit a Artist
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="artist-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Name" id="artist-name" name="name" data-cy="name" type="text" />
              <ValidatedField label="Real Name" id="artist-realName" name="realName" data-cy="realName" type="text" />
              <ValidatedField label="Img Url" id="artist-imgUrl" name="imgUrl" data-cy="imgUrl" type="text" />
              <ValidatedField label="Phone" id="artist-phone" name="phone" data-cy="phone" type="text" />
              <ValidatedField label="Career" id="artist-career" name="career" data-cy="career" type="text" />
              <ValidatedField label="Vo Artwork" id="artist-voArtwork" name="voArtwork" data-cy="voArtwork" type="text" />
              <ValidatedField label="Vo Member" id="artist-voMember" name="voMember" data-cy="voMember" type="text" />
              <ValidatedField label="Status" id="artist-status" name="status" data-cy="status" type="select">
                {statusValues.map(status => (
                  <option value={status} key={status}>
                    {status}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/artist" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">뒤로</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; 저장
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ArtistUpdate;
