/**
 * React Starter Kit (https://www.reactstarterkit.com/)
 *
 * Copyright © 2014-present Kriasoft, LLC. All rights reserved.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */

import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import ButtonAppBar from './ButtonAppBar';

class Header extends React.Component {
  render() {
    return (
      <div>
        <ButtonAppBar title={this.props.name} routes={this.props.routes} />
      </div>
    );
  }
}

Header.propTypes = {
  name: PropTypes.string.isRequired,
  routes: PropTypes.arrayOf(PropTypes.object).isRequired,
};

const mapState = state => ({
  name: state.menu.name,
  routes: state.menu.routes,
});

export default connect(mapState)(Header);
