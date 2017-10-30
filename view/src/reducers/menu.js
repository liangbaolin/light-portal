/* eslint no-underscore-dangle: 0 */
import { MENU_SERVICE_SUCCESS } from '../constants';

const initialState = {
  name: '',
  routes: [],
};

/**
 * Define reducer for Menu Service
 * @param {object} state - Menu State
 * @param {string} action - Actions
 * @returns Immutable object with new state
 */
export default function menu(state = initialState, action) {
  switch (action.type) {
    case MENU_SERVICE_SUCCESS:
      return {
        ...state,
        name: action.payload._key,
        routes: action.payload.contains,
        id: action.payload._id,
      };
    default:
      return state;
  }
}
