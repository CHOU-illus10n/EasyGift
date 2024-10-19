var data = {
      //分享配置
      share_title: '小区二手转增平台',
      //默认启动页背景图，防止请求失败完全空白 
      bgurl: '',
      //校区
}
//下面的就别动了
function formTime(creatTime) {
      let date = new Date(creatTime),
            Y = date.getFullYear(),
            M = date.getMonth() + 1,
            D = date.getDate(),
            H = date.getHours(),
            m = date.getMinutes(),
            s = date.getSeconds();
      if (M < 10) {
            M = '0' + M;
      }
      if (D < 10) {
            D = '0' + D;
      }
      if (H < 10) {
            H = '0' + H;
      }
      if (m < 10) {
            m = '0' + m;
      }
      if (s < 10) {
            s = '0' + s;
      }
      return Y + '-' + M + '-' + D + ' ' + H + ':' + m + ':' + s;
}

function days() {
      let now = new Date();
      let year = now.getFullYear();
      let month = now.getMonth() + 1;
      let day = now.getDate();
      if (month < 10) {
            month = '0' + month;
      }
      if (day < 10) {
            day = '0' + day;
      }
      let date = year + "" + month + day;
      return date;
}

async function getList() {
  console.log("调用了getlist")
  let that = this;
  // let collegeid = that.data.collegeCur === -2 ? 'all' : that.data.collegeCur.toString(); // 将collegeCur转换为对应格式
  let token=wx.getStorageSync('token');
  let res = await xzRequest.get({
    url: '/easygift/giftlist',
    header: {
      token: token
    }
  }).then(res => {
    wx.stopPullDownRefresh(); // 暂停刷新动作
    if (res.length === 0) { // 根据返回数据进行处理
      that.setData({
        nomore: true,
        list: []
      });
    } else if (res.length < 20) {
      that.setData({
        nomore: true,
        page: 0,
        list: res.data.giftlist
      });
      console.log(this.data.list);
    } else {
      that.setData({
        page: 0,
        list: res.data.giftlist,
        nomore: false
      });
    }
  }).catch(err => {
    console.error(err);
  });
}

//获取转赠物品类别数据
async function getcategory() {
  let res = await xzRequest.get({
    url: '/easygift/category',
  })
  console.log(res);
  this.setData({
    category: res.data.list
  });
}

module.exports = {
      data: JSON.stringify(data),
      formTime: formTime,
      days: days
}