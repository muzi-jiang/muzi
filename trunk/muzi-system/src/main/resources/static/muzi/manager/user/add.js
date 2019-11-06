$().ready(function() {
	getRoleTreeData();
    validateRule();
});

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#userFrom").validate({
        rules : {
            userName : {
                required : true,
                minlength : 2,
                maxlength : 20
            },
            name : {
                required : true,
                minlength : 2,
                maxlength : 20
            },
            tel : {
                required : true,
                minlength : 2,
                maxlength : 20
            }
        },
        messages : {
            userName : {
                required : icon + "请输入账号"
            },
            name : {
                required : icon + "请输入姓名"
            },
            tel : {
                required : icon + "请输入电话号码"
            }
        }
    })
}

$.validator.setDefaults({
    submitHandler : function() {
    	getAllSelectNodes();
        save();
    }
});

function save() {
	if(roleIds == '' || roleIds == null || roleIds == undefined){
		 parent.layer.msg("请选择角色");
		return false;
	}
	
	$('#roleIds').val(roleIds);
    $.ajax({
        cache : true,
        type : "POST",
        url : "/backstage/manager/user/insertUser",
        data : $('#userFrom').serialize(), // 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("网络超时");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg(data.msg);
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            } else {
                parent.layer.alert(data.msg);
            }

        }
    });
}




//获取角色树形节点
function getRoleTreeData() {
    $.ajax({
        type : "GET",
        url : "/backstage/manager/user/tree",
        success : function(roleTree) {
            loadRoleTree(roleTree);
        }
    });
}
function loadRoleTree(roleTree) {
    $('#roleTree').jstree({
        'core' : {
            'data' : roleTree
        },
        "checkbox" : {
            "three_state" : true,
        },
        "plugins" : [ "wholerow", "checkbox" ]
    });
}

var roleIds;
function getAllSelectNodes() {
    var ref = $('#roleTree').jstree(true); // 获得整个树
    roleIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组
    $("#roleTree").find(".jstree-undetermined").each(function(i, element) {
    	roleIds.push($(element).closest('.jstree-node').attr("id"));

    });
}























