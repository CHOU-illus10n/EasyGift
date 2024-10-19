const app = getApp()
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    selected: 0,
    color: "#000000",
    selectedColor: "#FBBD08",
    backgroundColor: "#F7F8F8",
    "list": [
      {
        "pagePath": "pages/index/index",
        "iconPath": "/images/tabbar/home.png",
        "selectedIconPath": "/images/tabbar/home_fill.png",
        "text": "首页"
      },
      {
        "pagePath": "pages/pointmall/pointmall",
        "iconPath": "/images/tabbar/pointmall.png",
        "selectedIconPath": "/images/tabbar/pointmall_fill.png",
        "text": "积分商城"
      },
      {
        pagePath: "pages/publish/publish",
        bulge:true,
        iconPath: "/images/tabbar/publish.png",
        selectedIconPath: "/images/tabbar/publish_fill.png",
        text: "发布"
      },
      {
        "pagePath": "pages/notice/notice",
        "iconPath": "/images/tabbar/notice.png",
        "selectedIconPath": "/images/tabbar/notice_fill.png",
        "text": "消息"
      },
      {
        "pagePath": "pages/my/my",
        "iconPath": "/images/tabbar/my.png",
        "selectedIconPath": "/images/tabbar/my_fill.png",
        "text": "我的",
      }
    ],
  },
  ready: function() {
    this.setData({
      selected: app.globalData.selected
    })
  },
  /**
   * 组件的方法列表
   */
  methods: {
    switchTab(e) {
      console.log(e);
      const data = e.currentTarget.dataset;
      const url = '/'+data.path;
      console.log(url);
      app.globalData.selected = data.index;
      wx.switchTab({url})
    }
  }
})