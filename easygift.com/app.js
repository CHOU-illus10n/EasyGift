import { xzRequest } from "./services/request"

// app.js
App({
  openid: '',
  userinfo:'',
  globalData: {
    userInfo: null,
    selected: 0
  },
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    wx.login({
      
    })
    
  },
  

  
})
