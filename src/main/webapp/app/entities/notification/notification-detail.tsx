import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './notification.reducer';

export const NotificationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const notificationEntity = useAppSelector(state => state.notification.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="notificationDetailsHeading">Notification</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{notificationEntity.id}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{notificationEntity.date ? <TextFormat value={notificationEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="details">Details</span>
          </dt>
          <dd>{notificationEntity.details}</dd>
          <dt>
            <span id="sentDate">Sent Date</span>
          </dt>
          <dd>
            {notificationEntity.sentDate ? <TextFormat value={notificationEntity.sentDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="format">Format</span>
          </dt>
          <dd>{notificationEntity.format}</dd>
          <dt>
            <span id="userId">User Id</span>
          </dt>
          <dd>{notificationEntity.userId}</dd>
          <dt>
            <span id="productId">Product Id</span>
          </dt>
          <dd>{notificationEntity.productId}</dd>
        </dl>
        <Button tag={Link} to="/notification" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">뒤로</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notification/${notificationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">수정</span>
        </Button>
      </Col>
    </Row>
  );
};

export default NotificationDetail;
