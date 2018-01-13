import {connect} from 'react-redux';
import { withStyles } from 'material-ui/styles';

import * as c from '../components';

const maptState = (state) => ({

});

const mapDispatch = (dispatch) => ({

});

const PlayerContainer = connect(maptState, mapDispatch)(
    withStyles(null, {})(c.PlayerComponent)
);

export default PlayerContainer;