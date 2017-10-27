import { createStore, applyMiddleware, compose } from 'redux';
import thunk from 'redux-thunk';
import rootReducer from '../reducers';
import createLogger from './logger/logger';

const initialState = {};
const middleware = [thunk];

let enhancer;

if (process.env.NODE_ENV === 'development') {
  middleware.push(createLogger());

  enhancer = compose(
    applyMiddleware(...middleware),
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__());
} else {
  enhancer = applyMiddleware(...middleware);
}

// See https://github.com/rackt/redux/releases/tag/v3.1.0
const store = createStore(rootReducer, initialState, enhancer);

export default store;
