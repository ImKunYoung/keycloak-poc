import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product-order.reducer';

export const ProductOrderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productOrderEntity = useAppSelector(state => state.productOrder.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productOrderDetailsHeading">Product Order</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productOrderEntity.id}</dd>
          <dt>
            <span id="placedDate">Placed Date</span>
          </dt>
          <dd>
            {productOrderEntity.placedDate ? (
              <TextFormat value={productOrderEntity.placedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{productOrderEntity.status}</dd>
          <dt>
            <span id="code">Code</span>
          </dt>
          <dd>{productOrderEntity.code}</dd>
          <dt>
            <span id="invoiceId">Invoice Id</span>
          </dt>
          <dd>{productOrderEntity.invoiceId}</dd>
          <dt>
            <span id="customer">Customer</span>
          </dt>
          <dd>{productOrderEntity.customer}</dd>
        </dl>
        <Button tag={Link} to="/product-order" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product-order/${productOrderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductOrderDetail;
