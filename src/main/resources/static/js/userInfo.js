$(document).ready(function() {

    $("#updateButton").click(function() {

        let loginIdVal = $("#loginId").val();
        let userNameVal = $("#userName").val();
        let userIdVal = $("#userId").val();

        if ( loginIdVal === "" || userNameVal === "") {
            alert("공란 없이 전부 입력해주시기 바랍니다.");
            return;
        }

        let userData = {
            userId : userIdVal,
            loginId : loginIdVal,
            userName :userNameVal
        };

        $.ajax({
            type : "POST", // 또는 다른 HTTP 메소드 사용
            url : "/user/update",
            data : userData,
            success : function(responseCode) {
                if (responseCode === "1000") {
                    alert("회원정보가 정상적으로 수정되었습니다.");
                    location.href = "/userInfoPage";
                } else if (responseCode === "-1000") {
                    alert("시스템 에러가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
                    location.href = "/userInfoPage";
                }
            },
            error: function(xhr, status, error) {
                // 에러 처리
                alert.error("에러:", error);
            }
        });
    });

    $("#deleteButton").click(function() {

        if ( !confirm("만약 작성한 게시글이 있다면 삭제됩니다. 정말로 회원 탈퇴를 하시겠습니까?") ) return;

        let loginIdVal = $("#loginId").val();
        let userIdVal = $("#userId").val();

        let userData = {
            userId : userIdVal,
            loginId : loginIdVal
        };

        $.ajax({
            type : "POST", // 또는 다른 HTTP 메소드 사용
            url : "/user/delete",
            data : userData,
            success : function(responseCode) {
                if (responseCode === "1000") {
                    alert("회원탈퇴가 완료되었습니다.");
                    location.href = "/";
                } else if (responseCode === "-1000") {
                    alert("시스템 에러가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
                    location.href = "/userInfoPage";
                }
            },
            error: function(xhr, status, error) {
                // 에러 처리
                alert.error("에러:", error);
            }
        });
    });
});