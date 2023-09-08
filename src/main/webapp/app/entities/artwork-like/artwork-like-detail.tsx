import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artwork-like.reducer';

export const ArtworkLikeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artworkLikeEntity = useAppSelector(state => state.artworkLike.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artworkLikeDetailsHeading">Artwork Like</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{artworkLikeEntity.id}</dd>
          <dt>
            <span id="member">Member</span>
          </dt>
          <dd>{artworkLikeEntity.member}</dd>
          <dt>Artwork</dt>
          <dd>{artworkLikeEntity.artwork ? artworkLikeEntity.artwork.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/artwork-like" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artwork-like/${artworkLikeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtworkLikeDetail;
