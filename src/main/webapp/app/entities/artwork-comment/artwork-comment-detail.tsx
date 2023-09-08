import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './artwork-comment.reducer';

export const ArtworkCommentDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const artworkCommentEntity = useAppSelector(state => state.artworkComment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="artworkCommentDetailsHeading">Artwork Comment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{artworkCommentEntity.id}</dd>
          <dt>
            <span id="member">Member</span>
          </dt>
          <dd>{artworkCommentEntity.member}</dd>
          <dt>
            <span id="content">Content</span>
          </dt>
          <dd>{artworkCommentEntity.content}</dd>
          <dt>Artwork</dt>
          <dd>{artworkCommentEntity.artwork ? artworkCommentEntity.artwork.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/artwork-comment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/artwork-comment/${artworkCommentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ArtworkCommentDetail;
