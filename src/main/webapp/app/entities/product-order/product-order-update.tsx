import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProductOrder } from 'app/shared/model/product-order.model';
import { OrderStatus } from 'app/shared/model/enumerations/order-status.model';
import { getEntity, updateEntity, createEntity, reset } from './product-order.reducer';

export const ProductOrderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const productOrderEntity = useAppSelector(state => state.productOrder.entity);
  const loading = useAppSelector(state => state.productOrder.loading);
  const updating = useAppSelector(state => state.productOrder.updating);
  const updateSuccess = useAppSelector(state => state.productOrder.updateSuccess);
  const orderStatusValues = Object.keys(OrderStatus);

  const handleClose = () => {
    navigate('/product-order' + location.search);
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
    values.placedDate = convertDateTimeToServer(values.placedDate);

    const entity = {
      ...productOrderEntity,
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
          placedDate: displayDefaultDateTime(),
        }
      : {
          status: 'COMPLETED',
          ...productOrderEntity,
          placedDate: convertDateTimeFromServer(productOrderEntity.placedDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keycloakpocApp.productOrder.home.createOrEditLabel" data-cy="ProductOrderCreateUpdateHeading">
            Create or edit a Product Order
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
                <ValidatedField name="id" required readOnly id="product-order-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Placed Date"
                id="product-order-placedDate"
                name="placedDate"
                data-cy="placedDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                }}
              />
              <ValidatedField label="Status" id="product-order-status" name="status" data-cy="status" type="select">
                {orderStatusValues.map(orderStatus => (
                  <option value={orderStatus} key={orderStatus}>
                    {orderStatus}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Code"
                id="product-order-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                }}
              />
              <ValidatedField label="Invoice Id" id="product-order-invoiceId" name="invoiceId" data-cy="invoiceId" type="text" />
              <ValidatedField
                label="Customer"
                id="product-order-customer"
                name="customer"
                data-cy="customer"
                type="text"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/product-order" replace color="info">
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

export default ProductOrderUpdate;
