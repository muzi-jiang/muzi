<template>
  
<div class="container">

    <!-- 按钮 -->
    <div class="handle-box">
        <el-button type="warning" icon="el-icon-plus" @click="roleDialog('','2')">{{$t('i18n.FR_MSG00001')}}</el-button>
    </div>

    <!-- 表格 -->
    <el-table
        :data="tableData" border
        element-loading-background="rgba(0, 0, 0, 0.8)"
        style="width: 100%">

        <el-table-column
        align="center"
        type="index"
        width="50">
        </el-table-column>

        <el-table-column
        sortable
        width="300"
        align="center"
        prop="rolename"
        :label="$t('i18n.FR_MSG00002')">
        </el-table-column>

        <el-table-column
        prop="remarks"
        align="center"
        :label="$t('i18n.FR_MSG00003')">
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

        <el-table-column  :label="$t('i18n.FR_MSG00007')" width="300" align="center">
            <template slot-scope="scope">
                <el-button type="text" icon="el-icon-edit" @click="roleDialog(scope.row,'1')" class="edit"> {{$t('i18n.FR_MSG00008')}} </el-button>
                <el-button type="text" icon="el-icon-delete" @click="delRole(scope.row.id)"  class="red"> {{$t('i18n.FR_MSG00009')}} </el-button>
            </template>
        </el-table-column>
    </el-table>
    



    <!-- 编辑弹出框 -->
    <el-dialog :title="title" :visible.sync="editVisible" :close-on-click-modal="false"  width="40%" @close="closeDialog" @opened="closeDialog">
        <el-form ref="form" :model="form" label-width="100px" :rules="rules" class="demo-ruleForm">

            <el-form-item :label="$t('i18n.FR_MSG00002') + ':'" prop="rolename">
                <el-input v-model="form.rolename"></el-input>
            </el-form-item>

            <el-form-item :label="$t('i18n.FR_MSG00010') + ':'">
                <el-input v-model="form.remarks" maxlength="255"></el-input>
            </el-form-item>

            <el-form-item :label="$t('i18n.FR_MSG00004') + ':'">
                <el-select v-model="form.status" :placeholder="$t('i18n.FR_MSG00011')"> 
                    <el-option :label="$t('i18n.FR_MSG00005')" value="1"></el-option>
                    <el-option :label="$t('i18n.FR_MSG00006')" value="0"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item :label="$t('i18n.FR_MSG00012') + ':'">
                <el-tree
                    ref="tree"
                    v-if="editVisible"
                    :data="permissionData"
                    show-checkbox
                    node-key="id"
                    default-expand-all
                    :default-checked-keys="defaultcheckedData"
                    :props="defaultProps">
                </el-tree>
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
import {getRole, saveRole, delRole} from '@/api/getData'
import { Loading } from 'element-ui';
export default {
    data() {
      return { 
        title:'',  
        tableData: [],
        editVisible: false,
        form: {},
        permissionData: [],
        defaultcheckedData: [],
        defaultProps: {
          children: 'children',
          label: 'permissionName'
        }
      }

    },
    computed:{
        rules(){
            const rules = {
                rolename:[
                    { required: true, message: this.$i18n.t('i18n.FR_MSG00015'), trigger: 'blur'},
                    { min: 1, max: 20, message: this.$i18n.t('i18n.FR_MSG00016'), trigger: 'blur' }
                ] ,
            }
            return rules;
        }
    },
    created(){
         this.initData();
    },
    methods: {
        async initData(){
            const countData = await getRole();
            //表格数据
            this.tableData = countData.roles;
            
            //菜单树形节点数据
            this.permissionData = countData.permissions;
        },
        
        //flag 1：编辑  2：新增
        roleDialog(row,flag){
            if(flag == 1){
                this.title = this.$i18n.t('i18n.FR_MSG00008')
                this.form = JSON.parse(JSON.stringify(row));
                this.defaultcheckedData = row.rolePermission;
            }else{
                this.title = this.$i18n.t('i18n.FR_MSG00017')
                this.form = {status:"1"};
                this.defaultcheckedData = [];
            }
            this.editVisible = true;
        },

        // 保存编辑
        async saveEdit() {
            const res = await saveRole({...this.form});
            if(res.resultCode == 1){
                this.editVisible = false;
                this.$message({type: 'success', message: this.$i18n.t('i18n.FR_MSG00018') });
            }else if(res.resultCode == 0){
                this.$message({type: 'error', message: this.$i18n.t('i18n.FR_MSG00019') });
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
                    //获取所有的子节点
                    var array1 = this.$refs.tree.getCheckedKeys();
                    //获取半选中节点数据
                    var array2 = this.$refs.tree.getHalfCheckedNodes();
                    if(array2.length != 0){
                        for(var i =0; i< array2.length; i++){
                            array1.push(array2[i].id);
                        }
                    }
                    if(array1.length == 0){
                        //提示为该角色选择菜单权限
                        this.$message({type: 'error', message: this.$i18n.t('i18n.FR_MSG00022') });
                        return false;
                    }
                    this.form.rolePermission = array1;
                    this.saveEdit();
                }else{
                    return false;
                }
            });
        },
        
        //删除角色
        delRole(id){
            this.$confirm(this.$i18n.t('i18n.FR_MSG00023'), this.$i18n.t('i18n.FR_MSG00024'), {
                type: 'warning'
            }).then(() => {
                this.removeRole(id);
            }).catch(() => {});
        },
        //发送请求删除角色
        async removeRole(id){
            const res = await delRole({'id':id});
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