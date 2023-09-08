import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtistComment from './artist-comment';
import ArtistCommentDetail from './artist-comment-detail';
import ArtistCommentUpdate from './artist-comment-update';
import ArtistCommentDeleteDialog from './artist-comment-delete-dialog';

const ArtistCommentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtistComment />} />
    <Route path="new" element={<ArtistCommentUpdate />} />
    <Route path=":id">
      <Route index element={<ArtistCommentDetail />} />
      <Route path="edit" element={<ArtistCommentUpdate />} />
      <Route path="delete" element={<ArtistCommentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtistCommentRoutes;
