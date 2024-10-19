import {
  xzRequest
} from '../../../services/request'
const service = require('../../../services/format.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list: "",
    token: '',
  },
  reject(e) {
    let giftId = e.currentTarget.dataset.customdata
    console.log(giftId);
    wx.showModal({
      title: "真的要拒绝吗?",
      confirmColor: "#FBBD08",
      cancelColor: "#d81e06",
      success: (res) => {
        if (res.cancel) {} else if (res.confirm) {
          let token = wx.getStorageSync('token');
          console.log('用户点击了确认');
          xzRequest.post({
            url: '/easygift/GiftStateReject',
            header: {
              token: token,
            },
            data: {
              giftId: giftId,
            }
          }).then(res => {
            this.onLoad();
          })
        }
      },
      fail: (err) => {
        console.log(res);
      }
    })
  },
  confirm(e) {
    let giftId = e.currentTarget.dataset.customdata
    console.log(giftId);
    wx.showModal({
      title: "确认收到吗?",
      confirmColor: "#FBBD08",
      cancelColor: "#d81e06",
      success: (res) => {
        if (res.cancel) {} else if (res.confirm) {
          let token = wx.getStorageSync('token');
          console.log('用户点击了确认');
          xzRequest.post({
            url: '/easygift/GiftState',
            header: {
              token: token,
            },
            data: {
              giftId: giftId,
            }
          }).then(res => {
            this.onLoad();
          })
        }
      },
      fail: (err) => {}
    })
  },
  async getISend() {
    let that = this;
    let token = wx.getStorageSync('token');
    let res = await xzRequest.get({
      url: '/easygift/IReceive',
      header: {
        token: token,
      }
    }).then(res => {
      var formattedList = res.data.List.map(function (item) {
        var datetime = item.dealTime;
        var formattedDate = service.GetDate(datetime);
        item.dealTime = formattedDate;
        return item;
      });
      this.setData({
        list: formattedList
      })
      console.log(this.data.list)
    }).catch(err => {
      console.error(err);
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad() {
    this.getISend();
  },
  onShow() {
    this.getISend();
  }

})