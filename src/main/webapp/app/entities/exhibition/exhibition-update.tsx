import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IExhibition } from 'app/shared/model/exhibition.model';
import { Status } from 'app/shared/model/enumerations/status.model';
import { getEntity, updateEntity, createEntity, reset } from './exhibition.reducer';

export const ExhibitionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const exhibitionEntity = useAppSelector(state => state.exhibition.entity);
  const loading = useAppSelector(state => state.exhibition.loading);
  const updating = useAppSelector(state => state.exhibition.updating);
  const updateSuccess = useAppSelector(state => state.exhibition.updateSuccess);
  const statusValues = Object.keys(Status);

  const handleClose = () => {
    navigate('/exhibition' + location.search);
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
      ...exhibitionEntity,
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
          ...exhibitionEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keycloakpocApp.exhibition.home.createOrEditLabel" data-cy="ExhibitionCreateUpdateHeading">
            Create or edit a Exhibition
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="exhibition-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Title" id="exhibition-title" name="title" data-cy="title" type="text" />
              <ValidatedField label="Location" id="exhibition-location" name="location" data-cy="location" type="text" />
              <ValidatedField label="Fee" id="exhibition-fee" name="fee" data-cy="fee" type="text" />
              <ValidatedField label="Contact" id="exhibition-contact" name="contact" data-cy="contact" type="text" />
              <ValidatedField label="Img Url" id="exhibition-imgUrl" name="imgUrl" data-cy="imgUrl" type="text" />
              <ValidatedField label="Vo Period" id="exhibition-voPeriod" name="voPeriod" data-cy="voPeriod" type="text" />
              <ValidatedField label="Vo Artist" id="exhibition-voArtist" name="voArtist" data-cy="voArtist" type="text" />
              <ValidatedField label="Vo Member" id="exhibition-voMember" name="voMember" data-cy="voMember" type="text" />
              <ValidatedField label="Status" id="exhibition-status" name="status" data-cy="status" type="select">
                {statusValues.map(status => (
                  <option value={status} key={status}>
                    {status}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/exhibition" replace color="info">
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

export default ExhibitionUpdate;
