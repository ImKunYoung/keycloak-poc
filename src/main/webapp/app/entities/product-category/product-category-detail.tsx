import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product-category.reducer';

export const ProductCategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productCategoryEntity = useAppSelector(state => state.productCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productCategoryDetailsHeading">Product Category</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{productCategoryEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{productCategoryEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{productCategoryEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/product-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product-category/${productCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductCategoryDetail;
