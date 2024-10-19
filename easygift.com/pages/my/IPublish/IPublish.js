import {
  xzRequest
} from '../../../services/request'
const service = require('../../../services/format.js');
const app = getApp()
const config = require("../../../config");
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list: "",
    token: '',
  },
  detail(e) {
    let that = this;
    console.log(e);
    wx.navigateTo({
      url: '/pages/detail/detail?scene=' + e.currentTarget.dataset.id,
    })
  },
  async getIPublish() {
    let that = this;
    let token = wx.getStorageSync('token');
    let res = await xzRequest.get({
      url: '/easygift/IPublish',
      header: {
        token: token,
      }
    }).then(res => {
      console.log(res);
      var formattedList = res.data.giftlist.map(function(item) {
        var datetime = item.dealTime;
        var formattedDate = service.GetDate(datetime);
        item.dealTime = formattedDate;
        return item;
      });
      this.setData({
        list:formattedList
      })
    }).catch(err => {
      console.error(err);
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad() {
    this.getIPublish();
  },
  onShow() {
    this.getIPublish();
  }

})