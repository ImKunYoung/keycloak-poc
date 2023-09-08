import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavDropdown } from './menu-components';

const adminMenuItems = () => (
  <>
    <MenuItem icon="tachometer-alt" to="/admin/metrics">
      메트릭스
    </MenuItem>
    <MenuItem icon="heart" to="/admin/health">
      상태 확인
    </MenuItem>
    <MenuItem icon="cogs" to="/admin/configuration">
      설정
    </MenuItem>
    <MenuItem icon="tasks" to="/admin/logs">
      로그
    </MenuItem>
    {/* jhipster-needle-add-element-to-admin-menu - JHipster will add entities to the admin menu here */}
  </>
);

const openAPIItem = () => (
  <MenuItem icon="book" to="/admin/docs">
    API
  </MenuItem>
);

export const AdminMenu = ({ showOpenAPI }) => (
  <NavDropdown icon="users-cog" name="관리자" id="admin-menu" data-cy="adminMenu">
    {adminMenuItems()}
    {showOpenAPI && openAPIItem()}
  </NavDropdown>
);

export default AdminMenu;
