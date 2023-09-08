import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ArtworkComment from './artwork-comment';
import ArtworkCommentDetail from './artwork-comment-detail';
import ArtworkCommentUpdate from './artwork-comment-update';
import ArtworkCommentDeleteDialog from './artwork-comment-delete-dialog';

const ArtworkCommentRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ArtworkComment />} />
    <Route path="new" element={<ArtworkCommentUpdate />} />
    <Route path=":id">
      <Route index element={<ArtworkCommentDetail />} />
      <Route path="edit" element={<ArtworkCommentUpdate />} />
      <Route path="delete" element={<ArtworkCommentDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArtworkCommentRoutes;
