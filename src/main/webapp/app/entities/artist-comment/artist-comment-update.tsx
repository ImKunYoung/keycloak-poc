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
import { IArtistComment } from 'app/shared/model/artist-comment.model';
import { getEntity, updateEntity, createEntity, reset } from './artist-comment.reducer';

export const ArtistCommentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const artists = useAppSelector(state => state.artist.entities);
  const artistCommentEntity = useAppSelector(state => state.artistComment.entity);
  const loading = useAppSelector(state => state.artistComment.loading);
  const updating = useAppSelector(state => state.artistComment.updating);
  const updateSuccess = useAppSelector(state => state.artistComment.updateSuccess);

  const handleClose = () => {
    navigate('/artist-comment');
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
      ...artistCommentEntity,
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
          ...artistCommentEntity,
          artist: artistCommentEntity?.artist?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keycloakpocApp.artistComment.home.createOrEditLabel" data-cy="ArtistCommentCreateUpdateHeading">
            Create or edit a Artist Comment
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="artist-comment-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Vo Member" id="artist-comment-voMember" name="voMember" data-cy="voMember" type="text" />
              <ValidatedField label="Content" id="artist-comment-content" name="content" data-cy="content" type="text" />
              <ValidatedField id="artist-comment-artist" name="artist" data-cy="artist" label="Artist" type="select">
                <option value="" key="0" />
                {artists
                  ? artists.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/artist-comment" replace color="info">
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

export default ArtistCommentUpdate;
