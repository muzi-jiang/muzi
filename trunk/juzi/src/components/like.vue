<template>
    <div>
      <header class="mui-bar mui-bar-nav">
        <h1 class="mui-title">喜欢</h1>
      </header>
      <div class="mui-content">
         <!--<div>平生不修善果，只爱杀人放火。忽地顿开金绳，这里扯断玉锁。咦！钱塘江上潮信来，今日方知我是我</div>-->
          <div>
            <ul class="mui-table-view mui-grid-view mui-grid-9">
              <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3" v-for="item in booklist">
                <a href="#">
                  <span class="mui-icon mui-icon-email"><span class="mui-badge mui-badge-red">{{item.id}}</span></span>
                  <div class="mui-media-body">{{item.catalog}}</div>
                </a>
              </li>
            </ul>
          </div>
      </div>
    </div>

</template>

<script>

    export default {
        name: "like",
        data(){
            return{
               booklist:[]
            }
        },
        mounted(){
            this.bookinit();
        },
        methods:{
          bookinit(){
            this.axios({method:"get",url:"/api/app/like/api/list"}).then(res =>{
                this.booklist = res.data;
                console.log(this.booklist);
            });
          }
        }
    }
</script>

<style scoped>

</style>
