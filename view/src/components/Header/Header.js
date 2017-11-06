import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import ButtonAppBar from './ButtonAppBar';

/**
 * Connected Component
 * Renders ButtonBar and fetches MenuService onMount
 */
class Header extends React.Component {
  static propTypes = {
    name: PropTypes.string.isRequired,
    routes: PropTypes.arrayOf(PropTypes.object).isRequired,
  };

  render() {
    return (
      <div>
        {this.props.routes ? (
          <ButtonAppBar title={this.props.name} routes={this.props.routes} />
        ) : null}
      </div>
    );
  }
}

const mapState = state => ({
  name: state.menu.name,
  routes: state.menu.routes,
});

export default connect(mapState, null)(Header);
