//var menuTree;

var roleUrl = "/backstage/manager/role"


var menuIds;
$(function() {
    getMenuTreeData();
    validateRule();
});
$.validator.setDefaults({
    submitHandler : function() {
        getAllSelectNodes();
        save();
    }
});

function getAllSelectNodes() {
    var ref = $('#menuTree').jstree(true); // 获得整个树

    menuIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组
    $("#menuTree").find(".jstree-undetermined").each(function(i, element) {
        menuIds.push($(element).closest('.jstree-node').attr("id"));

    });
}
function getMenuTreeData() {
    $.ajax({
        type : "GET",
        url : roleUrl+"/tree",
        success : function(menuTree) {
            loadMenuTree(menuTree);
        }
    });
}
function loadMenuTree(menuTree) {
    $('#menuTree').jstree({
        'core' : {
            'data' : menuTree
        },
        "checkbox" : {
            "three_state" : true,
        },
        "plugins" : [ "wholerow", "checkbox" ]
    });
}

function save() {
	
	//判断是否选择菜单
	if(menuIds == '' || menuIds == null || menuIds ==undefined){
		parent.layer.msg("请选择菜单");
		return false;
	}
	
    $.ajax({
        cache : true,
        type : "POST",
        url : roleUrl+"/save",
        data : {
            "rolename":$("#rolename").val(),
            "remarks":$("#remarks").val(),
            "menuIds":menuIds.toString()
        },
        async : false,
        error : function(request) {
            alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg(data.msg);
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);
            } else {
                parent.layer.msg(data.msg);
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules : {
        	rolename : {
                required : true
            }
        },
        messages : {
        	rolename : {
                required : icon + "请输入角色名"
            }
        }
    });
}