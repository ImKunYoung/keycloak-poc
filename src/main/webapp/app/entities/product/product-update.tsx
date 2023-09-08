import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProductCategory } from 'app/shared/model/product-category.model';
import { getEntities as getProductCategories } from 'app/entities/product-category/product-category.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { Size } from 'app/shared/model/enumerations/size.model';
import { getEntity, updateEntity, createEntity, reset } from './product.reducer';

export const ProductUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const productCategories = useAppSelector(state => state.productCategory.entities);
  const productEntity = useAppSelector(state => state.product.entity);
  const loading = useAppSelector(state => state.product.loading);
  const updating = useAppSelector(state => state.product.updating);
  const updateSuccess = useAppSelector(state => state.product.updateSuccess);
  const sizeValues = Object.keys(Size);

  const handleClose = () => {
    navigate('/product' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProductCategories({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...productEntity,
      ...values,
      productCategory: productCategories.find(it => it.id.toString() === values.productCategory.toString()),
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
          itemSize: 'S',
          ...productEntity,
          productCategory: productEntity?.productCategory?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="keycloakpocApp.product.home.createOrEditLabel" data-cy="ProductCreateUpdateHeading">
            Create or edit a Product
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="product-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Name"
                id="product-name"
                name="name"
                data-cy="name"
                type="text"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                }}
              />
              <ValidatedField label="Description" id="product-description" name="description" data-cy="description" type="text" />
              <ValidatedField
                label="Price"
                id="product-price"
                name="price"
                data-cy="price"
                type="text"
                validate={{
                  required: { value: true, message: '필수항목입니다.' },
                  min: { value: 0, message: '최소 0 이상의 값만 입력 가능합니다.' },
                  validate: v => isNumber(v) || '숫자만 입력 가능합니다.',
                }}
              />
              <ValidatedField label="Item Size" id="product-itemSize" name="itemSize" data-cy="itemSize" type="select">
                {sizeValues.map(size => (
                  <option value={size} key={size}>
                    {size}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedBlobField label="Image" id="product-image" name="image" data-cy="image" isImage accept="image/*" />
              <ValidatedField
                id="product-productCategory"
                name="productCategory"
                data-cy="productCategory"
                label="Product Category"
                type="select"
              >
                <option value="" key="0" />
                {productCategories
                  ? productCategories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/product" replace color="info">
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

export default ProductUpdate;
