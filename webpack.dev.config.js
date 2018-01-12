const webpack = require('webpack');

module.exports = {
    devtool: 'inline-source-map',
    devServer: {
        historyApiFallback: true,
        compress: true,
        publicPath: '/',
        host: "localhost",
        port: 3000,
        proxy: {
            "**": "http://localhost:8080"
        }
    },
    plugins: [
        new webpack.NamedModulesPlugin() //브라우저에서 HMR 에러발생시 module name 표시
    ]
}
