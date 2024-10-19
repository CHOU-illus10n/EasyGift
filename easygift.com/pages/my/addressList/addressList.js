// pages/my-center/redemption/my-redemption.js
import {xzRequest} from '../../../services/request'
Page({

  /**
   * 页面的初始数据
   */
  data: {
		page: 1,
		limit: 10,
		token: '',
		shoppingInfo:'',
		userInfo:'',
		checked: [],
		state:0,
		num:0
  },

  /**
   * 生命周期函数--监听页面加载
   */
   onLoad(options) {
		const token = wx.getStorageSync('token')
		this.setData({token})
		if(token) {
			this.getUserInfo();
			// this.fetchRedemptionListData(token);
		}
	},

	async getUserInfo(){
		  let that = this;
			let token = wx.getStorageSync('token');
			console.log(token);
			await xzRequest.get({
				url:'/easygift/userinfo',
				header:{
					token: token
				}
			}).then(async res => {
				console.log(res);
				this.setData({
					userInfo:res.data.user
				})
				console.log(this.data.userInfo)
				await this.getOrder();
				for(let j =0;j<this.data.shoppingInfo.length;j++){
					if(this.data.shoppingInfo[j].state == 1){
						this.data.checked[j+1] = true
						this.data.num=j+1
					}else{
						this.data.checked[j+1] = false
					}
				}
				this.setData({
					checked: this.data.checked,
					num:this.data.num
				});
				console.log(this.data.checked)
			})
		},
	async getOrder(){
		const token = wx.getStorageSync('token')
		console.log(this.data.userInfo.userId)
		await xzRequest.get({
			url:'/user/shopping/list',
			header: {
				page: this.data.page,
				userId: this.data.userInfo.userId
			}
		}).then(res =>{
			console.log(res)
			this.setData({
				shoppingInfo:res.data.shoppingInfo
			})
			console.log(this.data.shoppingInfo)
		})
	}
	,

	onReachBottom() {
	
	},
//  onChange({ event }) {

// 		console.log(event.currentTarget.dataset.itemindex)
// 		// 需要手动对 checked 状态进行更新
// 		const index;
// 		for(let j =0;j<this.data.shoppingInfo.length;j++){
// 				if(checked[j+1] == true && this.data.num!=j+1){
// 					index = j+1
// 				}
// 		}
// 		this.setData({ checked: detail });
// 		if(this.data.checked==false){
// 			this.setData({state: 0})
// 		}else if(this.data.checked==true){
// 			this.setData({state: 1})
// 		}
	// 	await xzRequest.post({
	// 		url:'/user/shopping/update',
	// 		data:{
	// 			userId:this.data.userInfo.userId,
	// 			shoppingId:this.data.shoppingId
	// 		}
	// 	})
	// },
onChange: function (event) {
	const index = event.currentTarget.dataset.itemindex;
	const shoppingId = this.data.shoppingInfo[index].shoppingId;

	// 调用处理开关切换的函数
//	this.handleSwitchChange(shoppingId);
},
	goMyAddress(){
    wx.navigateTo({
      url: '/pages/my/myAddress/myAddress'
    })
	},
	async change(e){
		let  index  = e.currentTarget.id;
		console.log(index)
		for(let j =0;j<this.data.shoppingInfo.length;j++){
			if(this.data.shoppingInfo[j].shoppingId!=index){
				this.data.shoppingInfo[j].state=0
			}
			this.data.shoppingInfo[j].state=1
		}
		wx.showToast({
			              title: '正在更改...',
			              icon: 'loading',
			              mask: true,
			              duration: 100
			            })
		await xzRequest.post({
			url:'/user/shopping/update',
			data:{
				userId:this.data.userInfo.userId,
				shoppingId:index
			},
			header:{'Content-Type':"application/x-www-form-urlencoded"}

		}).then(res =>{
			console.log(res)
			this.getOrder()
		})
	}
})