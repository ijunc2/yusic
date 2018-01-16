import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import store from '../store';
import * as actions from '../actions/behaviors/MessageAction';

var stompClient;

const getMessages = (msg) => {
    // store.dispatch(actions.getMessages(msg));
};

export const initSocket = () => {
    var socket = SockJS('/msg');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, frame => {
        stompClient.subscribe('/topic/hi', data => {
            console.log(data)
            // getMessages(JSON.parse(data.body).message);
        });
        stompClient.subscribe('/topic/total', data => {
            console.log('data::::::::::::::::>>>>>')
            console.log(JSON.parse(data.body).total)
            // getMessages(JSON.parse(data.body).message);
        });
    });
};

export const getStatus = () => stompClient;

export const sendMessage = (url, msg) => {
    // url : "/app/hello"
    stompClient.send(url, {}, JSON.stringify(msg));
};
