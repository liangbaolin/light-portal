/* eslint no-underscore-dangle: 0 */
import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import Button from 'material-ui/Button';
import Typography from 'material-ui/Typography';
import IconButton from 'material-ui/IconButton';
import MenuIcon from 'material-ui-icons/Menu';
import { Link } from 'react-router-dom';

const styles = {
  root: {
    width: '100%',
  },
  menuButton: {
    marginLeft: -12,
    marginRight: 20,
  },
  brand: {
    flex: 1,
    color: '#ffffff',
    textDecoration: 'none',
    textAlign: 'left',
  },
};

const ButtonAppBar = props => {
  const { classes, title, routes } = props;
  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            className={classes.menuButton}
            color="contrast"
            aria-label="Menu"
          >
            <MenuIcon />
          </IconButton>
          <Link className={classes.brand} to="/" href="/">
            <Typography type="title" color="inherit">
              {title}
            </Typography>
          </Link>
          {routes.map(route => (
            <Link to={route.route} key={route.label} href={route.route}>
              <Button color="contrast">{route.label}</Button>
            </Link>
          ))}
        </Toolbar>
      </AppBar>
    </div>
  );
};

ButtonAppBar.propTypes = {
  classes: PropTypes.shape({
    brand: PropTypes.string.isRequired,
    menuButton: PropTypes.string.isRequired,
    root: PropTypes.string.isRequired,
  }).isRequired,
  title: PropTypes.string.isRequired,
  routes: PropTypes.arrayOf(PropTypes.object).isRequired,
};

export default withStyles(styles)(ButtonAppBar);
