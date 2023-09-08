import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArtist } from 'app/shared/model/artist.model';
import { getEntities as getArtists } from 'app/entities/artist/artist.reducer';
import { IArtistLike } from 'app/shared/model/artist-like.model';
import { getEntity, updateEntity, createEntity, reset } from './artist-like.reducer';

export const ArtistLikeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const artists = useAppSelector(state => state.artist.entities);
  const artistLikeEntity = useAppSelector(state => state.artistLike.entity);
  const loading = useAppSelector(state => state.artistLike.loading);
  const updating = useAppSelector(state => state.artistLike.updating);
  const updateSuccess = useAppSelector(state => state.artistLike.updateSuccess);

  const handleClose = () => {
    navigate('/artist-like');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getArtists({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...artistLikeEntity,
      ...values,
      artist: artists.find(it => it.id.toString() === values.artist.toString()),
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
          ...artistLikeEntity,
          artist: artistLikeEntity?.artist?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keycloakpocApp.artistLike.home.createOrEditLabel" data-cy="ArtistLikeCreateUpdateHeading">
            Create or edit a Artist Like
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="artist-like-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Vo Member" id="artist-like-voMember" name="voMember" data-cy="voMember" type="text" />
              <ValidatedField id="artist-like-artist" name="artist" data-cy="artist" label="Artist" type="select">
                <option value="" key="0" />
                {artists
                  ? artists.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/artist-like" replace color="info">
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

export default ArtistLikeUpdate;
