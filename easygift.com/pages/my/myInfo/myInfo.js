import {
  xzRequest
} from '../../../services/request'
import {
  StructMap
} from "../../../services/strcut";
import Dialog from '@vant/weapp/dialog/dialog';
Page({

  data: {
    userInfo: '',
    avatarUrl: "",
    //保存更新的头像
    faceUrl: "https://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRna42FI242Lcia07jQodd2FJGIYQfG0LAJGFxM4FbnQP6yfMxBgJ0F3YRqJCJ1aPAK2dQagdusBZg/0",
    //保存更新的名字
    nickName: '',
    phone: '',
    //小区ID
    communityID: 0,
    //小区
    community: [{}],
    ID: -1,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const token = wx.getStorageSync('token')
    this.getUserInfo();
    this.GetCommunityList();
  },
  onshow() {
    this.getUserInfo();
    this.GetCommunityList();
  },
  async GetCommunityList() {
    let res = await xzRequest.get({
      url: '/easygift/community/all',
    });
    console.log(res)
    let list = StructMap(res.data.list);
    this.setData({
      community: list
    })
  },
  async getUserInfo() {
    let that = this;
    let token = wx.getStorageSync('token');
    console.log(token);
    await xzRequest.get({
      url: '/easygift/userinfo',
      header: {
        token: token
      }
    }).then(async res => {
      console.log(res);
      this.setData({
        userInfo: res.data.user,
        nickName:res.data.user.nickName,
        phone:res.data.user.phone,
        avatarUrl: res.data.user.profileUrl,
        communityID: res.data.user.communityId,
        ID: res.data.user.communityId - 1
      })
      console.log(this.data.userInfo)
    })
  },
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
        that.setData({
          faceUrl: uploadData.data.url,
          avatarUrl: uploadData.data.url
        })
        console.log(this.data.avatarUrl);
      }
    })
  },
  async submit() {
    const token = wx.getStorageSync('token');

    xzRequest.post({
      url: '/easygift/update',
      header: {
        token: token
      },
      data: {
        nickName: this.data.nickName,
        phone: this.data.phone,
        communityId: this.data.communityID,
        profileUrl: this.data.avatarUrl
      }
    }).then(async res => {
      this.Login();
      Dialog.alert({
        message: '更新成功',
      }).then(() => {

        wx.switchTab({
          url: '/pages/my/my'
        });
      });

    })


  },
  //保存按钮
  formSubmit: function (e) {
    //表单返回的所有数据
    var formData = e.detail.value;
    //获取上一个页面的对象
    var pages = getCurrentPages()
    var prevPage = pages[pages.length - 2]
    //调用上一个页面的setData()方法，把数据存储到上一个页面中去
    prevPage.setData({
      username: formData.username,
      gender: formData.gender
    })
    //返回上一个页面
    wx.navigateBack()
  },
  nameInput(e) {
    // 获取输入框的值并更新数据
    this.setData({
      nickName: e.detail.value,
    });
    console.log(this.data.nickName)
  },
  phoneInput(e) {
    this.setData({
      phone: e.detail.value,
    });
    console.log(this.data.phone)
  },
  choose(e) {
    let that = this;
    console.log(e);
    that.setData({
      ID: e.detail.value
    })
    this.data.communityID = parseInt(this.data.ID) + 1;

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
  }
})