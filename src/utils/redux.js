import { applyMiddleware, compose } from 'redux';
import thunk from 'redux-thunk';
import {
  createReactNavigationReduxMiddleware,
  createReduxBoundAddListener,
} from 'react-navigation-redux-helpers';

const middleware = createReactNavigationReduxMiddleware(
  'root',
  state => state.nav,
);
/* eslint-disable no-underscore-dangle */
const composeEnhancers = (typeof window === 'object' &&
  window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__
  ? window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({
    // Specify extension’s options like name,
    // actionsBlacklist, actionsCreators, serialize...
  })
  : compose
);
/* eslint-enable no-underscore-dangle */
const enhancer = composeEnhancers(applyMiddleware(thunk, middleware));
const addListener = createReduxBoundAddListener('root');

export {
  enhancer,
  addListener,
};
