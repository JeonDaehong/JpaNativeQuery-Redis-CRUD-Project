$(document).ready(function() {
    $("#joinButton").click(function() {

        let loginIdVal = $("#loginId").val();
        let userNameVal = $("#userName").val();
        let passwordVal = $("#password").val();

        if ( loginIdVal === "" || userNameVal === "" || passwordVal === "") {
            alert("공란 없이 전부 입력해주시기 바랍니다.");
            return;
        }

        let userData = {
            loginId : loginIdVal,
            userName :userNameVal,
            password : passwordVal
        };

        $.ajax({
            type : "POST", // 또는 다른 HTTP 메소드 사용
            url : "/user/join",
            data : userData,
            success : function(responseCode) {
                if (responseCode === "1000") {
                    alert("회원가입이 성공적으로 처리되었습니다. 로그인 해주시기 바랍니다.");
                    location.href = "/loginPage";
                } else if (responseCode === "-1000") {
                    alert("시스템 에러가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
                    location.href = "/joinPage";
                } else if ( responseCode === "-1001") {
                    alert("이미 존재하는 아이디입니다. 아이디를 바꿔주시기 바랍니다.");
                }
            },
            error: function(xhr, status, error) {
                // 에러 처리
                alert.error("에러:", error);
            }
        });
    });
});