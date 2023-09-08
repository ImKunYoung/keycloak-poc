import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INotification } from 'app/shared/model/notification.model';
import { NotificationType } from 'app/shared/model/enumerations/notification-type.model';
import { getEntity, updateEntity, createEntity, reset } from './notification.reducer';

export const NotificationUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const notificationEntity = useAppSelector(state => state.notification.entity);
  const loading = useAppSelector(state => state.notification.loading);
  const updating = useAppSelector(state => state.notification.updating);
  const updateSuccess = useAppSelector(state => state.notification.updateSuccess);
  const notificationTypeValues = Object.keys(NotificationType);

  const handleClose = () => {
    navigate('/notification');
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
    values.date = convertDateTimeToServer(values.date);
    values.sentDate = convertDateTimeToServer(values.sentDate);

    const entity = {
      ...notificationEntity,
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
      ? {
          date: displayDefaultDateTime(),
          sentDate: displayDefaultDateTime(),
        }
      : {
          format: 'EMAIL',
          ...notificationEntity,
          date: convertDateTimeFromServer(notificationEntity.date),
          sentDate: convertDateTimeFromServer(notificationEntity.sentDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keycloakpocApp.notification.home.createOrEditLabel" data-cy="NotificationCreateUpdateHeading">
            Create or edit a Notification
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="notification-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Date"
                id="notification-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                }}
              />
              <ValidatedField label="Details" id="notification-details" name="details" data-cy="details" type="text" />
              <ValidatedField
                label="Sent Date"
                id="notification-sentDate"
                name="sentDate"
                data-cy="sentDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                }}
              />
              <ValidatedField label="Format" id="notification-format" name="format" data-cy="format" type="select">
                {notificationTypeValues.map(notificationType => (
                  <option value={notificationType} key={notificationType}>
                    {notificationType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="User Id"
                id="notification-userId"
                name="userId"
                data-cy="userId"
                type="text"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                  validate: v => isNumber(v) || '숫자만 입력 가능합니다.',
                }}
              />
              <ValidatedField
                label="Product Id"
                id="notification-productId"
                name="productId"
                data-cy="productId"
                type="text"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                  validate: v => isNumber(v) || '숫자만 입력 가능합니다.',
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/notification" replace color="info">
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

export default NotificationUpdate;
