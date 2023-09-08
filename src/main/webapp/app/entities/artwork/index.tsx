import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Artwork from './artwork';
import ArtworkDetail from './artwork-detail';
import ArtworkUpdate from './artwork-update';
import ArtworkDeleteDialog from './artwork-delete-dialog';

const ArtworkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Artwork />} />
    <Route path="new" element={<ArtworkUpdate />} />
    <Route path=":id">
      <Route index element={<ArtworkDetail />} />
      <Route path="edit" element={<ArtworkUpdate />} />
      <Route path="delete" element={<ArtworkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtworkRoutes;
