import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artist-comment.reducer';

export const ArtistCommentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artistCommentEntity = useAppSelector(state => state.artistComment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artistCommentDetailsHeading">Artist Comment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{artistCommentEntity.id}</dd>
          <dt>
            <span id="voMember">Vo Member</span>
          </dt>
          <dd>{artistCommentEntity.voMember}</dd>
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{artistCommentEntity.content}</dd>
          <dt>Artist</dt>
          <dd>{artistCommentEntity.artist ? artistCommentEntity.artist.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/artist-comment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artist-comment/${artistCommentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtistCommentDetail;
