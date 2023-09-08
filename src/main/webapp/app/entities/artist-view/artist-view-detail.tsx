import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artist-view.reducer';

export const ArtistViewDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artistViewEntity = useAppSelector(state => state.artistView.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artistViewDetailsHeading">Artist View</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{artistViewEntity.id}</dd>
          <dt>
            <span id="voMember">Vo Member</span>
          </dt>
          <dd>{artistViewEntity.voMember}</dd>
          <dt>Artist</dt>
          <dd>{artistViewEntity.artist ? artistViewEntity.artist.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/artist-view" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artist-view/${artistViewEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtistViewDetail;
