// pages/detail/detail.js
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
    obj:"",
    messageList: "",
    //用户输入的内容
    content: "",
    //当前用户的openid
    curUserId: '',
    //物品id
    id: '',
    //物品发布信息（不含图片）
    publishInfo: '',
    //物品图片信息，用于轮播图和物品详情展示
    publishImg: ''
  },


  //赠送的方法
  send(e) {
    wx.showModal({
      title: "确认赠送吗?",
      confirmColor: "#FBBD08",
      cancelColor: "#d81e06",
      success: (res) => {
        if (res.cancel) {
          console.log('用户点击了取消');
        } else if (res.confirm) {
          console.log('用户点击了确认');
          var customValue = e.currentTarget.dataset.customValue;
          let token = wx.getStorageSync('token');
          console.log(customValue);
          let price = this.data.publishInfo.giftOriginPrice
          let quality = this.data.publishInfo.giftQuality
          let incrPoint = price * (1 - quality * 0.1)
          console.log(incrPoint)
          xzRequest.post({
            url: '/easygift/send',
            header: {
              token: token,
            },
            data: {
              giftId: this.data.id,
              senderId: this.data.publishInfo.userId,
              receiverId: customValue,
              incrPoint: incrPoint
            }
          }).then(res => {
            
            this.onLoad(this.data.obj);
          })
        }
      },
      fail: (err) => {
        console.log(res);
      }
    })


  },

  async getMessageList() {
    let token = wx.getStorageSync('token');
    await xzRequest.get({
      url: '/easygift/getMessageList',
      header: {
        token: token,
        giftId: this.data.id
      },
    }).then(res => {
      console.log(res);
      this.setData({
        messageList: res.data.messageList,
      })
    }).catch(err => {
      console.error(err);
    });
  },

  //获取从方输入的信息
  messageInput(e) {
    this.data.content = e.detail.value;
    console.log(this.data.content)
  },
  //获取进入当前页面的用户信息
  async getCurrentUser() {
    let token = wx.getStorageSync('token');
    console.log();
    await xzRequest.get({
      url: '/easygift/userinfo',
      header: {
        token: token
      }
    }).then(res => {
      console.log(res);
      this.setData({
        curUserId: res.data.user.openId
      })
      console.log(this.data.curUserId)
    }).catch(err => {
      console.error(err);
    });
  },
  //留言
  formSubmit: function (e) {
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
    if (this.data.publishInfo.state == 1) {
      console.log(this.data.content.length == 0)
      if (this.data.content.length == 0) {
        wx.showToast({
          title: '留言不可以为空哦',
          icon: 'error'
        })
      } else {
        var that = this;
        let token = wx.getStorageSync('token');
        xzRequest.post({
          url: '/easygift/submitMessage',
          header: {
            token: token,
          },
          data: {
            content: this.data.content,
            giftId: this.data.id
          }
        }).then(res => {

          wx.showToast({
            title: '已留言',
            icon: 'success'
          })
          that.setData({
            content: "",
            messageList: res.data.messageList
          })
        })
      }

    }
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
  async getPublishImg(e) {
    let token = wx.getStorageSync('token');
    console.log(e);
    await xzRequest.get({
      url: '/easygift/publishinfoImg',
      header: {
        giftId: e,
        token: token
      }
    }).then(res => {
      console.log(res);
      this.setData({
        publishImg: res.data.list
      })
      console.log(this.data.publishImg)
    }).catch(err => {
      console.error(err);
    });
  },
  //改变发布信息/物品详情
  changeTitle(e) {
    let that = this;
    that.setData({
      first_title: e.currentTarget.dataset.id
    })
  },
  //获取发布物品信息
  async getPublish(e) {
    let that = this;
    let token = wx.getStorageSync('token');
    console.log(e);
    await xzRequest.get({
      url: '/easygift/publishinfo',
      header: {
        giftId: e,
        token: token
      }
    }).then(res => {
      console.log("获取详情信息成功");
      this.setData({
        publishInfo: res.data.publishVoList[0]
      })
      console.log(this.data.publishInfo)
    }).catch(err => {
      console.error(err);
    });
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
    this.data.obj = e;
    this.getCurrentUser();
    this.data.id = e.scene;
    this.getPublish(e.scene);
    this.getPublishImg(e.scene);
    this.getMessageList();
    // let openid = wx.getStorageSync('openId')
    // if (openid) {
    //   this.setData({
    //     openid: app.openid
    //   })
    // } else {
    //   console.log("no openid");
    //   wx.showModal({
    //     title: '温馨提示',
    //     content: '该功能需要注册方可使用，是否马上去注册',
    //     success(res) {
    //       if (res.confirm) {
    //         wx.navigateTo({
    //           url: '/pages/login/login',
    //         })
    //       }
    //     }
    //   })
    //   return false
    // }
    // this.getBuyer(this.data.openid)
    // wx.showShareMenu({
    //   withShareTicket: true,
    //   menus: ['shareAppMessage', 'shareTimeline']
    // })
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

  }
})