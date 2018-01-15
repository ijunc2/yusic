import {takeEvery} from 'redux-saga';
import {call, put, fork} from 'redux-saga/effects';
import * as t from '../actions/definitions/YoutubeTypes';
import {getListForMusic} from "../services/youtube";
import store from '../store'

function* getList(action){
    const {response, error} = yield call(getListForMusic);

    if(response){
        yield put({
            type: t.GET_LIST_SUCCESS,
            list: response.data.list,
            nextPageToken: response.data.nextPageToken
        });
    }else{
        yield put({
            type: t.GET_LIST_FAIL,
            err: error
        });
    }

}

function* nextTo(action){
    var dummy = store.getState().YoutubeRdx.toJS();
    var length = dummy.list.length,
        idx = dummy.idx;

    if(length - idx <= 2){
        const {response, error} = yield call(getListForMusic, {
            p: dummy.nextPageToken
        });

        if(response){
            yield put({
                type: t.NEXT_PLAYER_CALL_SUCCESS,
                list: response.data.list,
                nextPageToken: response.data.nextPageToken
            });
        }else{
            yield put({
                type: t.NEXT_PLAYER_CALL_FAIL,
                err: error
            });
        }
    }
}

function* watchGetList(){
    yield* takeEvery(t.GET_LIST, getList);
}

function* watchNext(){
    yield* takeEvery(t.NEXT_PLAYER, nextTo);
}

export default function* YoutubeSaga(){
    yield fork(watchGetList);
    yield fork(watchNext);
}