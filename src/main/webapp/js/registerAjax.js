// 表单校验
function checkUsername() {
    var username = $("#username").val();
    var reg_username = /^[a-zA-Z0-9_-]{4,16}$/;
    var flag = reg_username.test(username);
    if(flag) {
        $("#username").css("border","1px solid green");
    } else {
        $("#username").css("border","1px solid red");
    }
    return flag;
}

function checkPassword() {
    var password = $("#password").val();
    var reg_password = /^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,20})$/;
    var flag = reg_password.test(password);
    if(flag) {
        $("#password").css("border","1px solid green");
    } else {
        $("#password").css("border","1px solid red");
    }
    return flag;
}

function checkEmail() {
    var email = $("#email").val();
    var reg_email = /^\w+@\w+(\.\w+)+$/;
    var flag = reg_email.test(email);
    if(flag) {
        $("#email").css("border","1px solid green");
    } else {
        $("#email").css("border","1px solid red");
    }
    return flag;
}

function checkName() {
    var name = $("#name").val();
    var reg_name = /^[\u4E00-\u9FA5]{2,4}$/;
    var flag = reg_name.test(name);
    if(flag) {
        $("#name").css("border","1px solid green");
    } else {
        $("#name").css("border","1px solid red");
    }
    return flag;
}

function checkTelephone() {
    var telephone = $("#telephone").val();
    var reg_telephone = /^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\d{8}$/;
    var flag = reg_telephone.test(telephone);
    if(flag) {
        $("#telephone").css("border","1px solid green");
    } else {
        $("#telephone").css("border","1px solid red");
    }
    return flag;
}

function checkBirthday() {
    var birthday = $("#birthday").val();
    if(birthday == "") {
        return false;
    } else {
        return true;
    }
}

$(function () {
    // while click submit button, post request to register
    $("#registerForm").submit(function () {
        if(checkUsername() && checkPassword() && checkEmail() && checkName() && checkTelephone() && checkBirthday()){
            $.post("user/register",$(this).serialize(),function(data){
                if(data.flag) {
                    // register success redirect to ok page
                    location.href="register_ok.html"
                } else {
                    // register fail return error message;
                    $("#errorMsg").html(data.errorMsg);
                }
            });
        }
        // it will do nothing if the information is incomplete
        return false;
    });

    // add defocus events;
    $("#username").blur(checkUsername);
    $("#password").blur(checkPassword);
    $("#email").blur(checkEmail);
    $("#name").blur(checkName);
    $("#telephone").blur(checkTelephone);
});