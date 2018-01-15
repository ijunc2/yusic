import sendAxios from '../utils/conn';

export const getListForMusic = ({...state}) => {
    var url = '/search/list';
    if(state.p != null || state.p != ''
        || state.p !== undefined){
        url += '?p=' + state.p
    }
    return sendAxios(url, 'get', state, {timeout: 30 * 1000});
};