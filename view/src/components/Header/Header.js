import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import ButtonAppBar from './ButtonAppBar';
import { getMenuService } from '../../actions/menu';

class Header extends React.Component {
  static propTypes = {
    name: PropTypes.string.isRequired,
    routes: PropTypes.arrayOf(PropTypes.object).isRequired,
    getMenuService: PropTypes.func.isRequired,
  };

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

const mapState = state => ({
  name: state.menu.name,
  routes: state.menu.routes,
});

const mapDispatchToProps = dispatch => ({
  getMenuService: () => dispatch(getMenuService()),
});

export default connect(mapState, mapDispatchToProps)(Header);
