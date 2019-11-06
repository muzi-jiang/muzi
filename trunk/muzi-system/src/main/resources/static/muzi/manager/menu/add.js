var prefix = "/backstage/manager/menu"
$(function() {
    validateRule();
  //打开图标列表
    $("#ico-btn").click(function(){
    
        layer.open({
            type: 2,
			title:'图标列表',
            content: prefix+'/fontIcoList',
            area: ['480px', '90%'],
            success: function(layero, index){
              
            }
        });
    });
});

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#resourcesFrom").validate({
        rules : {
        	permissionName : {
                required : true
            }
        },
        messages : {
        	permissionName : {
                required : icon + "请输入菜单名"
            }
        }
    })
}

$.validator.setDefaults({
    submitHandler : function() {
        submit01();
    }
});

function submit01() {
    $.ajax({
        cache : true,
        type : "POST",
        url : prefix + "/save",
        data : $('#resourcesFrom').serialize(),
        dataType: "json",
        async : false,
        error : function(request) {
            layer.alert("Connection error");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg(data.msg);
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);

            } else {
                layer.alert(data.msg)
            }
        }
    });
}