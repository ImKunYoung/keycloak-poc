import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Artist from './artist';
import ArtistDetail from './artist-detail';
import ArtistUpdate from './artist-update';
import ArtistDeleteDialog from './artist-delete-dialog';

const ArtistRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Artist />} />
    <Route path="new" element={<ArtistUpdate />} />
    <Route path=":id">
      <Route index element={<ArtistDetail />} />
      <Route path="edit" element={<ArtistUpdate />} />
      <Route path="delete" element={<ArtistDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtistRoutes;
