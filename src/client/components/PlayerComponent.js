import React from 'react';
import YouTube from 'react-youtube';

const PlayerComponent = ({...state}) => {
    const opts = {
        height: '100%',
        width: '100%',
        playerVars: { // https://developers.google.com/youtube/player_parameters
            autoplay: 1
        }
    };
    return (
        <div>
            <YouTube
                videoId="UfwwDXbNnmU"
                opts={opts}
            />
        </div>
    );
};

export default PlayerComponent;