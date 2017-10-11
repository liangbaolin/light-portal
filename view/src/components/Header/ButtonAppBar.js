import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from 'material-ui/styles';
import AppBar from 'material-ui/AppBar';
import Toolbar from 'material-ui/Toolbar';
import Typography from 'material-ui/Typography';
import IconButton from 'material-ui/IconButton';
import MenuIcon from 'material-ui-icons/Menu';
import AccountCircle from 'material-ui-icons/AccountCircle';
import Link from '../Link';

const styles = theme => ({
  root: {
    marginTop: theme.spacing.unit,
    width: '100%',
  },
  flex: {
    flex: 1,
  },
  menuButton: {
    marginLeft: -12,
    marginRight: 20,
  },
  brand: {
    color: '#ffffff',
    textDecoration: 'none',
  },
  icon: {
    margin: theme.spacing.unit,
    width: 38,
    height: 38,
  },
});

function ButtonAppBar(props) {
  const { classes, title } = props;
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
          <Link className={classes.brand} to="/">
            <Typography type="title" color="inherit" className={classes.flex}>
              {title}
            </Typography>
          </Link>
          <AccountCircle className={classes.icon} />
        </Toolbar>
      </AppBar>
    </div>
  );
}

ButtonAppBar.propTypes = {
  classes: PropTypes.shape({
    brand: PropTypes.string.isRequired,
    flex: PropTypes.string.isRequired,
    icon: PropTypes.string.isRequired,
    menuButton: PropTypes.string.isRequired,
    root: PropTypes.string.isRequired,
  }).isRequired,
  title: PropTypes.string.isRequired,
};

export default withStyles(styles)(ButtonAppBar);
