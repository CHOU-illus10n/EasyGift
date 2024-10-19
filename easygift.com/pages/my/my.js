// pages/my/my.js
import {
  xzRequest
} from "../../services/request";
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    token: '',
    userinfo: '',
  },
  goIPublish() {
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
    wx.navigateTo({
      url: '/pages/my/IPublish/IPublish'
    })
  },
  goIGet() {
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
    wx.navigateTo({
      url: '/pages/my/IGet/my-redemption'
    })
  },
  goMyInfo(){
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
    wx.navigateTo({
      url: '/pages/my/myInfo/myInfo'
    })
  },
  goMyAddress(){
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
    wx.navigateTo({
      url: '/pages/my/addressList/addressList'
    })
  },
  goIReceive() {
    
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
    wx.navigateTo({
      url: '/pages/my/IReceive/IReceive'
    })
  },
  goISend() {
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
    wx.navigateTo({
      url: '/pages/my/ISend/ISend'
    })
  },
  async appLogin() {
    let openId = wx.getStorageSync('openId');
    let token = wx.getStorageSync('token');
    console.log(openId);
    console.log(token);
    if (openId == "" || token == "") {
      let res = await new Promise((resolve, reject) => {
        wx.login({
          success: (res) => {
            console.log(res);
            let code = res.code;
            wx.request({
              url: `https://api.weixin.qq.com/sns/jscode2session?appid=wx28daaf963a62af94&secret=80364337a4e85bfc767c155da7f787a1&js_code=${code}&grant_type=authorization_code`,
              success: (resp) => {
                console.log(resp.data.openid);
                wx.setStorageSync('openId', resp.data.openid);
                resolve(resp.data.openid);
              },
              fail: (error) => {
                reject(error);
              }
            })
          },
          fail: (error) => {
            reject(error);
          }
        });
      });
      // 调用Login函数
      await this.Login();
    }
  },
  async Login() {
    let openid = wx.getStorageSync('openId')
    let res = await xzRequest.post({
      url: `/easygift/login`,
      data: {
        openId: openid
      }
    });
    console.log(res);
    if (res.code == 20000) {
      console.log("登录成功");
      wx.setStorageSync('token', res.data.token);
      console.log(res.data.token);
      let userInfoRes = await xzRequest.get({
        url: '/easygift/userinfo',
        header: {
          token: res.data.token
        }
      });
      this.setData({
        userinfo: userInfoRes.data.user
      });
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.appLogin().then((res) => {
      const token = wx.getStorageSync('token')
      console.log(token);
      this.setData({
        token
      })
      if (token) {
        this.fetchMyCenterInfo(token)
      } else {
        console.log(app.userinfo);
        console.log('token不合法, 请先登录');
      }
    }).catch((error) => {
      console.log(error);
    });
  },
  fetchMyCenterInfo(token) {
    xzRequest.get({
      url: '/easygift/userinfo',
      header: {
        token
      }
    }).then(res => {
      console.log(res);
      this.setData({
        userinfo: res.data.user
      })
      console.log(this.data.userinfo)
    })
  },
  go(e) {
    if (e.currentTarget.dataset.status == '1') {
      if (!app.openid) {
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
    }
    wx.navigateTo({
      url: e.currentTarget.dataset.go
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
    const token = wx.getStorageSync('token')
    console.log(token);
    this.setData({
      token
    })
    if (token) {
      this.fetchMyCenterInfo(token)
    } else {
      console.log(app.userinfo);
      console.log('token不合法, 请先登录');
    }
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

})