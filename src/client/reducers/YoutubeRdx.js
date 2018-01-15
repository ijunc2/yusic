import * as t from '../actions/definitions/YoutubeTypes';
import {List, Map} from 'immutable';

const init = new Map({
    list: new List([]),
    idx: 0,
    videoId: '',
    nextPageToken: ''
});

const YoutubeRdx = (state = init, actions) => {
    switch(actions.type){
        case t.GET_LIST_SUCCESS:
            var list = state.get('list');
            actions.list.map(r => {
                list = list.push(new Map(r));
            });
            state = state.set('list', list);
            var index = state.get('list').get(state.get('idx'));
            return state
                .set('idx', (state.get('idx') + 1))
                .set('videoId', index.get('videoId'))
                .set('nextPageToken', actions.nextPageToken);
        case t.GET_LIST_FAIL:
            return state;
        case t.NEXT_PLAYER:
            var idx = state.get('idx') + 1;
            return state
                .set('videoId', state.get('list').get(idx).get('videoId'))
                .set('idx', idx);
        case t.NEXT_PLAYER_CALL_SUCCESS:
            var list = state.get('list');
            actions.list.map(r => {
                list = list.push(new Map(r));
            });
            return state
                .set('list', list)
                .set('nextPageToken', actions.nextPageToken);
        case t.NEXT_PLAYER_CALL_FAIL:

            return state;
    }

    return state
}

export default YoutubeRdx;