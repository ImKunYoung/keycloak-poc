import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './exhibition.reducer';

export const ExhibitionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const exhibitionEntity = useAppSelector(state => state.exhibition.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="exhibitionDetailsHeading">Exhibition</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{exhibitionEntity.id}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{exhibitionEntity.title}</dd>
          <dt>
            <span id="location">Location</span>
          </dt>
          <dd>{exhibitionEntity.location}</dd>
          <dt>
            <span id="fee">Fee</span>
          </dt>
          <dd>{exhibitionEntity.fee}</dd>
          <dt>
            <span id="contact">Contact</span>
          </dt>
          <dd>{exhibitionEntity.contact}</dd>
          <dt>
            <span id="imgUrl">Img Url</span>
          </dt>
          <dd>{exhibitionEntity.imgUrl}</dd>
          <dt>
            <span id="voPeriod">Vo Period</span>
          </dt>
          <dd>{exhibitionEntity.voPeriod}</dd>
          <dt>
            <span id="voArtist">Vo Artist</span>
          </dt>
          <dd>{exhibitionEntity.voArtist}</dd>
          <dt>
            <span id="voMember">Vo Member</span>
          </dt>
          <dd>{exhibitionEntity.voMember}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{exhibitionEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/exhibition" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/exhibition/${exhibitionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ExhibitionDetail;
