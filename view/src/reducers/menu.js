/* eslint no-underscore-dangle: 0 */
import { MENU_SERVICE_SUCCESS } from '../constants';

export default function runtime(state = {}, action) {
  switch (action.type) {
    case MENU_SERVICE_SUCCESS:
      return {
        ...state,
        name: action.payload._key,
        routes: action.payload.contains,
      };
    default:
      return state;
  }
}
