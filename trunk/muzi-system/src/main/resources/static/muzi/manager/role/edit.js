var prefix = "/backstage/manager/role";

var menuIds;
$(function() {
	//初始化
	//load();
	
	validateRule();
	getMenuTreeData();
});
$.validator.setDefaults({
	submitHandler : function() {
		getAllSelectNodes();
		update();
	}
});
/**
 * 初始化：加载表单的信息
 * @returns
 */
//function load(){
//	$.ajax({
//		url:basePath+"system/role/edit/"+getUrlParam("id"),
//		type:'get',
//		success:function(r){
//			if (r.code == 0) {
//				console.log(r);
//				$("#roleId").val(r.role.roleId);
//				$("#roleName").val(r.role.roleName);
//				$("#remark").val(r.role.remark);
//				$("#deptId").val(r.role.deptId);
//				getMenuTreeData();
//             } else {
//                 layer.msg(r.msg);
//             }
//		},
//		error:function(r){
//			
//		}
//	});
//}
function loadMenuTree(menuTree) {
	$('#menuTree').jstree({
		"plugins" : [ "wholerow", "checkbox" ],
		'core' : {
			'data' : menuTree
		},
		"checkbox" : {
			//"keep_selected_style" : false,
			//"undetermined" : true
			//"three_state" : false,
			//"cascade" : ' up'
		}
	});
	$('#menuTree').jstree('open_all');
}
function getAllSelectNodes() {
	var ref = $('#menuTree').jstree(true); // 获得整个树
	menuIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组
	$("#menuTree").find(".jstree-undetermined").each(function(i, element) {
		menuIds.push($(element).closest('.jstree-node').attr("id"));
	});
	console.log(menuIds); 
}
function getMenuTreeData() {
	var roleId = $('#roleId').val();
	$.ajax({
		type : "GET",
		url : prefix + "/editTree",
		data:{
			"roleId":roleId
		},
		success : function(data) {
			loadMenuTree(data);
		}
	});
}
function update() {
	
	//判断是否选择菜单
	if(menuIds == '' || menuIds == null || menuIds ==undefined){
		parent.layer.msg("请选择菜单");
		return false;
	}
	
	$('#menuIds').val(menuIds);
	var role = $('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : prefix+"/update",
		data : role, // 你的formid
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(r) {
			if (r.code == 0) {
				parent.layer.msg(r.msg);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.msg(r.msg);
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