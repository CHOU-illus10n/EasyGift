// pages/my-center/redemption/my-redemption.js
import {xzRequest} from '../../../services/request'
import Dialog from '@vant/weapp/dialog/dialog';
Page({

  /**
   * 页面的初始数据
   */
  data: {
		redemptionList: [],
		page: 1,
		limit: 10,
		token: '',
		goodOrder:[],
		userInfo:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
   onLoad() {
		const token = wx.getStorageSync('token')
		this.setData({token})
		if(token) {
			this.getUserInfo();
			this.getOrder();
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
				this.changeState();
				console.log(this.data.goodOrder)
			})
		},
	async getOrder(){
		const token = wx.getStorageSync('token')
		console.log(this.data.userInfo.userId)
		await xzRequest.get({
			url:'/user/good/order/list',
			header: {
				page: this.data.page,
				userId: this.data.userInfo.userId
			}
		}).then(res =>{
			console.log(res)
			this.setData({
				goodOrder:res.data.goodOrder
			})
		})
	}
	,
 changeState (){
		let j = this.data.goodOrder.length;
		console.log("111")
		for(let i = 0;i<j;i++){
			switch(this.data.goodOrder[i].state){
				case -1:
					this.data.goodOrder[i].state = '已取消';
					break;
				case 3:
					this.data.goodOrder[i].state = '待发货';
					break;
				case 4:
					this.data.goodOrder[i].state = '已发货';
					break;
				case 5:
						this.data.goodOrder[i].state = '交易完成';
						break;
			}
		}
		this.setData({
			goodOrder: this.data.goodOrder
		});
	}
	,
	fetchRedemptionListData(token) {
		const page = this.data.page
		const limit = this.data.limit
		xzRequest.get({
			url: '/user/good/order/list',
			header: {
				page: this.data.page

			}
		}).then(resp => {
			const resList = resp.data.list
			const finalList = [...this.data.redemptionList, ...resList]
			this.setData({ 
				redemptionList: finalList
			})
			this.data.page++
		})
	},

	onReachBottom() {
		this.fetchRedemptionListData(this.data.token)
	},
	//确认收货
	async confirm(e){
		Dialog.confirm({
			message: '您是否要确认收货',
		})
			.then(async res  => {
				xzRequest.post({
					url: '/user/good/order/change',
					header:{
						userId:this.data.userInfo.userId
					},
					data:{
						orderId:e.target.dataset.id,
						state:5
					}
				})
				this.onLoad();
			})
			.catch(() => {
				return
			});
	},
	async cancel(e){
		Dialog.confirm({
			message: '您是否要取消订单',
		})
			.then(async res  => {
				xzRequest.post({
					url: '/user/good/order/change',
					header:{
						userId:this.data.userInfo.userId
					},
					data:{
						orderId:e.target.dataset.id,
						state:-1
					}
				})
				this.onLoad();
			})
			.catch(()  => {
				return
			});
	}
})