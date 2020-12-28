$.validator.setDefaults({
    highlight: function(e) {
        $(e).closest(".form-group").removeClass("has-success").addClass("has-error")
    },
    success: function(e) {
        e.closest(".form-group").removeClass("has-error").addClass("has-success")
    },
    errorElement: "span",
    errorPlacement: function(e, r) {
        e.appendTo(r.is(":radio") || r.is(":checkbox") ? r.parent().parent().parent() : r.parent())
    },
    errorClass: "help-block m-b-none",
    validClass: "help-block m-b-none"
}),
$().ready(function() {
    var e = "<i class='fa fa-times-circle'></i> ";
    $("#myForm").validate({
        rules: {
            firstname: "required",
            lastname: "required",
            old_password: {
                required: !0,
                minlength: 5
            },
            password: {
                required: !0,
                minlength: 5
            },
            confirm_password: {
                required: !0,
                minlength: 5,
                equalTo: "#password"
            }
        },
        messages: {
        	old_password: {
                required: e + "请输入当前的密码",
                minlength: e + "密码必须5个字符以上"
            },
            password: {
                required: e + "请输入您的密码",
                minlength: e + "密码必须5个字符以上"
            },
            confirm_password: {
                required: e + "请再次输入密码",
                minlength: e + "密码必须5个字符以上",
                equalTo: e + "两次输入的密码不一致"
            }
        }
    });
  
    $('#myForm').on('submit', function() {
    
        $(this).ajaxSubmit({
            type: 'post',
	    	beforeSubmit:function(){
	    		
	    		return $("#myForm").valid();
	    		
	    	},
            success: function(data) { // data 保存提交后返回的数据，一般为 json 数据
                // 此处可对 data 作相关处理
            	
            	layer.msg(data.message);
            }
        });
        return false; // 阻止表单自动提交事件
    });
    
});