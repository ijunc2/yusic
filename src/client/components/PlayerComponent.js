import React from 'react';
import YouTube from 'react-youtube';

const PlayerComponent = ({...state}) => {
    const opts = {
        height: '100%',
        width: '100%',
        playerVars: {
            autoplay: 1
        }
    };
    return (
        <div>
            <YouTube
                videoId={state.videoId}
                opts={opts}
                // onPause={state.getList}
                onReady={state.getList}
                onEnd={e => {
                    if(e.data === 0) {
                        state.nextTo();
                    }
                }}
            />
        </div>
    );
};

export default PlayerComponent;