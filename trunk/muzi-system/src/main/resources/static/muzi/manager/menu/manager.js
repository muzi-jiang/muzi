var prefix = "/backstage/manager/menu"
$(document).ready(function () {
    load();
});
var load = function () {
    $('#exampleTable')
        .bootstrapTreeTable(
            {
                id: 'id',
                code: 'id',
                parentCode: 'permissionParentid',
                type: "GET", // 请求数据的ajax类型
                url: prefix + '/list', // 请求数据的ajax的url
                ajaxParams: {sort:'order_num'}, // 请求数据的ajax的data属性
                expandColumn: '1',// 在哪一列上面显示展开按钮
                striped: false, // 是否各行渐变色
                bordered: true, // 是否显示边框
                expandAll: false, // 是否全部展开
                // toolbar : '#exampleToolbar',
                columns: [
                    {
                        title: '编号',
                        field: 'id',
                        visible: false,
                        align: 'center',
                        valign: 'center',
                        width: '5%'
                    },
                    {
                        title: '菜单名称',
                        align: 'center',
                        valign: 'center',
                        field: 'permissionName',
                        width: '20%'
	                	
                    },

                    {
                        title: '类型',
                        field: 'permissionParentid',
                        align: 'center',
                        valign: 'center',
                        width : '10%',
                        formatter: function (item, index) {
                            if (item.permissionParentid === 0) {
                                return '<span class="label label-warning">目录</span>';
                            }else{
                                return '<span class="label label-success">菜单</span>';
                            }

                        }
                    },
                    {
                        title: '请求路径',
                        valign: 'center',
                        width : '30%',
                        field: 'permissionUrl'
                    },
                    
                    
                    {
                        title: 'ICON图标',
                        align: 'center',
                        valign: 'center',
                        width : '120px',
                        field: 'permissionIcon',
                        formatter : function(item, index){
                        	 return '<i class="'+item.permissionIcon+'" ></i>';
                        	 
                        }
                    },
                    
                    
                    
                    
                    {
                        field : 'status',
                        title : '菜单状态',
                        width : '90px',
                        align : 'center',
                        formatter : function(item, index) {
                            if (item.status == '1') {
                                return '<span class="label label-primary">正常</span>';
                            }else{
                                return '<span class="label label-danger">禁用</span>';
                            }
                        }
                    },
                    {
                        title: '操作',
                        field: 'id',
                        align : 'center',
                        valign: 'center',
                        formatter: function (item, index) {
                            var e = '<a class="btn btn-primary btn-sm '
                                + s_edit_h
                                + '" href="#" mce_href="#" title="编辑" onclick="edit(\''
                                + item.id
                                + '\')"><i class="fa fa-edit"></i></a> ';


                            if(item.permissionParentid == 0){
                                var p = '<a class="btn btn-primary btn-sm '
                                    + s_add_h
                                    + '" href="#" mce_href="#" title="添加下级" onclick="add(\''
                                    + item.id
                                    + '\')"><i class="fa fa-plus"></i></a> ';
                            }else{
                                var p ='';
                            }
                            var d = '<a class="btn btn-warning btn-sm '
                                + s_remove_h
                                + '" href="#" title="删除"  mce_href="#" onclick="remove('+ item.id +','+item.permissionParentid+')"><i class="fa fa-remove"></i></a> ';
                            return e + d + p;
                        }
                    }
                    ]
            });
}

function reLoad() {
    load();
}

function edit(id) {
    layer.open({
        type: 2,
        title: '菜单修改',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area : [ width='80%', height='80%'],
        content: prefix+"/toEdit?id=" + id // iframe的url
    });
}


function add(pid) {
    layer.open({
        type: 2,
        title: '增加菜单',
        maxmin: true,
        shadeClose: false, // 点击遮罩关闭层
        area : [ width='80%', height='80%'],
        content: prefix+"/toAdd?id=" + pid // iframe的url
    });
}


function remove(id,parentId) {
	layer.confirm(parentId == 0 ? '确定要删除选中的目录？删除目录后，所有的下级菜单都将被删除!!!':'确定要删除选中的菜单!!!', {
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














