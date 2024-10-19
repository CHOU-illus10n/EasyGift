//index.js
//获取应用实例
const app = getApp()
import {
  xzRequest
} from "../../services/request";
Page({
  data: {
    // 组件所需的参数
    height: 0,  
    floorstatus: "none",
    activeIndex: 1,
    me_message: [],
    userId: -1,
    // 此页面 页面内容距最顶部的距离
    isLoading: false
  },
  async getNoticeList() {
    let token = wx.getStorageSync('token');
    await xzRequest.get({
      url: '/easygift/NoticeList',
      header: {
        token: token,
      },
    }).then(res => {
      console.log(res);
      this.setData({
        messageList: res.data.noticeList,
      })
    }).catch(err => {
      console.error(err);
    });
  },
  onReady() {
    let that = this
    setTimeout(function() {
      that.setData({
        isLoading: true
      })
    }, 500)




  },
  onLoad() {
    this.getNoticeList();

  },
  onShow(){
    this.getNoticeList();
  }


})