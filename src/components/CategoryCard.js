import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {
  StyleSheet,
  TouchableOpacity,
  View,
  Text,
  Image,
} from 'react-native';

const styles = StyleSheet.create({
  card: {
    position: 'relative',
    margin: 3,
    flex: 1,
  },
  title: {
    position: 'absolute',
    top: 10,
    left: 10,
    zIndex: 1,
    fontSize: 32,
    color: '#fff',
  },
  subTitle: {
    position: 'absolute',
    top: 45,
    left: 10,
    zIndex: 1,
    fontSize: 12,
    color: '#fff',
  },
  image: {
    flex: 1,
    width: '100%',
  },
});

export default class Card extends Component {
  static propTypes = {
    param: PropTypes.oneOfType([
      PropTypes.string,
      PropTypes.number,
    ]).isRequired,
    style: View.propTypes.style,
    title: PropTypes.string.isRequired,
    subTitle: PropTypes.string,
    currentRoute: PropTypes.string.isRequired,
    nextRoute: PropTypes.string.isRequired,
    getImage: PropTypes.func,
    onPress: PropTypes.func.isRequired,
  };
  static defaultProps = {
    getImage: undefined,
  };

  handlePress = () => {
    const {
      param, currentRoute, nextRoute, onPress,
    } = this.props;
    onPress(
      nextRoute,
      {
        param,
        currentRoute,
      },
    );
  };

  render() {
    const {
      style, title, subTitle, getImage,
    } = this.props;
    return (
      <TouchableOpacity
        style={[
          styles.card,
          style || {},
        ]}
        onPress={this.handlePress}
      >
        <Text style={styles.title}>
          {title}
        </Text>
        <Text style={styles.subTitle}>
          {subTitle}
        </Text>
        {getImage &&
          <Image
            style={styles.image}
            resizeMode="stretch"
            source={getImage()}
          />
        }
      </TouchableOpacity>
    );
  }
}
