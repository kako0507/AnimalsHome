import _ from 'lodash';
import HomeScreen from './components/HomeScreen';
import PetChoice from './components/PetChoice';
import PetDetail from './components/PetDetail';

const sexChoice = 'sexChoice';
const ageChoice = 'ageChoice';
const petChoice = 'petChoice';

export default _.mapValues({
  home: {
    text: '主頁',
    param: 'type',
    component: HomeScreen,
    nextRoute: sexChoice,
    contents: [
      {
        param: 1,
        title: '找狗狗',
        getImage: () => require('../images/dog_dark.png'),
      },
      {
        param: 2,
        title: '找貓貓',
        getImage: () => require('../images/cat_dark.png'),
      },
      {
        param: 3,
        title: '找其他',
        getImage: () => require('../images/other_dark.jpg'),
      },
    ],
  },
  [sexChoice]: {
    text: '選擇性別',
    param: 'sex',
    component: HomeScreen,
    nextRoute: ageChoice,
    contents: [
      {
        param: 1,
        title: '找男生',
        style: {
          backgroundColor: '#9FD9F8',
        },
        getImage: () => require('../images/male.png'),
      },
      {
        param: 2,
        title: '找女生',
        style: {
          backgroundColor: '#FFC3CE',
        },
        getImage: () => require('../images/female.png'),
      },
    ],
  },
  sizeChoice: {
    text: '選擇體型',
    param: 'size',
    component: HomeScreen,
  },
  [ageChoice]: {
    text: '選擇年齡',
    param: 'age',
    component: HomeScreen,
    nextRoute: petChoice,
    contents: [
      {
        param: '幼齡',
        title: '初生之犢',
        subTitle: '3個月齡以下',
        getImage: () => require('../images/baby.png'),
      },
      {
        param: '年輕',
        title: '年輕力壯',
        subTitle: '3個月齡至1歲',
        getImage: () => require('../images/young.png'),
      },
      {
        param: '成年',
        title: '成熟穩重',
        subTitle: '1至7歲',
        getImage: () => require('../images/forman.png'),
      },
      {
        param: '老年',
        title: '老邁智者',
        subTitle: '7歲以上',
        getImage: () => require('../images/wisdom.png'),
      },
    ],
  },
  [petChoice]: {
    component: PetChoice,
  },
  petDetail: {
    component: PetDetail,
  },
}, (route, routeName) => ({ ...route, routeName }));
