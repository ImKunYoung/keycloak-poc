import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artist-like.reducer';

export const ArtistLikeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artistLikeEntity = useAppSelector(state => state.artistLike.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artistLikeDetailsHeading">Artist Like</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{artistLikeEntity.id}</dd>
          <dt>
            <span id="voMember">Vo Member</span>
          </dt>
          <dd>{artistLikeEntity.voMember}</dd>
          <dt>Artist</dt>
          <dd>{artistLikeEntity.artist ? artistLikeEntity.artist.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/artist-like" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artist-like/${artistLikeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtistLikeDetail;
