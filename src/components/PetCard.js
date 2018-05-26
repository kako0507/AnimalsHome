import React, { Component } from 'react';
import PropTypes from 'prop-types';
import {
  Dimensions,
  StyleSheet,
  TouchableOpacity,
  Image,
  View,
  Text,
} from 'react-native';
import routes from '../routes';

const windowWidth = Dimensions.get('window').width;
const cardWidth = (windowWidth - 20) / 2;

const styles = StyleSheet.create({
  container: {
    position: 'relative',
    margin: 3,
  },
  image: {
    width: cardWidth,
    height: cardWidth,
  },
  name: {
    position: 'absolute',
    bottom: 0,
    width: '100%',
    height: 25,
    padding: 3,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  text: {
    fontSize: 16,
    color: '#fff',
  },
});

export default class PetCard extends Component {
  static propTypes = {
    Id: PropTypes.string,
    Tid: PropTypes.string,
    Name: PropTypes.string,
    pic: PropTypes.string,
    onPress: PropTypes.func,
  };

  handlePress = () => {
    this.props.onPress(routes.petDetail.routeName, {
      Id: this.props.Id,
      Tid: this.props.Tid,
    });
  }

  render() {
    const { Name, pic } = this.props;
    return (
      <TouchableOpacity
        style={styles.container}
        onPress={this.handlePress}
      >
        <Image
          style={styles.image}
          source={pic
            ? { uri: `https://tas.taipei/uploads/images/medium/${pic}` }
            : require('../../images/no_image_text.png')
          }
        />
        {Name &&
          <View style={styles.name}>
            <Text style={styles.text}>
              {Name}
            </Text>
          </View>
        }
      </TouchableOpacity>
    );
  }
}
