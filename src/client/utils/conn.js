import axios from 'axios';

const sendAxios = (url, method = 'get', data = '{}', config) => {

    return axios({
        // headers,
        method,
        url,
        data,
        ...config
    }).then(
        response => {
            if(response.data.result == 'y'){
                return {response}
            }else{
                throw response
            }
        }
    ).catch(
        error => {
            return {error};
        }
    );
};

export default sendAxios;