import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArtwork } from 'app/shared/model/artwork.model';
import { Status } from 'app/shared/model/enumerations/status.model';
import { getEntity, updateEntity, createEntity, reset } from './artwork.reducer';

export const ArtworkUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const artworkEntity = useAppSelector(state => state.artwork.entity);
  const loading = useAppSelector(state => state.artwork.loading);
  const updating = useAppSelector(state => state.artwork.updating);
  const updateSuccess = useAppSelector(state => state.artwork.updateSuccess);
  const statusValues = Object.keys(Status);

  const handleClose = () => {
    navigate('/artwork' + location.search);
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
      ...artworkEntity,
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
          ...artworkEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keycloakpocApp.artwork.home.createOrEditLabel" data-cy="ArtworkCreateUpdateHeading">
            Create or edit a Artwork
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="artwork-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Title" id="artwork-title" name="title" data-cy="title" type="text" />
              <ValidatedField
                label="Short Description"
                id="artwork-shortDescription"
                name="shortDescription"
                data-cy="shortDescription"
                type="text"
              />
              <ValidatedField
                label="Long Description"
                id="artwork-longDescription"
                name="longDescription"
                data-cy="longDescription"
                type="text"
              />
              <ValidatedField label="Image Url" id="artwork-imageUrl" name="imageUrl" data-cy="imageUrl" type="text" />
              <ValidatedField label="Artistname" id="artwork-artistname" name="artistname" data-cy="artistname" type="text" />
              <ValidatedField label="Makingday" id="artwork-makingday" name="makingday" data-cy="makingday" type="text" />
              <ValidatedField label="Status" id="artwork-status" name="status" data-cy="status" type="select">
                {statusValues.map(status => (
                  <option value={status} key={status}>
                    {status}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/artwork" replace color="info">
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

export default ArtworkUpdate;
