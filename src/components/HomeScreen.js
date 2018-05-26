import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {
  StyleSheet,
  ActivityIndicator,
  View,
} from 'react-native';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import routes from '../routes';
import * as actionCreators from '../actions';
import Card from './CategoryCard';

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'space-between',
    backgroundColor: '#F5FCFF',
    padding: 3,
  },
  spinner: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});


class HomeScreen extends Component {
  static propTypes = {
    navigation: PropTypes.shape({
      dispatch: PropTypes.func.isRequired,
    }).isRequired,
    petList: PropTypes.object,
    actions: PropTypes.objectOf(PropTypes.func).isRequired,
  };
  static navigationOptions = ({ navigation }) => ({
    title: (
      navigation.state.params === undefined ||
      navigation.state.params.title === undefined
    )
      ? '主頁'
      : routes[navigation.state.params.title].text,
  });

  componentDidMount() {
    this.props.actions.fetchPetList();
  }

  render() {
    const {
      petList,
      navigation: { state },
      actions: { setRoute },
    } = this.props;
    const {
      nextRoute,
      contents,
    } = routes[state.routeName];
    if (!petList) {
      return (
        <ActivityIndicator
          style={styles.spinner}
          size="large"
          color="#0000ff"
        />
      );
    }
    return (
      <View style={styles.container}>
        {contents && contents.map(category => (
          <Card
            {...category}
            currentRoute={state.routeName}
            nextRoute={nextRoute}
            onPress={setRoute}
            key={category.param}
          />
        ))}
      </View>
    );
  }
}

const mapStateToProps = state => state.app;
const mapActionToProps = dispatch => ({
  actions: bindActionCreators(actionCreators, dispatch),
});
export default connect(mapStateToProps, mapActionToProps)(HomeScreen);
