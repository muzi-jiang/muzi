<template>
  <div id="app">
    <!--<router-view/>-->

    <keep-alive>
      <router-view v-if="$route.meta.keepAlive"></router-view>
    </keep-alive>
    <router-view v-if="!$route.meta.keepAlive"></router-view>

  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>


<!--设置全局样式-->
<style>
    *{
      font-family: 楷体;
    }
    .mui-bar-nav{
      background: -webkit-gradient(linear, 0 0, 0 100%, from(#929292), to(#8f8f94));
    }
    .mui-bar .mui-title{
      line-height: 40px;
      font-size: 20px;
    }
    .mui-content>.mui-table-view:first-child{
      margin-top: 0px;
    }
</style>
