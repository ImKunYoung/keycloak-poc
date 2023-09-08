import invoice from 'app/entities/invoice/invoice.reducer';
import shipment from 'app/entities/shipment/shipment.reducer';
import notification from 'app/entities/notification/notification.reducer';
import product from 'app/entities/product/product.reducer';
import productCategory from 'app/entities/product-category/product-category.reducer';
import productOrder from 'app/entities/product-order/product-order.reducer';
import orderItem from 'app/entities/order-item/order-item.reducer';
import customer from 'app/entities/customer/customer.reducer';
import exhibition from 'app/entities/exhibition/exhibition.reducer';
import comment from 'app/entities/comment/comment.reducer';
import view from 'app/entities/view/view.reducer';
import like from 'app/entities/like/like.reducer';
import artist from 'app/entities/artist/artist.reducer';
import artistComment from 'app/entities/artist-comment/artist-comment.reducer';
import artistView from 'app/entities/artist-view/artist-view.reducer';
import artistLike from 'app/entities/artist-like/artist-like.reducer';
import artwork from 'app/entities/artwork/artwork.reducer';
import artworkComment from 'app/entities/artwork-comment/artwork-comment.reducer';
import artworkView from 'app/entities/artwork-view/artwork-view.reducer';
import artworkLike from 'app/entities/artwork-like/artwork-like.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  invoice,
  shipment,
  notification,
  product,
  productCategory,
  productOrder,
  orderItem,
  customer,
  exhibition,
  comment,
  view,
  like,
  artist,
  artistComment,
  artistView,
  artistLike,
  artwork,
  artworkComment,
  artworkView,
  artworkLike,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
