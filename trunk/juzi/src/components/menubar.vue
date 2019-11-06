<template>

  <div>
      <router-view></router-view>

    <nav class="mui-bar mui-bar-tab">
      <router-link class="mui-tab-item" to="/index">
        <span class="mui-icon mui-icon-home"></span>
        <span class="mui-tab-label">图文</span>
      </router-link>
      <router-link class="mui-tab-item" to="/text">
        <span class="mui-icon mui-icon-weibo"></span>
        <span class="mui-tab-label">句子</span>
      </router-link>
      <router-link class="mui-tab-item" to="/like">
        <span class="mui-icon mui-icon-chat"></span>
        <span class="mui-tab-label">喜欢</span>
      </router-link>
      <router-link class="mui-tab-item" to="/myinfo">
        <span class="mui-icon mui-icon-gear"></span>
        <span class="mui-tab-label">我的</span>
      </router-link>
    </nav>
  </div>

</template>

<script>
    export default {
        name: "menubar"
    }
</script>

<style scoped>

</style>
