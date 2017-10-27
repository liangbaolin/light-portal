import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import ButtonAppBar from './ButtonAppBar';
import { getMenuService } from '../../actions/menu';

class Header extends React.Component {
  componentWillMount() {
    this.props.getMenuService();
  }
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

Header.propTypes = {
  name: PropTypes.string.isRequired,
  routes: PropTypes.arrayOf(PropTypes.object).isRequired,
  getMenuService: PropTypes.func.isRequired,
};

const mapState = state => ({
  name: state.menu.name,
  routes: state.menu.routes,
});

const mapDispatchToProps = dispatch => ({
  getMenuService: () => dispatch(getMenuService()),
});

export default connect(mapState, mapDispatchToProps)(Header);
