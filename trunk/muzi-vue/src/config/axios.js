import Axios from 'axios'

Axios.interceptors.request.use(function (config) {
    console.log('------')
    //判断如果登录了就把token配置到axios的协议中
    // let token = localStorage.getItem("token")
    // if (token) {
    //     config.headers['Authorization'] = token
    // }
    //处理请求前代码
    return config;
}, function (error) {
    //一些事情伴随着错误
    return Promise.reject(error)
})