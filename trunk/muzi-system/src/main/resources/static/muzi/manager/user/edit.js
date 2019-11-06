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
        url : "/backstage/manager/user/editUser",
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



function getRoleTreeData() {
	var userId = $('#userId').val();
	$.ajax({
		type : "GET",
		url : "/backstage/manager/user/editTree",
		data:{
			"userId":userId
		},
		success : function(data) {
			loadRoleTree(data);
		}
	});
}
function loadRoleTree(roleTree) {
	$('#roleTree').jstree({
		"plugins" : [ "wholerow", "checkbox" ],
		'core' : {
			'data' : roleTree
		},
		"checkbox" : {
		}
	});
	$('#roleTree').jstree('open_all');
}

var roleIds;
function getAllSelectNodes() {
	var ref = $('#roleTree').jstree(true); // 获得整个树
	roleIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组
	$("#roleTree").find(".jstree-undetermined").each(function(i, element) {
		roleIds.push($(element).closest('.jstree-node').attr("id"));
	});
	console.log(roleIds); 
}









