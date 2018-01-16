import {connect} from 'react-redux';
import { withStyles } from 'material-ui/styles';

import * as c from '../components';
import * as a from '../actions/behaviors/YotubeActions'
import * as w from '../utils/websock';
const maptState = (state) => ({
    videoId: state.YoutubeRdx.toJS().videoId,
    idx: state.YoutubeRdx.toJS().idx,
    size: state.YoutubeRdx.toJS().list.length
});

const mapDispatch = (dispatch) => ({
    getList: () => dispatch(a.getList()),
    nextTo: () => dispatch(a.goToNext()),
    pushMessage: (url, msg) => w.sendMessage(url, msg)
});

const PlayerContainer = connect(maptState, mapDispatch)(
    withStyles(null, {})(c.PlayerComponent)
);

export default PlayerContainer;