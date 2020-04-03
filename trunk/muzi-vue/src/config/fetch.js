import { baseUrl } from './env'
import router from './../router'
import { Loading } from 'element-ui'


export default async(url = '', data = {}, type = 'GET', method = 'fetch') => {
	let loadingInstance = Loading.service({ fullscreen: true,spinner:'el-icon-loading',});
	type = type.toUpperCase();
	url = baseUrl + url;
	data.access_token = localStorage.getItem('access_token');
	if(type == 'GET'){
		//判断是请求方式
		let dataStr = ''; //数据拼接字符串
		Object.keys(data).forEach(key => {
			dataStr += key + '=' + data[key] + '&';
		})

		if (dataStr !== '') {
			dataStr = dataStr.substr(0, dataStr.lastIndexOf('&'));
			url = url + '?' + dataStr;
		}
		return new Promise((resolve, reject) => {
			let requestObj;
			if (window.XMLHttpRequest) {
				requestObj = new XMLHttpRequest();
			} else {
				requestObj = new ActiveXObject;
			}

			let sendData = '';
			if (type == 'POST') {
				sendData = JSON.stringify(data);
			}

			requestObj.open(type, url, true);
			requestObj.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			//requestObj.setRequestHeader("Content-type", "application/json");
			requestObj.send(sendData);

			requestObj.onreadystatechange = () => {
				loadingInstance.close();
				if (requestObj.readyState == 4) {
					if (requestObj.status == 200) {
						let obj = requestObj.response
						if (typeof obj !== 'object') {
							obj = JSON.parse(obj);
						}
						resolve(obj)
					}else if(requestObj.status == 401) {
						//表示session过期或者未授权
						router.push('/login');
					} else {
						reject(requestObj)
					}
				}
			}
		})

	}else{
		let requestConfig = {
			credentials: 'include',
			method: type,
			headers: {
				'Accept': 'application/json',
				'Content-Type': 'application/json'
			},
			mode: "cors",
			cache: "force-cache"
		}
		if (type == 'POST') {
			Object.defineProperty(requestConfig, 'body', {
				value: JSON.stringify(data)
			})
		}
		try {
			const response = await fetch(url, requestConfig);
			loadingInstance.close();
			if (response.status == 200) {
				const responseJson = await response.json();
				return responseJson
			}else if(response.status == 401) {
				//表示session过期或者未授权
				router.push('/login');
			}
		} catch (error) {
			throw new Error(error)
		}
	}
}