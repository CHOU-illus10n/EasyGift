
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
  
  async getISend() {
    let that = this;
    let token = wx.getStorageSync('token');
    let res = await xzRequest.get({
      url: '/easygift/ISend',
      header: {
        token: token,
      }
    }).then(res => {
      var formattedList = res.data.List.map(function(item) {
        var datetime = item.dealTime;
        var formattedDate = service.GetDate(datetime);
        item.dealTime = formattedDate;
        return item;
      });
      this.setData({
        list:formattedList
      })
      console.log(this.data.List)
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