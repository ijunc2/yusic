import * as t from '../actions/definitions/TestDefinition';
const init = {

};

const TestRdc = (state = init, actions) => {
    console.log('reducers')
    switch(actions.type){
        case t.TEST_ACTIONS:

            return state;
    }

    return state
}

export default TestRdc;