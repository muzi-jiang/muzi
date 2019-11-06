<template>
      <div>
        <header class="mui-bar mui-bar-nav">
          <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" style="color: black" @click="back()"></a>
          <h1 class="mui-title">登录</h1>
        </header>
        <!--主体部分-->
        <div class="mui-content">
            <div class="muzi-input">
                <img src="http://m.yougou.com:80/images/login/my.png">
                <input placeholder="手机号/邮箱" name="username" id="username" maxlength="50" v-model="username">
            </div>

            <div class="muzi-input">
              <img src="http://m.yougou.com:80/images/login/lock.png">
              <input placeholder="密码" type="password" name="password" id="password" maxlength="50" v-model="password">
            </div>
            <div class="muzi-lockpass">
              忘记密码
            </div>
            <div class="muzi-login">
                <button @click="login()">登录</button>
            </div>
            <div class="muzi-register">新用户注册</div>
            <div class="muzi-loginother">
                <div class="muzi-info">第三方登录</div>
                <div class="muzi-img">
                  <div><a><img width="38" alt="" src="http://m.yougou.com:80/images/login/QQ.png"></a></div>
                  <div><a><img width="38" alt="" src="http://m.yougou.com:80/images/login/pay.png"></a></div>
                  <div><a><img width="38" alt="" src="http://m.yougou.com:80/images/login/sina.png"></a></div>
                </div>
            </div>
        </div>
      </div>
</template>

<script>
    import { Toast } from 'mint-ui';
    import global from './../router/global';
    export default {
        name: "login",
        data(){
          return{
            username:"",
            password:"",
          }
        },
        methods:{
          login(){
              //首位去空格
              this.username   =   this.username.replace(/\s+/g,"");
              this.axios({method:"get",url:"/api/app/login/api/userLogin?username="+this.username+"&password="+this.password}).then(res =>{
                  let map = res.data;
                  if(map.code == 0){
                    Toast({message: map.msg,position: 'bottom',duration: 1000});
                  }else if(map.code == 1){
                    //将用户放入session
                    let objUser = JSON.stringify(map.user);
                    sessionStorage.setItem("app_user",objUser);
                    //跳回请求路由
                    this.$router.push({ path: global.topath });
                  }else{
                    Toast({message: global.dict_network,position: 'bottom',duration: 1000});
                  }
              }).catch(error =>{
                Toast({message: global.dict_networkError,position: 'bottom',duration: 1000});
              });
          },
          back(){
            this.$router.push({ path: global.frompath });
          },




        }
    }
</script>

<style scoped>
  @import "../../static/css/login/login.css";
</style>
