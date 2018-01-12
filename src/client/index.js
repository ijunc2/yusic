import React from 'react';
import ReactDOM from 'react-dom';
import App from './containers/App';
import { BrowserRouter, Route } from 'react-router-dom';
import { Provider  } from 'react-redux';
import store from './store';
import { MuiThemeProvider, createMuiTheme } from 'material-ui/styles';
// import injectTapEventPlugin from 'react-tap-event-plugin';
// 스토어 생성
// const store = createStore(reducers);
const theme = createMuiTheme();

const appElement = document.getElementById('root');

ReactDOM.render(
    <Provider store = {store}>
        <MuiThemeProvider theme={theme}>
            <BrowserRouter>
                <App />
            </BrowserRouter>
        </MuiThemeProvider>
    </Provider>,
    appElement
);