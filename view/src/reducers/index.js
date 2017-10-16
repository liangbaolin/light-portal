import { combineReducers } from 'redux';
import runtime from './runtime';
import menu from './menu';

export default combineReducers({
  menu,
  runtime,
});
