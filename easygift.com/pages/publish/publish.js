// pages/publish/publish.js

import {
  xzRequest
} from "../../services/request";
const app = getApp();
const config = require("../../config.js");
const MAX_IMG_NUM = 8;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //用于页面跳转的物品id
    detail_id: "",
    //物品
    //物品名称
    giftName: '',
    //物品详细描述
    description: '',
    //把所有图片地址concat起来
    tempFilePaths: [],
    //物品原价
    giftPrice: 15.0,
    //要传出去的类别值
    categoryID: "",
    //物品成色选择的默认值AND要传出去的物品成色ID
    qualityID: -1,
    //要传出去的购买日期
    purchaseTime: '',
    //要传出去的交易日期
    dealDate: '',
    //要传出去的取货地址
    dealAddress: '',

    //配置两个时间选择器按钮的值   
    timeValue1: "",
    timeValue2: "",
    //物品成色配置列表
    qualitys: [{
        id: 0,
        qualityName: '九九新'
      },
      {
        id: 1,
        qualityName: '九五新'
      },
      {
        id: 2,
        qualityName: '九成新'
      },
      {
        id: 3,
        qualityName: '八成新'
      },
      {
        id: 4,
        qualityName: '以下'
      }
    ],
    //类别选择的默认值
    cids: '-1',
    //物品类别
    category: [{}],
    //删除图片参数
    params: {
      imgUrl: new Array(),
    },
    //添加的单个文件的地址
    filePath: [],
    //步骤
    steps: [{
        text: '步骤一',
        desc: '补充物品信息'
      },
      {
        text: '步骤二',
        desc: '发布成功',
      },
    ],
    entime: {
      enter: 600,
      leave: 300
    },
    selectPhoto: true,
  },

  detail() {
    let that = this;
    wx.navigateTo({
      url: '/pages/detail/detail?scene=' + that.data.detail_id,
    })
  },
  //恢复初始态
  initial() {
    let that = this;
    that.setData({
      giftName: '',
      //物品详细描述
      description: '',
      //把所有图片地址concat起来
      tempFilePaths: [],
      //物品原价
      giftPrice: 15.0,
      //要传出去的类别值
      categoryID: "",
      //物品成色选择的默认值AND要传出去的物品成色ID
      qualityID: -1,
      //要传出去的购买日期
      purchaseTime: '',
      //要传出去的交易日期
      dealDate: '',
      //要传出去的取货地址
      dealAddress: '',

      //配置两个时间选择器按钮的值   
      timeValue1: "",
      timeValue2: "",
      dura: 30,
      price: 15,
      place: '',
      chooseDelivery: 0,
      cids: '-1', //类别选择的默认值
      show_b: true,
      show_c: false,
      active: 0,
      note_counts: 0,
      desc_counts: 0,
      notes: '',
      describe: '',
      good: '',
      kindid: 0,
      showorhide: true,
      tempFilePaths: [],
      params: {
        imgUrl: new Array(),
      },
      imgUrl: [],
      selectPhoto: true
    })
  },
  //物品输入
  goodInput(e) {
    this.data.giftName = e.detail.value;
    console.log(this.data.giftName)
  },
  //输入描述
  describeInput(e) {
    let that = this;
    that.setData({
      desc_counts: e.detail.cursor,
      description: e.detail.value,
    });
    console.log(this.data.description);
  },
  //价格输入改变
  priceChange(e) {
    this.data.giftPrice = e.detail;
  },
  //取货地址输入
  placeInput(e) {
    console.log(e)
    this.data.dealAddress = e.detail.value

    console.log(this.data.dealAddress)
  },

  //发布功能
  publish() {
    let that = this;
    let token = wx.getStorageSync('token');
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
    if (that.data.giftName == '') {
      wx.showToast({
        title: '请输入转赠物品名称',
        icon: 'none',
      });
      return false;
    }
    if (that.data.description == '') {
      wx.showToast({
        title: '请输入转增物品的详细描述',
        icon: 'none',
      });
      return false;
    }
    if (that.data.cids == -1) {
      wx.showToast({
        title: '请选择物品类别',
        icon: 'none',
      });
      return false;
    }
    if (that.data.qualityID == -1) {
      wx.showToast({
        title: '请选择物品成色',
        icon: 'none',
      });
      return false;
    }
    if (that.data.purchaseTime == "") {
      wx.showToast({
        title: '请选择您购买物品的时间',
        icon: 'none',
      });
      return false;
    }
    if (that.data.dealDate == "") {
      wx.showToast({
        title: '请选择您计划交易的时间',
        icon: 'none',
      });
      return false;
    }
    if (that.data.dealAddress == "") {
      wx.showToast({
        title: '请输入取货地址',
        icon: 'none',
      });
      return false;
    }
    wx.showModal({
      title: '温馨提示',
      content: '经检测您填写的信息无误，是否马上发布？',
      success(res) {
        if (res.confirm) {
          const giftInfo = {
            giftName: that.data.giftName,
            description: that.data.description,
            giftImgUrls: that.data.tempFilePaths,
            giftPrice: that.data.giftPrice,
            giftTypeId: that.data.categoryID,
            giftQuality: that.data.qualityID,
            purchaseTime: that.data.purchaseTime,
            dealTime: that.data.dealDate,
            dealAddress: that.data.dealAddress,
          }
          console.log(giftInfo);
          let token = wx.getStorageSync('token');
          let res = xzRequest.post({
            url: '/easygift/submit',
            header: {
              token: token,
            },
            data: giftInfo,
          }).then(res => {
            console.log(res);
            console.log(res.data.giftId);
            that.setData({
              show_b: false,
              show_c: true,
              active: 2,
              detail_id: res.data.giftId,
              giftName: '',
              //物品详细描述
              description: '',
              //把所有图片地址concat起来
              tempFilePaths: [],
              //物品原价
              giftPrice: 15.0,
              //要传出去的类别值
              categoryID: "",
              //物品成色选择的默认值AND要传出去的物品成色ID
              qualityID: -1,
              //要传出去的购买日期
              purchaseTime: '',
              //要传出去的交易日期
              dealDate: '',
              //要传出去的取货地址
              dealAddress: '',

              //配置两个时间选择器按钮的值   
              timeValue1: "",
              timeValue2: "",
            });
            console.log(this.data.detail_id);
            wx.showToast({
              title: '正在上传...',
              icon: 'loading',
              mask: true,
              duration: 1000
            })
            wx.pageScrollTo({
              scrollTop: 0,
            })
          });
        }
      }
    })
  },
  //时间选择器的方法
  handleChange1(e) {
    console.log(e)
    this.setData({
      purchaseTime: e.detail.date
    });
    console.log(this.data.purchaseTime);
  },
  //时间选择器的方法
  handleChange2(e) {
    console.log(e)
    this.setData({
      dealDate: e.detail.date
    });
    console.log(this.data.dealDate);
  },
  //选择类别
  choCategory(e) {
    let that = this;
    that.setData({
      cids: e.detail.value
    })
    this.data.categoryID = parseInt(this.data.cids) + 1;
  },
  //选择物品成色
  choQuality(e) {
    let that = this;
    that.setData({
      qualityID: e.detail.value
    })
    console.log(this.data.qualityID);
  },

  //删除图片的方法
  deletePic(e) {
    console.log(e);
    let index = e.currentTarget.dataset.index
    let imgUrl = this.data.params.imgUrl
    const {
      tempFilePaths
    } = this.data;
    tempFilePaths.splice(index, 1);
    imgUrl.splice(index, 1)
    this.setData({
      ['params.imgUrl']: imgUrl,
      tempFilePaths,
    })
    console.log(this.data.tempFilePaths)
    // 当添加的图片达到设置最大的数量时,添加按钮隐藏,不让新添加图片
    if (this.data.tempFilePaths.length == MAX_IMG_NUM - 1) {
      this.setData({
        selectPhoto: true,
      })
    }
  },
  //上传图片的方法-部分功能
  doUpload(filePath) {
    return new Promise((resolve, reject) => {
      wx.uploadFile({
        url: xzRequest.baseUrl + "/easygift/obs/upload",
        filePath: filePath,
        name: 'file',
        formData: {
          'content-type': 'Application/json'
        },
        success(res) {
          const uploadData = JSON.parse(res.data);
          const url = uploadData.data.url;
          console.log(url);
          resolve(url);
        },
        fail(error) {
          console.error('上传失败', error);
          reject(error);
        }
      });
    });
  },
  //上传图片的方法
  chooseImage: function () {
    const that = this;
    let max = MAX_IMG_NUM - this.data.tempFilePaths.length;
    wx.chooseMedia({
      count: max,
      sizeType: ['compressed'],
      sourceType: ['album', 'camera'],
      success: async (res) => {
        console.log(res);
        that.setData({
          filePath: res.tempFiles[0].tempFilePath
        });
        try {
          const url = await this.doUpload(this.data.filePath);
          const {
            tempFilePaths
          } = that.data;
          that.setData({
            tempFilePaths: tempFilePaths.concat(url)
          }, () => {
            console.log(that.data.tempFilePaths);
            const max = MAX_IMG_NUM - that.data.tempFilePaths.length;
            that.setData({
              selectPhoto: max <= 0 ? false : true
            });
          });
        } catch (error) {
          console.error(error);
          wx.showToast({
            icon: 'none',
            title: '上传失败',
            duration: 1000
          });
        }
      },
      fail: e => {
        console.error(e);
      }
    });
  },
  //废弃的版本，以防万一有用
  // //上传图片方法--上传功能
  // async doUpload(filePath) {
  //   const that = this;
  //   wx.uploadFile({
  //     url: xzRequest.baseUrl + "/easygift/obs/upload",
  //     filePath: filePath,
  //     name: 'file',
  //     formData: {
  //       'content-type': 'Application/json'
  //     },
  //     success(res) {
  //       const uploadData = JSON.parse(res.data);
  //       that.data.filePath = uploadData.data.url;
  //       console.log(that.data.filePath);
  //     },
  //     fail(error) {
  //       console.error('上传失败', error);
  //       wx.showToast({
  //         icon: 'none',
  //         title: '上传失败',
  //         duration: 1000
  //       })
  //     }
  //   })
  // },

  // //上传图片方法
  //  chooseImage: function () {
  //   const that = this;
  //   // 还能再选几张图片,初始值设置最大的数量-当前的图片的长度
  //   let max = MAX_IMG_NUM - this.data.tempFilePaths.length;
  //   // 选择图片
  //   wx.chooseMedia({
  //     count: max, // count表示最多可以选择的图片张数
  //     sizeType: ['compressed'],
  //     sourceType: ['album', 'camera'],
  //     success: (res) => {
  //       console.log(res);
  //       that.setData({
  //         filePath:res.tempFiles[0].tempFilePath
  //       });
  //       console.log(this.data.filePath);
  //       //将选择的图片上传
  //       this.doUpload(this.data.filePath);
  //       const {
  //         tempFilePaths
  //       } = that.data;
  //       that.setData({
  //         tempFilePaths: tempFilePaths.concat(this.data.filePath)
  //       }, () => {
  //         console.log(that.data.tempFilePaths)
  //       })
  //       // 还能再选几张图片
  //       max = MAX_IMG_NUM - this.data.tempFilePaths.length
  //       this.setData({
  //         selectPhoto: max <= 0 ? false : true // 当超过8张时,加号隐藏
  //       })
  //     },
  //     fail: e => {
  //       console.error(e)
  //     }
  //   })
  // },

  async getcategory() {
    let res = await xzRequest.get({
      url: '/easygift/category',
    })
    console.log(res);
    this.setData({
      category: res.data.list
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.initial();
    this.getcategory();

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