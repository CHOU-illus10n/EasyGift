// pages/index/index.js
import {
  xzRequest
} from "../../services/request";
const app = getApp()
const config = require("../../config.js");
const util = require('../../utils/util.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    page: 1,
    //用来检测是否要下拉刷新的
    nomore: false,
    //返回物品列表
    list: [],
    //屏幕滚动操作
    scrollTop: 0,
    //控制类别小窗口的
    showList: false,
    //轮播图
    banner: [],
    //当前类别
    categoryCur: 0,
    //物品类别
    category: [{}],
    nomore: false
  },
  //去往最顶端
  gotop() {
    wx.pageScrollTo({
      scrollTop: 0
    })
  },
  //显示更多
  async more() {
    let that = this;
    if (that.data.nomore || that.data.list.length < 20) {
      wx.stopPullDownRefresh();
      return false
    }
    that.data.page = that.data.page + 1;
    if (this.data.categoryCur == 0) {
      console.log("调用了getlist")
      let that = this;
      let token = wx.getStorageSync('token');
      let res = await xzRequest.get({
        url: '/easygift/giftlist',
        header: {
          token: token,
          page: this.data.page
        }
      }).then(res => {
        wx.stopPullDownRefresh(); // 暂停刷新动作
        console.log(res);
        if (res.data.length == 0) {
          that.setData({
            nomore: true
          })
          return false;
        }
        if (res.data.length < 20) {
          that.setData({
            nomore: true
          })
        }
        that.setData({
          list: that.data.list.concat(res.data.giftlist)
        })
      }).catch(err => {
        console.error(err);
      });
      console.log("执行完了");
    } else {
      console.log("调用了getlistbyCategoryId")
      let that = this;
      let token = wx.getStorageSync('token');
      await xzRequest.get({
        url: '/easygift/giftlist/bycategory',
        header: {
          categoryId: that.data.categoryCur,
          token: token
        }
      }).then(res => {
        wx.stopPullDownRefresh(); // 暂停刷新动作
        console.log(res);
        if (res.data.length == 0) {
          that.setData({
            nomore: true
          })
          return false;
        }
        if (res.data.length < 20) {
          that.setData({
            nomore: true
          })
        }
        that.setData({
          page: page,
          list: that.data.list.concat(that.data.giftlist)
        })
      }).catch(err => {
        console.error(err);
      });
      console.log("执行完了");
    }
  },
  //跳转搜索
  search() {
    wx.navigateTo({
      url: '/pages/search/search',
    })
  },
  //设想是用户如果很久没登陆，token那些失效，会通过再次获取openid来获取身份
  // async appLogin() {
  //   let openId = wx.getStorageSync('openId');
  //   let token = wx.getStorageSync('token');
  //   console.log(openId);
  //   console.log(token);
  //   if (openId == "" || token == "") {
  //     wx.login({
  //       success: (res) => {
  //         console.log(res);
  //         let code = res.code;
  //         wx.request({
  //           url: `https://api.weixin.qq.com/sns/jscode2session?appid=wx28daaf963a62af94&secret=80364337a4e85bfc767c155da7f787a1&js_code=${code}&grant_type=authorization_code`,
  //           success: (resp) => {
  //             console.log(resp.data.openid);
  //             wx.setStorageSync('openId', resp.data.openid);
  //             let res = xzRequest.post({
  //               url: `/easygift/login`,
  //               data: {
  //                 openId:resp.data.openid
  //               }
  //             });
  //             if (res.code == 20000) {
  //               console.log("登录成功");
  //               wx.setStorageSync('token', res.data.token);
  //               console.log(res.data.token);
  //               xzRequest.get({
  //                 url: '/easygift/userinfo',
  //                 header: {
  //                   token:res.data.token
  //                 }
  //               }).then(res => {
  //                 app.userinfo = res.data.user
  //               })                                          
  //             }

  //           }
  //         })
  //       },
  //     })
  //   }


  // },
  
  //下拉刷新
  onPullDownRefresh() {
    this.getList();
  },
  //监测屏幕滚动
  onPageScroll: function (e) {
    this.setData({
      scrollTop: parseInt((e.scrollTop) * wx.getSystemInfoSync().pixelRatio)
    })
  },
  //展示列表小面板
  showlist() {
    let that = this;
    if (that.data.showList) {
      that.setData({
        showList: false,
      })
    } else {
      that.setData({
        showList: true,
      })
    }
  },
  //根据类别选择商品
  categorySelect(e) {
    this.setData({
      categoryCur: e.currentTarget.dataset.id + 1,
      scrollLeft: (e.currentTarget.dataset.id - 3) * 100,
      showList: false,
      page : 1
    })
    console.log(this.data.categoryCur);
    this.getListByCategory();
  },
  //根据全部选择商品
  selectAll() {
    this.setData({
      categoryCur: 0,
      scrollLeft: -200,
      showList: false,
    })
    this.getList();
  },
  //跳转详情
  detail(e) {
    let that = this;
    console.log(e);
    wx.navigateTo({
      url: '/pages/detail/detail?scene=' + e.currentTarget.dataset.id,
    })
  },

  //获取轮播图数据
  async getbanner() {
    let token = wx.getStorageSync('token');
    let res = await xzRequest.get({
      url: '/easygift/swiper',
      header: {
        token: token
      }
    });
    let swiperData = res.data.swiper;
    let urls = swiperData.map(item => item.giftImgUrl);
    this.setData({
      banner: urls
    });
  },
  //获取物品列表数据by类别
  async getListByCategory() {
    console.log("调用了getlistbyCategoryId")
    let that = this;
    let token = wx.getStorageSync('token');
    await xzRequest.get({
      url: '/easygift/giftlist/bycategory',
      header: {
        categoryId: that.data.categoryCur,
        token: token,
        page:this.data.page
      }
    }).then(res => {
      wx.stopPullDownRefresh(); // 暂停刷新动作
      console.log(res);
      if (res.length === 0) { // 根据返回数据进行处理
        that.setData({
          nomore: true,
          list: []
        });
      } else if (res.length < 20) {
        that.setData({
          nomore: true,
          list: res.data.giftlist
        });
        console.log(this.data.list);
      } else {
        that.setData({
          list: res.data.giftlist,
          nomore: false
        });
      }
    }).catch(err => {
      console.error(err);
    });
  },
  //获取物品列表数据
  async getList() {
    console.log("调用了getlist")
    let that = this;
    let categoryId = that.data.collegeCur
    let token = wx.getStorageSync('token');
    let res = await xzRequest.get({
      url: '/easygift/giftlist',
      header: {
        token: token,
        page:this.data.page
      }
    }).then(res => {
      wx.stopPullDownRefresh(); // 暂停刷新动作
      console.log(res);
      if (res.length === 0) { // 根据返回数据进行处理
        that.setData({
          nomore: true,
          list: []
        });
      } else if (res.length < 20) {
        that.setData({
          nomore: true,
          list: res.data.giftlist
        });
        console.log(this.data.list);
      } else {
        that.setData({
          list: res.data.giftlist,
          nomore: false
        });
      }
    }).catch(err => {
      console.error(err);
    });
  },
  //获取转赠物品类别数据
  async getcategory() {
    let res = await xzRequest.get({
      url: '/easygift/category',
    })
    console.log(res);
    this.setData({
      category: res.data.list
    });
  },

  //布局方式选择
  changeCard() {
    let that = this;
    if (that.data.iscard) {
      that.setData({
        iscard: false
      })
      wx.setStorage({
        key: 'iscard',
        data: false,
      })
    } else {
      that.setData({
        iscard: true
      })
      wx.setStorage({
        key: 'iscard',
        data: true,
      })
    }
  },
  //获取上次布局记忆
  listkind() {
    let that = this;
    wx.getStorage({
      key: 'iscard',
      success: function (res) {
        that.setData({
          iscard: res.data
        })
      },
      fail() {
        that.setData({
          iscard: true,
        })
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */


  onLoad(options) {
    this.getbanner();
    this.getcategory();
    this.getList();
    this.listkind();
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
    this.data.page=1;
    this.getbanner();
    this.getcategory();
    this.getList();
    this.listkind();
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },
  //下拉刷新
  onPullDownRefresh() {
    this.getList();
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
    this.more();
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }

})