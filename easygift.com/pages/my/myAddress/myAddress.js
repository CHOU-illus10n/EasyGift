// pages/my/my-add-address/index.js
var address = require("mock.js");
const { xzRequest } = require("../../../services/request");
 
var app = getApp()
Page({
  /**
    * 控件当前显示的数据
    * provinces:所有省份
    * citys 选择省对应的所有市,
    * areas 选择市对应的所有区
    * consigneeRegion：点击确定时选择的省市县结果
    * animationAddressMenu：动画
    * addressMenuIsShow：是否可见
    */
  /**
   * 页面的初始数据
   */
  data: {
		//默认地址
		checked:false,
    animationAddressMenu: {},
    addressMenuIsShow: false,
    value: [0, 0, 0],
    provinces: [],
    citys: [],
    areas: [],
    consigneeName: "", 
    phone: "",
    consigneeRegion: "",
    detailedAddress: "",
    labelList: ["家", "公司", "学校"],            //标签
    labelDefault: 0,              // 标签默认,
		userInfo:'',
    state:0
	},
	onChange({ detail }) {
    // 需要手动对 checked 状态进行更新
		this.setData({ checked: detail });
		if(this.data.checked==false){
			this.setData({state: 0})
		}else if(this.data.checked==true){
			this.setData({state: 1})
		}
		console.log(this.data.state)
  },
  consigneeNameInput: function(e) {
    
    this.setData({
      consigneeName: e.detail.value
    })
  },
  phoneInput: function(e) {
    
    this.setData({
      phone: e.detail.value
    })
  },
  consigneeRegionInput: function (e) {
   console.log("1111"+e)
    this.setData({
      consigneeRegion: e.detail.value
		})
		console.log(this.data.consigneeRegion)
  },
  detailedAddressInput: function (e) {
    this.setData({
      detailedAddress: e.detail.value
		})
		console.log(this.data.detailedAddress);
  },
  chooseLabelSelect: function(e) {
    var index = e.currentTarget.dataset.index;
    this.setData({
      labelDefault: index
    })
  },
  async submit() {
		
    var consigneeName = this.data.consigneeName;
    console.log(consigneeName)
    var phone = this.data.phone;
    console.log(phone)
    var consigneeRegion = this.data.consigneeRegion;
    console.log(consigneeRegion)
    var detailedAddress = this.data.detailedAddress
    console.log(detailedAddress)
    if (consigneeName == "") {
      wx: wx.showToast({
        title: '请输入姓名',
				//image: "../../../img/icon/icon-reminder.png"
				icon:'error'
      })
      return false
    }
    else if (phone == "") {
      wx: wx.showToast({
        title: '请输入手机号码',
				icon:'error'
      })
      return false
    }
    else if (consigneeRegion == "") {
      wx: wx.showToast({
        title: '请选择所在地区',
				icon:'error'
      })
      return false
    }
    else if (detailedAddress == "") {
      wx: wx.showToast({
        title: '请输入详细地址',
				icon:'error'
      })
      return false
    }
    else {
			const token = wx.getStorageSync('token')
			const shoppingInfo = {
				receiverPhone:this.data.phone,
				address:this.data.consigneeRegion,
				receiverAddress:this.data.detailedAddress,
				receiverName:this.data.consigneeName,
				state:this.data.state
		}
			await xzRequest.post({
				url: '/user/shopping/address',
				header: {
					token: token,
				},
				data: shoppingInfo,
			})
      wx.switchTab({
        url: '../../my/my',
      })
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
		})
	},
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    // 默认联动显示北京
    var id = address.provinces[0].id
    this.setData({
      provinces: address.provinces,
      citys: address.citys[id],
      areas: address.areas[address.citys[id][0].id],
		})
		const token = wx.getStorageSync('token')
		// this.setData({token})
		if(token) {
			this.getUserInfo();
		}
  },
  // 点击所在地区弹出选择框
  select: function (e) {
    // 如果已经显示，不在执行显示动画
    if (this.data.addressMenuIsShow) {
      return false
    } else {
      // 执行显示动画
      this.startAddressAnimation(true)
    }
  },
  // 执行动画
  startAddressAnimation: function (isShow) {
    if (isShow) {
      // vh是用来表示尺寸的单位，高度全屏是100vh
      this.animation.translateY(0 + 'vh').step()
    } else {
      this.animation.translateY(40 + 'vh').step()
    }
    this.setData({
      animationAddressMenu: this.animation.export(),
      addressMenuIsShow: isShow,
    })
  },
  // 点击地区选择取消按钮
  cityCancel: function (e) {
    this.startAddressAnimation(false)
  },
  // 点击地区选择确定按钮
  citySure: function (e) {
    var that = this
    var city = that.data.city
    var value = that.data.value
    this.startAddressAnimation(false)
    // 将选择的城市信息显示到输入框
    var consigneeRegion = that.data.provinces[value[0]].name + '-' + that.data.citys[value[1]].name + '-' + that.data.areas[value[2]].name
    that.setData({
      consigneeRegion: consigneeRegion,
    })
  },
  // 处理省市县联动逻辑
  cityChange: function (e) {
    var value = e.detail.value
    var provinces = this.data.provinces
    var citys = this.data.citys
    var areas = this.data.areas
    var provinceNum = value[0]
    var cityNum = value[1]
    var countyNum = value[2]
    // 如果省份选择项和之前不一样，表示滑动了省份，此时市默认是省的第一组数据，
    if (this.data.value[0] != provinceNum) {
      var id = provinces[provinceNum].id
      this.setData({
        value: [provinceNum, 0, 0],
        citys: address.citys[id],
        areas: address.areas[address.citys[id][0].id],
      })
    } else if (this.data.value[1] != cityNum) {
      // 滑动选择了第二项数据，即市，此时区显示省市对应的第一组数据
      var id = citys[cityNum].id
      this.setData({
        value: [provinceNum, cityNum, 0],
        areas: address.areas[citys[cityNum].id],
      })
    } else {
      // 滑动选择了区
      this.setData({
        value: [provinceNum, cityNum, countyNum]
      })
    }
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
 
  },
 
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var animation = wx.createAnimation({
      duration: 500,
      timingFunction: 'linear',
    })
    this.animation = animation
  },
 
  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
 
  },
 
  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
 
  },
 
  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
 
  },
 
  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
 
  },
 
  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
 
	},
	 /**
   * 生命周期函数--监听页面加载
   */

})