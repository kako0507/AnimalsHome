import React, { Component } from 'react';
import { createStore } from 'redux';
import { Provider } from 'react-redux';
import AppWithNavigationState from './navigators/AppNavigator';
import { enhancer } from './utils/redux';
import AppReducer from './reducers';

const store = createStore(AppReducer, enhancer);

export default class Root extends Component {
  constructor(props) {
    super(props);
    console.disableYellowBox = true;
  }
  render() {
    return (
      <Provider store={store}>
        <AppWithNavigationState />
      </Provider>
    );
  }
}
