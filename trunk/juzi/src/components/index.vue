<template>
    <div>
      <header class="mui-bar mui-bar-nav">
        <h1 class="mui-title">主页</h1>
      </header>
      <div class="mui-content">
        <ul class="mui-table-view">

          <li class="mui-table-view-cell mui-media" v-for="item in juziDatas" >
            <a href="javascript:;">
              <img class="mui-media-object mui-pull-left" v-bind:src="item.picture">
              <div class="mui-media-body">
                {{item.title}}
                <p class="mui-ellipsis">{{item.text}}</p>
              </div>
            </a>
          </li>

        </ul>
      </div>
    </div>
</template>

<script>

  import { Toast } from 'mint-ui';
  import axios from 'axios';


    export default {
      name: "index",
      data(){
        return{
          juziDatas:[],
        }
      },
      created() {   //表示在页面刚加载的时候获取数据
        this.getDataInit();
      },
      methods:{
        getDataInit(){
          axios({method:"get",url:"static/data/index.json"}).then(res =>{
            this.juziDatas = res.data;
            console.log(res.data);
          }).catch(error =>{
              Toast("数据获取失败,请重新加载.....");
          });
        }
      }
    }
</script>

<style scoped>

</style>
