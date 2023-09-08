import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IExhibition } from 'app/shared/model/exhibition.model';
import { getEntities as getExhibitions } from 'app/entities/exhibition/exhibition.reducer';
import { IView } from 'app/shared/model/view.model';
import { getEntity, updateEntity, createEntity, reset } from './view.reducer';

export const ViewUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const exhibitions = useAppSelector(state => state.exhibition.entities);
  const viewEntity = useAppSelector(state => state.view.entity);
  const loading = useAppSelector(state => state.view.loading);
  const updating = useAppSelector(state => state.view.updating);
  const updateSuccess = useAppSelector(state => state.view.updateSuccess);

  const handleClose = () => {
    navigate('/view');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getExhibitions({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...viewEntity,
      ...values,
      artwork: exhibitions.find(it => it.id.toString() === values.artwork.toString()),
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
          ...viewEntity,
          artwork: viewEntity?.artwork?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keycloakpocApp.view.home.createOrEditLabel" data-cy="ViewCreateUpdateHeading">
            Create or edit a View
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="view-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Vo Member" id="view-voMember" name="voMember" data-cy="voMember" type="text" />
              <ValidatedField id="view-artwork" name="artwork" data-cy="artwork" label="Artwork" type="select">
                <option value="" key="0" />
                {exhibitions
                  ? exhibitions.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/view" replace color="info">
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

export default ViewUpdate;
