/* eslint no-underscore-dangle: 0 */

import { MENU_SERVICE_SUCCESS } from '../constants';

export default function menu(state = {}, action) {
  switch (action.type) {
    case MENU_SERVICE_SUCCESS:
      return {
        ...state,
        key: action.payload._key,
      };
    default:
      return state;
  }
}
