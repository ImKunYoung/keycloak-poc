import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Exhibition from './exhibition';
import ExhibitionDetail from './exhibition-detail';
import ExhibitionUpdate from './exhibition-update';
import ExhibitionDeleteDialog from './exhibition-delete-dialog';

const ExhibitionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Exhibition />} />
    <Route path="new" element={<ExhibitionUpdate />} />
    <Route path=":id">
      <Route index element={<ExhibitionDetail />} />
      <Route path="edit" element={<ExhibitionUpdate />} />
      <Route path="delete" element={<ExhibitionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ExhibitionRoutes;
