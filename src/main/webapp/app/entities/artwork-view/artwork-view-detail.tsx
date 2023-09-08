import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artwork-view.reducer';

export const ArtworkViewDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artworkViewEntity = useAppSelector(state => state.artworkView.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artworkViewDetailsHeading">Artwork View</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{artworkViewEntity.id}</dd>
          <dt>
            <span id="member">Member</span>
          </dt>
          <dd>{artworkViewEntity.member}</dd>
          <dt>Artwork</dt>
          <dd>{artworkViewEntity.artwork ? artworkViewEntity.artwork.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/artwork-view" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artwork-view/${artworkViewEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtworkViewDetail;
