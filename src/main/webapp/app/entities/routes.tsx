import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Invoice from './invoice';
import Shipment from './shipment';
import Notification from './notification';
import Product from './product';
import ProductCategory from './product-category';
import ProductOrder from './product-order';
import OrderItem from './order-item';
import Customer from './customer';
import Exhibition from './exhibition';
import Comment from './comment';
import View from './view';
import Like from './like';
import Artist from './artist';
import ArtistComment from './artist-comment';
import ArtistView from './artist-view';
import ArtistLike from './artist-like';
import Artwork from './artwork';
import ArtworkComment from './artwork-comment';
import ArtworkView from './artwork-view';
import ArtworkLike from './artwork-like';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="invoice/*" element={<Invoice />} />
        <Route path="shipment/*" element={<Shipment />} />
        <Route path="notification/*" element={<Notification />} />
        <Route path="product/*" element={<Product />} />
        <Route path="product-category/*" element={<ProductCategory />} />
        <Route path="product-order/*" element={<ProductOrder />} />
        <Route path="order-item/*" element={<OrderItem />} />
        <Route path="customer/*" element={<Customer />} />
        <Route path="exhibition/*" element={<Exhibition />} />
        <Route path="comment/*" element={<Comment />} />
        <Route path="view/*" element={<View />} />
        <Route path="like/*" element={<Like />} />
        <Route path="artist/*" element={<Artist />} />
        <Route path="artist-comment/*" element={<ArtistComment />} />
        <Route path="artist-view/*" element={<ArtistView />} />
        <Route path="artist-like/*" element={<ArtistLike />} />
        <Route path="artwork/*" element={<Artwork />} />
        <Route path="artwork-comment/*" element={<ArtworkComment />} />
        <Route path="artwork-view/*" element={<ArtworkView />} />
        <Route path="artwork-like/*" element={<ArtworkLike />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
