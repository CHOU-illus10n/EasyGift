
// component/date-time-picker/date-time-picker.js
// component/date-time-picker/date-time-picker.js
Component({
  options: {
      styleIsolation: 'apply-shared' //调用页面控制组件样式
  },
  properties: {
      name: String,
      className: String,
      startYear: String,
      endYear: String,
      value: String
  },

  /**
   * 组件的初始数据
   */
  data: {
      multiArray:[],
      multiSelectdArray: [0, 0, 0],
  },

  observers: {
  },

  ready: function(e) {
      this._formDateTime();
  },

  /**
   * 组件的方法列表
   */
  methods: {
      //格式化日期
      _formDateTime(){
          // 默认开始显示数据
          var nowDateTime = this.properties.value ? this.properties.value.split('-') : this._getNewDateArry();
          var multiDateTime = [[],[],[]];
          var startYear = this.properties.startYear || nowDateTime[0];
          var endYear = this.data.properties || parseInt(nowDateTime[0]) + 1;
      
          // 处理联动列表数据
          /* 年月日 */ 
          multiDateTime[0] = this._getLoopArray(startYear, endYear, '年');
          multiDateTime[1] = this._getLoopArray(1, 12, '月');
          multiDateTime[2] = this._getMonthDay(nowDateTime[0], nowDateTime[1], '日');

          var multiSelectdArray = this._getMultiSelectdArray(multiDateTime, nowDateTime);
          this.setData({
              multiDateTime: multiDateTime,
              multiSelectdArray: multiSelectdArray,
          });
      },

      /* 查询选中状态数组 */
      _getMultiSelectdArray(multiDateTime, nowDateTime){
          let multiSelectdArray = this.data.multiSelectdArray;
          for (let i in multiDateTime) {
              for (let k in multiDateTime[i]) {
                  var a = multiDateTime[i][k];
                  if(a.replace(/[\u4e00-\u9fa5]/g, '') == nowDateTime[i]){
                      multiSelectdArray[i] = k;
                  }
              }
          }
          
          return multiSelectdArray;
      },

      /* 获取起始数组 */
      _getLoopArray(start, end, name){
          var name = name || '';
          var start = start || 0;
          var end = end || 1;
          var array = [];
          for (var i = start; i <= end; i++) {
              array.push(this._withData(i) + name);
          }
          
          return array;
      },

      /* 获取每月天数 */
      _getMonthDay(year, month, name){
          var name = name || 0;
          var flag = year % 400 == 0 || (year % 4 == 0 && year % 100 != 0), array = null;
          switch (month) {
              case '01':
              case '03':
              case '05':
              case '07':
              case '08':
              case '10':
              case '12':
              array = this._getLoopArray(1, 31, name)
              break;

              case '04':
              case '06':
              case '09':
              case '11':
              array = this._getLoopArray(1, 30, name)
              break;

              case '02':
              array = flag ? this._getLoopArray(1, 29, name) : this._getLoopArray(1, 28, name)
              break;

              default:
              array = '月份格式不正确，请重新输入！'
          }
          return array;
      },

      /* 获取当前时间 */
      _getNewDateArry(){
          // 当前时间的处理
          var newDate = new Date();
          var year = this._withData(newDate.getFullYear()),
              mont = this._withData(newDate.getMonth() + 1),
              date = this._withData(newDate.getDate());

          return [year, mont, date];
      },

      _stringToDate(str) {
          str = str.replace(/-/g, "/");
          return new Date(str);
      },

      /* 数据左边加0 */
      _withData(param){
          return param < 10 ? '0' + param : '' + param;
      },

      /* 切换时间 */
      handleColumnChange(e) {
          var multiSelectdArray = this.data.multiSelectdArray;
          multiSelectdArray[e.detail.column] = e.detail.value;
          if(e.detail.column <= 1){
              var multiDateTime = this.data.multiDateTime;
              var year = this.data.multiArray[multiSelectdArray[0]];
              var month = this._withData(e.detail.value + 1);
              multiDateTime[2] = this._getMonthDay(year, month, '日');

              this.setData({
                  multiDateTime: multiDateTime
              });
          }

          this.setData({
              multiSelectdArray: multiSelectdArray
          });
      },

      /* 改变picker值 */
      handleValueChange(e){
          var _this = this;
          let dateArr = [], multiSelectdArray = [];
          for (let i in e.detail.value) {
              let v = _this.data.multiDateTime[i][e.detail.value[i]];
                  v = v.replace(/[\u4e00-\u9fa5]/g, '');
                  multiSelectdArray.push(e.detail.value[i]);
              dateArr.push(v.toString());
          }

          _this.setData({
              multiSelectdArray: multiSelectdArray //设置下拉选中状态
          });

          let defaultDateTime = dateArr[0] + "-" + dateArr[1] + "-" + dateArr[2];
          _this.triggerEvent('change', {
              date: defaultDateTime
          })
      },

      /* 取消 */
      handleCancel(e){
          _this.triggerEvent('cancel', {
          })
      }
  }
})
