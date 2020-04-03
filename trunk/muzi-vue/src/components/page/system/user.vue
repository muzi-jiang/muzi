<template>
  
<div class="container">

    <!-- 按钮 -->
    <div class="handle-box">
        <el-button type="warning" icon="el-icon-plus" @click="userDialog('','2')">{{$t('i18n.FR_MSG00039')}}</el-button>
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
        align="center"
        prop="username"
        :label="$t('i18n.FR_MSG00040')">
        </el-table-column>

        <el-table-column
        prop="name"
        align="center"
        :label="$t('i18n.FR_MSG00041')">
        </el-table-column>

        <el-table-column
        prop="headportrait"
        align="center"
        :label="$t('i18n.FR_MSG00042')">
                <template slot-scope="scope">
                    <el-image
                        class="table-td-thumb"
                        :src="scope.row.headportrait"
                        :preview-src-list="[scope.row.headportrait]"
                    ></el-image>
                </template>
        </el-table-column>

        <el-table-column
        prop="tel"
        align="center"
        :label="$t('i18n.FR_MSG00043')">
        </el-table-column>

        <el-table-column
        prop="sex"
        align="center"
        :label="$t('i18n.FR_MSG00044')">
            <template slot-scope="scope">
                <!-- 0:未知  1:男   2:女 -->
                <div v-if="scope.row.sex == '0'">{{$t('i18n.FR_MSG00045')}}</div>
                <div v-if="scope.row.sex == '1'">{{$t('i18n.FR_MSG00046')}}</div>
                <div v-if="scope.row.sex == '2'">{{$t('i18n.FR_MSG00047')}}</div>
            </template>
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
                <el-button type="text" icon="el-icon-edit" @click="userDialog(scope.row,'1')" class="edit"> {{$t('i18n.FR_MSG00008')}} </el-button>
                <el-button type="text" icon="el-icon-delete" @click="delUser(scope.row.id)"  class="red"> {{$t('i18n.FR_MSG00009')}} </el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="totalCount"
        @current-change="findPage"
        >
    </el-pagination>



    <!-- 编辑弹出框 -->
    <el-dialog :title="title" :visible.sync="editVisible" :close-on-click-modal="false"  width="40%" @close="closeDialog" @opened="closeDialog">
        <el-form ref="form" :model="form" label-width="100px" :rules="rules" class="demo-ruleForm">

            <el-form-item :label="$t('i18n.FR_MSG00042') + ':'" >
               <el-upload
                    class="avatar-uploader"
                    action="#"
                    accept="image/png,image/jpg,image/jpeg"
                    :show-file-list="false"
                    :http-request="uploadImg"
                    :before-upload="beforeAvatarUpload">
                    <img v-if="this.showFlag" class="avatar" :src="this.form.headportrait" />
                    <div v-else class="head-img">
                        <i  v-if="this.progressFlag" class="el-icon-plus avatar-uploader-icon"></i>
                        <el-progress v-else type="circle" :percentage="progressPercent"></el-progress>
                    </div>
                </el-upload>
            </el-form-item>   

            <el-form-item :label="$t('i18n.FR_MSG00048') + ':'" prop="username">
                <el-input v-model="form.username"></el-input>
            </el-form-item>

             <el-form-item :label="$t('i18n.FR_MSG00041') + ':'" prop="name">
                <el-input v-model="form.name"></el-input>
            </el-form-item>

            <el-form-item :label="$t('i18n.FR_MSG00043') + ':'" prop="tel">
                <el-input v-model="form.tel" maxlength="100"></el-input>
            </el-form-item>

            <el-form-item :label="$t('i18n.FR_MSG00044') + ':'">
                <el-select v-model="form.sex" :placeholder="$t('i18n.FR_MSG00011')"> 
                    <el-option :label="$t('i18n.FR_MSG00045')" value="0"></el-option>
                    <el-option :label="$t('i18n.FR_MSG00046')" value="1"></el-option>
                    <el-option :label="$t('i18n.FR_MSG00047')" value="2"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item :label="$t('i18n.FR_MSG00004') + ':'">
                <el-select v-model="form.status" :placeholder="$t('i18n.FR_MSG00011')"> 
                    <el-option :label="$t('i18n.FR_MSG00005')" value="1"></el-option>
                    <el-option :label="$t('i18n.FR_MSG00006')" value="0"></el-option>
                </el-select>
            </el-form-item>

            <el-form-item :label="$t('i18n.FR_MSG00049') + ':'">
                <el-tree
                    ref="tree"
                    v-if="editVisible"
                    :data="roleData"
                    show-checkbox
                    node-key="id"
                    default-expand-all
                    :default-checked-keys="defaultcheckedData"
                    :props="defaultProps">
                </el-tree>
            </el-form-item>

            <el-header v-if="this.form.id == null" style="line-height: 60px;background-color: #EBB563;">{{$t('i18n.FR_MSG00050')}}:{{passwordInit}}</el-header>
        </el-form>
        <span slot="footer" class="dialog-footer">
             <el-button type="primary" @click="submitFun('form')">{{$t('i18n.FR_MSG00013')}}</el-button>
            <el-button @click="editVisible = false">{{$t('i18n.FR_MSG00014')}}</el-button>
        </span>
    </el-dialog>

</div>
   
</template>
<script>
import {getUser,saveUser, delUser} from '@/api/getData'
import { Loading } from 'element-ui';
import axios from 'axios'
export default {
    data() {
      return { 
        title:'',  
        tableData: [],
        editVisible: false,
        form: {},
        
        roleData: [],
        defaultcheckedData: [],
        defaultProps: {
          children: 'children',
          label: 'rolename'
        },

        //分页参数
        pageNum:1,
        pageSize:10, 
        totalCount:0,//总数
        passwordInit:'',

        showFlag: true,
        progressFlag: true,
        progressPercent: 0
      }

    },
    computed:{
        rules(){
            const rules = {
                username:[
                    { required: true, message:  this.$i18n.t('i18n.FR_MSG00051'), trigger: 'blur'},
                    { pattern:/[0-9a-zA-Z]{4,23}/, message:  this.$i18n.t('i18n.FR_MSG00052'), trigger: 'blur' }
                ] ,
                name:[
                    { required: true, message:  this.$i18n.t('i18n.FR_MSG00053'), trigger: 'blur'},
                    { min: 1, max: 10, message:  this.$i18n.t('i18n.FR_MSG00054'), trigger: 'blur' }
                ] ,
                tel:[           
                    { required: true, message:  this.$i18n.t('i18n.FR_MSG00055'), trigger: 'blur'},
                    { pattern:/^1[3456789]\d{9}$/, message:  this.$i18n.t('i18n.FR_MSG00056'), trigger: 'blur' }
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
            const countData = await getUser({
                current: this.pageNum,
                size: this.pageSize
            });
            //表格数据
            this.tableData = countData.iPage.records;
            this.totalCount = countData.iPage.total;
            //角色树形节点图
            this.roleData = countData.roles;

            //初始化密码
            this.passwordInit = countData.passwordInit;
        },
        findPage(val){
           this.pageNum = val;
           this.initData();
        },
        
        //flag 1：编辑  2：新增
        userDialog(row,flag){
            if(flag == 1){
                this.title = this.$i18n.t('i18n.FR_MSG00008')
                this.form = JSON.parse(JSON.stringify(row));
                this.defaultcheckedData = row.hasRole;
                this.showFlag = true;
            }else{
                this.title = this.$i18n.t('i18n.FR_MSG00017')
                this.form = {status:"1",sex:"0",password:this.passwordInit};
                this.defaultcheckedData = [];
                this.showFlag = false;
            }
            this.editVisible = true;
        },

        // 保存编辑
        async saveEdit() {
            const res = await saveUser({...this.form});
            if(res.resultCode == 1){
                this.editVisible = false;
                this.$message({type: 'success', message: this.$i18n.t('i18n.FR_MSG00018') });
            }else if(res.resultCode == 0){
                this.$message({type: 'error', message: this.$i18n.t('i18n.FR_MSG00057') });
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
                    var array = this.$refs.tree.getCheckedKeys();
                    if(array.length == 0){
                        //请选择角色
                        this.$message({type: 'error', message: this.$i18n.t('i18n.FR_MSG00058') });
                        return false;
                    }
                    this.form.hasRole = array;
                    this.saveEdit();
                }else{
                    return false;
                }
            });
        },
        
        //删除菜单
        delUser(id){
            this.$confirm(this.$i18n.t('i18n.FR_MSG00059'), this.$i18n.t('i18n.FR_MSG00024'), {
                type: 'warning'
            }).then(() => {
                this.removeUser(id);
            }).catch(() => {});
        },
        //向后台发送请求删除当前用户
        async removeUser(id){
            const res = await delUser({'id':id});
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
        },
        handleAvatarSuccess(res, file) {
            this.from.headportrait = URL.createObjectURL(file.raw);
        },
        beforeAvatarUpload(file) {
            if (!(file.type === 'image/png' || file.type === 'image/jpg' || file.type === 'image/jpeg')) {
                this.$message.error(this.$i18n.t('i18n.FR_MSG00060'));
                return false;
            }
            const isLt2M = file.size / 1024 / 1024 < 2;
            if (!isLt2M) {
                this.$message.error(this.$i18n.t('i18n.FR_MSG00061'));
                return false;
            }
            return true;
        },
        uploadImg(f){
            this.showFlag = false;
            this.progressFlag = false;
            let formdata = new FormData()
            formdata.append('picFile', f.file);
            axios({
                url: '/api/system/manager/user/up',
                method: 'post',
                data: formdata,
                headers: {'Content-Type': 'multipart/form-data'},
                onUploadProgress: progressEvent => {
                    // progressEvent.loaded:已上传文件大小
                    // progressEvent.total:被上传文件的总大小
                    this.progressPercent = (progressEvent.loaded / progressEvent.total * 100)
                }
            }).then(res => {
                this.form.headportrait = res.data;
                this.showFlag = true;
                this.progressFlag = true;
                this.progressPercent = 0;
            })
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


.avatar-uploader .el-upload {
    width: 150px;
    height: 150px;
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 150px;
    height: 150px;
    line-height: 150px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>