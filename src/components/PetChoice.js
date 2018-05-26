import React from 'react';
import PropTypes from 'prop-types';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import {
  StyleSheet,
  ScrollView,
} from 'react-native';
import * as actionCreators from '../actions';
import PetCard from './PetCard';

const styles = StyleSheet.create({
  contentContainer: {
    margin: 3,
    paddingVertical: 20,
    flexWrap: 'wrap',
    flexDirection: 'row',
  },
});

const PetChoice = ({ filteredData, actions }) => (
  <ScrollView contentContainerStyle={styles.contentContainer}>
    {filteredData && filteredData.map(pet => (
      <PetCard
        {...pet}
        key={pet.Id}
        onPress={actions.setRoute}
      />
    ))}
  </ScrollView>
);
PetChoice.propTypes = {
  filteredData: PropTypes.arrayOf(PropTypes.shape({
    Id: PropTypes.string,
  })),
  actions: PropTypes.objectOf(PropTypes.func).isRequired,
};
PetChoice.navigationOptions = {
  title: '等待領養中',
};

const mapStateToProps = state => state.app.petList;
const mapActionToProps = dispatch => ({
  actions: bindActionCreators(actionCreators, dispatch),
});
export default connect(mapStateToProps, mapActionToProps)(PetChoice);

