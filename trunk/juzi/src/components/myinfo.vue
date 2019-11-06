<template>
    <div>
      <!--<div>师夷长技以制夷</div>-->
      <!--<button @click="logout()">登出</button>-->
      <!--  ----------------------------muzi...----------------------------->

        <nav class="_main">
          <div class="head"><img :src="user.inage"></div>
          <div class="name">
            <center>
                  <span>{{user.nick}}
                  </span>
            </center>
          </div>
        </nav>
        <div class="_main_section" style="background-color: #20D449">
          <ul>
            <a href="#"><li style="border-radius: 20px 20px 0px 0px;">
              <span class="pic"><img src="http://47.99.220.61:8001/muzi/timg.png"></span>个人资料
            </li></a>
            <a href="#"><li>
              <span class="pic"><img src="http://47.99.220.61:8001/muzi/timg.png"></span>历史足迹
            </li></a>
            <a href="#"><li>
              <span class="pic"><img src="http://47.99.220.61:8001/muzi/timg.png"></span>我的收藏
            </li></a>
            <a href="#" @click="wxpay()"><li>
              <span class="pic"><img src="http://47.99.220.61:8001/muzi/timg.png"></span>微信支付
            </li></a>
            <a href="#" @click="logout()"><li>
              <span class="pic"><img src="http://47.99.220.61:8001/muzi/timg.png"></span>设置
            </li></a>

          </ul>
        </div>

    </div>
</template>

<script>
    // import "../lib/js/wx/jquery.min.js";
    // import "../lib/js/wx/mui.min.js";
    // import "../lib/js/wx/jquery-weui.min.js";
    export default {
        name: "myinfo",
        data(){
          return{
            user:""
          }
        },
        mounted(){
            this.init();
        },
        methods:{
          init(){
              this.user = eval('('+sessionStorage.getItem('app_user')+')');
          },
          /**
           * 登出 销毁session操作
           */
          logout(){
            sessionStorage.removeItem("app_user");
            this.$router.push({ path: "/login" });
          },
          wxpay(){
            $.ajax({
              type: 'POST',
              url: '/app/weixin/api/pay',
              data: {
                "orderId": "75",
              },
              dataType: 'json',
              success: function (data) {
                WeixinJSBridge.invoke('getBrandWCPayRequest', {
                  "appId": data.appId,
                  "nonceStr": data.nonceStr,
                  "package": data.packageObj,
                  "paySign": data.paySign,
                  "signType": data.signType,
                  "timeStamp": data.timeStamp
                }, function (res) {
                  WeixinJSBridge.log(res.err_msg);
                  if (res.err_msg == "get_brand_wcpay_request:ok") {
                    alert("支付成功!");
                  } else if (res.err_msg == "get_brand_wcpay_request:cancel") {
                    alert("取消支付!");
                    return;
                  } else {
                    alert("支付失败!");
                    return;
                  }
                })
              }
            });
          }



        }
    }
</script>

<style scoped>
    @import "../../static/css/myinfo.css";
</style>
