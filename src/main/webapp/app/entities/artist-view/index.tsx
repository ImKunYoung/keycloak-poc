import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtistView from './artist-view';
import ArtistViewDetail from './artist-view-detail';
import ArtistViewUpdate from './artist-view-update';
import ArtistViewDeleteDialog from './artist-view-delete-dialog';

const ArtistViewRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtistView />} />
    <Route path="new" element={<ArtistViewUpdate />} />
    <Route path=":id">
      <Route index element={<ArtistViewDetail />} />
      <Route path="edit" element={<ArtistViewUpdate />} />
      <Route path="delete" element={<ArtistViewDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtistViewRoutes;
