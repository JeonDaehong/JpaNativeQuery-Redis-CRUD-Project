$(document).ready(function() {
    $("#loginButton").click(function() {

        let loginIdVal = $("#loginId").val();
        let passwordVal = $("#password").val();

        if ( loginIdVal === "" || passwordVal === "") {
            alert("공란 없이 전부 입력해주시기 바랍니다.");
            return;
        }

        let userData = {
            loginId : loginIdVal,
            password : passwordVal
        };

        $.ajax({
            type : "POST", // 또는 다른 HTTP 메소드 사용
            url : "/user/login",
            data : userData,
            success : function(responseCode) {
                if (responseCode === "1000") {
                    alert("로그인 성공");
                    location.href = "/";
                } else if (responseCode === "-1000") {
                    alert("시스템 에러가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
                    location.href = "/loginPage";
                } else if ( responseCode === "-1002") {
                    alert("로그인 에러가 발생했습니다. 아이디나 패스워드를 확인해주시기 바랍니다.");
                }
            },
            error: function(xhr, status, error) {
                // 에러 처리
                alert.error("에러:", error);
            }
        });
    });
});