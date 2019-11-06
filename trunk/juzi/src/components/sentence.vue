<template>
    <div id="muzi-scroll">
      <header class="mui-bar mui-bar-nav">
        <h1 class="mui-title">佳木</h1>
      </header>
      <div class="mui-content">
          <div class="muzi-parent" v-for="item in juziInfo">
              <div class="muzi-user">
                  <div class="muzi-img">
                    <img :src="item.user[0].inage">
                  </div>
                  <div class="muzi-user-info">
                      <div style="font-size: 17px">
                        <span style="float: left" v-text="item.user[0].nick"></span>
                        <span  class="muzi-guanzhu">+关注</span>
                      </div>
                      <div style="font-size: 15px;color: #C0C0C0" v-text="item.create_time"></div>
                  </div>
              </div>
              <div class="muzi-juzi">
                  <div class="muzi-juzi-info" v-text="item.text">

                  </div>
                  <div class="muzi-juzi-author">
                      --{{item.author}}  《{{item.source}}》
                  </div>
              </div>
            <div class="muzi-status">
              <span>点赞(10)</span>
              <span>投币(12)</span>
              <span>收藏(15)</span>
              <span>评论(20)</span>
            </div>
          </div>
      </div>
      <br><br><br>
    </div>
</template>

<script>
  import { Toast } from 'mint-ui';
  import global from './../router/global';
    export default {
        name: "sentence",
        data(){
          return{
            juziInfo:[],
            pageNum:1,
          }
        },
        //初始化调用一次方法
        mounted(){
          console.log("第一次进入......");
         this.init();
        },
        //再次进入只触发activated，不再触发created 和mounted
        activated() {
          window.addEventListener('scroll', this.init);
        },
        methods:{
          init(){
              let flag = this.page();
              if (flag) {
                //加载更多操作
                //this.axios({method:"get",url:"/static/data/juzi.json"}).then(res =>{

                this.axios({method:"get",url:"/api/app/juzi/api/list?pageNum="+this.pageNum}).then(res =>{
                  if(res.data.length!=0){
                    this.juziInfo.push.apply(this.juziInfo,res.data);
                    this.pageNum++;
                  }else{
                    Toast({message: global.dict_notmoreData,position: 'bottom',duration: 1000});
                  }
                }).catch(error =>{
                  Toast({message: global.dict_networkError,position: 'bottom',duration: 1000});
                });
              }
          },
          page(){

            //屏幕尺寸高度
            let outerHeight = document.documentElement.clientHeight;
            //可滚动容器的高度
            let innerHeight =outerHeight+1;
            if(document.querySelector('#muzi-scroll') != null){
              innerHeight = document.querySelector('#muzi-scroll').clientHeight;
            }
            //可滚动容器超出当前窗口显示范围的高度
            let scrollTop = document.documentElement.scrollTop;
            //scrollTop在页面为滚动时为0，开始滚动后，慢慢增加，滚动到页面底部时，出现innerHeight < (outerHeight + scrollTop)的情况，严格来讲，是接近底部
            //console.log(innerHeight +"--"+outerHeight +"----"+ scrollTop);
            if (innerHeight <= (outerHeight + scrollTop )) {
              return true;
            }
            return false;
          }
        },
        //进入路由之前
        beforeRouteEnter (to, from, next) {
          next(vm => {
            //console.log(vm.scrollTop);
            document.documentElement.scrollTop = vm.scrollTop;
          });
        },
        beforeRouteLeave (to, from, next) {
          //路由离开之前remove掉当前监听
          window.removeEventListener('scroll', this.init);
          this.scrollTop = document.documentElement.scrollTop;
          next();
        },

    }
</script>

<style scoped>
  @import "../../static/css/sentence.css";
</style>
