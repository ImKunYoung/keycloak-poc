import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtworkView from './artwork-view';
import ArtworkViewDetail from './artwork-view-detail';
import ArtworkViewUpdate from './artwork-view-update';
import ArtworkViewDeleteDialog from './artwork-view-delete-dialog';

const ArtworkViewRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtworkView />} />
    <Route path="new" element={<ArtworkViewUpdate />} />
    <Route path=":id">
      <Route index element={<ArtworkViewDetail />} />
      <Route path="edit" element={<ArtworkViewUpdate />} />
      <Route path="delete" element={<ArtworkViewDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtworkViewRoutes;
