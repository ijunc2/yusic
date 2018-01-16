import React from 'react';
import * as w from './utils/websock';

import {PlayerContainer} from './containers';
class App extends React.Component{

    constructor(props){
        super(props);
        w.initSocket();


    }

    componentDidMount(){
        // w.sendMessage('/topic/total', {})
        var s = setInterval(() => {
            console.log('1')
            if(w.getStatus().connected){
                console.log('djdjdjdj :: ' +  w.getStatus().connected)
                w.sendMessage('/app/init', {})
                clearInterval(s);
            }
        }, 500)
    }

    render(){
        return (
            <PlayerContainer />
        );
    }
}

export default App;