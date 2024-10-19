const {
  xzRequest
} = require("../../services/request");

// pages/search/search.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    page: 1,
    scrollTop: 0,
    newlist: [],
    list: [],
    key: '',
    blank: false,
    hislist: [],
    nomore: false,
  },

  //添加到搜索历史
  history(key) {
    let that = this;
    wx.getStorage({
      key: 'history',
      success(res) {
        let oldarr = JSON.parse(res.data); //字符串转数组
        let newa = [key]; //对象转为数组
        let newarr = JSON.stringify(newa.concat(oldarr)); //连接数组\转字符串
        wx.setStorage({
          key: 'history',
          data: newarr,
        })
      },
      fail(res) {
        //第一次打开时获取为null
        let newa = [key]; //对象转为数组
        var newarr = JSON.stringify(newa); //数组转字符串
        wx.setStorage({
          key: 'history',
          data: newarr,
        })
      }
    });
  },
  //获取搜索框内容
  keyInput(e) {
    this.data.key = e.detail.value
  },

  search(n) {
    let that = this;
    let key = that.data.key;
    if (key == '') {
      wx.showToast({
        title: '请输入关键词',
        icon: 'none',
      })
      return false;
    }
    wx.setNavigationBarTitle({
      title: '"' + that.data.key + '"的搜索结果',
    })
    wx.showLoading({
      title: '加载中',
    })
    if (n !== 'his') {
      that.history(key);
    }
    let token=wx.getStorageSync('token');
    let searchText=encodeURIComponent(this.data.key);
    xzRequest.get({
      url: '/easygift/search',
      header: {
        token: token,
        searchText: searchText,
        page: this.data.page
      }
    }).then(res => {
      console.log(res);
      if (res.data.list.length == 0) { // 根据返回数据进行处理
        wx.hideLoading();
        that.setData({
          blank: true,
          list: [],
          nomore: false,
        });
      } else if (res.data.list.length < 20) {
        wx.hideLoading();
        console.log("为什么关不掉？？");
        that.setData({
          blank: true,
          list: res.data.list,
          nomore: false,
        });
        console.log(this.data.list);
      }
    }).catch(err => {
      console.error(err);
    });
  },


  //获取本地记录
  gethis() {
    let that = this;
    wx.getStorage({
      key: 'history',
      success: function (res) {
        let hislist = JSON.parse(res.data);
        //限制长度
        if (hislist.length > 5) {
          hislist.length = 5
        }
        that.setData({
          hislist: hislist
        })
      },
    })
  },
  //选择历史搜索关键词
  choosekey(e) {
    this.data.key = e.currentTarget.dataset.key;
    this.search('his');
  },
  //跳转详情
  detail(e) {
    let that = this;
    wx.navigateTo({
      url: '/pages/detail/detail?scene=' + e.currentTarget.dataset.id,
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

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