import * as t from '../definitions/YoutubeTypes';

export const getList = () => ({
    type: t.GET_LIST
});

export const goToNext = () => ({
    type: t.NEXT_PLAYER
});