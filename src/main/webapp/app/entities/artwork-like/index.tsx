import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtworkLike from './artwork-like';
import ArtworkLikeDetail from './artwork-like-detail';
import ArtworkLikeUpdate from './artwork-like-update';
import ArtworkLikeDeleteDialog from './artwork-like-delete-dialog';

const ArtworkLikeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtworkLike />} />
    <Route path="new" element={<ArtworkLikeUpdate />} />
    <Route path=":id">
      <Route index element={<ArtworkLikeDetail />} />
      <Route path="edit" element={<ArtworkLikeUpdate />} />
      <Route path="delete" element={<ArtworkLikeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtworkLikeRoutes;
