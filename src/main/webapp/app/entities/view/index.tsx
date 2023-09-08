import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import View from './view';
import ViewDetail from './view-detail';
import ViewUpdate from './view-update';
import ViewDeleteDialog from './view-delete-dialog';

const ViewRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<View />} />
    <Route path="new" element={<ViewUpdate />} />
    <Route path=":id">
      <Route index element={<ViewDetail />} />
      <Route path="edit" element={<ViewUpdate />} />
      <Route path="delete" element={<ViewDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ViewRoutes;
