$().ready(function() {
    validateRule();
});

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#adminFrom").validate({
        rules : {
            userName : {
                required : true,
                minlength : 2,
                maxlength : 20
            },
            loginName : {
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
            loginName : {
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
        save();
    }
});

function save() {
    // $("#deviceOrgcode").val($("#organization").val());
    // $("#deviceOrgname").val($("#organization").find("option:selected").text());
    $.ajax({
        cache : true,
        type : "POST",
        url : "/backstage/manager/admin/insertAdmin.do",
        data : $('#adminFrom').serialize(), // 你的formid
        async : false,
        error : function(request) {
            parent.layer.alert("网络超时");
        },
        success : function(data) {
            if (data.code == 0) {
                parent.layer.msg("操作成功");
                parent.load();
                var index = parent.layer.getFrameIndex(window.name);
                parent.layer.close(index);
            } else {
                parent.layer.alert(data.msg)
            }

        }
    });
}