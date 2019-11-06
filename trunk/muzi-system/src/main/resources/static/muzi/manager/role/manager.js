var prefix = "/backstage/manager/role";
$(function() {
    load();
});


function load() {
    $('#role-manager')
        .bootstrapTable(
            {
                method : 'get', // 服务器数据的请求方式 get or post
                url : prefix + "/list", // 服务器数据的加载地址
                iconSize : 'outline',
                toolbar : '#exampleToolbar',
                striped : true, // 设置为true会有隔行变色效果
                dataType : "json", // 服务器返回的数据类型
                pagination : true, // 设置为true会在底部显示分页条
                singleSelect : false, // 设置为true将禁止多选
                pageSize : 10, // 如果设置了分页，每页数据条数
                pageNumber : 1, // 如果设置了分布，首页页码
                search : false, // 是否显示搜索框
                showColumns : false, // 是否显示内容下拉框（选择显示的列）
                sidePagination : "server", // 设置在哪里进行分页，可选值为"client" 或者
                queryParams : function(params) {
                    return {
                        //说明：传入后台的参数包括offset开始索引，limit步长，sort排序列，order：desc或者,以及所有列的键值对
                        limit : params.limit,
                        offset : params.offset,
                        rolename : $("#rolename").val(),
                        status : ($("#status option:selected")).val()
                    };
                },
                columns : [
                    { // 列配置项
                        checkbox : true
                        // 列表中显示复选框
                    },
                    {
                        field : 'id', // 列字段名
                        title : '序号' // 列标题
                    },
                    {
                        field : 'rolename',
                        title : '角色名称'
                    },
                    {
                        field : 'remarks',
                        title : '备注'
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
                        field : 'roleId',
                        align : 'center',
                        formatter : function(value, row, index) {
                            var e = '<a class="btn btn-primary btn-sm '+s_edit_h+'" href="#" mce_href="#" title="编辑" onclick="roleEdit(\''
                                + row.id
                                + '\')"><i class="fa fa-edit"></i></a> ';
                            var d = '<a class="btn btn-warning btn-sm '+s_remove_h+'" href="#" title="删除"  mce_href="#" onclick="remove(\''
                                + row.id
                                + '\')"><i class="fa fa-remove"></i></a> ';
                            return e + d;
                        }
                    } ]
            });
}
function reLoad() {
    $('#role-manager').bootstrapTable('refresh');
}
function add() {
    // iframe层
    layer.open({
        type : 2,
        title : '添加角色',
        maxmin : true,
        shadeClose : false, // 点击遮罩关闭层
        area : [ width='80%', height='90%'],
        content : prefix+'/toaddRole' // iframe的url
    });
}
function roleEdit(id) {
    layer.open({
        type : 2,
        title : '角色修改',
        maxmin : true,
        shadeClose : true, // 点击遮罩关闭层
        area : [ width='80%', height='90%'],
        content : prefix + '/toEditRole?id=' + id // iframe的url
    });
}

/**
 * 角色删除
 * @param id
 * @param parentId
 */
function remove(id) {
	layer.confirm('确定要删除选中的角色!!!', {
        btn: ['确定', '取消']
    }, function () {
        $.ajax({
            url:prefix + "/remove",
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
            }
        });
    })
}





















