// pages/login/login.js
const defaultAvatarUrl = 'https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0'
const app = getApp();
import {
  xzRequest
} from "../../services/request";
import {
  StructMap
} from "../../services/strcut";

Page({

  /**
   * 页面的初始数据
   */
  data: {
    //昵称
    nickname: "",
    //头像图片url
    avatarUrl: defaultAvatarUrl,
    //手机号码
    phone: "",
    //用来配合组件使用的小区ID
    ID: -1,
    //小区ID
    communityID: 0,
    //小区
    community: [{}],

    //默认值
    faceUrl: "https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0"
  },
  phoneInput(e) {
    this.setData({
      phone: e.detail.value,
    });
    console.log(this.data.phone)
  },
  nameInput(e) {
    // 获取输入框的值并更新数据
    this.setData({
      nickname: e.detail.value,
    });
    console.log(this.data.nickname)
  },
  //先不注册
  skip() {
    wx.navigateBack();
  },

  //头像功能
  onChooseAvatar(e) {
    const {
      avatarUrl
    } = e.detail
    this.setData({
      avatarUrl,
    });


    var that = this;
    wx.uploadFile({
      url: xzRequest.baseUrl + "/easygift/obs/upload",
      filePath: avatarUrl,
      name: 'file',
      header: {
        'content-type': 'Application/json'
      },
      success: function (res) {
        console.log(res.data);
        const uploadData = JSON.parse(res.data);
        console.log(uploadData);
        that.data.faceUrl = uploadData.data.url;
        that.data.avatarUrl=that.data.faceUrl;
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    //Login
    this.GetCommunityList();
  },
  //小区选择器
  choose(e) {
    let that = this;
    console.log(e);
    that.setData({
      ID: e.detail.value
    })
    this.data.communityID = parseInt(this.data.ID) + 1;
  },
  appRegister() {
    var reg = /^[1][3,4,5,7,8][0-9]{9}$/;
    var test_phone = this.data.phone;
    console.log(this.data.nickname);
    console.log(this.data.phone);
    console.log(this.data.communityID);
    if (!this.data.faceUrl || !this.data.nickname || !this.data.phone || this.data.communityID == 0) {
      wx.showToast({
        title: '请填写完整信息',
      })
    } else if (!reg.test(test_phone)) {
      wx.showToast({
        title: '手机号不合法',
      })
    } else {
      wx.login({
        //成功返回
        success: (res) => {
          let code = res.code
          wx.request({
            url: `https://api.weixin.qq.com/sns/jscode2session?appid=wx28daaf963a62af94&secret=80364337a4e85bfc767c155da7f787a1&js_code=${code}&grant_type=authorization_code`,
            success: (res) => {
              console.log(res.data.openid);
              wx.setStorageSync('openId', res.data.openid);
              xzRequest.post({
                url: '/easygift/register',
                data: {
                  openId: res.data.openid,
                  nickName: this.data.nickname,
                  phone: this.data.phone,
                  communityId: this.data.communityID,
                  faceUrl: this.data.faceUrl
                }
              }).then(res => {
                if (res.code == 20000) {
                  wx.setStorageSync('token', res.data.token);
                  console.log(res.data.token);
                  const token = wx.getStorageSync('token')
                  xzRequest.get({
                    url: '/easygift/userinfo',
                    header: {
                      token
                    }
                  }).then(res => {
                    app.userinfo = res.data.user
                    wx.switchTab({
                      url: '../../pages/my/my',
                      success() {
                        let page = getCurrentPages().pop();
                        if (page == undefined || page == null) return;
                        page.onLoad();
                      }
                    })
                  })

                } else {
                  console.log(res);
                }
              })
            }
          })
        }
      })

    }
  },
  async GetCommunityList() {
    let res = await xzRequest.get({
      url: '/easygift/community/all',
    });
    let list = StructMap(res.data.list);
    this.setData({
      community: list
    })
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