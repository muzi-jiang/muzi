<template>
  <el-table
    :data="tableData"
    border
    style="width: 100%">
    <el-table-column
      prop="date"
      label="出处"
      width="180">
    </el-table-column>
    <el-table-column
      prop="name"
      label="作者"
      width="180">
    </el-table-column>
    <el-table-column
      prop="address"
      label="内容">
    </el-table-column>
  </el-table>
</template>
<script>
  //import headTop from '../../components/headTop'
  import {getJuziList} from '@/api/getData'
  export default {
    data() {
      return {
        pageNum:1,
        pageSize:30,  
        tableData: []
      }
    },
    created(){
         this.initData();
    },
    methods: {
        async initData(){
            try{
                const countData = await getJuziList({
                    current:this.pageNum,
                    size:this.pageSize
                });
                console.log(countData);
            }catch(err){
                console.log('获取数据失败', err);
            }
        }
    }
  }
</script>
<style scoped>

</style>