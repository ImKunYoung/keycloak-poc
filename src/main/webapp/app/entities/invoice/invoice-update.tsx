import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IInvoice } from 'app/shared/model/invoice.model';
import { InvoiceStatus } from 'app/shared/model/enumerations/invoice-status.model';
import { PaymentMethod } from 'app/shared/model/enumerations/payment-method.model';
import { getEntity, updateEntity, createEntity, reset } from './invoice.reducer';

export const InvoiceUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const invoiceEntity = useAppSelector(state => state.invoice.entity);
  const loading = useAppSelector(state => state.invoice.loading);
  const updating = useAppSelector(state => state.invoice.updating);
  const updateSuccess = useAppSelector(state => state.invoice.updateSuccess);
  const invoiceStatusValues = Object.keys(InvoiceStatus);
  const paymentMethodValues = Object.keys(PaymentMethod);

  const handleClose = () => {
    navigate('/invoice' + location.search);
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
    values.paymentDate = convertDateTimeToServer(values.paymentDate);

    const entity = {
      ...invoiceEntity,
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
          paymentDate: displayDefaultDateTime(),
        }
      : {
          status: 'PAID',
          paymentMethod: 'CREDIT_CARD',
          ...invoiceEntity,
          date: convertDateTimeFromServer(invoiceEntity.date),
          paymentDate: convertDateTimeFromServer(invoiceEntity.paymentDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keycloakpocApp.invoice.home.createOrEditLabel" data-cy="InvoiceCreateUpdateHeading">
            Create or edit a Invoice
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="invoice-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Code"
                id="invoice-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                }}
              />
              <ValidatedField
                label="Date"
                id="invoice-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                }}
              />
              <ValidatedField label="Details" id="invoice-details" name="details" data-cy="details" type="text" />
              <ValidatedField label="Status" id="invoice-status" name="status" data-cy="status" type="select">
                {invoiceStatusValues.map(invoiceStatus => (
                  <option value={invoiceStatus} key={invoiceStatus}>
                    {invoiceStatus}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField label="Payment Method" id="invoice-paymentMethod" name="paymentMethod" data-cy="paymentMethod" type="select">
                {paymentMethodValues.map(paymentMethod => (
                  <option value={paymentMethod} key={paymentMethod}>
                    {paymentMethod}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Payment Date"
                id="invoice-paymentDate"
                name="paymentDate"
                data-cy="paymentDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                }}
              />
              <ValidatedField
                label="Payment Amount"
                id="invoice-paymentAmount"
                name="paymentAmount"
                data-cy="paymentAmount"
                type="text"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                  validate: v => isNumber(v) || '숫자만 입력 가능합니다.',
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/invoice" replace color="info">
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

export default InvoiceUpdate;
