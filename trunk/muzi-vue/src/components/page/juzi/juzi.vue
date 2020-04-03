<template>
<div class="container">

   <!-- 按钮 -->
  <div class="handle-box">
      <el-button type="warning" icon="el-icon-plus" >新增语录</el-button>
  </div>

  <el-table
    :data="tableData" border
    element-loading-background="rgba(0, 0, 0, 0.8)"
    style="width: 100%">

    <el-table-column
      sortable
      align="center"
      prop="source"
      label="出处"
      width="250">
    </el-table-column>

    <el-table-column
      sortable
      align="center"
      prop="author"
      label="作者"
      width="100">
    </el-table-column>

    <el-table-column
      prop="text"
      label="内容">
    </el-table-column>
  </el-table>
  
  <el-pagination
    background
    layout="prev, pager, next"
    :page-size="pageSize"
    :total="totalCount"
    @current-change="findPage">
  </el-pagination>
</div>
</template>
<script>
  import {getJuziList} from '@/api/getData'
  import { Loading } from 'element-ui';
  export default {
    data() {
      return {
        pageNum:1,
        pageSize:10,  
        tableData: [],
        totalCount: 0,
      }
    },
    components:{
      //获取title

    },
    created(){
         this.initData();
    },
    methods: {
        async initData(){

            let loadingInstance = 
            Loading.service({ fullscreen: true,
                              text:'数据加载中...',
                              spinner:'el-icon-loading',
                              'background':'rgba(0, 0, 0, 0.8)'});
            const countData = await getJuziList({
                    current: this.pageNum,
                    size: this.pageSize
            });
            this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
							loadingInstance.close();
						});
            this.tableData = countData.records;
            this.totalCount = countData.total;
        },
        findPage(val){
           this.pageNum = val;
           this.initData();
        }
    }
  }
</script>
<style scoped>

</style>