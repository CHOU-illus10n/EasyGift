// pages/detail/detail.js
import Dialog from '../../miniprogram_npm/@vant/weapp/dialog/dialog';
const app = getApp()
const config = require("../../config.js");
const {
  xzRequest
} = require("../../services/request.js");
let obj = ''
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //物品发布信息（不含图片）
    publishInfo: '',
    //物品图片信息，用于轮播图和物品详情展示
    publishImg: '',
    num: '1',
    userInfo: ''
  },
  //图片点击事件
  img: function (event) {
    let arr = [];
    this.data.publishImg.forEach(item => {
      arr.push(item.giftImgUrl);
    });
    console.log(arr);
    var src = event.currentTarget.dataset.src;
    console.log(src);
    //图片预览
    wx.previewImage({
      current: src, // 当前显示图片的http链接
      urls: arr // 需要预览的图片http链接列表
    })
  },
  changeTitle(e) {
    let that = this;
    that.setData({
      first_title: e.currentTarget.dataset.id
    })
  },
  //获取发布物品信息
  async getPublish(e) {
    let that = this;
    //let token = wx.getStorageSync('token');
    console.log(e);
    await xzRequest.get({
      url: '/pointmall/good/detail',
      header: {
        goodId: e,
        //token: token
      }
    }).then(res => {
      console.log(res);
      this.setData({
        publishInfo: res.data.goodInfo[0],
        publishImg: res.data.goodInfo[0].goodImg
      })
      console.log(this.data.goodInfo)
    }).catch(err => {
      console.error(err);
    });
  },
  async getUserInfo() {
    let that = this;
    let token = wx.getStorageSync('token');
    console.log(token);
    await xzRequest.get({
      url: '/easygift/userinfo',
      header: {
        token: token
      }
    }).then(res => {
      console.log(res);
      this.setData({
        userInfo: res.data.user
      })
      console.log(this.data.userInfo)
    })
  },

  //回到首页
  home() {
    wx.switchTab({
      url: '/pages/index/index',
    })
  },
  //回到我的
  my() {
    wx.switchTab({
      url: '/pages/my/my',
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(e) {
    obj = e;
    this.data.id = e.scene;
    this.getPublish(e.scene);
   
    this.getUserInfo();
    
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  },
  load: function (e) {
    this.setData({
      height: e.detail.height,
      width: e.detail.width
    })
    console.log(this.data.height)
    console.log(this.data.width)
  },
  //点击我显示底部弹出框
  clickme: function () {
    let token = wx.getStorageSync('token')
    console.log(token);
    if (token == "") {
      wx.showModal({
        title: '温馨提示',
        content: '该功能需要注册方可使用，是否马上去注册',
        success(res) {
          if (res.confirm) {
            wx.navigateTo({
              url: '/pages/login/login',
            })
          }
        }
      })
      return false
    }
    this.showModal();
  },
  onChange: function (e) {
    this.data.num = e.detail;
    console.log(e.detail)
  },
  buy: function () {
    if (this.data.num > this.data.publishInfo.goodCount) {
      Dialog.alert({
        context: this,
        message: '购买数量超过商品库存'
      }).then(() => {
        // on close
      });
    } else if (this.data.num * this.data.publishInfo.goodPoint > this.data.userInfo.points) {
      Dialog.alert({
        context: this,
        message: '积分不足！'
      }).then(() => {
        // on close
      });
    } else {
      this.buyGood();
      Dialog.alert({
        context: this,
        message: '兑换成功'
      }).then(() => {
        // on close
      });
    }
  },
  async buyGood() {

    await xzRequest.post({
      url: '/pointmall/good/buy',
      header: {
        goodId: this.data.publishInfo.goodId,
        goodCount: this.data.num,
        userId: this.data.userInfo.userId
      }
    }).then(res => {
      console.log(res);
    })
  },
  //显示对话框
  showModal: function () {
    // 显示遮罩层
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()
    this.setData({
      animationData: animation.export(),
      showModalStatus: true
    })
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation.export()
      })
    }.bind(this), 200)
  },
  //隐藏对话框
  hideModal: function () {
    // 隐藏遮罩层
    var animation = wx.createAnimation({
      duration: 200,
      timingFunction: "linear",
      delay: 0
    })
    this.animation = animation
    animation.translateY(300).step()
    this.setData({
      animationData: animation.export(),
    })
    setTimeout(function () {
      animation.translateY(0).step()
      this.setData({
        animationData: animation.export(),
        showModalStatus: false
      })
    }.bind(this), 200)
  },

})