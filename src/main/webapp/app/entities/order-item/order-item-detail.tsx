import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './order-item.reducer';

export const OrderItemDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const orderItemEntity = useAppSelector(state => state.orderItem.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="orderItemDetailsHeading">Order Item</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{orderItemEntity.id}</dd>
          <dt>
            <span id="quantity">Quantity</span>
          </dt>
          <dd>{orderItemEntity.quantity}</dd>
          <dt>
            <span id="totalPrice">Total Price</span>
          </dt>
          <dd>{orderItemEntity.totalPrice}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{orderItemEntity.status}</dd>
          <dt>Product</dt>
          <dd>{orderItemEntity.product ? orderItemEntity.product.name : ''}</dd>
          <dt>Order</dt>
          <dd>{orderItemEntity.order ? orderItemEntity.order.code : ''}</dd>
        </dl>
        <Button tag={Link} to="/order-item" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/order-item/${orderItemEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default OrderItemDetail;
