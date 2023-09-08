import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artwork.reducer';

export const ArtworkDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artworkEntity = useAppSelector(state => state.artwork.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artworkDetailsHeading">Artwork</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{artworkEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{artworkEntity.title}</dd>
          <dt>
            <span id="shortDescription">Short Description</span>
          </dt>
          <dd>{artworkEntity.shortDescription}</dd>
          <dt>
            <span id="longDescription">Long Description</span>
          </dt>
          <dd>{artworkEntity.longDescription}</dd>
          <dt>
            <span id="imageUrl">Image Url</span>
          </dt>
          <dd>{artworkEntity.imageUrl}</dd>
          <dt>
            <span id="artistname">Artistname</span>
          </dt>
          <dd>{artworkEntity.artistname}</dd>
          <dt>
            <span id="makingday">Makingday</span>
          </dt>
          <dd>{artworkEntity.makingday}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{artworkEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/artwork" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artwork/${artworkEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtworkDetail;
