import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IProductOrder } from 'app/shared/model/product-order.model';
import { getEntities as getProductOrders } from 'app/entities/product-order/product-order.reducer';
import { IOrderItem } from 'app/shared/model/order-item.model';
import { OrderItemStatus } from 'app/shared/model/enumerations/order-item-status.model';
import { getEntity, updateEntity, createEntity, reset } from './order-item.reducer';

export const OrderItemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const products = useAppSelector(state => state.product.entities);
  const productOrders = useAppSelector(state => state.productOrder.entities);
  const orderItemEntity = useAppSelector(state => state.orderItem.entity);
  const loading = useAppSelector(state => state.orderItem.loading);
  const updating = useAppSelector(state => state.orderItem.updating);
  const updateSuccess = useAppSelector(state => state.orderItem.updateSuccess);
  const orderItemStatusValues = Object.keys(OrderItemStatus);

  const handleClose = () => {
    navigate('/order-item' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProducts({}));
    dispatch(getProductOrders({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...orderItemEntity,
      ...values,
      product: products.find(it => it.id.toString() === values.product.toString()),
      order: productOrders.find(it => it.id.toString() === values.order.toString()),
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
          status: 'AVAILABLE',
          ...orderItemEntity,
          product: orderItemEntity?.product?.id,
          order: orderItemEntity?.order?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keycloakpocApp.orderItem.home.createOrEditLabel" data-cy="OrderItemCreateUpdateHeading">
            Create or edit a Order Item
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="order-item-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Quantity"
                id="order-item-quantity"
                name="quantity"
                data-cy="quantity"
                type="text"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                  min: { value: 0, message: '최소 0 이상의 값만 입력 가능합니다.' },
                  validate: v => isNumber(v) || '숫자만 입력 가능합니다.',
                }}
              />
              <ValidatedField
                label="Total Price"
                id="order-item-totalPrice"
                name="totalPrice"
                data-cy="totalPrice"
                type="text"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                  min: { value: 0, message: '최소 0 이상의 값만 입력 가능합니다.' },
                  validate: v => isNumber(v) || '숫자만 입력 가능합니다.',
                }}
              />
              <ValidatedField label="Status" id="order-item-status" name="status" data-cy="status" type="select">
                {orderItemStatusValues.map(orderItemStatus => (
                  <option value={orderItemStatus} key={orderItemStatus}>
                    {orderItemStatus}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="order-item-product" name="product" data-cy="product" label="Product" type="select" required>
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>필수항목입니다.</FormText>
              <ValidatedField id="order-item-order" name="order" data-cy="order" label="Order" type="select" required>
                <option value="" key="0" />
                {productOrders
                  ? productOrders.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.code}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>필수항목입니다.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/order-item" replace color="info">
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

export default OrderItemUpdate;
