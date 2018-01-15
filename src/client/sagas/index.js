import YoutubeSaga from './YoutubeSaga';
export default function* rootSaga() {
    yield [
        YoutubeSaga()
    ]
}