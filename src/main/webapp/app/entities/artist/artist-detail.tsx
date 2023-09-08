import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artist.reducer';

export const ArtistDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artistEntity = useAppSelector(state => state.artist.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artistDetailsHeading">Artist</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{artistEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{artistEntity.name}</dd>
          <dt>
            <span id="realName">Real Name</span>
          </dt>
          <dd>{artistEntity.realName}</dd>
          <dt>
            <span id="imgUrl">Img Url</span>
          </dt>
          <dd>{artistEntity.imgUrl}</dd>
          <dt>
            <span id="phone">Phone</span>
          </dt>
          <dd>{artistEntity.phone}</dd>
          <dt>
            <span id="career">Career</span>
          </dt>
          <dd>{artistEntity.career}</dd>
          <dt>
            <span id="voArtwork">Vo Artwork</span>
          </dt>
          <dd>{artistEntity.voArtwork}</dd>
          <dt>
            <span id="voMember">Vo Member</span>
          </dt>
          <dd>{artistEntity.voMember}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{artistEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/artist" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artist/${artistEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtistDetail;
