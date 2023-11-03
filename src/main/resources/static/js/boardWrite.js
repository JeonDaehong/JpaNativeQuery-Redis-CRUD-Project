function goBack() {
    window.history.back();
}

$(document).ready(function() {
    $("#writeButton").click(function() {

        let titleVal = $("#title").val();
        let contentVal = $("#content").val();
        let userIdVal = $("#userId").val();

        if ( userIdVal === "" ) {
            alert("세션이 만료되었습니다. 로그인을 다시 해주시기 바랍니다.");
            return;
        }

        if ( titleVal === "" || contentVal === "") {
            alert("공란 없이 전부 입력해주시기 바랍니다.");
            return;
        }

        let userData = {
            title : titleVal,
            content : contentVal,
            userId : userIdVal
        };

        $.ajax({
            type : "POST", // 또는 다른 HTTP 메소드 사용
            url : "/board/write",
            data : userData,
            success : function(responseCode) {
                if (responseCode === "1000") {
                    alert("글 작성 성공");
                    location.href = "/boardListPage";
                } else if (responseCode === "-1000") {
                    alert("시스템 에러가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
                } else if ( responseCode === "-1002") {
                    alert("세션이 만료되었습니다. 다시 로그인해주시기 바랍니다.");
                    location.href = "/loginPage";
                }
            },
            error: function(xhr, status, error) {
                // 에러 처리
                alert.error("에러:", error);
            }
        });
    });
});