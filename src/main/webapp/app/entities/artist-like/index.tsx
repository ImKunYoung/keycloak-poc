import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtistLike from './artist-like';
import ArtistLikeDetail from './artist-like-detail';
import ArtistLikeUpdate from './artist-like-update';
import ArtistLikeDeleteDialog from './artist-like-delete-dialog';

const ArtistLikeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtistLike />} />
    <Route path="new" element={<ArtistLikeUpdate />} />
    <Route path=":id">
      <Route index element={<ArtistLikeDetail />} />
      <Route path="edit" element={<ArtistLikeUpdate />} />
      <Route path="delete" element={<ArtistLikeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtistLikeRoutes;
