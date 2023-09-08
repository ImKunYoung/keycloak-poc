import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArtwork } from 'app/shared/model/artwork.model';
import { getEntities as getArtworks } from 'app/entities/artwork/artwork.reducer';
import { IArtworkView } from 'app/shared/model/artwork-view.model';
import { getEntity, updateEntity, createEntity, reset } from './artwork-view.reducer';

export const ArtworkViewUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const artworks = useAppSelector(state => state.artwork.entities);
  const artworkViewEntity = useAppSelector(state => state.artworkView.entity);
  const loading = useAppSelector(state => state.artworkView.loading);
  const updating = useAppSelector(state => state.artworkView.updating);
  const updateSuccess = useAppSelector(state => state.artworkView.updateSuccess);

  const handleClose = () => {
    navigate('/artwork-view');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getArtworks({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...artworkViewEntity,
      ...values,
      artwork: artworks.find(it => it.id.toString() === values.artwork.toString()),
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
          ...artworkViewEntity,
          artwork: artworkViewEntity?.artwork?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keycloakpocApp.artworkView.home.createOrEditLabel" data-cy="ArtworkViewCreateUpdateHeading">
            Create or edit a Artwork View
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="artwork-view-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Member" id="artwork-view-member" name="member" data-cy="member" type="text" />
              <ValidatedField id="artwork-view-artwork" name="artwork" data-cy="artwork" label="Artwork" type="select">
                <option value="" key="0" />
                {artworks
                  ? artworks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/artwork-view" replace color="info">
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

export default ArtworkViewUpdate;
