var prefix = "/backstage/manager/user";


$(function() {

    load();

});
function load() {

    $('#user-manager').bootstrapTable({
        method : 'get', // 服务器数据的请求方式 get or get
        url : prefix + "/list", // 服务器数据的加载地址
        iconSize : 'outline',
        toolbar : '#exampleToolbar',
        striped : true, // 设置为true会有隔行变色效果
        dataType : "json", // 服务器返回的数据类型
        pagination : true, // 设置为true会在底部显示分页条
        singleSelect : false, // 设置为true将禁止多选
        pageSize : 10, // 如果设置了分页，每页数据条数
        pageNumber : 1, // 如果设置了分布，首页页码
        showColumns : false, // 是否显示内容下拉框（选择显示的列）
        sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者 "server"
        queryParams : function(params) {
            return {
                limit : params.limit,
                offset : params.offset,
                userName : $("#userName").val(),
                status : ($("#status option:selected")).val()
            };
        },
        columns : [
            {
                field : 'id',
                title : '编号',
                width : '70px',
                align : 'center'
            },
            {
                field : 'userName',
                title : '账号',
                width : '150px',
                align : 'center'
            },
            {
                field : 'name',
                title : '管理员姓名',
                width : '150px',
                align : 'center'
            },
            {
                field : 'tel',
                title : '联系电话',
                width : '120px',
                align : 'center',
            },
            {
                field : 'sex',
                title : '性别',
                width : '120px',
                align : 'center',
                formatter : function(value, row, index) {
                    if (value == '1') {
                        return '男';
                    }else if(value == '2'){
                        return '女';
                    }
                }
            },
            {
                field : 'status',
                title : '账号状态',
                width : '180px',
                align : 'center',
                formatter : function(value, row, index) {
                    if (value == '1') {
                        return '<span class="label label-primary">正常</span>';
                    }else{
                        return '<span class="label label-danger">禁用</span>';
                    }
                }
            },
            {
                title : '操作',
                field : 'id',
                width : '150px',
                align : 'center',
                formatter : function(value, row, index) {
                    var e = '<a class="btn btn-primary btn-sm ' + s_edit_h + '" href="#" mce_href="#" title="编辑"'
                        + 'onclick="edit(' + row.id + ')"><i class="fa fa-edit"></i></a> ';
                    var r = '<a class="btn btn-warning btn-sm ' + s_remove_h + '" href="#" mce_href="#" title="删除"'
                        + 'onclick="remove('+ row.id +')"><i class="fa fa-remove"></i></a> ';
                    var v = '<a class="btn btn-success btn-sm ' + s_resetPwd_h + '" href="#" title="重置密码"  mce_href="#" onclick="resetPwd(\''
						+ row.id
						+ '\')"><i class="fa fa-key"></i></a> ';
                    return e + r + v;
                }
            }
        ]
    });

}




function reLoad() {
    $('#user-manager').bootstrapTable('refresh');
}
function add() {
    layer.open({
        type : 2,
        title : '添加管理员',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ width='80%', height='80%'],
        content : prefix + "/toAddUser"
    });
}

function edit(id) {
    layer.open({
        type : 2,
        title : '修改管理员',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ width='80%', height='80%'],
        content : prefix + "/toEdit?id="+id
    });
}

function remove(id){
	layer.confirm('确定要删除选中的用户!!!', {
	    btn: ['确定', '取消']
	}, function () {
	    $.ajax({
	    	url : "/backstage/manager/user/deleteUser",
	        type: "post",
	        data: {
	            'id': id
	        },
	        success: function (data) {
	            if (data.code == 0) {
	                layer.msg(data.msg);
	                reLoad();
	            } else {
	                layer.msg(data.msg);
	            }
	        },
	        error : function(request) {
	            layer.msg("网络超时");
	        },
	    });
	});
}



function resetPwd(id){
	layer.confirm('确定重置该用户的密码!!!', {
	    btn: ['确定', '取消']
	}, function () {
	    $.ajax({
	    	url : "/backstage/manager/user/resetPwd",
	        type: "post",
	        data: {
	            'id': id
	        },
	        success: function (data) {
	            if (data.code == 0) {
	                layer.msg(data.msg + "; 初始密码为：123456");
	                reLoad();
	            } else {
	                layer.msg(data.msg);
	            }
	        },
	        error : function(request) {
	            layer.msg("网络超时");
	        },
	    });
	});
}



