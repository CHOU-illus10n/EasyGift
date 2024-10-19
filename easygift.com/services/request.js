// const { resolve } = require("path")

class XZRequest {
	constructor(baseUrl) {
		this.baseUrl = baseUrl
	}
	request(options) {
		const {url} = options
		return new Promise((resolve, reject) => {
			wx.request({
				...options,
				url: this.baseUrl + url,
				success: (res) => {
					resolve(res.data)
				},
				fail: reject
			})
		})
	}
	get(options) {
		return this.request({...options, method: 'get'})
	}
	post(options) {
		return this.request({...options, method: 'post'})
	}
	put(options) {
		return this.request({...options, method: 'put'})
	}
	delete(options) {
		return this.request({...options, method: 'delete'})
	}
}


export const xzRequest = new XZRequest("http://182.92.97.61:9090")//自己调试的话这个一定要改！