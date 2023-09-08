import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/invoice">
        Invoice
      </MenuItem>
      <MenuItem icon="asterisk" to="/shipment">
        Shipment
      </MenuItem>
      <MenuItem icon="asterisk" to="/notification">
        Notification
      </MenuItem>
      <MenuItem icon="asterisk" to="/product">
        Product
      </MenuItem>
      <MenuItem icon="asterisk" to="/product-category">
        Product Category
      </MenuItem>
      <MenuItem icon="asterisk" to="/product-order">
        Product Order
      </MenuItem>
      <MenuItem icon="asterisk" to="/order-item">
        Order Item
      </MenuItem>
      <MenuItem icon="asterisk" to="/customer">
        Customer
      </MenuItem>
      <MenuItem icon="asterisk" to="/exhibition">
        Exhibition
      </MenuItem>
      <MenuItem icon="asterisk" to="/comment">
        Comment
      </MenuItem>
      <MenuItem icon="asterisk" to="/view">
        View
      </MenuItem>
      <MenuItem icon="asterisk" to="/like">
        Like
      </MenuItem>
      <MenuItem icon="asterisk" to="/artist">
        Artist
      </MenuItem>
      <MenuItem icon="asterisk" to="/artist-comment">
        Artist Comment
      </MenuItem>
      <MenuItem icon="asterisk" to="/artist-view">
        Artist View
      </MenuItem>
      <MenuItem icon="asterisk" to="/artist-like">
        Artist Like
      </MenuItem>
      <MenuItem icon="asterisk" to="/artwork">
        Artwork
      </MenuItem>
      <MenuItem icon="asterisk" to="/artwork-comment">
        Artwork Comment
      </MenuItem>
      <MenuItem icon="asterisk" to="/artwork-view">
        Artwork View
      </MenuItem>
      <MenuItem icon="asterisk" to="/artwork-like">
        Artwork Like
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
