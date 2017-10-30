import { combineReducers } from 'redux';
import { routerReducer } from 'react-router-redux';
import menu from './menu';

export default combineReducers({
  menu,
  routing: routerReducer,
});
