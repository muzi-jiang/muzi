<template>
  
<div class="container">

    <!-- 按钮 -->
    <div class="handle-box">
        <el-button type="warning" icon="el-icon-plus" @click="addNextMenu('1','0')">{{$t('i18n.FR_MSG00026')}}</el-button>
    </div>

    <!-- 表格 -->
    <el-table
        :data="tableData" border
        :row-class-name="tableRowClassName"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        row-key="id"
        default-expand-all
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
        style="width: 100%">

        <el-table-column
        align="center"
        type="index"
        width="50">
        </el-table-column>

        <el-table-column
        sortable
        prop="permissionName"
        :label="$t('i18n.FR_MSG00027')">
        </el-table-column>

        <el-table-column
        align="center"
        width="100"
        prop="permissionParentid"
        :label="$t('i18n.FR_MSG00028')">
            <template slot-scope="scope">
                <el-button :type="scope.row.permissionParentid == 0 ? 'warning' : 'primary'" disable-transitions>
                    {{scope.row.permissionParentid == 0 ? $t('i18n.FR_MSG00029'):$t('i18n.FR_MSG00030')}}
                </el-button>
            </template>
        </el-table-column>

        <el-table-column
        prop="component"
        align="center"
        :label="$t('i18n.FR_MSG00031')">
        </el-table-column>

        <el-table-column
        align="center"
        prop="permissionUrl"
        :label="$t('i18n.FR_MSG00032')">
        </el-table-column>

        <el-table-column
        align="center"
        prop="permissionIcon"
        :label="$t('i18n.FR_MSG00033')">
        </el-table-column>

        <el-table-column
        align="center"
        sortable
        width="100"
        prop="status"
        :label="$t('i18n.FR_MSG00004')">
            <template slot-scope="scope">
                <el-tag :type="scope.row.status == 1 ? 'primary' : 'danger'" disable-transitions>
                    {{scope.row.status == 1 ? $t('i18n.FR_MSG00005'):$t('i18n.FR_MSG00006')}}
                </el-tag>
            </template>
        </el-table-column>

        <el-table-column :label="$t('i18n.FR_MSG00007')" width="300" align="center">
            <template slot-scope="scope">
                <el-button type="text" icon="el-icon-plus" @click="addNextMenu('2',scope.row.id)" v-if="scope.row.permissionParentid == 0"> {{$t('i18n.FR_MSG00034')}} </el-button>
                <el-button type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" class="edit"> {{$t('i18n.FR_MSG00008')}} </el-button>
                <el-button type="text" icon="el-icon-delete" @click="delMenu(scope.row.id,scope.row.permissionParentid)"  class="red"> {{$t('i18n.FR_MSG00009')}} </el-button>
            </template>
        </el-table-column>
    </el-table>
    



    <!-- 编辑弹出框 -->
    <el-dialog :title="title" :visible.sync="editVisible" :close-on-click-modal="false"  width="40%" @close="closeDialog" @opened="closeDialog">
        <el-form ref="form" :model="form" label-width="100px" :rules="rules" class="demo-ruleForm">

            <el-form-item :label="$t('i18n.FR_MSG00027') + ':'" prop="permissionName">
                <el-input v-model="form.permissionName"></el-input>
            </el-form-item>

            <!-- <el-form-item label="路由名称:">
                <el-input v-model="form.component"></el-input>
            </el-form-item> -->

            <el-form-item :label="$t('i18n.FR_MSG00032') + ':'">
                <el-input v-model="form.permissionUrl" :disabled="form.permissionParentid == 0"></el-input>
            </el-form-item>

            <el-form-item :label="$t('i18n.FR_MSG00033') + ':'">
                <el-input v-model="form.permissionIcon" :disabled="form.permissionParentid != 0"></el-input>
            </el-form-item>

            <el-form-item :label="$t('i18n.FR_MSG00004') + ':'">
                <el-select v-model="form.status" :placeholder="$t('i18n.FR_MSG00011')"> 
                    <el-option :label="$t('i18n.FR_MSG00005')" value="1"></el-option>
                    <el-option :label="$t('i18n.FR_MSG00006')" value="0"></el-option>
                </el-select>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
             <el-button type="primary" @click="submitFun('form')">{{$t('i18n.FR_MSG00013')}}</el-button>
            <el-button @click="editVisible = false">{{$t('i18n.FR_MSG00014')}}</el-button>
        </span>
    </el-dialog>

</div>
   
</template>
<script>
import {getPermission ,savePermission, delPermission} from '@/api/getData'
import { Loading } from 'element-ui';
export default {
    data() {
      return { 
        title:'',  
        tableData: [],
        editVisible: false,
        form: {},
      }
    },
    computed:{
        rules(){
            const rules = {
                permissionName:[
                    { required: true, message: this.$i18n.t('i18n.FR_MSG00035'), trigger: 'blur'},
                    { min: 1, max: 20, message: this.$i18n.t('i18n.FR_MSG00016'), trigger: 'blur' }
                ] 
            }
            return rules;
        }
    },
    created(){
         this.initData();
    },
    methods: {
        async initData(){
            const countData = await getPermission();
            this.tableData = countData;
        },
        //给父级菜单栏添加颜色
        tableRowClassName({row, rowIndex}) {
            if (row.permissionParentid == 0) {
               return 'warning-row';
            } 
            return 'success-row';
        },
        //打开编辑窗口
         handleEdit(row) {
            this.title = this.$i18n.t('i18n.FR_MSG00008');
            this.form = JSON.parse(JSON.stringify(row));
            this.editVisible = true;
        },
        // 保存编辑
        async saveEdit() {
            const res = await savePermission({...this.form});
            if(res.resultCode == 1){
                this.editVisible = false;
                this.$message({type: 'success', message: this.$i18n.t('i18n.FR_MSG00018') });
            }else if(res.resultCode == 0){
                this.$message({type: 'error', message: this.$i18n.t('i18n.FR_MSG00036')});
            }else{
                this.$notify.error({title: this.$i18n.t('i18n.FR_MSG00020'),message: this.$i18n.t('i18n.FR_MSG00021'),offset: 100});
            }
            //重新加载数据
            this.initData();
        },
        //提交按钮
        submitFun(formName){
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    this.saveEdit();
                }else{
                    return false;
                }
            });
        },
        //添加菜单   flag: 1 父级菜单  2:子集菜单
        addNextMenu(flag , permissionParentid){
            this.title = this.$i18n.t('i18n.FR_MSG00017');
            this.editVisible = true;
            //设置当前添加的父级节点  且状态为正常
            this.form = {'permissionParentid': permissionParentid,'status':'1'};
        },
        //删除菜单
        delMenu(id,permissionParentid){            
            this.$confirm(permissionParentid == 0 ? this.$i18n.t('i18n.FR_MSG00037'):this.$i18n.t('i18n.FR_MSG00038'), this.$i18n.t('i18n.FR_MSG00024'), {
                type: 'warning'
            }).then(() => {
                this.removeMenu(id);
            }).catch(() => {});
        },

        async removeMenu(id){
            const res = await delPermission({'id':id});
            if(res.resultCode == 1){
                this.$message({type: 'success', message: this.$i18n.t('i18n.FR_MSG00025') });
                //重新加载数据
                this.initData();
            }else{
                this.$message({type: 'error', message: this.$i18n.t('i18n.FR_MSG00021') });
            } 
        },
        //关闭弹框的时候清除表单验证，防止下次点击弹出触发验证
        closeDialog(){
            //this.$refs['form'].resetFields();
            this.$refs['form'].clearValidate();
        }
    }
}
</script>
<style lang="less">

/* 表格父级栏颜色  */
.el-table .warning-row {
    background: oldlace;
}

.el-table .success-row {
    background-color: #f0f9eb
}

.el-select--small{
    width: 100%;
}
.handle-box {
    margin-bottom: 20px;
}

.handle-select {
    width: 200px;
}

.handle-input {
    width: 200px;
    display: inline-block;
}
.table {
    width: 100%;
    font-size: 14px;
}
.red {
    color: #ff0000;
}
.edit{
    color: #9B351E;
}
.mr10 {
    margin-right: 20px;
}
.el-dialog__title{
    font-size: 15px;
}
.table-td-thumb {
    display: block;
    margin: auto;
    width: 40px;
    height: 40px;
}
</style>